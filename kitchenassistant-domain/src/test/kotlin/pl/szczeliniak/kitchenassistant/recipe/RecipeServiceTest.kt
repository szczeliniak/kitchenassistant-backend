package pl.szczeliniak.kitchenassistant.recipe

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import pl.szczeliniak.kitchenassistant.recipe.db.AuthorDao
import pl.szczeliniak.kitchenassistant.recipe.db.CategoryDao
import pl.szczeliniak.kitchenassistant.recipe.db.IngredientDao
import pl.szczeliniak.kitchenassistant.recipe.db.IngredientGroupDao
import pl.szczeliniak.kitchenassistant.recipe.db.Recipe
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeCriteria
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.recipe.db.StepDao
import pl.szczeliniak.kitchenassistant.recipe.db.StepGroupDao
import pl.szczeliniak.kitchenassistant.recipe.db.TagDao
import pl.szczeliniak.kitchenassistant.recipe.dto.response.RecipeResponse
import pl.szczeliniak.kitchenassistant.recipe.dto.response.RecipesResponse
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.RequestContext
import pl.szczeliniak.kitchenassistant.shared.TokenType
import pl.szczeliniak.kitchenassistant.shared.dtos.Page
import pl.szczeliniak.kitchenassistant.user.db.UserDao
import java.util.*

class RecipeServiceTest {

    private val recipeDao: RecipeDao = mockk()
    private val authorDao: AuthorDao = mockk()
    private val tagDao: TagDao = mockk()
    private val categoryDao: CategoryDao = mockk()
    private val userDao: UserDao = mockk()
    private val recipeMapper: RecipeMapper = mockk()
    private val ingredientGroupDao: IngredientGroupDao = mockk()
    private val ingredientDao: IngredientDao = mockk()
    private val stepGroupDao: StepGroupDao = mockk()
    private val stepDao: StepDao = mockk()
    private val requestContext: RequestContext = mockk()
    private val recipeService = RecipeService(recipeDao, authorDao, tagDao, categoryDao, userDao, recipeMapper, ingredientGroupDao, ingredientDao, stepGroupDao, stepDao, requestContext)

    @Test
    fun shouldFindById() {
        val recipe = recipe()
        val recipeResponse = RecipeResponse.Recipe(0, "", "", "", "", true, null, Collections.emptyList(), Collections.emptyList(), Collections.emptyList())
        every { requestContext.tokenType() } returns TokenType.ACCESS
        every { requestContext.userId() } returns 2
        every { recipeDao.findById(1, null, 2) } returns recipe
        every { recipeMapper.mapDetails(recipe) } returns recipeResponse

        val response = recipeService.findById(1)

        Assertions.assertEquals(RecipeResponse(recipeResponse), response)
    }

    @Test
    fun shouldThrowExceptionWhenFindByIdAndTokenTypeOtherThanAccess() {
        every { requestContext.tokenType() } returns TokenType.REFRESH

        val exception = assertThrows<KitchenAssistantException> { recipeService.findById(1) }

        Assertions.assertEquals(exception.error, ErrorCode.WRONG_TOKEN_TYPE)
    }

    @Test
    fun shouldThrowExceptionWhenFindByIdAndRecipeNotFound() {
        every { requestContext.tokenType() } returns TokenType.ACCESS
        every { requestContext.userId() } returns 2
        every { recipeDao.findById(1, null, 2) } returns null

        val exception = assertThrows<KitchenAssistantException> { recipeService.findById(1) }

        Assertions.assertEquals(exception.error, ErrorCode.RECIPE_NOT_FOUND)
    }

    @Test
    fun shouldFindAll() {
        val recipe = recipe()
        val recipeResponse = RecipesResponse.Recipe(0, "", "", true, null, Collections.emptyList())
        every { requestContext.tokenType() } returns TokenType.ACCESS
        every { requestContext.userId() } returns 2
        every { recipeDao.count(RecipeCriteria(true, 3, "search", "tag", false), 2) } returns 35
        every { recipeDao.findAll(RecipeCriteria(true, 3, "search", "tag", false), 2, 0, 10) } returns Collections.singletonList(recipe)
        every { recipeMapper.map(recipe) } returns recipeResponse

        val response = recipeService.findAll(1, 10, true, 3, "search", "tag")

        Assertions.assertEquals(RecipesResponse(Page(1, 10, 4, Collections.singletonList(recipeResponse))), response)
    }

    @Test
    fun shouldThrowExceptionWhenFindAllAndTokenTypeOtherThanAccess() {
        every { requestContext.tokenType() } returns TokenType.REFRESH

        val exception = assertThrows<KitchenAssistantException> { recipeService.findAll(1, 10, true, 3, "search", "tag") }

        Assertions.assertEquals(exception.error, ErrorCode.WRONG_TOKEN_TYPE)
    }

    private fun recipe(): Recipe {
        return Recipe()
    }

}