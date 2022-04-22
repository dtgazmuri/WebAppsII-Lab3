package it.group24.lab3.validators

import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class PasswordConstraintValidator : ConstraintValidator<PasswordConstraint, String> {
    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        return (value != null && value.length > 8 && value.contains(Regex("[0-9]+"))
                && value.contains(Regex("[a-z]+")) && value.contains(Regex("[A-Z]+"))
                && value.contains(Regex("[a-z]+")) && value.contains(Regex("[^\\w]+")))

    }
}