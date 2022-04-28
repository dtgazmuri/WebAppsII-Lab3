package it.polito.group24.lab3.repositories

import it.polito.group24.lab3.entities.Activation
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.sql.Timestamp
import java.util.*

@Repository
interface ActivationRepository : CrudRepository<Activation, UUID> {

    @Modifying
    @Query("update Activation set attemptCounter = attemptCounter - 1 where id = ?1")
    fun decrementAttemptCounter(id: UUID);

    fun findByDeadlineBefore(timestamp: Timestamp): Set<Activation>;
}