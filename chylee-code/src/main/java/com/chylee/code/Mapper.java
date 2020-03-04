package com.chylee.code;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Mapper {
    public static void toXml(String pkg, String tableName, Class clazz) {
        String objName = clazz.getSimpleName();
        String name = clazz.getName();

        StringBuilder fieldBuilder = new StringBuilder();
        StringBuilder valueBuilder = new StringBuilder();
        initFieldsAndValues(clazz, fieldBuilder, valueBuilder);

        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append("\n");
        builder.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">").append("\n");
        builder.append("<mapper namespace=\"").append(pkg).append(".").append(objName).append("Mapper\">").append("\n");

        //insert
        builder.append("\t<insert id=\"insert\" parameterType=\"").append(name).append("\">").append("\n");
        builder.append("\t\tinsert into ").append(tableName).append("\n");
        builder.append("\t\t<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">").append("\n");
        builder.append(fieldBuilder);
        builder.append("\t\t</trim>").append("\n");
        builder.append("\t\t<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">").append("\n");
        builder.append(valueBuilder);
        builder.append("\t\t</trim>").append("\n");
        builder.append("\t</insert>").append("\n");

        builder.append("</mapper>");

        System.out.println(builder);
    }

    private static void initFieldsAndValues(Class clazz, StringBuilder fieldBuilder, StringBuilder valueBuilder) {
        Class supperClass = clazz.getSuperclass();
        if (!Object.class.equals(supperClass))
            initFieldsAndValues(supperClass, fieldBuilder, valueBuilder);

        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            String fieldName = field.getName();
            fieldBuilder.append("\t\t\t<if test=\"").append(fieldName).append(" != null\">").append(fieldName).append(",</if>").append("\n");
            valueBuilder.append("\t\t\t<if test=\"").append(fieldName).append(" != null\">#{").append(fieldName).append("},</if>").append("\n");
        }
    }

    private static String getter(String name) {
        String firstLetter = name.substring(0, 1);
        return "get" + name.replaceAll(firstLetter, firstLetter.toUpperCase());
    }
}
