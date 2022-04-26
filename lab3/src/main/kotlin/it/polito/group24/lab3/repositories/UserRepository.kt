package it.polito.group24.lab3.repositories

import it.polito.group24.lab3.entities.User
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository: CrudRepository<User, Long> {

    fun getUserByUsername(username: String): Optional<User>

    fun getUserByEmail(email: String): Optional<User>

    @Modifying
    @Query("update User set isActive = true where id = ?1")
    fun setActive(id: Long);
}