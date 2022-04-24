package it.group24.lab3.repositories

import it.group24.lab3.entities.Activation
import it.group24.lab3.entities.User
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface ActivationRepository: CrudRepository<Activation, UUID> {

    @Modifying
    @Query("delete from Activation where deadline < ?1")
    fun deleteExpiredActivation(date: Date);

    fun findActivationByUser(user: User): Optional<Activation>;

    @Query("select user from Activation where activationID = ?1")
    fun findUserByActivationID(activationID: UUID): Optional<User>

    fun findActivationByActivationID(activationID: UUID): Optional<Activation>

    @Modifying
    @Query("update Activation set attemptCounter = (attemptCounter - 1) where activationID = ?1")
    fun decreaseCounterByID(activationID: UUID);

    @Query("select activationID from Activation where user = ?1")
    fun getActivationIDByUser(user: User): Optional<UUID>
}