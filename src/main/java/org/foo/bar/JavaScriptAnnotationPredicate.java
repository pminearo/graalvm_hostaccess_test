package org.foo.bar;

import java.lang.reflect.Method;
import java.util.function.Predicate;

import org.apache.commons.lang3.StringUtils;

public class JavaScriptAnnotationPredicate implements Predicate<String> {

    @Override
    public boolean test(final String className) {

        if (StringUtils.isBlank(className)) {
            return false;
        }

        try {
            @SuppressWarnings("unchecked")
            final Class<Object> clazz = (Class<Object>) Class.forName(className);

            if (clazz == null) {
                return false;
            }

            return hasJsAnnotations(clazz);
        } catch (ClassNotFoundException e) {
            // Do Nothing. We will ignore as it will keep anyone 'fishing' from knowing what is going on.
        }

        return false;
    }

    boolean hasJsAnnotations(final Class<Object> clazz) {

        if (clazz.isAnnotationPresent(JsClass.class)) {
            return true;
        } else {

            final Method[] methods = clazz.getMethods();

            for (final Method method : methods) {

                if (method.isAnnotationPresent(JsFunction.class)) {
                    return true;
                }

            }

        }

        return false;
    }

}