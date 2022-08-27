/*
package com.each.adsc.blockingbutcher.utils

import java.util.stream.Stream
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class ValueOfEnumValidator : ConstraintValidator<ValueOfEnum, CharSequence> {
    private val acceptedValues: MutableList<String> = mutableListOf()

    override fun initialize(constraintAnnotation: ValueOfEnum?) {
        super.initialize(constraintAnnotation)
        acceptedValues.addAll(constraintAnnotation.enumClass.java
            .enumConstants
            .map {it.name}) = Stream.of(constraintAnnotation.)
    }

    override fun isValid(value: CharSequence?, context: ConstraintValidatorContext?): Boolean {
        if (value == null) {
            return true;
        }
        return acceptedValues.contains(value.toString());
    }

}*/
