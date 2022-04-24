package it.group24.lab3.unitTest

import it.group24.lab3.entities.User
import it.group24.lab3.entities.toDTO
import it.group24.lab3.repositories.ActivationRepository
import it.group24.lab3.repositories.UserRepository
import it.group24.lab3.services.UserServiceImplementation
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class ServicesTests() {

    @Autowired
    lateinit var userServiceImplementation: UserServiceImplementation
    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var activationRepository: ActivationRepository

    @BeforeEach
    fun emptyTables(){
        activationRepository.deleteAll()
        userRepository.deleteAll()
    }

    @Test
    @Order(1)
    fun testAddUser(){
        var user = User().apply{
            username = "Luca"
            password = "secret"
            email = "luca@luca.it"
        }
        Assertions.assertDoesNotThrow { (userServiceImplementation.addUser(user.toDTO())) }
        user = userRepository.findUserByUsername("Luca").get()
        Assertions.assertTrue(activationRepository.findActivationByUser(user).isPresent)
        Assertions.assertTrue(userRepository.findUserByUsername("Luca").isPresent)
    }

}