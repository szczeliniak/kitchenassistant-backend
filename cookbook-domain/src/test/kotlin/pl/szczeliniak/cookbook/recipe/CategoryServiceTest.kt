package pl.szczeliniak.cookbook.recipe

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import pl.szczeliniak.cookbook.recipe.db.Category
import pl.szczeliniak.cookbook.recipe.db.CategoryDao
import pl.szczeliniak.cookbook.recipe.db.Recipe
import pl.szczeliniak.cookbook.recipe.db.RecipeCriteria
import pl.szczeliniak.cookbook.recipe.db.RecipeDao
import pl.szczeliniak.cookbook.recipe.dto.request.NewCategoryRequest
import pl.szczeliniak.cookbook.recipe.dto.request.UpdateCategoryRequest
import pl.szczeliniak.cookbook.recipe.dto.response.CategoriesResponse
import pl.szczeliniak.cookbook.shared.ErrorCode
import pl.szczeliniak.cookbook.shared.CookBookException
import pl.szczeliniak.cookbook.shared.RequestContext
import pl.szczeliniak.cookbook.shared.TokenType
import pl.szczeliniak.cookbook.shared.dtos.SuccessResponse
import pl.szczeliniak.cookbook.user.db.User
import pl.szczeliniak.cookbook.user.db.UserDao
import java.util.*

class CategoryServiceTest {

    private val recipeDao: RecipeDao = mockk()
    private val categoryDao: CategoryDao = mockk()
    private val userDao: UserDao = mockk()
    private val recipeMapper: RecipeMapper = mockk()
    private val requestContext: RequestContext = mockk()
    private val categoryService = CategoryService(recipeDao, categoryDao, userDao, recipeMapper, requestContext)

    @Test
    fun shouldReturnAll() {
        val category = category()
        val responseCategory = CategoriesResponse.Category(0, "")
        every { requestContext.tokenType() } returns TokenType.ACCESS
        every { requestContext.userId() } returns 1
        every { categoryDao.findAll(1) } returns Collections.singletonList(category)
        every { recipeMapper.mapCategory(category) } returns responseCategory

        val response = categoryService.getAll()

        assertEquals(CategoriesResponse(Collections.singletonList(responseCategory)), response)
    }

    @Test
    fun shouldThrowExceptionWhenReturnAllAndTokenTypeOtherThanAccess() {
        every { requestContext.tokenType() } returns TokenType.REFRESH

        val exception = assertThrows<CookBookException> { categoryService.getAll() }

        assertEquals(exception.error, ErrorCode.WRONG_TOKEN_TYPE)
    }

    @Test
    fun shouldAddNewCategory() {
        val category = category()
        val user = user()
        val request = NewCategoryRequest("")
        every { requestContext.tokenType() } returns TokenType.ACCESS
        every { requestContext.userId() } returns 1
        every { userDao.findById(1) } returns user
        every { recipeMapper.category(0, request, user) } returns category
        every { categoryDao.save(category) } returns category(1)

        val response = categoryService.add(request)

        assertEquals(SuccessResponse(1), response)
    }

    @Test
    fun shouldThrowExceptionWhenAddCategoryAndTokenTypeOtherThanAccess() {
        every { requestContext.tokenType() } returns TokenType.REFRESH

        val exception = assertThrows<CookBookException> { categoryService.add(NewCategoryRequest("")) }

        assertEquals(exception.error, ErrorCode.WRONG_TOKEN_TYPE)
    }

    @Test
    fun shouldThrowExceptionWhenAddCategoryAndUserNotFound() {
        every { requestContext.tokenType() } returns TokenType.ACCESS
        every { requestContext.userId() } returns 1
        every { userDao.findById(1) } returns null

        val exception = assertThrows<CookBookException> { categoryService.add(NewCategoryRequest("")) }

        assertEquals(exception.error, ErrorCode.USER_NOT_FOUND)
    }

    @Test
    fun shouldUpdateCategory() {
        val category = category(1)
        val request = UpdateCategoryRequest("")
        every { requestContext.tokenType() } returns TokenType.ACCESS
        every { requestContext.userId() } returns 2
        every { categoryDao.findById(1, 2) } returns category
        every { recipeMapper.category(category, request) } returns Unit
        every { categoryDao.save(category) } returns category

        val response = categoryService.update(1, request)

        assertEquals(SuccessResponse(1), response)
        verify { recipeMapper.category(category, request) }
    }

    @Test
    fun shouldThrowExceptionWhenUpdateCategoryAndTokenTypeOtherThanAccess() {
        every { requestContext.tokenType() } returns TokenType.REFRESH

        val exception = assertThrows<CookBookException> { categoryService.update(1, UpdateCategoryRequest("")) }

        assertEquals(exception.error, ErrorCode.WRONG_TOKEN_TYPE)
    }

    @Test
    fun shouldThrowExceptionWhenUpdateCategoryAndCategoryNotFound() {
        every { requestContext.tokenType() } returns TokenType.ACCESS
        every { requestContext.userId() } returns 2
        every { categoryDao.findById(1, 2) } returns null

        val exception = assertThrows<CookBookException> { categoryService.update(1, UpdateCategoryRequest("")) }

        assertEquals(exception.error, ErrorCode.CATEGORY_NOT_FOUND)
    }

    @Test
    fun shouldDeleteCategory() {
        val category = category(1)
        val recipe = recipe()
        every { requestContext.tokenType() } returns TokenType.ACCESS
        every { requestContext.userId() } returns 2
        every { categoryDao.findById(1, 2) } returns category
        every { recipeDao.findAll(RecipeCriteria(categoryId = 1), 2) } returns Collections.singletonList(recipe)
        every { recipeDao.save(Collections.singletonList(recipe)) } returns Unit
        every { categoryDao.delete(category) } returns Unit

        val response = categoryService.delete(1)

        assertNull(recipe.category)
        assertEquals(SuccessResponse(1), response)
    }

    @Test
    fun shouldThrowExceptionWhenDeleteCategoryAndTokenTypeOtherThanAccess() {
        every { requestContext.tokenType() } returns TokenType.REFRESH

        val exception = assertThrows<CookBookException> { categoryService.delete(1) }

        assertEquals(exception.error, ErrorCode.WRONG_TOKEN_TYPE)
    }

    @Test
    fun shouldThrowExceptionWhenDeleteCategoryAndCategoryNotFound() {
        every { requestContext.tokenType() } returns TokenType.ACCESS
        every { requestContext.userId() } returns 2
        every { categoryDao.findById(1, 2) } returns null

        val exception = assertThrows<CookBookException> { categoryService.delete(1) }

        assertEquals(exception.error, ErrorCode.CATEGORY_NOT_FOUND)
    }

    private fun category(id: Int = 0): Category {
        return Category(id = id, name = "name", user = User())
    }

    private fun user(): User {
        return User()
    }

    private fun recipe(): Recipe {
        return Recipe(name = "", user = user(), category = category())
    }

}