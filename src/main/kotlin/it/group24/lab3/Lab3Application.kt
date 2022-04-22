package it.group24.lab3

import it.group24.lab3.controllers.UserRegistrationController
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Lab3Application

fun main(args: Array<String>) {
    runApplication<Lab3Application>(*args)
    //SpringApplication.run(UserRegistrationController::class, args);

}
