package com.fishedee.reflection_boost;

import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public class GenericFormalArgumentFiller {
    private GenericActualArgumentExtractor extractor;

    public GenericFormalArgumentFiller(GenericActualArgumentExtractor extractor){
        this.extractor = extractor;
    }

    public Type fillType(Type t){
        if( t instanceof Class ){
            //普通类型
            return t;
        }else if( t instanceof ParameterizedType){
            //泛型类型
            //原来的信息
            ParameterizedType pt = (ParameterizedType)t;
            Type ptRawType = pt.getRawType();
            Type[] ptActualArguments = pt.getActualTypeArguments();

            //提取信息
            Class convertRawType = (Class)this.fillType(ptRawType);
            Type[] actualArguments = new Type[ptActualArguments.length];
            for( int i = 0 ;i != ptActualArguments.length;i++){
                actualArguments[i] = this.fillType(ptActualArguments[i]);
            }
            return ParameterizedTypeImpl.make(convertRawType,actualArguments,null);
        }else if( t instanceof TypeVariable){
            //泛型参数类型
            String variableName = ((TypeVariable<?>) t).getName();
            return this.extractor.getActualType(variableName);
        }else{
            throw new ReflectionBoostException("Unknown type "+t);
        }
    }
}
