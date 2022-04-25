package it.polito.group24.lab3.entities

import javax.persistence.*

@Entity
@Table(name="users")
class User(
    var username: String = "",
    var password: String = "",
    var email: String = "",
    var isActive: Boolean = false
): EntityBase<Long>() {}