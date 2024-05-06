package pl.szczeliniak.cookbook.user

interface PasswordEncoder {

    fun encode(rawPassword: String): String

}