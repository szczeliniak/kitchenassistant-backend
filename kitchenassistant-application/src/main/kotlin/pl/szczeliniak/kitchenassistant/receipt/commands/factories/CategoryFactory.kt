package pl.szczeliniak.kitchenassistant.receipt.commands.factories

import pl.szczeliniak.kitchenassistant.receipt.Category
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.NewCategoryDto

open class CategoryFactory {

    open fun create(dto: NewCategoryDto): Category {
        return Category(0, dto.name, dto.userId, dto.sequence)
    }

}