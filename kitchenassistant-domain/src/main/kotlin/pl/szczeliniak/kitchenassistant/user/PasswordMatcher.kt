package pl.szczeliniak.kitchenassistant.user

interface PasswordMatcher {

    fun matches(encryptedPassword: String, rawPassword: String): Boolean

}