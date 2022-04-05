package pl.szczeliniak.kitchenassistant.receipt.commands.factories

import pl.szczeliniak.kitchenassistant.receipt.Category
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.NewCategoryDto

open class CategoryFactory {

    open fun create(dto: NewCategoryDto): Category {
        return Category(name_ = dto.name, userId_ = dto.userId)
    }

}