package com.basigo.project.domain.validator;

public interface ConditionalValidator<T> {
    boolean shouldValidate(T object);
    void validate(T object);
}
