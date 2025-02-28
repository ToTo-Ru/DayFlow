package io.notecalendar.utils;

import java.util.Map;

/**
 * ThreadLocal
 */
@SuppressWarnings("all")
public class ThreadLocalUtil {

    private static final ThreadLocal THREAD_LOCAL = new ThreadLocal();


    public static <T> T get(){
        return (T) THREAD_LOCAL.get();
    }


    public static void set(Object value){
        THREAD_LOCAL.set(value);
    }

    public static void remove(){
        THREAD_LOCAL.remove();
    }
    public static Integer getUserId(){
        Map<String, Object> claims = ThreadLocalUtil.get();
        return (Integer) claims.get("id");
    }
}
