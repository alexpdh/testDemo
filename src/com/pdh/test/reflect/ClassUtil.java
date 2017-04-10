package com.pdh.test.reflect;

import java.lang.reflect.Method;

/**
 * 获取类所有信息工具类
 *
 * @auther:pengdh
 * @create:2017-04-07 14:13
 */
public class ClassUtil {
    public static void getPrintClassMessage (Object o) {
        Class c = o.getClass();
        System.out.println("类的名称是 " + c.getName());
        Method[] m = c.getMethods();
       for (int i = 0; i < m.length; i++){
           Class returnType = m[i].getReturnType();
           System.out.print(returnType.getName() + "");
           System.out.print(m[i].getName() + "(");
           Class[] paramTypes = m[i].getParameterTypes();
           for (int j = 0; j < paramTypes.length; j++) {
               String p = paramTypes[j].getName();
               System.out.print(p + ",");
           }
           System.out.println(")");
       }
    }
}
