package pl.szczeliniak.kitchenassistant.security

import pl.szczeliniak.kitchenassistant.dayplan.db.DayPlanDao
import pl.szczeliniak.kitchenassistant.recipe.db.CategoryDao
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.RequestContext
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListDao

class AuthorizationService(
    private val requestContext: RequestContext,
    private val recipeDao: RecipeDao,
    private val categoryDao: CategoryDao,
    private val dayPlanDao: DayPlanDao,
    private val shoppingListDao: ShoppingListDao
) {

    fun checkIsOwnerOfRecipe(recipeId: Int) {
        if (!isOwnerOfRecipe(recipeId)) {
            throw KitchenAssistantException(ErrorCode.AUTHORIZATION_ERROR)
        }
    }

    private fun isOwnerOfRecipe(recipeId: Int): Boolean {
        if (requestContext.userId() == null) {
            return false
        }
        return recipeDao.findById(recipeId)?.let { recipe ->
            requestContext.userId() == (recipe.user.id)
        } ?: false
    }

    fun checkIsOwnerOfCategory(categoryId: Int) {
        if (!isOwnerOfCategory(categoryId)) {
            throw KitchenAssistantException(ErrorCode.AUTHORIZATION_ERROR)
        }
    }

    private fun isOwnerOfCategory(categoryId: Int): Boolean {
        if (requestContext.userId() == null) {
            return false
        }
        return categoryDao.findById(categoryId)?.let { category ->
            requestContext.userId() == (category.user.id)
        } ?: false
    }

    fun checkIsOwner(userId: Int?) {
        if (!isOwner(userId)) {
            throw KitchenAssistantException(ErrorCode.AUTHORIZATION_ERROR)
        }
    }

    private fun isOwner(userId: Int?): Boolean {
        if (userId == null) {
            return true
        }
        return requestContext.userId() == userId
    }

    fun checkIsOwnerOfDayPlan(dayPlanId: Int) {
        if (!isOwnerOfDayPlan(dayPlanId)) {
            throw KitchenAssistantException(ErrorCode.AUTHORIZATION_ERROR)
        }
    }

    private fun isOwnerOfDayPlan(dayPlanId: Int): Boolean {
        if (requestContext.userId() == null) {
            return false
        }
        return dayPlanDao.findById(dayPlanId)?.let { dayPlan ->
            requestContext.userId() == (dayPlan.user.id)
        } ?: false
    }

    fun checkIsOwnerOfShoppingList(shoppingListId: Int) {
        if (!isOwnerOfShoppingList(shoppingListId)) {
            throw KitchenAssistantException(ErrorCode.AUTHORIZATION_ERROR)
        }
    }

    private fun isOwnerOfShoppingList(shoppingListId: Int): Boolean {
        if (requestContext.userId() == null) {
            return false
        }
        return shoppingListDao.findById(shoppingListId)?.let { shoppingList ->
            requestContext.userId() == (shoppingList.user.id)
        } ?: false
    }

    fun checkIsAdmin() {
        if (!isAdmin()) {
            throw KitchenAssistantException(ErrorCode.AUTHORIZATION_ERROR)
        }
    }

    private fun isAdmin(): Boolean {
        return true
    }

}