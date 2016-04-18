package cn.accessbright.blade.core;

import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public abstract class ClassUtils {
    public static List getMethodsByName(Class clazz, String methodName) {
        Assert.notNull(clazz, "Class must not be null");
        Assert.notNull(methodName, "Method name must not be null");
        List methods = new ArrayList();
        Method[] allPublicMethods = clazz.getMethods();
        for (int i = 0; i < allPublicMethods.length; i++) {
            Method method = allPublicMethods[i];
            if (methodName.equals(method.getName())) {
                methods.add(method);
            }
        }
        return methods;
    }

    /**
     * Return the number of methods with a given name (with any argument types),
     * for the given class and/or its superclasses. Only public methods.
     *
     * @param clazz      the clazz to check
     * @param methodName the name of the method
     * @return the number of methods with the given name
     */
    public static int getMethodCountForName(Class clazz, String methodName) {
        Assert.notNull(clazz, "Class must not be null");
        Assert.notNull(methodName, "Method name must not be null");
        int count = 0;
        Method[] allPublicMethods = clazz.getMethods();
        for (int i = 0; i < allPublicMethods.length; i++) {
            Method method = allPublicMethods[i];
            if (methodName.equals(method.getName())) {
                count++;
            }
        }
        return count;
    }

    public static String[] getPropNames(Class clazz) {
        PropertyDescriptor[] propDescs = BeanUtils.getPropertyDescriptors(clazz);
        List propNames = new ArrayList();
        for (int i = 0; i < propDescs.length; i++) {
            PropertyDescriptor propDesc = propDescs[i];
            String propName = propDesc.getName();
            if (!"class".equals(propName)) propNames.add(propDesc.getName());
        }
        return (String[]) propNames.toArray(new String[propNames.size()]);
    }
}