package com.simulation.notebook.constant;

/**
 * 平台公共常量定义
 */
public class Constants {



    /**
     * 返回结果码常量定义
     */
    public static class ResultCode {
        /**
         * 正确：200
         */
        public static final int OK = 200;

        /**
         * 异常：500
         */
        public static final int EXCEPTION = 500;

        /**
         * 自定义异常：400
         */
        public static final int ERROR = 400;

        /**
         * 未登录或登录信息失效：410
         */
        public static final int ERROR_NO_LOGIN = 410;

        /**
         * 非法请求：409
         */
        public static final int ERROR_ILLEGAL_REQUEST = 409;

        /**
         * 强制修改密码：413
         */
        public static final int ERROR_MUST_MODIFY_PWD = 413;

        /**
         * 密码输入错误达最大次数，账号锁定：414
         */
        public static final int ERROR_LOCKED = 414;

        /**
         * 密码输入错误：415
         */
        public static final int ERROR_PWD = 415;

        /**
         * 其他地方登录，被迫退出：416
         */
        public static final int ERROR_FORCE_LOGOUT = 416;


        /**
         * 非法请求header参数错误：444
         */
        public static final int ERROR_ILLEGAL_URI = 444;


    }

}
