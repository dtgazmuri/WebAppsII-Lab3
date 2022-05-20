package it.group24.lab3.validators

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass


@MustBeDocumented
@Target(allowedTargets = [AnnotationTarget.FIELD])
@Constraint(validatedBy = [PasswordConstraintValidator::class])
annotation class PasswordConstraint(
    val message: String = "Password invalid! It must be\n" +
            "at least 8 characters long, it must contain at least one digit, one uppercase letter, one\n" +
            "lowercase letter, one non alphanumeric character",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)