package com.example.labgui.domain.validators;

import com.example.labgui.exceptions.ValidationException;

public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}
