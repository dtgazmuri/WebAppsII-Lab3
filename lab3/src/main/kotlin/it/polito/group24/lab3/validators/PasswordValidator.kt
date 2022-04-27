package it.polito.group24.lab3.validators

import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext


class PasswordValidator : ConstraintValidator<PasswordConstraint?, String?> {
    override fun initialize(password: PasswordConstraint?) {}

    private fun isValidPattern(password: String?): Boolean {
        val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$)$"
        val pattern = Pattern.compile(passwordPattern)
        val matcher = pattern.matcher(password)
        return matcher.matches()
    }
    override fun isValid(
        password: String?,
        cxt: ConstraintValidatorContext
    ): Boolean {
        return (password != null && password.length >= 8 && isValidPattern(password))
    }
}