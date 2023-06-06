package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.dayplan.db.DayPlanDao
import pl.szczeliniak.kitchenassistant.dayplan.queries.dto.DayPlanCriteria
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListCriteria
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListDao

class DeleteRecipeCommand(
    private val recipeDao: RecipeDao,
    private val dayPlanDao: DayPlanDao,
    private val shoppingListDao: ShoppingListDao
) {

    fun execute(id: Int): SuccessResponse {
        recipeDao.findById(id)?.let {
            val dayPlans = dayPlanDao.findAll(DayPlanCriteria(recipeId = it.id))
            dayPlans.forEach { dayPlan ->
                dayPlan.recipeIds.remove(it.id)
                if (dayPlan.recipeIds.isEmpty()) {
                    dayPlanDao.delete(dayPlan)
                } else {
                    dayPlanDao.save(dayPlan)
                }
            }

            val shoppingLists = shoppingListDao.findAll(ShoppingListCriteria(recipeId = it.id))
            shoppingLists.forEach { shoppingList ->
                shoppingList.items.filter { item -> item.recipeId == it.id }.forEach { item -> item.recipeId = null }
            }
            shoppingListDao.save(shoppingLists)

            recipeDao.delete(it)
        }
        return SuccessResponse(id)
    }

}