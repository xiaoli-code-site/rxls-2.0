package cn.rx.system.service.impl;

import cn.rx.common.constant.JwtConstant;
import cn.rx.common.dto.admin.sysAdmin.SysAdminAddDTO;
import cn.rx.common.dto.admin.sysAdmin.SysAdminPageDTO;
import cn.rx.common.dto.admin.sysAdmin.SysAdminUpdateDTO;
import cn.rx.common.dto.admin.sysExcel.SysSysExcelAdminDTO;
import cn.rx.common.enums.CommonStateEnum;
import cn.rx.common.enums.R;
import cn.rx.common.enums.RoleEnum;
import cn.rx.common.util.*;
import cn.rx.common.vo.admin.sysAdmin.SysAdminPageVO;
import cn.rx.common.vo.admin.sysAdmin.SysAdminVO;
import cn.rx.common.vo.admin.sysRole.SysRoleVO;
import cn.rx.core.exception.BusinessException;
import cn.rx.db.entity.*;
import cn.rx.db.mapper.*;
import cn.rx.system.excelObj.ExcelAdmin;
import cn.rx.system.service.SysAdminService;
import cn.rx.system.service.SysExcelService;
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
 * 角色用户关联表(sysAdmin)表服务实现类
 */
@Service("adminService")
public class SysAdminServiceImplSys extends ServiceImpl<SysAdminMapper, SysAdmin> implements SysAdminService, SysExcelService<SysSysExcelAdminDTO> {


    private final SysAdminMapper sysAdminMapper;
    private final SysRoleMapper sysRoleMapper;
    private final SysUserMapper sysUserMapper;
    private final SysRoleUserMapper sysRoleUserMapper;

    SysAdminServiceImplSys(SysAdminMapper sysAdminMapper, SysRoleUserMapper sysRoleUserMapper, SysRoleMapper sysRoleMapper, SysUserMapper sysUserMapper) {
        this.sysAdminMapper = sysAdminMapper;
        this.sysUserMapper = sysUserMapper;
        this.sysRoleMapper = sysRoleMapper;
        this.sysRoleUserMapper = sysRoleUserMapper;
    }

    /**
     * 新增管理员
     *
     * @param dto 参数对象
     */
    @Override
    @Transactional
    public void addAdmin(SysAdminAddDTO dto) {

        if (!QueryUtil.validatePhone(dto.getPhone())) {
            throw new BusinessException(R.ERROR_PHONE_FORMAT);
        }

        if (!QueryUtil.validateName(dto.getNickname())) {
            throw new BusinessException(R.ERROR_NAME_FORMAT);
        }
        if (!QueryUtil.validatePassword(dto.getPassword())) {
            throw new BusinessException(R.ERROR_PASSWORD_FORMAT);
        }
        if (!QueryUtil.validateAccount(dto.getUsername())) {
            throw new BusinessException(R.ERROR_ACCOUNT_FORMAT);
        }


        //检查手机号是否已被使用
        LambdaQueryWrapper<SysUser> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.eq(SysUser::getPhone, dto.getPhone());
        if (sysUserMapper.selectList(userWrapper).size() > 0) {
            throw new BusinessException(R.ERROR_PHONE_USED);
        }

        //检查账户是否已存在
        LambdaQueryWrapper<SysAdmin> adminWrapper = new LambdaQueryWrapper<>();
        adminWrapper.eq(SysAdmin::getUsername, dto.getUsername());
        if (sysAdminMapper.selectList(adminWrapper).size() > 0) {
            throw new BusinessException(R.ERROR_ACCOUNT_REPEAT);
        }

        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(dto, sysUser);
        sysUser.setState(CommonStateEnum.OK.code);
        if (sysUserMapper.insert(sysUser) < 1) {
            throw new BusinessException(R.ERROR_ENROLL);
        }

        SysAdmin sysAdmin = new SysAdmin();
        sysAdmin.setUserId(sysUser.getUserId());
        sysAdmin.setUsername(dto.getUsername());
        sysAdmin.setPassword(BcryptUtil.encrypt(dto.getPassword()));
        if (sysAdminMapper.insert(sysAdmin) < 1) {
            throw new BusinessException(R.ERROR_ENROLL);
        }

        SysRoleUser sysRoleUser = new SysRoleUser();
        sysRoleUser.setUserId(sysUser.getUserId());
        LambdaQueryWrapper<SysRole> roleWrapper = new LambdaQueryWrapper<>();
        roleWrapper.in(SysRole::getRoleId, dto.getRoleIds()).select(SysRole::getRoleId);
        List<SysRole> sysRoles = sysRoleMapper.selectList(roleWrapper);
        setUserRole(sysUser.getUserId(), sysRoles);
        if (sysRoleUserMapper.insert(sysRoleUser) < 1) {
            throw new BusinessException(R.ERROR_ENROLL);
        }
    }

    /**
     * 分页查询管理员信息(sysAdmin)
     *
     * @param dto 参数对象
     * @return 数据列表
     */
    @Override
    public SysAdminPageVO selectPage(SysAdminPageDTO dto) {
        SysAdminPageVO result = new SysAdminPageVO();
        SysUser admin = new SysUser();
        BeanUtils.copyProperties(dto, admin);
        dto.setPage(((dto.getPage() - 1) * dto.getSize()));
        List<SysAdminVO> sysAdminVO = sysAdminMapper.selectPageAdmin(admin, dto.getUsername(), dto.getPage(), dto.getSize(), dto.getRoleId());
        Long total = sysAdminMapper.selectCountPageAdmin(admin, dto.getUsername(), dto.getRoleId());
        result.setTotal(total);
        result.setDataList(sysAdminVO);
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

        //获取用户信息
        String strRole = String.valueOf(HttpUtil.getUserLoginInfo().get(JwtConstant.ROLE));
        if (Objects.isNull(strRole) || "".equals(strRole)) {
            throw new BusinessException(R.ERROR_AUTHORIZED);
        }


        LambdaQueryWrapper<SysUser> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.in(SysUser::getUserId, idList).ne(SysUser::getDeleted,CommonStateEnum.ERROR.code);
        SysUser sysUser = new SysUser();
        sysUser.setDeleted(CommonStateEnum.OK.code);
        if (sysUserMapper.update(sysUser,userWrapper) < 1) {
            throw new BusinessException(R.ERROR_DELETE);
        }

        LambdaQueryWrapper<SysRoleUser> roleUserWrapper = new LambdaQueryWrapper<>();
        roleUserWrapper.in(SysRoleUser::getUserId, idList);
        if (sysRoleUserMapper.delete(roleUserWrapper) < 1) {
            throw new BusinessException(R.ERROR_DELETE);
        }

        LambdaQueryWrapper<SysAdmin> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(SysAdmin::getUserId, idList).ne(SysAdmin::getDeleted,CommonStateEnum.ERROR.code);
        SysAdmin sysAdmin = new SysAdmin();
        sysAdmin.setDeleted(CommonStateEnum.OK.code);
        int delete = sysAdminMapper.update(sysAdmin,wrapper);
        if (delete < 1) {
            throw new BusinessException(R.ERROR_DELETE);
        }
        return delete;
    }

    /**
     * 变更管理员信息
     *
     * @param dto 参数对象
     */
    @Override
    @Transactional
    public void updateAdmin(SysAdminUpdateDTO dto) {
        //获取用户信息
        String uid = String.valueOf(HttpUtil.getUserLoginInfo().getSubject());

        //如果更新的是自己的信息
        if (dto.getUserId().equals(Long.valueOf(uid))) {
            updateInfo(dto);
            return;
        }

        Optional.ofNullable(dto.getNickname()).ifPresent(s -> {
            if (!QueryUtil.validateName(dto.getNickname())) {
                throw new BusinessException(R.ERROR_NAME_FORMAT);
            }
        });
        Optional.ofNullable(dto.getPhone()).ifPresent(s -> {
            if (!QueryUtil.validatePhone(dto.getPhone())) {
                throw new BusinessException(R.ERROR_PHONE_FORMAT);
            }
        });

        Optional.ofNullable(dto.getPassword()).ifPresent(s -> {
            if (!QueryUtil.validatePassword(dto.getPassword())) {
                throw new BusinessException(R.ERROR_PASSWORD_FORMAT);
            }
            LambdaUpdateWrapper<SysAdmin> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(SysAdmin::getDeleted,CommonStateEnum.ERROR.code).eq(SysAdmin::getUserId, dto.getUserId()).set(SysAdmin::getPassword, BcryptUtil.encrypt(dto.getPassword()));
            if (sysAdminMapper.update(null, wrapper) < 1) {
                throw new BusinessException(R.ERROR_UPDATE);
            }
        });



        if (dto.getRoleIds().size() > 0) {
            //检查角色id合法性
            LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(SysRole::getRoleId, dto.getRoleIds()).select(SysRole::getRoleValue, SysRole::getRoleId).eq(SysRole::getDeleted,CommonStateEnum.ERROR.code);
            List<SysRole> sysRoles = sysRoleMapper.selectList(queryWrapper);
            if (sysRoles.size() == 0 || sysRoles.size() != dto.getRoleIds().size()) {
                throw new BusinessException(R.ERROR_PERMISSION);
            }
            setUserRole(dto.getUserId(), sysRoles);
        }




        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(dto, sysUser);
        LambdaUpdateWrapper<SysUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysUser::getUserId, dto.getUserId());
        if (sysUserMapper.update(sysUser, updateWrapper) < 1) {
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
     * 更新管理员本人信息
     *
     * @param dto 参数对象
     */
    @Transactional
    @Override
    public void updateInfo(SysAdminUpdateDTO dto) {
        LambdaUpdateWrapper<SysUser> userWrapper = new LambdaUpdateWrapper<>();
        userWrapper.eq(SysUser::getUserId, dto.getUserId()).eq(SysUser::getDeleted,CommonStateEnum.ERROR.code);
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(dto, sysUser);
        if (sysUserMapper.update(sysUser, userWrapper) < 1) {
            throw new BusinessException(R.ERROR_UPDATE);
        }

        Optional.ofNullable(dto.getNickname()).ifPresent(s -> {
            if (!QueryUtil.validateName(dto.getNickname())) {
                throw new BusinessException(R.ERROR_NAME_FORMAT);
            }
        });
        Optional.ofNullable(dto.getPhone()).ifPresent(s -> {
            if (!QueryUtil.validatePhone(dto.getPhone())) {
                throw new BusinessException(R.ERROR_PHONE_FORMAT);
            }
        });

        Optional.ofNullable(dto.getPassword()).ifPresent(s -> {
            if (!QueryUtil.validatePassword(dto.getPassword())) {
                throw new BusinessException(R.ERROR_PASSWORD_FORMAT);
            }
            LambdaUpdateWrapper<SysAdmin> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(SysAdmin::getDeleted,CommonStateEnum.ERROR.code).eq(SysAdmin::getUserId, dto.getUserId()).set(SysAdmin::getPassword, BcryptUtil.encrypt(dto.getPassword()));
            if (sysAdminMapper.update(null, wrapper) < 1) {
                throw new BusinessException(R.ERROR_UPDATE);
            }
        });

    }


    /**
     * 导出报表
     *
     * @param excelObj 导出对象
     * @param response 网络请求对象
     */
    @Override
    public void export(SysSysExcelAdminDTO excelObj, HttpServletResponse response) throws IOException {

        //需要导出数据列表
        List<List<ExcelAdmin>> dataList = new ArrayList<>();

        //根据id查询
        if (Objects.nonNull(excelObj.getIdList()) && excelObj.getIdList().size() > 0) {
            LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(SysUser::getUserId, excelObj.getIdList());
            List<SysAdminVO> users = sysAdminMapper.selectInIds(excelObj.getIdList());
            List<ExcelAdmin> list = new ArrayList<>();
            users.forEach(s -> {
                ExcelAdmin user = new ExcelAdmin();
                BeanUtils.copyProperties(s, user);
                List<String> roleNames = s.getRoles().stream().map(SysRoleVO::getRoleName).collect(Collectors.toList());
                if(roleNames.size() > 0){
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
                List<SysAdminVO> list = sysAdminMapper.selectPageAdmin(sysUser, excelObj.getQuery().getUsername(), page, excelObj.getPageQuery().getSize(), excelObj.getQuery().getRoleId());
                List<ExcelAdmin> lists = new ArrayList<>();
                list.forEach(s -> {
                    ExcelAdmin data = new ExcelAdmin();
                    BeanUtils.copyProperties(s, data);
                    List<String> roleNames = s.getRoles().stream().map(SysRoleVO::getRoleName).collect(Collectors.toList());
                    if(roleNames.size() > 0){
                        data.setRole(String.join(",", roleNames));
                    }
                    lists.add(data);
                });
                dataList.add(lists);
            }

        }
       ExcelUtil.export(response, excelObj.getFieldList(), dataList, ExcelAdmin.class);

    }
}

