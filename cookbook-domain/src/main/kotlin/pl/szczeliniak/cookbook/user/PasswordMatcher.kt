package pl.szczeliniak.cookbook.user

interface PasswordMatcher {

    fun matches(encryptedPassword: String, rawPassword: String): Boolean

}