package cn.rx.core.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * 自定义bean
 * @author 不想说话而已
 */
@Component
public class CustomBean {


    @Bean("restTemplate")
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }


    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("i18n/messages");
        messageSource.setDefaultEncoding("UTF-8");

        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new CustomHeaderLocaleResolver();
    }

    public static class CustomHeaderLocaleResolver implements LocaleResolver {

        private final String HEADER_NAME = "X-Language";

        @Override
        public @NotNull Locale resolveLocale(HttpServletRequest request) {
            // 从请求头中获取语言参数
            String language = request.getHeader(HEADER_NAME);
            if (language != null && !language.isEmpty()) {
                // 支持的语言列表
                List<Locale> supportedLocales = Arrays.asList(
                        new Locale("zh", "CN"), // 中文
                        new Locale("en", "US")  // 英文
                );

                // 尝试解析用户提供的语言
                for (Locale supportedLocale : supportedLocales) {
                    if (supportedLocale.getLanguage().equalsIgnoreCase(language)) {
                        return supportedLocale;
                    }
                }
            }
            // 如果没有提供语言参数或不支持该语言，则使用默认语言
            return Locale.SIMPLIFIED_CHINESE; // 默认语言为中文
        }

        @Override
        public void setLocale(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Locale locale) {
            // 这里不需要实现，因为我们只关心如何解析请求头中的语言
        }
    }
}
