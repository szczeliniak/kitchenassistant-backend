package pl.szczeliniak.kitchenassistant.recipe

import org.hibernate.validator.constraints.Length
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import pl.szczeliniak.kitchenassistant.recipe.db.AuthorCriteria
import pl.szczeliniak.kitchenassistant.recipe.db.CategoryCriteria
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeCriteria
import pl.szczeliniak.kitchenassistant.recipe.db.TagCriteria
import pl.szczeliniak.kitchenassistant.recipe.dto.*
import pl.szczeliniak.kitchenassistant.recipe.dto.request.*
import pl.szczeliniak.kitchenassistant.recipe.dto.response.*
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import javax.transaction.Transactional
import javax.validation.Valid

@RestController
@RequestMapping("/recipes")
@Validated
class RecipeController(
    private val recipeService: RecipeService,
    private val authorService: AuthorService,
    private val categoryService: CategoryService,
    private val ingredientGroupService: IngredientGroupService,
    private val tagService: TagService,
    private val stepService: StepService
) {

    @GetMapping("/{recipeId}")
    fun findById(@PathVariable recipeId: Int): RecipeResponse {
        return recipeService.findById(recipeId)
    }

    @GetMapping
    fun findAll(
        @RequestParam(required = false) userId: Int?,
        @RequestParam(required = false) categoryId: Int?,
        @RequestParam(required = false) @Length(max = 50) name: String?,
        @RequestParam(required = false) @Length(max = 50) tag: String?,
        @RequestParam(required = false) page: Long?,
        @RequestParam(required = false) limit: Int?,
        @RequestParam(required = true, defaultValue = "false") onlyFavorites: Boolean?
    ): RecipesResponse {
        return recipeService.findAll(page, limit, RecipeCriteria(onlyFavorites, userId, categoryId, name, tag))
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
    @PostMapping("{recipeId}/steps")
    fun addStep(@PathVariable recipeId: Int, @Valid @RequestBody request: NewStepRequest): SuccessResponse {
        return stepService.add(recipeId, request)
    }

    @Transactional
    @PutMapping("/{recipeId}/steps/{stepId}")
    fun updateStep(
        @PathVariable recipeId: Int,
        @PathVariable stepId: Int,
        @Valid @RequestBody request: UpdateStepRequest
    ): SuccessResponse {
        return stepService.update(recipeId, stepId, request)
    }

    @Transactional
    @DeleteMapping("/{recipeId}/steps/{stepId}")
    fun deleteStep(@PathVariable recipeId: Int, @PathVariable stepId: Int): SuccessResponse {
        return stepService.delete(recipeId, stepId)
    }

    @Transactional
    @PostMapping("/{recipeId}/ingredientGroups")
    fun addIngredientGroup(
        @PathVariable recipeId: Int,
        @Valid @RequestBody request: NewIngredientGroupRequest
    ): SuccessResponse {
        return ingredientGroupService.add(recipeId, request)
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
    @PutMapping("/{recipeId}/ingredientGroups/{ingredientGroupId}")
    fun updateIngredientGroup(
        @PathVariable recipeId: Int,
        @PathVariable ingredientGroupId: Int,
        @Valid @RequestBody request: UpdateIngredientGroupRequest
    ): SuccessResponse {
        return ingredientGroupService.update(recipeId, ingredientGroupId, request)
    }

    @GetMapping("/{recipeId}/ingredientGroups/{ingredientGroupId}")
    fun getIngredientGroup(
        @PathVariable recipeId: Int,
        @PathVariable ingredientGroupId: Int
    ): IngredientGroupResponse {
        return ingredientGroupService.find(recipeId, ingredientGroupId)
    }

    @Transactional
    @DeleteMapping("/{recipeId}/ingredientGroups/{ingredientGroupId}/ingredients/{ingredientId}")
    fun deleteIngredient(
        @PathVariable recipeId: Int,
        @PathVariable ingredientGroupId: Int,
        @PathVariable ingredientId: Int
    ): SuccessResponse {
        return ingredientGroupService.deleteIngredient(recipeId, ingredientGroupId, ingredientId)
    }

    @Transactional
    @PostMapping("/categories")
    fun addCategory(@Valid @RequestBody request: NewCategoryRequest): SuccessResponse {
        return categoryService.add(request)
    }

    @GetMapping("/categories")
    fun getCategories(@RequestParam(required = false) userId: Int?): CategoriesResponse {
        return categoryService.getAll(CategoryCriteria(userId))
    }

    @GetMapping("/tags")
    fun getTags(
        @RequestParam(required = false) userId: Int?,
        @RequestParam(required = false) name: String?
    ): TagsResponse {
        return tagService.getAll(TagCriteria(name, userId))
    }

    @GetMapping("/authors")
    fun getAuthors(
        @RequestParam(required = false) userId: Int?,
        @RequestParam(required = false) name: String?
    ): AuthorsResponse {
        return authorService.getAll(AuthorCriteria(name, userId))
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
    fun markAsFavorite(@PathVariable recipeId: Int, @PathVariable isFavorite: Boolean): SuccessResponse {
        return recipeService.markAsFavorite(recipeId, isFavorite)
    }

}