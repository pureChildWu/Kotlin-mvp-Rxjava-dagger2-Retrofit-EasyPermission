package com.gzinfo.kotlintiktok.util;

import android.app.Activity;
import android.view.View;

import com.gzinfo.kotlintiktok.annontaion.BindView;
import com.gzinfo.kotlintiktok.annontaion.ClickView;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @ClassName:MyTools
 * @Author:CreatBy wlh
 * @Time:2020/8/28 15点
 * @Email:m15904921255@163.com
 * @Desc:TODO
 */
public class MyTools {
    public MyTools() {
    }
    public static void init(Activity activity){
        try {
            bindView(activity);
            bindClickEvent(activity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static void bindView(Activity activity) throws IllegalAccessException {
        Class<? extends Activity> aClass = activity.getClass();//获取当前Activity的类对象
        Field[] declaredFields = aClass.getDeclaredFields();//获取这个类对象里面所有的字段
        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            BindView bind = field.getAnnotation(BindView.class);//拿到字段上面的BindView注解
            if (bind != null){
                int value = bind.value();
                View viewById = activity.findViewById(value);
                field.setAccessible(true);
                field.set(activity,viewById);
            }
        }
    }
    private static void bindClickEvent(Activity activity){
        Class<? extends Activity> aClass = activity.getClass();
        Method[] declaredMethods = aClass.getDeclaredMethods();
        for (int i = 0; i < declaredMethods.length; i++) {
            Method method = declaredMethods[i];
            ClickView clickView = method.getAnnotation(ClickView.class);
            if (clickView != null){
                int[] value = clickView.value();
                for (int fId : value) {
                    View viewById = activity.findViewById(fId);
                    viewById.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            method.setAccessible(true);
                            try {
                                method.invoke(activity,viewById);
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        }
    }
}
