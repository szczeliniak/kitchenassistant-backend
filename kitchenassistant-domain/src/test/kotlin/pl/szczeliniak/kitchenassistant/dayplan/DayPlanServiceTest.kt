package pl.szczeliniak.kitchenassistant.dayplan

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import pl.szczeliniak.kitchenassistant.dayplan.db.DayPlan
import pl.szczeliniak.kitchenassistant.dayplan.db.DayPlanCriteria
import pl.szczeliniak.kitchenassistant.dayplan.db.DayPlanDao
import pl.szczeliniak.kitchenassistant.dayplan.db.IngredientGroupSnapshotDao
import pl.szczeliniak.kitchenassistant.dayplan.db.IngredientSnapshotDao
import pl.szczeliniak.kitchenassistant.dayplan.db.RecipeSnapshot
import pl.szczeliniak.kitchenassistant.dayplan.db.RecipeSnapshotDao
import pl.szczeliniak.kitchenassistant.dayplan.db.Sort
import pl.szczeliniak.kitchenassistant.dayplan.db.StepGroupSnapshotDao
import pl.szczeliniak.kitchenassistant.dayplan.db.StepSnapshotDao
import pl.szczeliniak.kitchenassistant.dayplan.dto.response.DayPlanResponse
import pl.szczeliniak.kitchenassistant.dayplan.dto.response.DayPlansResponse
import pl.szczeliniak.kitchenassistant.recipe.db.Author
import pl.szczeliniak.kitchenassistant.recipe.db.Category
import pl.szczeliniak.kitchenassistant.recipe.db.Recipe
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.RequestContext
import pl.szczeliniak.kitchenassistant.shared.TokenType
import pl.szczeliniak.kitchenassistant.shared.dtos.Page
import java.time.LocalDate
import java.util.*

class DayPlanServiceTest {

    private val dayPlanMapper: DayPlanMapper = mockk()
    private val recipeSnapshotMapper: RecipeSnapshotMapper = mockk()
    private val dayPlanDao: DayPlanDao = mockk()
    private val recipeDao: RecipeDao = mockk()
    private val requestContext: RequestContext = mockk()
    private val recipeSnapshotDao: RecipeSnapshotDao = mockk()
    private val ingredientGroupSnapshotDao: IngredientGroupSnapshotDao = mockk()
    private val stepSnapshotDao: StepSnapshotDao = mockk()
    private val ingredientSnapshotDao: IngredientSnapshotDao = mockk()
    private val stepGroupSnapshotDao: StepGroupSnapshotDao = mockk()

    private val dayPlanService = DayPlanService(
        dayPlanMapper,
        recipeSnapshotMapper,
        dayPlanDao,
        recipeDao,
        requestContext,
        recipeSnapshotDao,
        ingredientGroupSnapshotDao,
        stepSnapshotDao,
        ingredientSnapshotDao,
        stepGroupSnapshotDao
    )

    @Test
    fun shouldFindByDate() {
        val date = LocalDate.now()
        val recipeSnapshot = recipeSnapshot()
        val dayPlan = dayPlan(Collections.singletonList(recipeSnapshot))
        val recipeResponse = recipeResponse()
        val dayPlanResponse = dayPlanResponse()
        every { requestContext.tokenType() } returns TokenType.ACCESS
        every { requestContext.userId() } returns 1
        every { dayPlanDao.findByDate(date, 1) } returns dayPlan
        every { recipeDao.findById(2, null, 1) } returns recipe()
        every { dayPlanMapper.map(recipeSnapshot, "authorName", "categoryName") } returns recipeResponse
        every { dayPlanMapper.mapDetails(dayPlan, Collections.singletonList(recipeResponse)) } returns dayPlanResponse

        val result = dayPlanService.findByDate(date)

        Assertions.assertEquals(DayPlanResponse(dayPlanResponse), result)
    }

    @Test
    fun shouldThrowExceptionWhenFindByDateAndTokenTypeIsOtherThanAccess() {
        every { requestContext.tokenType() } returns TokenType.REFRESH

        val result = assertThrows<KitchenAssistantException> { dayPlanService.findByDate(LocalDate.now()) }

        Assertions.assertEquals(ErrorCode.WRONG_TOKEN_TYPE, result.error)
    }

    @Test
    fun shouldThrowExceptionWhenFindByDateAndDayPlanByDateNotFound() {
        val date = LocalDate.now()
        every { requestContext.tokenType() } returns TokenType.ACCESS
        every { requestContext.userId() } returns 1
        every { dayPlanDao.findByDate(date, 1) } returns null

        val result = assertThrows<KitchenAssistantException> { dayPlanService.findByDate(date) }

        Assertions.assertEquals(ErrorCode.DAY_PLAN_NOT_FOUND, result.error)
    }

    @Test
    fun shouldFindAll() {
        val since = LocalDate.now()
        val to = LocalDate.now()
        val dayPlan = dayPlan(Collections.emptyList())
        val dayPlanResponse = dayPlansResponse()
        every { requestContext.tokenType() } returns TokenType.ACCESS
        every { requestContext.userId() } returns 1
        every { dayPlanDao.findAll(DayPlanCriteria(2, since, to), 1, Sort.ASC, 40, 10) } returns Collections.singletonList(dayPlan)
        every { dayPlanDao.count(DayPlanCriteria(2, since, to), 1) } returns 55
        every { dayPlanMapper.map(dayPlan) } returns dayPlanResponse

        val result = dayPlanService.findAll(5, 10, Sort.ASC, 2, since, to)

        Assertions.assertEquals(DayPlansResponse(Page(5, 10, 6, Collections.singletonList(dayPlanResponse))), result)
    }

    @Test
    fun shouldThrowExceptionWhenFindAllAndTokenTypeIsOtherThanAccess() {
        every { requestContext.tokenType() } returns TokenType.REFRESH

        val result = assertThrows<KitchenAssistantException> { dayPlanService.findAll(1, 1, Sort.ASC, 2, LocalDate.now(), LocalDate.now()) }

        Assertions.assertEquals(ErrorCode.WRONG_TOKEN_TYPE, result.error)
    }

    private fun dayPlansResponse(): DayPlansResponse.DayPlan {
        return DayPlansResponse.DayPlan(LocalDate.now(), Collections.emptyList())
    }

    private fun dayPlanResponse(): DayPlanResponse.DayPlan {
        return DayPlanResponse.DayPlan(LocalDate.now(), Collections.emptyList())
    }

    private fun recipeResponse(): DayPlanResponse.DayPlan.Recipe {
        return DayPlanResponse.DayPlan.Recipe(0, "", "", 1, Collections.emptyList())
    }

    private fun author(): Author {
        val author = Author()
        author.name = "authorName"
        return author
    }

    private fun recipe(): Recipe {
        val recipe = Recipe()
        recipe.author = author()
        recipe.category = category()
        return recipe
    }

    private fun category(): Category {
        val category = Category()
        category.name = "categoryName"
        return category
    }

    private fun recipeSnapshot(): RecipeSnapshot {
        return RecipeSnapshot(originalRecipeId = 2)
    }

    private fun dayPlan(recipeSnapshots: MutableList<RecipeSnapshot>): DayPlan {
        return DayPlan(recipes = recipeSnapshots)
    }

}