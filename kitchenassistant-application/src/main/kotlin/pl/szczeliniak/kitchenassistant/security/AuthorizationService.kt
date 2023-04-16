package pl.szczeliniak.kitchenassistant.security

import pl.szczeliniak.kitchenassistant.dayplan.db.DayPlanDao
import pl.szczeliniak.kitchenassistant.recipe.db.CategoryDao
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.shared.RequestContext
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListDao

class AuthorizationService(private val requestContext: RequestContext,
                           private val recipeDao: RecipeDao,
                           private val categoryDao: CategoryDao,
                           private val dayPlanDao: DayPlanDao,
                           private val shoppingListDao: ShoppingListDao) {

    fun isOwnerOfRecipe(recipeId: Int): Boolean {
        if (requestContext.userId() == null) {
            return false
        }
        return recipeDao.findById(recipeId)?.let { recipe ->
            requestContext.userId() == (recipe.userId)
        } ?: false
    }

    fun isOwnerOfCategory(categoryId: Int): Boolean {
        if (requestContext.userId() == null) {
            return false
        }
        return categoryDao.findById(categoryId)?.let { category ->
            requestContext.userId() == (category.userId)
        } ?: false
    }

    fun isOwner(userId: Int?): Boolean {
        if (userId == null) {
            return true
        }
        return requestContext.userId() == userId
    }

    fun isOwnerOfDayPlan(dayPlanId: Int): Boolean {
        if (requestContext.userId() == null) {
            return false
        }
        return dayPlanDao.findById(dayPlanId)?.let { dayPlan ->
            requestContext.userId() == (dayPlan.userId)
        } ?: false
    }

    fun isOwnerOfShoppingList(shoppingListId: Int): Boolean {
        if (requestContext.userId() == null) {
            return false
        }
        return shoppingListDao.findById(shoppingListId)?.let { shoppingList ->
            requestContext.userId() == (shoppingList.userId)
        } ?: false
    }

    fun isAdmin(): Boolean {
        return true
    }

}