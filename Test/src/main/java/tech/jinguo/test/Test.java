package tech.jinguo.test;

import java.util.Date;
import java.util.TimeZone;

public class Test {
    public static void main(String[] args) {
        Date date = new Date();
        System.out.println(date);
        // 修改默认时区
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
        System.out.println(date);
    }
}
