package it.polito.group24.lab3.exceptions

class DuplicateEntryException(message: String): RuntimeException(message)
class UnmatchedActivationCodeException(message: String): RuntimeException(message)
class WrongActivationIDException(message: String): RuntimeException(message)
class DeadlineExpiredException(message: String): RuntimeException(message)