package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.dayplan.db.DayPlanDao
import pl.szczeliniak.kitchenassistant.dayplan.dto.DayPlanCriteria
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
                dayPlan.recipes.removeIf { recipe -> recipe.id == it.id }
                if (dayPlan.recipes.isEmpty()) {
                    dayPlanDao.delete(dayPlan.date)
                } else {
                    dayPlanDao.save(dayPlan)
                }
            }

            val shoppingLists = shoppingListDao.findAll(ShoppingListCriteria(recipeId = it.id))
            shoppingLists.forEach { shoppingList ->
                shoppingList.items.filter { item -> item.recipe?.id == it.id }.forEach { item -> item.recipe = null }
            }
            shoppingListDao.save(shoppingLists)

            recipeDao.delete(it)
        }
        return SuccessResponse(id)
    }

}