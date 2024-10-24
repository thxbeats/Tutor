package org.education.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Определяем LocaleResolver, который будет хранить выбранную локаль в сессии
    /*@Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.ENGLISH);  // Локаль по умолчанию — английская
        return slr;
    }*/

    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver clr = new CookieLocaleResolver();
        clr.setDefaultLocale(Locale.ENGLISH); // Локаль по умолчанию — английская
        clr.setCookieName("langCookie"); // Имя куки, где будет храниться язык
        clr.setCookieMaxAge(3600 * 24 * 30); // Куки будет жить 30 дней
        return clr;
    }



    // Определяем источник сообщений для разных языков
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");  // Указываем базовое имя для файлов сообщений
        messageSource.setDefaultEncoding("UTF-8"); // Устанавливаем кодировку UTF-8

        return messageSource;
    }

    // Добавляем перехватчик для изменения языка через параметр "lang"
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");  // Параметр для смены языка будет "lang"
        return lci;
    }

    // Регистрируем перехватчик в Spring MVC
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
