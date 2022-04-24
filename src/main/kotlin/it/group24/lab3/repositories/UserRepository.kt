package it.group24.lab3.repositories

import it.group24.lab3.entities.User
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository: CrudRepository<User, Long> {

    fun findUserByUsername(username: String): Optional<User>;

    @Query("select id from User where username = ?1")
    fun findUserIDByUsername(username: String): Optional<Long>

    @Modifying
    @Query("update User set isActive = ?2 where id = ?1")
    fun updateActivationStatus(activationStatus: Boolean, userID: Long);


}

