package br.com.kathulynneves.todolist.utils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class Utils {

    //Mescla propriedades não nulas de dois objetos -- Usado para fazer update em um valor e não retornar o restante nulo
    public static void copyNonNullProperties(Object source, Object target){
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));

    }
    

    public static String[] getNullPropertyNames(Object source){
        final BeanWrapper src = new BeanWrapperImpl(source);
         //Forma de acessar as propriedades de um objeto no java e Impl é uma implementação dessa interface

        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> enptyNames = new HashSet<>();

        for(PropertyDescriptor pd: pds){
            Object srcValue = src.getPropertyValue(pd.getName());
            if(srcValue == null){
                enptyNames.add(pd.getName());
            }
        }
        String[] result = new String[enptyNames.size()];
        return enptyNames.toArray(result);
    }
}
