package pl.szczeliniak.kitchenassistant.shoppinglist.commands

import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListCriteria
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListDao
import java.time.LocalDate

class ArchiveShoppingListsCommand(private val shoppingListDao: ShoppingListDao) {

    fun execute() {
        val shoppingLists = shoppingListDao.findAll(
            ShoppingListCriteria(
                archived = false,
                automaticArchiving = true,
                maxDate = LocalDate.now().minusDays(1)
            )
        )
        shoppingLists.forEach { it.archived = true }
        shoppingListDao.save(shoppingLists)
    }

}