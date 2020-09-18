package com.egar.exercise.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.stream.Collectors;

public class AppUtils {

    /**
     * Словарь ошибок валидации из BindingResult
     *
     * @param bindingResult
     * @return словарь
     */
    public static Map<String, String> getErrorsMap(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream()
                .collect(
                        Collectors.toMap(
                                fieldError -> fieldError.getField() + " ошибка ", FieldError::getDefaultMessage));
    }

    /**
     * Преобразование словаря ошибок валидации в строку нужного формата
     *
     * @param map - словарь ошибок валидации
     * @return строка ошибок валидации, строки разделены переносом
     */
    public static String mapToString(Map<String, String> map) {
        return map.keySet().stream()
                .map(key -> /*key + ": " +*/ map.get(key))
                .collect(Collectors.joining(";\n ", "", ""));
    }

    /**
     * Выброс исключения с сообщением, содержащим ошибки валидации
     *
     * @param bindingResult
     * @throws Exception
     */
    public static void receiveValidationResult(BindingResult bindingResult) throws Exception {
        Map<String, String> errorsMap = AppUtils.getErrorsMap(bindingResult);
        if (!errorsMap.isEmpty()) {
            throw new Exception(AppUtils.mapToString(errorsMap));
        }
    }
}
