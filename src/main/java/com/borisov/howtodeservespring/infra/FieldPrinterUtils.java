package com.borisov.howtodeservespring.infra;

import java.lang.reflect.Field;
import java.util.List;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FieldPrinterUtils {

    @SneakyThrows
    public static void printFieldsWithValues(String[] fieldNames, Object originalObject) {
        if (originalObject == null) {
            System.out.println("Object is null");
            return;
        }

        if (fieldNames == null) {
            System.out.println("No field names provided");
            return;
        }

        Class<?> clazz = originalObject.getClass();
        System.out.println("Fields of object " + clazz.getSimpleName() + ":");

        for (String fieldName : fieldNames) {
            Field field = findField(clazz, fieldName);
            if (field != null) {
                field.setAccessible(true);
                Object value = field.get(originalObject);
                System.out.println("  " + fieldName + " = " + formatValue(value));
            } else {
                System.out.println("  " + fieldName + " = FIELD NOT FOUND");
            }
        }
    }

    @SneakyThrows
    public static void printFieldsWithValuesDetailed(List<String> fieldNames, Object originalObject) {
        if (originalObject == null) {
            System.out.println("Object is null");
            return;
        }

        if (fieldNames == null || fieldNames.isEmpty()) {
            System.out.println("No field names provided");
            return;
        }

        Class<?> clazz = originalObject.getClass();
        System.out.println("=== Fields of " + clazz.getName() + " ===");

        for (String fieldName : fieldNames) {
            Field field = findField(clazz, fieldName);
            if (field != null) {
                field.setAccessible(true);
                Object value = field.get(originalObject);
                String type = field.getType().getSimpleName();

                System.out.printf("  %-20s : %-10s = %s%n",
                                  fieldName,
                                  type,
                                  formatValue(value)
                );
            } else {
                System.out.printf("  %-20s : %-10s = %s%n",
                                  fieldName,
                                  "UNKNOWN",
                                  "FIELD NOT FOUND"
                );
            }
        }
        System.out.println("=== End of fields ===");
    }

    // Поиск поля в классе и его родителях
    @SneakyThrows
    private static Field findField(Class<?> clazz, String fieldName) {
        Class<?> currentClass = clazz;

        while (currentClass != null) {
            Field[] fields = currentClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.getName().equals(fieldName)) {
                    return field;
                }
            }
            currentClass = currentClass.getSuperclass();
        }
        return null;
    }

    // Форматирование значения для красивого вывода
    private static String formatValue(Object value) {
        if (value == null) {
            return "null";
        }

        if (value instanceof String) {
            return "\"" + value + "\"";
        }

        if (value instanceof Character) {
            return "'" + value + "'";
        }

        if (value.getClass().isArray()) {
            return java.util.Arrays.toString((Object[]) value);
        }

        return value.toString();
    }
}
