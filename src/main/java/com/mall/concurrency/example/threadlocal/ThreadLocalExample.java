package com.mall.concurrency.example.threadlocal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author: JieEn
 * @date: 2020/10/11 10:59
 * @version: 1.0
 */
public class ThreadLocalExample implements Filter {

    private static Logger logger = LoggerFactory.getLogger(ThreadLocalExample.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        request.getSession().getAttribute("id");
        logger.info("do filter {}, {}",Thread.currentThread().getId(),request.getServletPath());
        RequestHolder.add(Thread.currentThread().getId());
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }

}
