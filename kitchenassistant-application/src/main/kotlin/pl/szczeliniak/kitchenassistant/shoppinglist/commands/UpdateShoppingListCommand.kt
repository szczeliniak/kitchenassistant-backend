package pl.szczeliniak.kitchenassistant.shoppinglist.commands

import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shared.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListDao
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.UpdateShoppingListDto

class UpdateShoppingListCommand(private val shoppingListDao: ShoppingListDao) {

    fun execute(id: Int, dto: UpdateShoppingListDto): SuccessResponse {
        val shoppingList = shoppingListDao.findById(id) ?: throw NotFoundException("Shopping list not found")

        shoppingList.update(dto.name, dto.description, dto.date)

        return SuccessResponse(shoppingListDao.save(shoppingList).id)
    }

}