package it.polito.group24.lab3.services

import it.polito.group24.lab3.entities.Activation
import it.polito.group24.lab3.entities.User
import it.polito.group24.lab3.exceptions.DuplicateEntryException
import it.polito.group24.lab3.exceptions.UnmatchedActivationCodeException
import it.polito.group24.lab3.exceptions.WrongActivationIDException
import it.polito.group24.lab3.repositories.ActivationRepository
import it.polito.group24.lab3.repositories.UserRepository
import org.springframework.stereotype.Service
import java.sql.SQLException
import java.sql.Timestamp
import java.util.*

@Service
class UserService(
    val userRepository: UserRepository,
    val activationRepository: ActivationRepository,
    val emailService: EmailService) {

    /**
     * Creates an alphanumeric random string of 10 characters
     */
    private fun createRandomString(): String{
        val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return  (1..10)
            .map { i -> kotlin.random.Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
    }

    /**
     * Adds an activation record in the activations table and returns the entity
     */
    private fun addActivation(user: User, activationCode: String): Activation{
        return activationRepository.
            save( Activation(user, activationCode = activationCode))
    }

    /**
     * Adds a user record in the users table and returns the entity
     */
    private fun addUser(username: String, password: String, email: String): User{
        return userRepository.save( User(username, password, email) )
    }

    /**
     * Checks that no user with same username or email exists, generates a random activation code
     * and sends it via email to the supplied address. Creates a new User entry,
     * which is not yet active, and a new Activation entry
     */
    fun registerNewUser(username: String, password: String, email: String): UUID {
        // First check if username and email are unique
        if(userRepository.getUserByUsername(username).isPresent)
            throw DuplicateEntryException("Username already present in the DB.")
        if(userRepository.getUserByEmail(email).isPresent)
            throw DuplicateEntryException("Email already present in the DB.")

        // Add records in the db
        val user = addUser(username, password, email)
        val activation = addActivation(user, createRandomString())

        emailService.sendEmail("Activation Code", activation.activationCode, email)

        return activation.getId()!!
    }

    /**
     *
     */
    fun verifyUser(provisionalId: UUID, activationCode: String) {

        val activation = activationRepository.findById(provisionalId)

        if (activation.isPresent) {
            // Wrong activation code
            if (activation.get().activationCode != activationCode) {
                if (activation.get().attemptCounter == 1) {
                    // When attempt counter reaches zero, activation and user record are removed
                    val userId = activation.get().user!!.getId()!!
                    activationRepository.deleteById(provisionalId)
                    userRepository.deleteById(userId)
                }
                else
                    activationRepository.decrementAttemptCounter(provisionalId)
                throw UnmatchedActivationCodeException("The activation code provided is wrong.")
            }
            // Correct activation code
            val userId = activation.get().user!!.getId()!!
            activationRepository.deleteById(provisionalId)
            userRepository.setActive(userId)
        } else
            throw WrongActivationIDException("The activation id is wrong.")
    }
}