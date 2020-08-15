package com.ip2region.demo.web;

import org.apache.tomcat.util.buf.StringUtils;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.lionsoul.ip2region.Util;
import org.springframework.stereotype.Component;

import javax.naming.InsufficientResourcesException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.security.interfaces.ECKey;
import java.util.Arrays;

@Component
public class Ip2regionUtil {


    public String getCityInfo(String ip, boolean toByteArr) throws IOException {

        byte[] bytes = new byte[1024 * 1024 * 16];
        //读取长度
        int read = 0;
        InputStream resourceAsStream = Ip2regionUtil.class.getClassLoader().getResourceAsStream("ip2region.db");
        try {
            assert resourceAsStream != null;
            if (toByteArr) {
                read = toByteArray(resourceAsStream, bytes);
            } else {
                read = resourceAsStream.read(bytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            resourceAsStream.close();
        }
        System.out.println("read:"+read);
        try {
            DbConfig config = new DbConfig();
            DbSearcher searcher = new DbSearcher(config, bytes);

            DataBlock dataBlock = null;
            if (Util.isIpAddress(ip) == false) {
                System.out.println("Error: Invalid ip address({})" + ip);
            }

            dataBlock = searcher.memorySearch(ip);

            String region = dataBlock.getRegion();
            return region;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * InputStream转化为byte[]数组
     *
     * @param input
     * @return
     * @throws IOException
     */
    public static int toByteArray(InputStream input, byte[] bytes) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        int count = 0;
        while (-1 != n) {
            count += n;
            output.write(buffer, 0, n);
            n = input.read(buffer);
        }
        byte[] bytes1 = output.toByteArray();
        for (int i = 0; i < bytes1.length; i++) {
            bytes[i]=bytes1[i];
        }
        return count;
    }

    public static void main(String[] args) throws Exception {
        Ip2regionUtil ip2regionUtil = new Ip2regionUtil();
        String cityInfo1 = ip2regionUtil.getCityInfo("10.128.1.88",true);
        String cityInfo2 = ip2regionUtil.getCityInfo("10.128.1.88",false);
        System.out.println(cityInfo1);
        System.out.println(cityInfo2);

    }
}
