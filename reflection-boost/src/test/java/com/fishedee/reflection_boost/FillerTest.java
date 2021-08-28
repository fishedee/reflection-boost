package com.fishedee.reflection_boost;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class FillerTest {

    private GenericFormalArgumentFiller filler;
    private Class clazz;

    @BeforeEach
    public void init(){
        filler = new GenericFormalArgumentFiller(new GenericActualArgumentExtractor(MyTplImpl2.class,MyTpl.class));
        clazz = MyTpl.class;
    }


    @Test
    public void goMethodTest() throws Exception{
        Method method = clazz.getMethod("go",Object.class);
        Type firstArgument = method.getGenericParameterTypes()[0];
        Type firstActualArgument = filler.fillType(firstArgument);
        assertEquals(firstActualArgument.getTypeName(),"java.lang.String");
    }

    @Test
    public void go2MethodTest() throws Exception{
        Method method = clazz.getMethod("go2",Nothing.class, List.class);
        Type firstArgument = method.getGenericParameterTypes()[0];
        Type firstActualArgument = filler.fillType(firstArgument);
        assertEquals(firstActualArgument.getTypeName(),"com.fishedee.reflection_boost.Nothing2");


        Type secondArgument = method.getGenericParameterTypes()[1];
        Type secondActualArgument = filler.fillType(secondArgument);
        assertEquals(secondActualArgument.getTypeName(),"java.util.List<com.fishedee.reflection_boost.Nothing2>");
    }

    @Test
    public void fieldTest() throws Exception{
        Field field = clazz.getField("data");
        Type fieldType = field.getGenericType();
        Type fieldActualType = filler.fillType(fieldType);
        assertEquals(fieldActualType.getTypeName(),"java.util.Map<java.lang.String, java.util.List<com.fishedee.reflection_boost.Nothing2>>");
    }
}
