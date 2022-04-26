package it.polito.group24.lab3.entities

import java.sql.Timestamp
import java.util.*
import java.util.concurrent.TimeUnit
import javax.persistence.*

@Entity
@Table(name = "activations")
class Activation (
    @OneToOne(cascade = [CascadeType.ALL])
    var user: User?,
    @Column(nullable = false)
    var deadline: Timestamp =
        Timestamp(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(30)),
    @Column(nullable = false)
    var activationCode: String,
    @Column(nullable = false)
    var attemptCounter: Int = 5
): EntityBase<UUID>() {}
