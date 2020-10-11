package com.mall.concurrency.example.threadlocal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: JieEn
 * @date: 2020/10/11 18:00
 * @version: 1.0
 */
@Controller
@RequestMapping("/threadLocal")
public class TestController1 {
    private static Logger logger = LoggerFactory.getLogger(TestController1.class);

    @RequestMapping("/test")
    @ResponseBody
    public Long test(){
        return RequestHolder.getId();
    }
}
