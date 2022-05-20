package it.group24.lab3.unitTest

import it.group24.lab3.entities.Activation
import it.group24.lab3.entities.User
import it.group24.lab3.repositories.ActivationRepository
import it.group24.lab3.repositories.UserRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import java.util.*

@SpringBootTest

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class UnitTests(){

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var activationRepository: ActivationRepository

    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    fun initialize(): List<User>{
        val users: List<User> = mutableListOf(
            User().apply {
                username = "Luca"
                password = "secret"
                email = "luca@luca.it"
            },
            User().apply {
                username = "Cristina"
                password = "secret"
                email = "cristina@cristina.it"
            },
            User().apply {
                username = "Diego"
                password = "secret"
                email = "diego@diego.cile"
            },
            User().apply {
                username = "Aliresa"
                password = "secret"
                email = "aliresa@aliresa.iran"
            },
            User().apply {
                username = "Mammata"
                password = "secret"
                email = "mammata@mammata.put"
            }
        )
        return users
    }

    fun getDeadLine(): Date{
        var c: Calendar = Calendar.getInstance()
        c.time = Date()
        c.add(Calendar.DAY_OF_MONTH, 5)
        return c.time
    }

    @AfterEach
    fun cleanup(){
        activationRepository.deleteAll()
        userRepository.deleteAll()
        jdbcTemplate.execute("ALTER SEQUENCE user_generator RESTART WITH 1")
    }


    @Test
    @Order(1)
    fun testSaveUser(){
            val userToSave: User = User().apply {
                username = "Luca"
                password = "secret"
                email = "luca@luca.it"
            }
            Assertions.assertDoesNotThrow{userRepository.save(userToSave)}

    }

    @Test
    @Order(2)
    fun testSaveActivation(){
        var date = getDeadLine()
        val userToSave: User = User().apply {
            username = "Luca"
            password = "secret"
            email = "luca@luca.it"
            isActive = false
        }
        val activationToSave: Activation = Activation().apply {
            activationCode = "ciao"
            deadline = date
            attemptCounter = 5
            user = userToSave
        }
        Assertions.assertDoesNotThrow{userRepository.save(userToSave)}
        Assertions.assertDoesNotThrow{activationRepository.save(activationToSave)}
    }

    @Test
    @Order(3)
    fun testSaveAll() {

            var usersInserted = listOf<User>()
            val date = getDeadLine()
            val usersToSave: List<User> = mutableListOf(
                User().apply {
                    username = "Luca"
                    password = "secret"
                    email = "luca@luca.it"
                },
                User().apply {
                    username = "Cristina"
                    password = "secret"
                    email = "cristina@cristina.it"

                },
                User().apply {
                    username = "Diego"
                    password = "secret"
                    email = "diego@diego.cile"

                },
                User().apply {
                    username = "Aliresa"
                    password = "secret"
                    email = "aliresa@aliresa.iran"

                },
                User().apply {
                    username = "Mammata"
                    password = "secret"
                    email = "mammata@mammata.put"

                }
            )
            Assertions.assertDoesNotThrow{ usersInserted = userRepository.saveAll(usersToSave).toList()}
            val activationsToSave: List<Activation> = mutableListOf(
                Activation().apply {
                    activationCode = "ciao"
                    deadline = date
                    attemptCounter = 5
                    user = usersInserted[0]
                },
                Activation().apply {
                    activationCode = "come"
                    deadline = date
                    attemptCounter = 5
                    user = usersInserted[1]
                },
                Activation().apply {
                    activationCode = "stai"
                    deadline = date
                    attemptCounter = 5
                    user = usersInserted[2]
                },
                Activation().apply {
                    activationCode = "io"
                    deadline = date
                    attemptCounter = 5
                    user = usersInserted[3]
                },
                Activation().apply {
                    activationCode = "bene"
                    deadline = date
                    attemptCounter = 5
                    user = usersInserted[4]
                }
            )
            Assertions.assertDoesNotThrow{activationRepository.saveAll(activationsToSave)}
        }


    @Test
    @Order(4)
    fun testCount(){
            val users = initialize()
            userRepository.saveAll(users)
            Assertions.assertEquals(5, userRepository.count())
        }


    @Test
    @Order(5)
    fun testDelete(){

            val users = initialize()
            userRepository.saveAll(users)
            userRepository.delete(users[4])
            Assertions.assertEquals(4, userRepository.count())

    }

    @Test
    @Order(6)
    fun testDeleteAll(){

            val users = initialize()
            userRepository.saveAll(users)
            userRepository.deleteAll()
            Assertions.assertEquals(0, userRepository.count())

    }

    @Test
    @Order(7)
    fun testFindById(){

            val users = initialize()
            userRepository.saveAll(users)
            Assertions.assertTrue(userRepository.findById(2).isPresent)

    }

    @Test
    @Order(8)
    fun testFind(){

            val date = getDeadLine()
            val usersToSave = initialize()
            userRepository.saveAll(usersToSave)
            val activationsToSave: List<Activation> = mutableListOf(
                Activation().apply {
                    activationCode = "ciao"
                    deadline = date
                    attemptCounter = 5
                    user = usersToSave[0]
                },
                Activation().apply {
                    activationCode = "come"
                    deadline = date
                    attemptCounter = 5
                    user = usersToSave[1]
                },
                Activation().apply {
                    activationCode = "stai"
                    deadline = date
                    attemptCounter = 5
                    user = usersToSave[2]
                },
                Activation().apply {
                    activationCode = "io"
                    deadline = date
                    attemptCounter = 5
                    user = usersToSave[3]
                },
                Activation().apply {
                    activationCode = "bene"
                    deadline = date
                    attemptCounter = 5
                    user = usersToSave[4]
                }
            )
            activationRepository.saveAll(activationsToSave)
            Assertions.assertTrue(userRepository.findByUsername("Luca").isPresent)
            Assertions.assertTrue(activationRepository.findByUser(usersToSave[0]).isPresent)

    }


}