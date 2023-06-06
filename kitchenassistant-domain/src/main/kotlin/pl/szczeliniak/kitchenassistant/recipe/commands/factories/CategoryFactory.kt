package pl.szczeliniak.kitchenassistant.recipe.commands.factories

import pl.szczeliniak.kitchenassistant.recipe.commands.dto.NewCategoryDto
import pl.szczeliniak.kitchenassistant.recipe.db.Category
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.user.db.UserDao

open class CategoryFactory(private val userDao: UserDao) {

    open fun create(dto: NewCategoryDto): Category {
        return Category(
            0,
            dto.name,
            userDao.findById(dto.userId) ?: throw KitchenAssistantException(ErrorCode.USER_NOT_FOUND),
            dto.sequence
        )
    }

}