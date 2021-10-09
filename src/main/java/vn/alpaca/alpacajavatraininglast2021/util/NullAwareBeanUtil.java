package vn.alpaca.alpacajavatraininglast2021.util;

import org.apache.commons.beanutils.BeanUtilsBean;

import java.lang.reflect.InvocationTargetException;

public class NullAwareBeanUtil extends BeanUtilsBean {

    @Override
    public void copyProperty(Object bean, String name, Object value)
            throws IllegalAccessException, InvocationTargetException {
        if (value == null) return;
        super.copyProperty(bean, name, value);
    }
}
