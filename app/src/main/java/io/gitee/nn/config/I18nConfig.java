package io.gitee.nn.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.TimeZoneAwareLocaleContext;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AbstractLocaleContextResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.util.WebUtils;

import java.util.Locale;
import java.util.TimeZone;

@Configuration
public class I18nConfig extends AbstractLocaleContextResolver {
    public static final String LOCALE_SESSION_ATTRIBUTE_NAME = SessionLocaleResolver.class.getName() + ".LOCALE";
    public static final String TIME_ZONE_SESSION_ATTRIBUTE_NAME = SessionLocaleResolver.class.getName() + ".TIME_ZONE";

    @Value("${spring.messages.basename}")
    public String[] baseFilenames;

    /**
     * 自定义LocalResolver
     */
    @Bean(name = "localeResolver")
    public LocaleResolver localeResolver() {
        String lang = System.getProperty("user.language");
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();

        if ("zh".equals(lang)) {
            sessionLocaleResolver.setDefaultLocale(Locale.CHINESE);
        } else {
            sessionLocaleResolver.setDefaultLocale(Locale.ENGLISH);
        }
        return sessionLocaleResolver;
    }


    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public LocaleContext resolveLocaleContext(HttpServletRequest request) {
        return null;
    }

    @Override
    public void setLocaleContext(HttpServletRequest request, HttpServletResponse response, LocaleContext localeContext) {
        Locale locale = null;
        TimeZone timeZone = null;
        if (localeContext != null) {
            locale = localeContext.getLocale();
            if (localeContext instanceof TimeZoneAwareLocaleContext) {
                timeZone = ((TimeZoneAwareLocaleContext) localeContext).getTimeZone();
            }
        }

        WebUtils.setSessionAttribute(request, LOCALE_SESSION_ATTRIBUTE_NAME, locale);
        WebUtils.setSessionAttribute(request, TIME_ZONE_SESSION_ATTRIBUTE_NAME, timeZone);
    }
}
