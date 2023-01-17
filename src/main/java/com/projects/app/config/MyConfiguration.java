package com.projects.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
@EnableWebMvc
public class MyConfiguration implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:images/");
    }

    @Bean
    public JavaMailSender javaMailSender() {
        return new JavaMailSenderImpl();
    }

    @Bean
    public ClassLoaderTemplateResolver yourTemplateResolver() {
        ClassLoaderTemplateResolver configure = new ClassLoaderTemplateResolver();
        configure.setPrefix("customLocation/");
        configure.setSuffix(".html");
        configure.setTemplateMode(TemplateMode.HTML);
        configure.setCharacterEncoding("UTF-8");
        configure.setOrder(0);  // this is important. This way spring //boot will listen to both places 0 and 1
        configure.setCheckExistence(true);
        return configure;
    }
}
