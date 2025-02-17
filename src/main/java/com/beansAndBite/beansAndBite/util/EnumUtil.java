package com.beansAndBite.beansAndBite.util;

import java.util.Arrays;
import java.util.stream.Collectors;

public class EnumUtil {
    public static <T extends Enum<T>> T convertToEnum(Class<T> enumType, String str) {
        if (str == null || str.trim().isEmpty()) {
            System.err.println("Invalid input string for enum conversion");
            return null;
        }
        str = str.replaceAll("\\(.*?\\)", "").trim();

        // Checking if the input string already matches an enum value
        for (T enumConstant : enumType.getEnumConstants()) {
            if (enumConstant.name().equals(str)) {
                return enumConstant;
            }
        }

        // Convert to PascalCase with underscores
        String convertedValue = Arrays.stream(str.split("\\s+"))
                .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1))
                .collect(Collectors.joining("_"));

        try {
            return Enum.valueOf(enumType, convertedValue);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid enum value: " + convertedValue + " for enum type: " + enumType.getSimpleName());
            return null;
        }
    }

    public static <T extends Enum<T>> String convertEnumToString(T enumValue) {
        if (enumValue == null) {
            return ""; // Handle null case
        }
        return enumValue.name().replace("_", " ");
    }


}
