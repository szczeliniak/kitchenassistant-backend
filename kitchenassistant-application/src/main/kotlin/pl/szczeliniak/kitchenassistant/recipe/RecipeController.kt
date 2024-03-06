package pl.szczeliniak.kitchenassistant.recipe

import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pl.szczeliniak.kitchenassistant.recipe.dto.request.NewCategoryRequest
import pl.szczeliniak.kitchenassistant.recipe.dto.request.NewRecipeRequest
import pl.szczeliniak.kitchenassistant.recipe.dto.request.UpdateCategoryRequest
import pl.szczeliniak.kitchenassistant.recipe.dto.request.UpdateIngredientGroupsRequest
import pl.szczeliniak.kitchenassistant.recipe.dto.request.UpdateRecipeRequest
import pl.szczeliniak.kitchenassistant.recipe.dto.request.UpdateStepsRequest
import pl.szczeliniak.kitchenassistant.recipe.dto.response.AuthorsResponse
import pl.szczeliniak.kitchenassistant.recipe.dto.response.CategoriesResponse
import pl.szczeliniak.kitchenassistant.recipe.dto.response.RecipeResponse
import pl.szczeliniak.kitchenassistant.recipe.dto.response.RecipesResponse
import pl.szczeliniak.kitchenassistant.recipe.dto.response.TagsResponse
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import javax.transaction.Transactional
import javax.validation.Valid
import javax.validation.constraints.Size

@RestController
@RequestMapping("/recipes")
@Validated
class RecipeController(
    private val recipeService: RecipeService,
    private val authorService: AuthorService,
    private val categoryService: CategoryService,
    private val ingredientGroupService: IngredientGroupService,
    private val tagService: TagService,
    private val stepGroupService: StepGroupService,
    private val ingredientService: IngredientService,
    private val stepService: StepService
) {

    @GetMapping("/{recipeId}")
    fun findById(@PathVariable recipeId: Int): RecipeResponse {
        return recipeService.findById(recipeId)
    }

    @GetMapping
    fun findAll(
        @RequestParam(required = false) categoryId: Int?,
        @RequestParam(required = false) @Size(max = 50) search: String?,
        @RequestParam(required = false) @Size(max = 50) tag: String?,
        @RequestParam(required = false) page: Long?,
        @RequestParam(required = false) limit: Int?,
        @RequestParam(required = false) favourite: Boolean?
    ): RecipesResponse {
        return recipeService.findAll(page, limit, favourite, categoryId, search, tag)
    }

    @Transactional
    @PostMapping
    fun add(@Valid @RequestBody request: NewRecipeRequest): SuccessResponse {
        return recipeService.add(request)
    }

    @Transactional
    @PutMapping("/{recipeId}")
    fun update(@PathVariable recipeId: Int, @Valid @RequestBody request: UpdateRecipeRequest): SuccessResponse {
        return recipeService.update(recipeId, request)
    }

    @Transactional
    @DeleteMapping("/{recipeId}")
    fun delete(@PathVariable recipeId: Int): SuccessResponse {
        return recipeService.delete(recipeId)
    }

    @Transactional
    @PutMapping("{recipeId}/stepsGroups")
    fun updateStepsGroups(@PathVariable recipeId: Int, @Valid @RequestBody request: UpdateStepsRequest): SuccessResponse {
        return stepGroupService.update(recipeId, request)
    }

    @Transactional
    @DeleteMapping("/{recipeId}/stepsGroups/{stepGroupId}")
    fun deleteStepGroup(@PathVariable recipeId: Int, @PathVariable stepGroupId: Int): SuccessResponse {
        return stepGroupService.delete(recipeId, stepGroupId)
    }

    @Transactional
    @DeleteMapping("/{recipeId}/stepGroups/{stepGroupId}/steps/{stepId}")
    fun deleteStep(
        @PathVariable recipeId: Int,
        @PathVariable stepGroupId: Int,
        @PathVariable stepId: Int
    ): SuccessResponse {
        return stepService.delete(recipeId, stepGroupId, stepId)
    }

    @Transactional
    @PutMapping("/{recipeId}/ingredientGroups")
    fun updateIngredientGroups(
        @PathVariable recipeId: Int,
        @Valid @RequestBody request: UpdateIngredientGroupsRequest
    ): SuccessResponse {
        return ingredientGroupService.update(recipeId, request)
    }

    @Transactional
    @DeleteMapping("/{recipeId}/ingredientGroups/{ingredientGroupId}")
    fun deleteIngredientGroup(
        @PathVariable recipeId: Int,
        @PathVariable ingredientGroupId: Int
    ): SuccessResponse {
        return ingredientGroupService.delete(recipeId, ingredientGroupId)
    }

    @Transactional
    @DeleteMapping("/{recipeId}/ingredientGroups/{ingredientGroupId}/ingredients/{ingredientId}")
    fun deleteIngredient(
        @PathVariable recipeId: Int,
        @PathVariable ingredientGroupId: Int,
        @PathVariable ingredientId: Int
    ): SuccessResponse {
        return ingredientService.delete(recipeId, ingredientGroupId, ingredientId)
    }

    @Transactional
    @PostMapping("/categories")
    fun addCategory(@Valid @RequestBody request: NewCategoryRequest): SuccessResponse {
        return categoryService.add(request)
    }

    @GetMapping("/categories")
    fun getCategories(): CategoriesResponse {
        return categoryService.getAll()
    }

    @GetMapping("/tags")
    fun getTags(): TagsResponse {
        return tagService.getAll()
    }

    @GetMapping("/authors")
    fun getAuthors(): AuthorsResponse {
        return authorService.getAll()
    }

    @Transactional
    @DeleteMapping("/categories/{categoryId}")
    fun deleteCategory(@PathVariable categoryId: Int): SuccessResponse {
        return categoryService.delete(categoryId)
    }

    @Transactional
    @PutMapping("/categories/{categoryId}")
    fun updateCategory(
        @PathVariable categoryId: Int,
        @Valid @RequestBody request: UpdateCategoryRequest
    ): SuccessResponse {
        return categoryService.update(categoryId, request)
    }

    @Transactional
    @PutMapping("/{recipeId}/favorite/{isFavorite}")
    fun markRecipeAsFavorite(@PathVariable recipeId: Int, @PathVariable isFavorite: Boolean): SuccessResponse {
        return recipeService.markAsFavorite(recipeId, isFavorite)
    }

}