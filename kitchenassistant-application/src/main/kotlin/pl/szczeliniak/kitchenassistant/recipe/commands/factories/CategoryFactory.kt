package pl.szczeliniak.kitchenassistant.recipe.commands.factories

import pl.szczeliniak.kitchenassistant.recipe.Category
import pl.szczeliniak.kitchenassistant.recipe.commands.dto.NewCategoryDto

open class CategoryFactory {

    open fun create(dto: NewCategoryDto): Category {
        return Category(0, dto.name, dto.userId, dto.sequence)
    }

}