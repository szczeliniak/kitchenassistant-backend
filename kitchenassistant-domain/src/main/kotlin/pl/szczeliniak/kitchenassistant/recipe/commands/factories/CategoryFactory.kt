package pl.szczeliniak.kitchenassistant.recipe.commands.factories

import pl.szczeliniak.kitchenassistant.recipe.commands.dto.NewCategoryRequest
import pl.szczeliniak.kitchenassistant.recipe.db.Category
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.user.db.UserDao

open class CategoryFactory(private val userDao: UserDao) {

    open fun create(request: NewCategoryRequest): Category {
        return Category(
            0,
            request.name,
            userDao.findById(request.userId) ?: throw KitchenAssistantException(ErrorCode.USER_NOT_FOUND),
            request.sequence
        )
    }

}