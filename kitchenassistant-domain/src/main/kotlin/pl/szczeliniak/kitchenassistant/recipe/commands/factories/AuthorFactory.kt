package pl.szczeliniak.kitchenassistant.recipe.commands.factories

import pl.szczeliniak.kitchenassistant.recipe.db.Author
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.user.db.User
import pl.szczeliniak.kitchenassistant.user.db.UserDao

open class AuthorFactory(private val userDao: UserDao) {

    open fun create(name: String, userId: Int): Author {
        return create(name, userDao.findById(userId) ?: throw KitchenAssistantException(ErrorCode.USER_NOT_FOUND))
    }

    open fun create(name: String, user: User): Author {
        return Author(0, name, user)
    }

}