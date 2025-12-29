package hello.loginservice;

import hello.loginservice.web.argumentResolver.LoginMemberArgumentResolver;
import hello.loginservice.web.filter.LogFilter;
import hello.loginservice.web.filter.LoginCheckFilter;
import hello.loginservice.web.interceptor.LogInterceptor;
import hello.loginservice.web.interceptor.LoginCheckInterceptor;
import jakarta.servlet.Filter;
import lombok.extern.java.Log;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver());
    }

    //@Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new LogFilter());
        bean.setOrder(1);
        bean.addUrlPatterns("/*");
        return bean;
    }
    //@Bean
    public FilterRegistrationBean loginCehckFilterBean() {
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new LoginCheckFilter());
        bean.setOrder(2);
        bean.addUrlPatterns("/*");
        return bean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**","/*.ico","/error");

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**","/*.ico","/error", "/home", "/members/add", "/login", "/logout", "/error-page/**");
    }

}
