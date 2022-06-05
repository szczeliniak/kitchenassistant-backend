package pl.szczeliniak.kitchenassistant.user

import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import java.time.ZonedDateTime

data class User(
    private val id_: Int = 0,
    private val email_: String,
    private val password_: String?,
    private val name_: String,
    private val createdAt_: ZonedDateTime = ZonedDateTime.now(),
    private val modifiedAt_: ZonedDateTime = ZonedDateTime.now()
) {

    val id: Int get() = id_
    val email: String get() = email_
    val password: String? get() = password_
    val name: String get() = name_
    val createdAt: ZonedDateTime get() = createdAt_
    val modifiedAt: ZonedDateTime get() = modifiedAt_

    fun validatePassword(password: String, passwordMatcher: PasswordMatcher) {
        this.password?.let {
            if (!passwordMatcher.matches(it, password)) {
                throw KitchenAssistantException(ErrorCode.PASSWORDS_DO_NOT_MATCH)
            }
        }
    }

}