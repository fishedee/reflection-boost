package com.fishedee.reflection_boost;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;

public class GenericActualArgumentExtractor {
    private Class instanceClass;

    private Class genericClass;

    private ParameterizedType genericInstanceType;

    private static class ActualArgumentInfo{
        TypeVariable variable;
        Class actual;
    }

    private List<ActualArgumentInfo> actualArguments;

    public GenericActualArgumentExtractor(Class instanceClass, Class genericClass){
        this.instanceClass = instanceClass;
        this.genericClass = genericClass;

        this.genericInstanceType = this.extractInstanceGeneric();
        if( genericInstanceType != null) {
            this.actualArguments = this.extractArgument();
        }else{
            this.actualArguments = new ArrayList<>();
        }
    }

    public int getActualTypeLength(){
        return this.actualArguments.size();
    }

    public Class getActualType(int i){
        if( i >= 0 && i < this.actualArguments.size()){
            return this.actualArguments.get(i).actual;
        }
        throw new ReflectionBoostException("Exceed index "+i);
    }

    public Class getActualType(String variable){
        for( int i = 0 ;i != this.actualArguments.size();i++){
            ActualArgumentInfo single = actualArguments.get(i);
            if( single.variable.getName().equals(variable)){
                return single.actual;
            }
        }
        throw new ReflectionBoostException("Unknown Variable : "+variable+" in "+this.genericClass);
    }

    private List<ActualArgumentInfo> extractArgument(){
        List<ActualArgumentInfo> result = new ArrayList<>();
        TypeVariable[] typeVariables = genericClass.getTypeParameters();
        Type[] actuals = this.genericInstanceType.getActualTypeArguments();
        for( int i = 0 ;i != typeVariables.length;i++){
            ActualArgumentInfo actualArgumentInfo = new ActualArgumentInfo();
            actualArgumentInfo.variable = typeVariables[i];
            actualArgumentInfo.actual = (Class)actuals[i];
            result.add(actualArgumentInfo);
        }
        return result;
    }

    private ParameterizedType extractInstanceGeneric(){
        Type parentType = instanceClass;
        while( true ){
            if( parentType instanceof Class ) {
                //???????????????
                if( parentType == genericClass ) {
                    return null;
                }else if (parentType == Object.class) {
                    throw new ReflectionBoostException("Chould not found genericInstanceType: " + instanceClass + "," + genericClass);
                } else {
                    parentType = ((Class<?>) parentType).getGenericSuperclass();
                }
            }else if( parentType instanceof ParameterizedType ){
                //???????????????
                ParameterizedType parentParameterizedType = (ParameterizedType) parentType;
                Class rawType = (Class)parentParameterizedType.getRawType();
                if( rawType == genericClass){
                    return parentParameterizedType;
                }else{
                    parentType = rawType.getGenericSuperclass();
                }
            }else{
                throw new ReflectionBoostException("Unknown parentType "+parentType);
            }
        }
    }
}
