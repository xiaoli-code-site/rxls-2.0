package cn.rx.system.service.impl;

import cn.rx.common.dto.admin.sysExcel.SysExcelUserDTO;
import cn.rx.common.dto.admin.sysUser.SysUserAddDTO;
import cn.rx.common.dto.admin.sysUser.SysUserPageDTO;
import cn.rx.common.dto.admin.sysUser.SysUserUpdateDTO;
import cn.rx.common.enums.CommonStateEnum;
import cn.rx.common.enums.R;
import cn.rx.common.enums.RoleEnum;
import cn.rx.common.util.*;
import cn.rx.common.vo.admin.sysRole.SysRoleVO;
import cn.rx.common.vo.admin.sysUser.StatisticsVO;
import cn.rx.common.vo.admin.sysUser.SysUserPageVO;
import cn.rx.common.vo.admin.sysUser.SysUserVO;
import cn.rx.core.exception.BusinessException;
import cn.rx.db.entity.SysRole;
import cn.rx.db.entity.SysRoleUser;
import cn.rx.db.entity.SysUser;
import cn.rx.db.mapper.SysRoleMapper;
import cn.rx.db.mapper.SysRoleUserMapper;
import cn.rx.db.mapper.SysUserMapper;
import cn.rx.system.excelObj.ExcelUser;
import cn.rx.system.service.SysExcelService;
import cn.rx.system.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 用户表(SysUser)表服务实现类
 */
@Service("userService")
public class SysUserServiceImplSys extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService, SysExcelService<SysExcelUserDTO> {

    private final SysUserMapper sysUserMapper;


    private final SysRoleMapper sysRoleMapper;

    private final SysRoleUserMapper sysRoleUserMapper;

    public SysUserServiceImplSys(SysUserMapper sysUserMapper, SysRoleMapper sysRoleMapper, SysRoleUserMapper sysRoleUserMapper) {
        this.sysUserMapper = sysUserMapper;
        this.sysRoleUserMapper = sysRoleUserMapper;
        this.sysRoleMapper = sysRoleMapper;
    }


    /**
     * 新增用户
     *
     * @param dto 参数对象
     */
    @Override
    @Transactional
    public void addUser(SysUserAddDTO dto) {

        if (!QueryUtil.validatePhone(dto.getPhone())) {
            throw new BusinessException(R.ERROR_PHONE_FORMAT);
        }

        if (!QueryUtil.validateName(dto.getNickname())) {
            throw new BusinessException(R.ERROR_NAME_FORMAT);
        }

        //检查角色id合法性
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysRole::getRoleId, dto.getRoleIds());
        List<SysRole> sysRoles = sysRoleMapper.selectList(queryWrapper);
        if (Objects.isNull(sysRoles) || sysRoles.size() < 1) {
            throw new BusinessException(R.ERROR_PERMISSION);
        }
        long count = sysRoles.stream().filter(r -> r.getRoleValue() >= RoleEnum.ADMIN_VALUE.value).count();
        if (count > 0) {
            throw new BusinessException(R.ERROR_PERMISSION);
        }

        //检查手机号是否已被使用
        LambdaQueryWrapper<SysUser> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.eq(SysUser::getPhone, dto.getPhone());
        if (sysUserMapper.selectList(userWrapper).size() > 0) {
            throw new BusinessException(R.ERROR_PHONE_USED);
        }

        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(dto, sysUser);
        sysUser.setState(CommonStateEnum.OK.code);

        if (sysUserMapper.insert(sysUser) < 1) {
            throw new BusinessException(R.ERROR_ENROLL);
        }

        if (sysRoleUserMapper.insertByUserId(sysUser.getUserId(), dto.getRoleIds()) < 1) {
            throw new BusinessException(R.ERROR_ADD);
        }
    }

    /**
     * 分页查询用户信息
     *
     * @param dto 参数对象
     * @return 数据列表
     */
    @Override
    public SysUserPageVO selectPage(SysUserPageDTO dto) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(dto, sysUser);
        dto.setPage((dto.getPage() - 1) * dto.getSize());
        List<SysUserVO> dataList = sysUserMapper.selectPageWithRoles(sysUser, dto.getPage(), dto.getSize(), dto.getRole());
        Long total = sysUserMapper.countPageWithRoles(sysUser, dto.getRole());
        SysUserPageVO result = new SysUserPageVO();
        result.setDataList(dataList);
        result.setTotal(total);
        return result;
    }

    /**
     * 根据id列表删除
     *
     * @param idList id列表
     * @return 成功数
     */
    @Override
    @Transactional
    public Integer deleteInIdList(List<Long> idList) {
        if (idList.size() < 1) {
            return 0;
        }

        LambdaQueryWrapper<SysRoleUser> roleUserWrapper = new LambdaQueryWrapper<>();
        roleUserWrapper.in(SysRoleUser::getUserId, idList);
        if (sysRoleUserMapper.delete(roleUserWrapper) < 1) {
            throw new BusinessException(R.ERROR_DELETE);
        }

        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(SysUser::getUserId, idList).ne(SysUser::getDeleted, CommonStateEnum.ERROR.code);
        SysUser sysUser = new SysUser();
        sysUser.setDeleted(CommonStateEnum.OK.code);
        int delete = sysUserMapper.update(sysUser, wrapper);
        if (delete < 1) {
            throw new BusinessException(R.ERROR_DELETE);
        }
        return delete;
    }

    /**
     * 变更用户信息
     *
     * @param dto 参数对象
     */
    @Override
    @Transactional
    public void updateUser(SysUserUpdateDTO dto) {
        Optional.ofNullable(dto.getPhone()).ifPresent(s -> {
            if (!QueryUtil.validatePhone(dto.getPhone())) {
                throw new BusinessException(R.ERROR_PHONE_FORMAT);
            }

        });
        Optional.ofNullable(dto.getNickname()).ifPresent(s -> {
            if (!QueryUtil.validateName(dto.getNickname())) {
                throw new BusinessException(R.ERROR_NAME_FORMAT);
            }
        });

        if (dto.getRoleIds().size() > 0) {
            //检查角色id合法性
            LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(SysRole::getRoleId, dto.getRoleIds()).select(SysRole::getRoleValue, SysRole::getRoleId);
            List<SysRole> sysRoles = sysRoleMapper.selectList(queryWrapper);
            if (Objects.isNull(sysRoles) || sysRoles.size() < 1) {
                throw new BusinessException(R.ERROR_PERMISSION);
            }
            long count = sysRoles.stream().filter(r -> r.getRoleValue() >= RoleEnum.ADMIN_VALUE.value).count();
            if (count > 0) {
                throw new BusinessException(R.ERROR_PERMISSION);
            }
            setUserRole(dto.getUserId(), sysRoles);
        }


        LambdaUpdateWrapper<SysUser> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysUser::getUserId, dto.getUserId()).eq(SysUser::getDeleted, CommonStateEnum.ERROR.code);
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(dto, sysUser);
        if (sysUserMapper.update(sysUser, wrapper) < 1) {
            throw new BusinessException(R.ERROR_UPDATE);
        }
    }


    /**
     * 角色设置
     *
     * @param sysRoles 角色俩表
     * @param uid      用户id
     */
    public void setUserRole(Long uid, List<SysRole> sysRoles) {
        List<Integer> list = sysRoles.stream().map(SysRole::getRoleId).collect(Collectors.toList());
        LambdaUpdateWrapper<SysRoleUser> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SysRoleUser::getUserId, uid);
        sysRoleUserMapper.delete(wrapper);
        if (sysRoleUserMapper.insertByUserId(uid, list) == 0) {
            throw new BusinessException(R.ERROR_AUTHORIZED);
        }
    }

    /**
     * 导出报表
     *
     * @param excelObj 导出对象
     * @param response 网络请求对象
     */
    @Override
    public void export(SysExcelUserDTO excelObj, HttpServletResponse response) throws IOException {
        //需要导出数据列表
        List<List<ExcelUser>> dataList = new ArrayList<>();
        //根据id查询
        if (Objects.nonNull(excelObj.getIdList()) && excelObj.getIdList().size() > 0) {
            LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(SysUser::getUserId, excelObj.getIdList());
            List<SysUserVO> users = sysUserMapper.selectInIds(excelObj.getIdList());
            List<ExcelUser> list = new ArrayList<>();
            users.forEach(s -> {
                ExcelUser user = new ExcelUser();
                BeanUtils.copyProperties(s, user);
                List<String> roleNames = s.getRoles().stream().map(SysRoleVO::getRoleName).collect(Collectors.toList());
                if (roleNames.size() > 0) {
                    user.setRole(String.join(",", roleNames));
                }
                list.add(user);
            });

            dataList.add(list);
        } else {
            SysUser sysUser = new SysUser();
            BeanUtils.copyProperties(excelObj.getQuery(), sysUser);
            for (int i = 0; i < excelObj.getPageQuery().getPage(); i++) {
                int page = (((i + 1) - 1) * excelObj.getPageQuery().getSize());
                List<SysUserVO> list = sysUserMapper.selectPageWithRoles(sysUser, page, excelObj.getPageQuery().getSize(), excelObj.getQuery().getRole());
                List<ExcelUser> lists = new ArrayList<>();
                list.forEach(s -> {
                    ExcelUser data = new ExcelUser();
                    BeanUtils.copyProperties(s, data);
                    List<String> roleNames = s.getRoles().stream().map(SysRoleVO::getRoleName).collect(Collectors.toList());
                    if (roleNames.size() > 0) {
                        data.setRole(String.join(",", roleNames));
                    }
                    lists.add(data);
                });
                dataList.add(lists);
            }

        }
        ExcelUtil.export(response, excelObj.getFieldList(), dataList, ExcelUser.class);

    }

    /**
     * 检查用户
     *
     * @param uid    用户id
     * @param roleId 角色id
     */
    @Override
    public Boolean checkUser(Long uid, Integer roleId) {
        SysUser sysUser = sysUserMapper.selectByUserRole(uid, roleId);
        return Objects.nonNull(sysUser);
    }

    /**
     * 用户统计
     */
    @Override
    public StatisticsVO statistics() {
        StatisticsVO data = new StatisticsVO();
        data.setNum(sysUserMapper.statistics());
        return data;
    }

}

