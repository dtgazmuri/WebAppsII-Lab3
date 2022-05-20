package it.group24.lab3.customExceptions

class WrongFieldException(m:String) : Exception(m)

class UsernameAlreadyRegisteredException(m: String): Exception(m)

class EmailNotSentException(m: String): Exception(m)

class ActivationNotFoundException(m: String): Exception(m)

class ActivationCodeNotValidException(m: String): Exception(m)

class AttemptCounterException(m: String): Exception(m)

class EmailAlreadyRegisteredException(m: String): Exception(m)

class WrongCredentials(m: String): Exception(m)

