package com.github.mygreen.supercsv.builder;

import java.lang.reflect.Constructor;
import java.util.Objects;

import org.supercsv.exception.SuperCsvReflectionException;
import org.supercsv.util.BeanInterfaceProxy;


/**
 * Beanのインスタンスを生成する標準のクラス。
 *
 * @since 2.0
 * @author T.TSUCHIE
 *
 */
public class DefaultBeanFactory implements BeanFactory<Class<?>, Object> {
    
    @Override
    public Object create(final Class<?> clazz) {
        Objects.requireNonNull(clazz, "clazz should not be null.");
        
        try {
            if(clazz.isInterface()) {
                return BeanInterfaceProxy.createProxy(clazz);
                
            } else {
                Constructor<?> cons = clazz.getDeclaredConstructor();
                cons.setAccessible(true);
                return cons.newInstance();
                
            }
        } catch (ReflectiveOperationException  e) {
            throw new SuperCsvReflectionException(String.format("fail create Bean instance of '%s'", clazz.getName()), e);
        }
    }
}
