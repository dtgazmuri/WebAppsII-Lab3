package it.polito.group24.lab3.entities

import javax.persistence.*

@Entity
@Table(name="users")
class User(
    @Column(nullable = false, unique = true)
    var username: String = "",
    @Column(nullable = false)
    var password: String = "",
    @Column(nullable = false, unique = true)
    var email: String = "",
    @Column(nullable = false)
    var isActive: Boolean = false
): EntityBase<Long>() {}