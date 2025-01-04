package cn.rx.boot.controller;

import cn.rx.common.annotations.Oplog;
import cn.rx.common.enums.RoleEnum;
import cn.rx.common.enums.oplog.LogLeve;
import cn.rx.common.enums.oplog.LogModel;
import cn.rx.common.util.JwtUtil;
import cn.rx.common.util.server.ServerInfoUtil;
import cn.rx.system.service.SysUserService;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 系统信息
 */
@RestController
@RequestMapping("sys")
@Slf4j
public class SysController {

    @Resource
    private SysUserService sysUserService;

    private final Map<String, SseEmitter> map = new HashMap<>();

    /**
     * SSE连接
     * @param token 令牌
     */
    @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@RequestParam("token") String token) {
        Claims claims = JwtUtil.parseToken(token);
        if (Objects.isNull(claims)) {
            return null;
        }
        String subject = claims.getSubject();
        Boolean bool = sysUserService.checkUser(Long.valueOf(subject), RoleEnum.ADMIN_VALUE.value);
        if (!bool) {
            return null;
        }
        SseEmitter sseInfo = map.get(subject);
        if(Objects.nonNull(sseInfo)){
            return sseInfo;
        }
        // 设置超时时间为5分钟
        SseEmitter sseEmitter = new SseEmitter(5 * 60 * 1000L);
        sseEmitter.onTimeout(() -> {
            log.error("Sys SSE Timeout: "+subject);
            map.remove(subject);
        });

        map.put(subject, sseEmitter);
        // 直接返回 SseEmitter 对象就可以和客户端连接
        return sseEmitter;
    }


    /**
     * 获取系统信息
     */
    @Scheduled(fixedDelay = 1000)
    public void getSysInfo() {
        ServerInfoUtil info = new ServerInfoUtil();
        String jsonString = JSON.toJSONString(info.getServerInfo());
        for (Map.Entry<String, SseEmitter> m : map.entrySet()) {
            SseEmitter sseEmitter = m.getValue();
            try {
                sseEmitter.send(jsonString);
            } catch (Exception e) {
                map.remove(m.getKey());
            }
        }

    }
}
