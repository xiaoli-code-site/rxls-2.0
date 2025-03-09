package cn.rx.common.util;


import cn.rx.common.constant.JwtConstant;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.jsonwebtoken.Claims;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

/**
 * http工具类
 * @author 不想说话而已
 */
public class HttpUtil {

    /**
     * xml转换器
     */
    private static final ObjectMapper xmlMapper = createObjectMapper();

    /**
     * 创建转换器
     * @return 转换器
     */
    private static ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new XmlMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        return objectMapper;
    }

    /**
     * 获取http信息
     * @return HttpServletRequest
     */
    public static HttpServletRequest getHttpInfo() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }


    /**
     * 获取令牌包含的用户信息
     * @return 令牌信息
     */
    public static Claims getUserLoginInfo() {
        HttpServletRequest request = getHttpInfo();
        String token = request.getHeader(JwtConstant.TOKEN);
        if (Objects.isNull(token) || "".equals(token)) {
            return null;
        }

        Claims claims = JwtUtil.parseToken(token);
        if (Objects.isNull(claims)) {
            return null;
        }
        return claims;
    }


    /**
     * 读取请求体内容
     * @param request 请求对象
     * @return Map对象
     */
    public static String bodyHandler(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        try (ServletInputStream inputStream = request.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return sb.toString();
    }

    /**
     * 读取请求体Xml内容转换成json
     * @param request 请求对象
     * @return Map对象
     */
    public static Map bodyXmlHandler(HttpServletRequest request) throws IOException {
        // 从请求体中读取XML数据
        String xmlMessage = bodyHandler(request);
        return xmlToJsonString(xmlMessage);
    }


    /**
     * xml转换成json
     * @param xml xml文本
     * @return Map对象
     */
    public static Map xmlToJsonString(String xml) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.setDefaultUseWrapper(false);
        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return xmlMapper.readValue(xml,Map.class);
    }
}
