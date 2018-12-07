package Tresure;

import sun.reflect.generics.tree.ReturnType;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//写一个类加载器类
//改代码的
public class TestTresure {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        ClassLoader cl  = new ClassLoader() {
            @Override
            protected Class<?> findClass(String name) throws ClassNotFoundException {
                try {
                    FileInputStream in = new FileInputStream("D:\\Treasure.class");
                    byte[] bytes=new byte[1024*8];

                    int len=in.read(bytes);

                    //调用父类的方法利用字节数组加载类

                    return defineClass(name,bytes,0,len);
                }catch (Exception e){
                    e.printStackTrace();
                }
                return  null;

            }
        };
      Class<?> clazz=cl.loadClass("com.westos.Treasure");//根据类名加载类得到类对象

        //获取空参构造方法
        Constructor<?> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);//调用
        Object o = constructor.newInstance();



        for (Method method : clazz.getMethods()) {
            System.out.println(method);
            //通过反射调取
            Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
            if(declaredAnnotations.length>=1){
                method.invoke(o);

            }

            //System.out.println(declaredAnnotations);

        }
    }
}
