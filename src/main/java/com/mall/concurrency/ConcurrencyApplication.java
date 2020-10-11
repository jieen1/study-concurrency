package com.mall.concurrency;

import com.mall.concurrency.example.threadlocal.ThreadLocalExample;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.Filter;
import java.util.Arrays;

@SpringBootApplication
public class ConcurrencyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConcurrencyApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean httpFilter(){
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new ThreadLocalExample());
        filterFilterRegistrationBean.setUrlPatterns(Arrays.asList("/threadLocal/*"));
        return filterFilterRegistrationBean;
    }
}
