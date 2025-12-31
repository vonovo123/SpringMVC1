package hello;

import hello.exception.resolver.MyHandlerExceptionResolver;
import hello.exception.resolver.UserHandlerExceptionResolver;
import hello.filter.LogFilter;
import hello.interceptor.LogInterceptor;
import hello.loginservice.web.argumentResolver.LoginMemberArgumentResolver;
import hello.loginservice.web.filter.LoginCheckFilter;
import hello.loginservice.web.interceptor.LoginCheckInterceptor;
import hello.typeConverter.converter.IntegerToStringConverter;
import hello.typeConverter.converter.IpPortToStringConverter;
import hello.typeConverter.converter.StringToIntegerConverter;
import hello.typeConverter.converter.StringToIpPortConverter;
import hello.typeConverter.formatter.MyNumberFormatter;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import org.apache.catalina.User;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
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
        bean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ERROR);
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
                .excludePathPatterns("/css/**","/*.ico","/error-page/**");

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/","/index.html", "/css/**","/*.ico","/error" , "/error-ex", "/home", "/members/add", "/login", "/logout",
                        "/error-page/**", "/api/**", "/converter/**" , "/formatter/**", "/upload/**");
    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver>
                                                        resolvers) {
        resolvers.add(new MyHandlerExceptionResolver());
        resolvers.add(new UserHandlerExceptionResolver());
    }

    public void addFormatters(FormatterRegistry registry) {
        //registry.addConverter(new StringToIntegerConverter());
        //registry.addConverter(new IntegerToStringConverter());
        registry.addConverter(new StringToIpPortConverter());
        registry.addConverter(new IpPortToStringConverter());
        registry.addFormatter(new MyNumberFormatter());
    }

}
