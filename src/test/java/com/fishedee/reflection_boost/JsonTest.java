package com.fishedee.reflection_boost;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    private ObjectMapper objectMapper = new ObjectMapper();


    private GenericFormalArgumentFiller filler;
    private Class clazz;

    @BeforeEach
    public void setUp(){
        filler = new GenericFormalArgumentFiller(new GenericActualArgumentExtractor(MyTplImpl2.class,MyTpl.class));
        clazz = MyTpl.class;
    }

    @Test
    public void test()throws Exception{
        //先生成actualType
        Field field = clazz.getField("data");
        Type fieldType = field.getGenericType();
        Type fieldActualType = filler.fillType(fieldType);

        //转换
        String json = "{\"a\":[{\"mm\":\"cat1\"},{\"mm\":\"cat2\"}],\"b\":[{\"mm\":\"cat3\"},{\"mm\":\"cat4\"}]}";
        Object target = objectMapper.readValue(json,objectMapper.getTypeFactory().constructType(fieldActualType));

        //进行类型测试
        Map<String, List<Nothing2>> result = (Map<String, List<Nothing2>>)target;
        assertEquals(result.get("a").get(0).getClass(),Nothing2.class);
        assertEquals(target.toString(),"{a=[{mm cat1}, {mm cat2}], b=[{mm cat3}, {mm cat4}]}");
    }
}
