package cn.rx.system.service.impl;


import cn.rx.common.constant.HeaderConstant;
import cn.rx.common.dto.admin.sysOplog.SysOplogPageDTO;
import cn.rx.common.enums.CommonStateEnum;
import cn.rx.common.enums.R;
import cn.rx.common.enums.oplog.LogLeve;
import cn.rx.common.enums.oplog.LogModel;
import cn.rx.common.util.HttpUtil;
import cn.rx.common.util.IpUtil;
import cn.rx.common.vo.admin.sysOplog.SysOplogPageVO;
import cn.rx.common.vo.admin.sysOplog.SysOplogVO;
import cn.rx.core.exception.BusinessException;
import cn.rx.db.entity.SysOplog;
import cn.rx.db.mapper.SysOplogMapper;
import cn.rx.system.service.SysOplogService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.dreamlu.mica.ip2region.core.Ip2regionSearcher;
import org.springframework.beans.BeanUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * 操作日志(SysOplog)表服务实现类
 */
@Service("oplogService")
public class SysOplogServiceImpl extends ServiceImpl<SysOplogMapper, SysOplog> implements SysOplogService {



    private final MessageSource messageSource;

    private final SysOplogMapper sysOplogMapper;

    private final Ip2regionSearcher ip2regionSearcher;

    SysOplogServiceImpl(SysOplogMapper sysOplogMapper,MessageSource messageSource,Ip2regionSearcher ip2regionSearcher) {
        this.sysOplogMapper = sysOplogMapper;
        this.ip2regionSearcher = ip2regionSearcher;
        this.messageSource = messageSource;
    }


    /**
     * 登录日志
     * @param userId 用户id
     * @param state 状态
     * @param description 描述信息
     * @param msg 返回信息
     */
    @Override
    public void login(Long userId, CommonStateEnum state,String description,String msg) {
        String ipAddress = IpUtil.getIpAddress(HttpUtil.getHttpInfo());
        String address = ip2regionSearcher.getAddress(ipAddress);
        SysOplog sysOplog = new SysOplog();
        sysOplog.setState(state.code);
        sysOplog.setResult(handlerMsg(msg));
        sysOplog.setIp(ipAddress);
        sysOplog.setAddress(address);
        sysOplog.setDescription(description);
        sysOplog.setModel(LogModel.SECURE.value);
        sysOplog.setCategory(LogLeve.CRITICAL.value);
        sysOplog.setUserId(userId);
        sysOplogMapper.insert(sysOplog);
    }






    /**
     * 分页查询日志信息(sysAdmin)
     *
     * @param dto 参数对象
     * @return 数据列表
     */
    @Override
    public SysOplogPageVO selectPage(SysOplogPageDTO dto) {
        SysOplogPageVO result = new SysOplogPageVO();
        long l = sysOplogMapper.countOplogPage(dto.getLeve());
        if (l == 0) {
            return result;
        }
        dto.setPage((dto.getPage() - 1) * dto.getSize());
        List<SysOplogVO> list = sysOplogMapper.selectOplogPage(dto.getPage(), dto.getSize(),dto.getLeve());
        result.setTotal(l);
        list.forEach(s -> {
            SysOplogVO info = new SysOplogVO();
            BeanUtils.copyProperties(s, info);
            result.getDataList().add(info);
        });
        return result;
    }

    /**
     * 根据id列表删除(sysAdmin)
     *
     * @param idList id列表
     * @return 成功数
     */
    @Override
    public Integer deleteInIdList(List<Long> idList) {
        if (idList.size() < 1) {
            return 0;
        }
        LambdaQueryWrapper<SysOplog> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(SysOplog::getId, idList);
        int delete = sysOplogMapper.delete(wrapper);
        if (delete < 1) {
            throw new BusinessException(R.ERROR_DELETE);
        }
        return delete;
    }


    public static String getLocal() {
        String lang = HttpUtil.getHttpInfo().getHeader(HeaderConstant.LANGUAGE);
        if (Objects.nonNull(lang)) {
            return lang;
        }
        return "zh";
    }

    public String handlerMsg(String msg) {
        try {
            return messageSource.getMessage(msg, null, Locale.forLanguageTag(getLocal()));
        } catch (Exception e) {
            e.printStackTrace();
            return msg;
        }
    }

}

