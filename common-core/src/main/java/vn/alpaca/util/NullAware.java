package vn.alpaca.util;

import org.apache.commons.beanutils.BeanUtilsBean;

import java.lang.reflect.InvocationTargetException;

public final class NullAware extends BeanUtilsBean {

    @Override
    public void copyProperty(Object bean, String name, Object value)
            throws IllegalAccessException, InvocationTargetException {
        if (value == null) return;
        super.copyProperty(bean, name, value);
    }
}
