package com.simulation.notebook.util;

import com.simulation.notebook.exceptions.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 对象工具类
 */
@Slf4j
@SuppressWarnings("all")
public class ObjectUtil {
    public enum ObjectMethodEnum {
        SET, GET;
    }

    /**
     * 获取泛型类Class对象，不是泛型类则返回null
     * @param clazz 类类型
     * @return 对应的实体类
     */
    public static Class<?> getActualTypeArgument(Class<?> clazz) {
        Class<?> entitiClass = null;
        Type genericSuperclass = clazz.getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
            if (ArrayUtils.isNotEmpty(actualTypeArguments)) {
                // service中的两个泛型：第一个是对应的mapper，第二个是对应的实体类。此处需要实体类
                entitiClass = (Class<?>) actualTypeArguments[1];
            }
        }
        return entitiClass;
    }

    /**
     * 获取对象obj的attribute的get方法值
     * @param obj       实体对象
     * @param attribute 对象属性字段
     * @param <T>       返回泛型
     * @return 返回指定泛型的get值
     * @throws Exception
     */
    public static <T> T invokeGetter(Object obj, String attribute) throws Exception {
        Class objClass = obj.getClass();
        Method method = objClass.getMethod(getMethodName(ObjectMethodEnum.GET, attribute));
        return (T) method.invoke(obj);
    }

    public static <T> void invokeSetter(Object obj, String attribute, T value) throws Exception {
        Class objClass = obj.getClass();
        Method method = objClass.getMethod(getMethodName(ObjectMethodEnum.SET, attribute), new Class[]{value.getClass()});
        method.invoke(obj, value);
    }

    /**
     * 利用java反射机制，调用对象方法
     * @param obj        具体对象
     * @param methodName 对象类方法名称
     * @param args       方法传参。注意：参数顺序必须正确
     * @return
     * @throws Exception
     */
    public static Object invoke(Object obj, String methodName, Object... args) throws Exception {
        Class clazz = obj.getClass();
        Class[] argsArr = null;
        if (args != null && args.length > 0) {
            argsArr = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                argsArr[i] = args[i].getClass();
            }
        }
        Method method = clazz.getMethod(methodName, argsArr);
        return method.invoke(obj, args);
    }

    /**
     * 利用java反射机制，调用对象方法
     * @param obj          业务对象
     * @param methodPrefix 方法前缀枚举类
     * @param attrName     属性名称
     * @param args
     * @return
     * @throws Exception
     */
    public static Object invoke(Object obj, ObjectMethodEnum methodPrefix, String attrName, Object... args) throws Exception {
        return invoke(obj, getMethodName(methodPrefix, attrName), args);
    }

    public static String getMethodName(ObjectMethodEnum methodPrefix, String attrName) {
        if (methodPrefix == null) throw new BusinessException("方法前缀不能为空！");
        // 说明多层
        String[] attrNames = attrName.split(".");
        return methodPrefix.name().toLowerCase() + attrName.substring(0, 1).toUpperCase() + attrName.substring(1);
    }

    /**
     * 利用java反射机制，根据指定的属性名称，获取属性对应的值
     * @param obj       具体对象
     * @param attribute 对象属性定义。必须与get方法的定义保持正确的规范定义形式。如果{attribute}中含有.说明对象中含有对象
     * @return
     * @throws Exception
     */
    public static Object invokeByAttr(Object obj, String attribute) throws Exception {
        if (obj instanceof Map) {
            Map objMap = (Map) obj;
            return objMap.get(attribute);
        } else {
            String[] attrs = attribute.split("\\.");
            return reverseInvokeByAttr(obj, attrs, 0);
        }
    }

    public static Object reverseInvokeByAttr(Object obj, String[] attrs, int attrIdx) throws Exception {
        Class clazz = obj.getClass();
        String attrName = attrs[attrIdx];
        // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
        String getMethodName = "get" + attrName.substring(0, 1).toUpperCase() + attrName.substring(1);
        Method method = clazz.getMethod(getMethodName);
        Object result = method.invoke(obj, new Object[]{});
        attrIdx++;

        if (attrIdx == attrs.length) {
            return result;
        }
        return reverseInvokeByAttr(result, attrs, attrIdx);
    }

    /**
     * 利用java反射机制，将origin对象的所有非空属性赋值给target对象
     * @param origin     源对象
     * @param target     目标对象
     * @param copyParent 是否复制父类中的属性
     * @return
     * @throws Exception
     */
    public static void copyAttrsNotNull(Object origin, Object target, boolean copyParent) {
        if (origin == null || target == null) return;
        Class originClass = origin.getClass();
        Class targetClass = target.getClass();
        if (!originClass.equals(targetClass)) return;

        Field field = null;
        String fieldName = null;
        Method method = null;
        String methodName = null;
        Field[] fields = origin.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            field = fields[i];
            invokeAttr(origin, target, field);
        }
        if (copyParent) {
            // 源对象的父类
            Class superOriginClass = originClass.getSuperclass();
            Class superTargetClass = targetClass.getSuperclass();
            if (superOriginClass != null) {
                fields = superOriginClass.getDeclaredFields();
                for (int i = 0; i < fields.length; i++) {
                    field = fields[i];
                    invokeAttr(origin, target, field);
                }
            }
        }
    }

    public static void copyAttrsNotNull(Object origin, Object target) {
        copyAttrsNotNull(origin, target, true);
    }

    /**
     * 利用java反射机制，将origin对象的非空属性赋值给target对象
     * @param origin 源对象
     * @param target 目标对象
     * @param field  具体属性值
     */
    private static void invokeAttr(Object origin, Object target, Field field) {
        try {
            field.setAccessible(true);
            Object value = field.get(origin);
            if (null != value) {
                field.set(target, value);
            }
            field.setAccessible(false);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public static String dealNumber(String number) {
        if (StringUtils.isBlank(number)) return null;
        return dealNumber(Double.parseDouble(number));
    }

    public static String dealNumber(Float number) {
        if (number == null) return null;
        return dealNumber(number.doubleValue());
    }

    public static String dealNumber(Double number) {
        String result = null;
        String scoreStr = null;
        if (number != null) {
            scoreStr = number.toString();
            scoreStr = scoreStr.substring(scoreStr.indexOf(".") + 1);
            if (new BigDecimal(scoreStr).compareTo(BigDecimal.ZERO) > 0) result = number.toString();
            else result = number.intValue() + "";
        }
        return result;
    }

    /**
     * 获取比较大小的数组中的最小值
     * @param values 多个需要比较的值
     * @return 返回最小值
     */
    public static int min(int... values) {
        if (values.length == 0) {
            throw new BusinessException("比较值values不能为空！");
        }
        int min = -1;
        for (int value : values) {
            if (min == -1 || value < min) {
                min = value;
            }
        }
        return min;
    }
}
