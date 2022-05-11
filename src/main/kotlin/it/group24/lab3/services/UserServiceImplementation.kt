package it.group24.lab3.services

import it.group24.lab3.CustomExceptions.*
import it.group24.lab3.dtos.UserDTO
import it.group24.lab3.dtos.toUser
import it.group24.lab3.entities.*
import it.group24.lab3.repositories.ActivationRepository
import it.group24.lab3.repositories.UserRepository
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional
import kotlin.NoSuchElementException

@Service
@Transactional
@EnableScheduling
@ConditionalOnProperty(name = ["scheduler.disabled"], matchIfMissing = true)
class UserServiceImplementation(
    private val userRep: UserRepository,
    private val activationRep: ActivationRepository,
    private val emailService: EmailServiceImplementation
) : UserService {


    private fun getDeadLine(): Date {
        val c: Calendar = Calendar.getInstance()
        c.time = Date()
        c.add(Calendar.DAY_OF_MONTH, 5)
        return c.time
    }

    @Scheduled(fixedDelay = 3000)
    fun checkDeadline() {
        activationRep.deleteExpiredActivation(Date())
    }

    override fun addUser(userDTO: UserDTO) {

        fun randomString(length: Long): String {
            val leftLimit = 97; // letter 'a'
            val rightLimit = 122; // letter 'z'
            val random = Random();

            var generatedString: String = random.ints(leftLimit, rightLimit + 1)
                .limit(length)
                .collect({ StringBuilder() }, java.lang.StringBuilder::appendCodePoint, java.lang.StringBuilder::append)
                .toString();

            return generatedString
        }

        val username = userDTO.username!!
        if (userRep.findByUsername(username).isPresent) {
            throw UsernameAlreadyRegisteredException("This username has been already chosen! Try Again")
        }
        if(userRep.findByEmail(userDTO.email!!).isPresent){
            throw EmailAlreadyRegisteredException("This email is already present in the database! Try Again")
        }
        userDTO.roles = mutableListOf<String>(Role.ROLE_CUSTOMER.name)
        val savedUser = userRep.save(userDTO.toUser())
        val activation = Activation().apply {
            activationCode = randomString(6L)
            deadline = getDeadLine()
            attemptCounter = 5
            user = savedUser
        }
        activationRep.save(activation)
        if (!emailService.sendEmail(userDTO, activation.activationCode!!)) {
            throw EmailNotSentException("Problem in sending the email! Please try again")
        }
    }

    override fun changeActiveState(userDTO: UserDTO) {
        val user = userRep.findByUsername(userDTO.username!!).get()
        userRep.updateActivationStatus(!user.isActive, user.id!!)
    }

    override fun getAllUsers(): Collection<UserDTO> {
        return userRep.findAll().map { user -> user.toDTO() }.toList()
    }

    override fun getUserByUsername(username: String): UserDTO {
            return userRep.findByUsername(username).get().toDTO()
    }

    override fun getActivationIDByUser(userDTO: UserDTO): UUID {
        val user = userRep.findByUsername(userDTO.username!!).get()
        return activationRep.getActivationIDByUser(user).get()
    }

    override fun getUserIDByUsername(username: String): Long{
        return userRep.findUserIDByUsername(username).get()
    }

    override fun checkActivationByID(activationID: UUID, activationCode: String) {
        try{
            val activation = activationRep.findActivationByActivationID(activationID).get()
            if (activation.activationCode != activationCode){
                activationRep.decreaseCounterByID(activationID)
                val counter = this.getCounterByID(activationID)
                if(counter == 0){
                    userRep.delete(activationRep.findUserByActivationID(activationID).get())
                    activationRep.delete(activation)
                    throw AttemptCounterException("You have reached your maximum attempts. Fullfill the registration form agai!")
                }
                throw ActivationCodeNotValidException("The activationCode doesn't match!")
            }
        }catch (e: NoSuchElementException){
            throw ActivationNotFoundException("The activationID you have provided is expired or not a valid one !")
        }
    }

    override fun getUserByActivationID(activationID: UUID): UserDTO{
        return activationRep.findUserByActivationID(activationID).get().toDTO()
    }

    override fun deleteActivationByID(activationID: UUID) {
        activationRep.deleteActivationByActivationID(activationID)
    }

    override fun getActivationCodeByActivationID(activationID: UUID): String{
        return activationRep.findActivationCodeByActivationID(activationID).get()
    }

    override fun getCounterByID(activationID: UUID): Int {
        return activationRep.getCounterByID(activationID).get()
    }

    override fun getUserByUsernameAndPassword(username: String, password: String): User {
        val user = userRep.findByUsernameAndPassword(username, password)
        if(!user.isPresent)
            throw WrongCredentials("Username or password you have provided are wrong!!")
        return user.get()
    }

}

