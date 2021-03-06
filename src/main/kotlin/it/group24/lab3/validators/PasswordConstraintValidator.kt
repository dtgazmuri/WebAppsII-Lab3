package it.group24.lab3.validators

import java.util.regex.Pattern
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class PasswordConstraintValidator : ConstraintValidator<PasswordConstraint, String> {
    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        return (value != null && Pattern.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@\$%^&*-])\\S{8,}\$", value))
    }
}