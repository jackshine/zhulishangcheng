package com.example.ddm.appui.bean;

/**
 * Created by Zhouziwen .
 * 邮箱：1916973618@qq.com
 */

public class Charge {

    /**
     * msg : success
     * code : 1
     * datas : alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2017070707672656&biz_content=%7B%22body%22%3A%22%E7%8E%B0%E9%87%91%E5%85%85%E5%80%BC%22%2C%22out_trade_no%22%3A%22010392671487002%22%2C%22product_code%22%3A%22QUICK_WAP_PAY%22%2C%22subject%22%3A%22%E7%8E%B0%E9%87%91%E5%85%85%E5%80%BC%22%2C%22timeout_express%22%3A%222m%22%2C%22total_amount%22%3A%22100%22%7D&charset=UTF-8&format=json&method=alipay.trade.wap.pay¬ify_url=http%3A%2F%2Fwww.ddmzl.com%2Fali%2Fnotify.do&return_url=http%3A%2F%2Fwww.ddmzl.com%2Fm%2Fhtml%2Fpay-success.html&sign=F3AZ8QU5k350TwUe2u8bGxcD2HSppxEXAtpwcA4e9nXv4%2BpB%2FENYjVHy%2FZxRrZ%2BfvpNUoGePjOr41JNJn5YdjLqY6xjRAli8y9Mlw5kWAGaQu%2BpNdGyVmXLlfEYzvAo%2BKTcnwmDy3fQjpGJDStzDYOZYNU%2Fzawb5z9HZzcB8UlzIFeK%2FJ9OMc7Ja4tPht6tuxnr8wGKAyegmHEVGj4roNIMbpp5VajhdLAAVrGwC6f7eqvbQynlsITfUjn2f34sLU26hgFzhM2Npq8BWVHQpaPPXyVnR35sDP0aUgLJYv8Jlh7inZGparIfg9oi8CXG%2BS8wGLxm1uEK%2FFXK%2BFWNqbA%3D%3D&sign_type=RSA2×tamp=2017-09-04+17%3A19%3A11&version=1.0&sign=F3AZ8QU5k350TwUe2u8bGxcD2HSppxEXAtpwcA4e9nXv4%2BpB%2FENYjVHy%2FZxRrZ%2BfvpNUoGePjOr41JNJn5YdjLqY6xjRAli8y9Mlw5kWAGaQu%2BpNdGyVmXLlfEYzvAo%2BKTcnwmDy3fQjpGJDStzDYOZYNU%2Fzawb5z9HZzcB8UlzIFeK%2FJ9OMc7Ja4tPht6tuxnr8wGKAyegmHEVGj4roNIMbpp5VajhdLAAVrGwC6f7eqvbQynlsITfUjn2f34sLU26hgFzhM2Npq8BWVHQpaPPXyVnR35sDP0aUgLJYv8Jlh7inZGparIfg9oi8CXG%2BS8wGLxm1uEK%2FFXK%2BFWNqbA%3D%3D
     */

    private String msg;
    private int code;
    private String datas;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDatas() {
        return datas;
    }

    public void setDatas(String datas) {
        this.datas = datas;
    }
}
