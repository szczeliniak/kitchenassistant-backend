package pl.szczeliniak.kitchenassistant.recipe.commands.factories

import pl.szczeliniak.kitchenassistant.recipe.db.Tag
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.user.db.User
import pl.szczeliniak.kitchenassistant.user.db.UserDao

open class TagFactory(private val userDao: UserDao) {

    open fun create(name: String, userId: Int): Tag {
        return create(name, userDao.findById(userId) ?: throw KitchenAssistantException(ErrorCode.USER_NOT_FOUND))
    }

    open fun create(name: String, user: User): Tag {
        return Tag(0, name, user)
    }

}