package com.yjsserrano.ecommerce;


import java.lang.reflect.Field;

/**
 * The {@link MockField} helps to mock private fields, because Mockito can not do this by yourself
 *
 * @author Yuri Serrano
 * @version 1.0
 * @see <a href="https://youtu.be/UPFUQOdm8zY">Mock Private Field I</a>
 * @see <a href="https://stackoverflow.com/questions/36173947/mockito-mock-private-field-initialization">Mock Private Field II</a>
 * @since 1.0
 */
public class MockField {

    public static void injectPrivateField(Object target, String fieldName, Object toInject) {
        boolean wasPrivate = false;

        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            if (!field.isAccessible()) {
                field.setAccessible(true);
                wasPrivate = true;
            }

            field.set(target, toInject);

            if (wasPrivate) {
                field.setAccessible(false);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
