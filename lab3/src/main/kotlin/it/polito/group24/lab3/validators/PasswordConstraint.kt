package it.polito.group24.lab3.validators

import java.lang.annotation.Documented
import java.lang.annotation.ElementType
import java.lang.annotation.RetentionPolicy
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@Documented
@Constraint(validatedBy = [PasswordValidator::class])
@Target(allowedTargets = [AnnotationTarget.FIELD])
annotation class PasswordConstraint(
    val message: String = "Invalid password",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)