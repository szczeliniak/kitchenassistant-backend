package pl.szczeliniak.kitchenassistant.recipe

import org.hibernate.validator.constraints.Length
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import pl.szczeliniak.kitchenassistant.recipe.commands.dto.*
import pl.szczeliniak.kitchenassistant.recipe.db.AuthorCriteria
import pl.szczeliniak.kitchenassistant.recipe.db.CategoryCriteria
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeCriteria
import pl.szczeliniak.kitchenassistant.recipe.db.TagCriteria
import pl.szczeliniak.kitchenassistant.recipe.queries.dto.*
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import javax.validation.Valid

@RestController
@RequestMapping("/recipes")
@Validated
class RecipeController(private val recipeFacade: RecipeFacade) {

    @GetMapping("/{recipeId}")
    fun findById(@PathVariable recipeId: Int): RecipeResponse {
        return recipeFacade.findById(recipeId)
    }

    @GetMapping
    fun findAll(
        @RequestParam(required = false) userId: Int?,
        @RequestParam(required = false) categoryId: Int?,
        @RequestParam(required = false) @Length(max = 50) name: String?,
        @RequestParam(required = false) @Length(max = 50) tag: String?,
        @RequestParam(required = false) page: Long?,
        @RequestParam(required = false) limit: Int?,
        @RequestParam(required = true, defaultValue = "false") onlyFavorites: Boolean
    ): RecipesResponse {
        return recipeFacade.findAll(page, limit, RecipeCriteria(onlyFavorites, userId, categoryId, name, tag))
    }

    @PostMapping
    fun add(@Valid @RequestBody request: NewRecipeRequest): SuccessResponse {
        return recipeFacade.add(request)
    }

    @PutMapping("/{recipeId}")
    fun update(@PathVariable recipeId: Int, @Valid @RequestBody request: UpdateRecipeRequest): SuccessResponse {
        return recipeFacade.update(recipeId, request)
    }

    @DeleteMapping("/{recipeId}")
    fun delete(@PathVariable recipeId: Int): SuccessResponse {
        return recipeFacade.delete(recipeId)
    }

    @PostMapping("{recipeId}/steps")
    fun addStep(@PathVariable recipeId: Int, @Valid @RequestBody request: NewStepRequest): SuccessResponse {
        return recipeFacade.addStep(recipeId, request)
    }

    @PutMapping("/{recipeId}/steps/{stepId}")
    fun updateStep(
        @PathVariable recipeId: Int,
        @PathVariable stepId: Int,
        @Valid @RequestBody request: UpdateStepRequest
    ): SuccessResponse {
        return recipeFacade.updateStep(recipeId, stepId, request)
    }

    @DeleteMapping("/{recipeId}/steps/{stepId}")
    fun deleteStep(@PathVariable recipeId: Int, @PathVariable stepId: Int): SuccessResponse {
        return recipeFacade.deleteStep(recipeId, stepId)
    }

    @PostMapping("/{recipeId}/ingredientGroups")
    fun addIngredientGroup(
        @PathVariable recipeId: Int,
        @Valid @RequestBody request: NewIngredientGroupRequest
    ): SuccessResponse {
        return recipeFacade.addIngredientGroup(recipeId, request)
    }

    @DeleteMapping("/{recipeId}/ingredientGroups/{ingredientGroupId}")
    fun deleteIngredientGroup(
        @PathVariable recipeId: Int,
        @PathVariable ingredientGroupId: Int
    ): SuccessResponse {
        return recipeFacade.deleteIngredientGroup(recipeId, ingredientGroupId)
    }

    @PutMapping("/{recipeId}/ingredientGroups/{ingredientGroupId}")
    fun updateIngredientGroup(
        @PathVariable recipeId: Int,
        @PathVariable ingredientGroupId: Int,
        @Valid @RequestBody request: UpdateIngredientGroupRequest
    ): SuccessResponse {
        return recipeFacade.updateIngredientGroup(recipeId, ingredientGroupId, request)
    }

    @GetMapping("/{recipeId}/ingredientGroups/{ingredientGroupId}")
    fun getIngredientGroup(
        @PathVariable recipeId: Int,
        @PathVariable ingredientGroupId: Int
    ): IngredientGroupResponse {
        return recipeFacade.getIngredientGroup(recipeId, ingredientGroupId)
    }

    @DeleteMapping("/{recipeId}/ingredientGroups/{ingredientGroupId}/ingredients/{ingredientId}")
    fun deleteIngredient(
        @PathVariable recipeId: Int,
        @PathVariable ingredientGroupId: Int,
        @PathVariable ingredientId: Int
    ): SuccessResponse {
        return recipeFacade.deleteIngredient(recipeId, ingredientGroupId, ingredientId)
    }

    @PostMapping("/categories")
    fun addCategory(@Valid @RequestBody request: NewCategoryRequest): SuccessResponse {
        return recipeFacade.addCategory(request)
    }

    @GetMapping("/categories")
    fun getCategories(@RequestParam(required = false) userId: Int?): CategoriesResponse {
        return recipeFacade.getCategories(CategoryCriteria(userId))
    }

    @GetMapping("/tags")
    fun getTags(
        @RequestParam(required = false) userId: Int?,
        @RequestParam(required = false) name: String?
    ): TagsResponse {
        return recipeFacade.getTags(TagCriteria(name, userId))
    }

    @GetMapping("/authors")
    fun getAuthors(
        @RequestParam(required = false) userId: Int?,
        @RequestParam(required = false) name: String?
    ): AuthorsResponse {
        return recipeFacade.getAuthors(AuthorCriteria(name, userId))
    }

    @DeleteMapping("/categories/{categoryId}")
    fun deleteCategory(@PathVariable categoryId: Int): SuccessResponse {
        return recipeFacade.deleteCategory(categoryId)
    }

    @PutMapping("/categories/{categoryId}")
    fun updateCategory(
        @PathVariable categoryId: Int,
        @Valid @RequestBody request: UpdateCategoryRequest
    ): SuccessResponse {
        return recipeFacade.updateCategory(categoryId, request)
    }

    @PutMapping("/{recipeId}/favorite/{isFavorite}")
    fun markAsFavorite(@PathVariable recipeId: Int, @PathVariable isFavorite: Boolean): SuccessResponse {
        return recipeFacade.markAsFavorite(recipeId, isFavorite)
    }

}