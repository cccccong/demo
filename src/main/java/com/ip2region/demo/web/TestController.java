package com.ip2region.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @Description:
 * @Author: ZhangCong
 * @Date: 2020/8/15 13:11
 */
@RestController
public class TestController {

    @Autowired
    Ip2regionUtil ip2regionUtil;

    @GetMapping("slice")
    public String slice() throws IOException {
        //分段读
        return ip2regionUtil.getCityInfo("10.128.1.88",true);
    }

    @GetMapping("noSlice")
    public String noSlice() throws IOException {
        //一次性读
        return ip2regionUtil.getCityInfo("10.128.1.88",false);
    }

}
