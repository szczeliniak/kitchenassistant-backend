package pl.szczeliniak.kitchenassistant.user

interface PasswordEncoder {

    fun encode(rawPassword: String): String

}