package com.module.linrary.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 解析json字符串工具
 */
public class JsonUtils {
    /**
     * 将json字符串解析成实体类
     *
     * @param jsonString json字符串
     * @param cls        实体类
     * @param <T>        实体类
     * @return
     */
    public static <T> T getObject(String jsonString, Class<T> cls) {
        try {
            return JSON.parseObject(jsonString, cls);
        } catch (Exception e) {
            throw new DataParseException(e);
        }
    }

    /**
     * 将json字符串解析成实体类列表
     *
     * @param jsonString json字符串
     * @param cls        实体类
     * @param <T>        实体类
     * @return
     */
    public static <T> List<T> getListObject(String jsonString, Class<T> cls) {
        try {
            List<T> resultData = new ArrayList<>();
            JSONArray jsonArray = JSON.parseArray(jsonString);
            for (int i = 0; i < jsonArray.size(); i++) {
                if (!JavaUtils.isStringEmpty(jsonArray.get(i).toString())) {
                    resultData.add(getObject(jsonArray.get(i).toString(), cls));
                }
            }
            return resultData;
        } catch (Exception e) {
            throw new DataParseException(e);
        }
    }

    /**
     * 将json字符串解析成Map列表
     *
     * @param jsonString json字符串
     * @return
     */
    public static List<Map<String, String>> getKeyStringMap(String jsonString) {
        List<Map<String, String>> list;
        try {
            list = JSON.parseObject(jsonString, new TypeReference<List<Map<String, String>>>() {
            });
        } catch (Exception e) {
            throw new DataParseException(e);
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    /**
     * 将json字符串解析成Map列表
     *
     * @param jsonString json字符串
     * @return
     */
    public static List<Map<Long, String>> getKeyLongMap(String jsonString) {
        List<Map<Long, String>> list;
        try {
            list = JSON.parseObject(jsonString, new TypeReference<List<Map<Long, String>>>() {
            });
        } catch (Exception e) {
            throw new DataParseException(e);
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    /**
     * 该类为json数据解析异常调用
     */
    public static class DataParseException extends BaseException {
        public DataParseException(Throwable throwable) {
            super(throwable);
        }
    }

    /**
     * 该类为json数据解析异常调用
     */
    public abstract static class BaseException extends RuntimeException {
        public int DEFAULT_RES_ID = -1;
        private String message = "";
        private Throwable throwable = null;
        private int statusCode = 0;

        public void setStatusCode(int statusCode) {
            this.statusCode = statusCode;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public BaseException(Throwable throwable) {
            this.throwable = throwable;
            message = throwable.getMessage();
        }

        public BaseException(String msg) {
            super(msg);
            this.message = msg;
        }

        public BaseException(String msg, Throwable throwable) {
            super(msg);
            this.throwable = throwable;
            this.message = msg;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        @Override
        public String getMessage() {
            return message;
        }

        public int getMessageResId() {
            return DEFAULT_RES_ID;
        }

        public Throwable getThrowable() {
            return throwable;
        }
    }
}
