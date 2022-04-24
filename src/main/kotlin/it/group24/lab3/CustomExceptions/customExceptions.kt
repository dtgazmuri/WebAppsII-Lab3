package it.group24.lab3.CustomExceptions

class WrongFieldException(m:String) : Exception(m)

class UsernameAlreadyRegisteredException(m: String): Exception(m)

class EmailNotSentException(m: String): Exception(m)

class ActivationNotFoundException(m: String): Exception(m)

class ActivationCodeNotValidException(m: String): Exception(m)