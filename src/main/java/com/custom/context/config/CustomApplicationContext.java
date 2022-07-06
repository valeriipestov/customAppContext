package com.custom.context.config;

import com.custom.context.annotation.CustomBean;
import com.custom.context.annotation.CustomInject;
import com.custom.context.annotation.SimpleQualifier;
import com.custom.context.exceptions.NoSuchBeanException;
import com.custom.context.exceptions.NoUniqueBeanException;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomApplicationContext implements ApplicationContext {

    private final Map<String, Object> beans = new HashMap<>();

    @SneakyThrows
    public CustomApplicationContext(String packageName) {
        var reflections = new Reflections(packageName);
        var annotatedClasses = reflections.getTypesAnnotatedWith(CustomBean.class);
        for (var annotatedClass : annotatedClasses) {
            var instance = annotatedClass.getConstructor().newInstance();
            var annotationValue = annotatedClass.getAnnotation(CustomBean.class).value();
            var beanName = !annotationValue.isEmpty() ? annotationValue :
                    StringUtils.uncapitalize(annotatedClass.getSimpleName()) ;
            beans.put(beanName, instance);
        }
        beans.values().forEach(b -> {
            var fields = b.getClass().getDeclaredFields();
            Arrays.stream(fields)
                    .filter(f -> f.isAnnotationPresent(CustomInject.class))
                    .forEach(field -> injectField(field, b));
        });
    }

    @SneakyThrows
    private <T> void injectField(Field field, T parentBean) {
        Object foundBean;
        if (field.isAnnotationPresent(SimpleQualifier.class)) {
            var qualifier = field.getAnnotation(SimpleQualifier.class).value();
            foundBean = beans.get(qualifier);
            if (foundBean == null) {
                throw new NoSuchBeanException(qualifier);
            }
        } else {
            foundBean = getBean(field.getType());
        }

        field.setAccessible(true);
        field.set(parentBean, field.getType().cast(foundBean));
    }


    @Override
    public <T> T getBean(Class<T> beanType) throws NoSuchBeanException, NoUniqueBeanException {
        var beanResult = beans.values().stream()
                .filter(val -> ClassUtils.isAssignable(val.getClass(), beanType))
                .toList();
        if (beanResult.isEmpty()) {
            throw new NoSuchBeanException(beanType.getSimpleName());
        } else if (beanResult.size() > 1) {
            throw new NoUniqueBeanException(beanType.getSimpleName());
        }
        return beanType.cast(beanResult.get(0));
    }

    @Override
    public <T> T getBean(String name, Class<T> beanType) throws NoSuchBeanException {
        var bean = beans.get(name);

        if (bean != null && ClassUtils.isAssignable(bean.getClass(), beanType)) {
            return beanType.cast(bean);
        }
        throw new NoSuchBeanException(beanType.getSimpleName());
    }

    @Override
    public <T> Map<String, T> getAllBeans(Class<T> beanType) {
        return beans.entrySet().stream()
                .filter(val -> ClassUtils.isAssignable(val.getValue().getClass(), beanType))
                .collect(Collectors.toMap(Map.Entry::getKey, v -> beanType.cast(v.getValue())));
    }
}
