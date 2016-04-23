package cn.accessbright.blade.core.utils;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/19.
 */
public class Objects {
    public static Object getPropValue(Object target, String property) {
        if (target != null) {
            if (target instanceof Map) {
                return getPropValue((Map) target, property, false);
            } else {
                PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(target.getClass(), property);


                try {
                    if (propertyDescriptor != null) {
                        Method readMethod = propertyDescriptor.getReadMethod();
                        return readMethod.invoke(target);
                    }

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static <T,P> P getPropertyValue(T target,String property){
        return (P)getPropValue(target,property);
    }


    /**
     * 获取map的值，并忽略大小写
     *
     * @param target
     * @param property
     * @param ignoreKeyCase
     * @return
     */
    private static Object getPropValue(Map target, String property, boolean ignoreKeyCase) {
        if (target.containsKey(property)) {
            return target.get(property);
        }
        if (ignoreKeyCase) {
            if (target.containsKey(property.toLowerCase())) {
                return target.get(property.toLowerCase());
            } else if (target.containsKey(property.toUpperCase())) {
                return target.get(property.toUpperCase());
            }
        }
        return null;
    }

    public static <T> T defaultValue(T source, T defaultValue) {
        return source == null ? defaultValue : source;
    }

    /**
     * 未考虑到属性值与目标属性类型不一致的问题<br>
     * 设置对象或者map的属性，并忽略map的key的大小写
     *
     * @param target
     * @param property
     * @param value
     */
    public static void setPropValue(Object target, String property, Object value) {
        if (target != null) {
            if (target instanceof Map) {
                setPropValue((Map) target, property, value, true);
            } else {
                try {
                    PropertyUtils.setProperty(target, property, value);
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 设置map的值，并忽略大小写
     *
     * @param target
     * @param property
     * @param value
     * @param ignoreKeyCase
     */
    private static void setPropValue(Map target, String property, Object value, boolean ignoreKeyCase) {
        if (ignoreKeyCase) {
            if (target.containsKey(property.toLowerCase())) {
                target.put(property.toLowerCase(), value);
                return;
            } else if (target.containsKey(property.toUpperCase())) {
                target.put(property.toUpperCase(), value);
                return;
            }
        }
        target.put(property, value);
        return;
    }

    public static String[] getPropText(Object target, String[] props) {
        String[] values = new String[props.length];
        for (int i = 0; i < props.length; i++) {
            values[i] = getPropText(target, props[i]);
        }
        return values;
    }

    public static String getPropText(Object target, String property) {
        if (target == null) return "";
        Object value = getPropValue(target, property);
        return Strings.toString(value);
    }


    /**
     * @param source 初始对象
     * @param dest   目标对象
     */
    public static void copyProperties(Object source, Object dest) {
        BeanUtils.copyProperties(source, dest);
    }

    public static void copyProperties(Object source, Object dest, String... ignoreProperties) {
        BeanUtils.copyProperties(source, dest, ignoreProperties);
    }


    /**
     * Copy the property values of the given source bean into the given target bean.
     * <p>Note: The source and target classes do not have to match or even be derived
     * from each other, as long as the properties match. Any bean properties that the
     * source bean exposes but the target bean does not will silently be ignored.
     *
     * @param source           the source bean
     * @param target           the target bean
     * @param editable         the class (or interface) to restrict property setting to
     * @param ignoreProperties array of property names to ignore
     * @throws BeansException if the copying failed
     * @see BeanWrapper
     */
    private static void copyProperties(Object source, Object target, Class<?> editable, boolean copyNull, boolean copyEmpty, String... ignoreProperties)
            throws BeansException {

        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");

        Class<?> actualEditable = target.getClass();
        if (editable != null) {
            if (!editable.isInstance(target)) {
                throw new IllegalArgumentException("Target class [" + target.getClass().getName() +
                        "] not assignable to Editable class [" + editable.getName() + "]");
            }
            actualEditable = editable;
        }
        PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(actualEditable);
        List<String> ignoreList = (ignoreProperties != null ? java.util.Arrays.asList(ignoreProperties) : null);

        for (PropertyDescriptor targetPd : targetPds) {
            Method writeMethod = targetPd.getWriteMethod();
            if (writeMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
                PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null) {
                    Method readMethod = sourcePd.getReadMethod();
                    if (readMethod != null &&
                            ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                        try {
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                readMethod.setAccessible(true);
                            }
                            Object value = readMethod.invoke(source);
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }
                            if (value == null && !copyNull)
                                continue;
                            if (value instanceof CharSequence && Strings.isEmpty((CharSequence) value) && !copyEmpty)
                                continue;
                            writeMethod.invoke(target, value);
                        } catch (Throwable ex) {
                            throw new FatalBeanException(
                                    "Could not copy property '" + targetPd.getName() + "' from source to target", ex);
                        }
                    }
                }
            }
        }
    }


    /**
     * 当orig的属性为 null 的就不拷贝到 dest
     * by sunmh
     *
     * @param dest
     * @param orig
     */
    public static void copyPropertiesWithoutNull(Object dest, Object orig) {
        try {
            // 得到两个Class 的所有成员变量
            Field[] destFields = dest.getClass().getDeclaredFields();
            Field[] origFields = orig.getClass().getDeclaredFields();

            // 设置访问权限
            AccessibleObject.setAccessible(destFields, true);
            AccessibleObject.setAccessible(origFields, true);

            Object value = null;
            String name = null;
            String returnType = null;

            for (int i = 0; i < origFields.length; i++) {
                name = origFields[i].getName();
                returnType = origFields[i].getType().getName();
                for (int j = 0; j < destFields.length; j++) {
                    if (name.equals(destFields[j].getName()) && returnType.equals(destFields[j].getType().getName())) {
                        value = origFields[i].get(orig);
                        if (value != null) {
                            destFields[j].set(dest, value);
                        }
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void copyProperties(Object target, Object source, boolean copyNull, boolean copyEmpty) {
        try {
            Map targetProps = PropertyUtils.describe(target);
            Map sourceProps = PropertyUtils.describe(source);
            Iterator iter = targetProps.keySet().iterator();
            while (iter.hasNext()) {
                String prop = (String) iter.next();
                if (sourceProps.containsKey(prop)) {
                    Object targetValue = targetProps.get(prop);
                    Object sourceValue = sourceProps.get(prop);
                    if (!ObjectUtils.equals(targetValue, sourceValue)) {
                        boolean isNull = sourceValue == null;
                        boolean isEmpty = sourceValue != null && sourceValue instanceof String && Strings.isEmpty((String) sourceValue);
                        if (!copyNull && isNull) continue;
                        if (!copyEmpty && isEmpty) continue;
                        Objects.setPropValue(targetProps, prop, sourceValue);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void copyProperties(Object target, Map sourceProps, boolean copyNull, boolean copyEmpty) {
        try {
            Map targetProps = PropertyUtils.describe(target);
            Iterator iter = targetProps.keySet().iterator();
            while (iter.hasNext()) {
                String prop = (String) iter.next();
                if (sourceProps.containsKey(prop) && targetProps.containsKey(prop)) {
                    Object targetValue = targetProps.get(prop);
                    Object sourceValue = sourceProps.get(prop);
                    if (!equals(targetValue, sourceValue)) {
                        boolean isNull = sourceValue == null;
                        boolean isEmpty = sourceValue != null && sourceValue instanceof String && Strings.isEmpty((String) sourceValue);
                        if (!copyNull && isNull) continue;
                        if (!copyEmpty && isEmpty) continue;
                        setPropValue(target, prop, sourceValue);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public static boolean equals(Object o1, Object o2) {
        if (o1 == o2) return true;
        if (o1 == null || o2 == null) return false;
        return o1.equals(o2);
    }
}
