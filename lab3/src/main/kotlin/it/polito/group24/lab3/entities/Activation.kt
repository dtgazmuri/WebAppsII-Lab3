package it.polito.group24.lab3.entities

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "activations")
class Activation (
    @OneToOne(cascade = [CascadeType.ALL])
    var user: User? = null,

    var attemptCounter: Int = 5
): EntityBase<UUID>() {}
