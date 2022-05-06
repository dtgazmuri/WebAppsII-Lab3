package it.group24.lab3.services


import it.group24.lab3.dtos.UserDTO
import it.group24.lab3.entities.User
import java.util.*


interface UserService {

    fun addUser(userDTO: UserDTO)

    fun changeActiveState(userDTO: UserDTO)

    fun getAllUsers(): Collection<UserDTO>

    fun getUserByUsernameAndPassword(username: String, password: String): User

    fun getUserByUsername(username: String): UserDTO

    fun getUserIDByUsername(username: String): Long

    fun getActivationIDByUser(userDTO: UserDTO): UUID

    fun checkActivationByID(activationID: UUID, activationCode: String)

    fun getUserByActivationID(activationID: UUID): UserDTO

    fun deleteActivationByID(activationID: UUID)

    fun getActivationCodeByActivationID(activationID: UUID): String

    fun getCounterByID(activationID: UUID): Int

}