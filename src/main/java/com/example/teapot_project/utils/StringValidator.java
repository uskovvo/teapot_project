package com.example.teapot_project.utils;

import com.example.teapot_project.exceptions.NotValidDataException;

public class StringValidator {
    public static boolean nameValidation(String name) {
        validation(name, "name");
        return true;
    }

    public static boolean surnameValidation(String surname) {
        validation(surname, "surname");
        return true;
    }

    private static boolean validation(String parameterToValidate, String nameOfParameter) {
        if (parameterToValidate == null || parameterToValidate.length() < 2 || parameterToValidate.length() > 30) {
            throw new NotValidDataException(String.format("%s length must not be less than 2 and higher than 30"));
        }
        if (!parameterToValidate.matches("[a-zA-Zа-яА-Я -]+")) {
            throw new NotValidDataException(String.format("%s must contain only letters", nameOfParameter));
        }
        return true;
    }

}
