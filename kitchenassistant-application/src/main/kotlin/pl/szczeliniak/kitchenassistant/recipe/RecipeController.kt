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
import pl.szczeliniak.kitchenassistant.security.AuthorizationService
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import javax.validation.Valid

@RestController
@RequestMapping("/recipes")
@Validated
class RecipeController(private val recipeFacade: RecipeFacade, private val authorizationService: AuthorizationService) {

    @GetMapping("/{recipeId}")
    fun findById(@PathVariable recipeId: Int): RecipeResponse {
        authorizationService.checkIsOwnerOfRecipe(recipeId)
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
        authorizationService.checkIsOwner(userId)
        return recipeFacade.findAll(page, limit, RecipeCriteria(onlyFavorites, userId, categoryId, name, tag))
    }

    @PostMapping
    fun add(@Valid @RequestBody dto: NewRecipeDto): SuccessResponse {
        return recipeFacade.add(dto)
    }

    @PutMapping("/{recipeId}")
    fun update(@PathVariable recipeId: Int, @Valid @RequestBody dto: UpdateRecipeDto): SuccessResponse {
        authorizationService.checkIsOwnerOfRecipe(recipeId)
        return recipeFacade.update(recipeId, dto)
    }

    @DeleteMapping("/{recipeId}")
    fun delete(@PathVariable recipeId: Int): SuccessResponse {
        authorizationService.checkIsOwnerOfRecipe(recipeId)
        return recipeFacade.delete(recipeId)
    }

    @PostMapping("{recipeId}/steps")
    fun addStep(@PathVariable recipeId: Int, @Valid @RequestBody dto: NewStepDto): SuccessResponse {
        authorizationService.checkIsOwnerOfRecipe(recipeId)
        return recipeFacade.addStep(recipeId, dto)
    }

    @PutMapping("/{recipeId}/steps/{stepId}")
    fun updateStep(
        @PathVariable recipeId: Int,
        @PathVariable stepId: Int,
        @Valid @RequestBody dto: UpdateStepDto
    ): SuccessResponse {
        authorizationService.checkIsOwnerOfRecipe(recipeId)
        return recipeFacade.updateStep(recipeId, stepId, dto)
    }

    @DeleteMapping("/{recipeId}/steps/{stepId}")
    fun deleteStep(@PathVariable recipeId: Int, @PathVariable stepId: Int): SuccessResponse {
        authorizationService.checkIsOwnerOfRecipe(recipeId)
        return recipeFacade.deleteStep(recipeId, stepId)
    }

    @PostMapping("/{recipeId}/ingredientGroups")
    fun addIngredientGroup(
        @PathVariable recipeId: Int,
        @Valid @RequestBody dto: NewIngredientGroupDto
    ): SuccessResponse {
        authorizationService.checkIsOwnerOfRecipe(recipeId)
        return recipeFacade.addIngredientGroup(recipeId, dto)
    }

    @DeleteMapping("/{recipeId}/ingredientGroups/{ingredientGroupId}")
    fun deleteIngredientGroup(
        @PathVariable recipeId: Int,
        @PathVariable ingredientGroupId: Int
    ): SuccessResponse {
        authorizationService.checkIsOwnerOfRecipe(recipeId)
        return recipeFacade.deleteIngredientGroup(recipeId, ingredientGroupId)
    }

    @PutMapping("/{recipeId}/ingredientGroups/{ingredientGroupId}")
    fun updateIngredientGroup(
        @PathVariable recipeId: Int,
        @PathVariable ingredientGroupId: Int,
        @Valid @RequestBody request: UpdateIngredientGroupDto
    ): SuccessResponse {
        authorizationService.checkIsOwnerOfRecipe(recipeId)
        return recipeFacade.updateIngredientGroup(recipeId, ingredientGroupId, request)
    }

    @GetMapping("/{recipeId}/ingredientGroups/{ingredientGroupId}")
    fun getIngredientGroup(
        @PathVariable recipeId: Int,
        @PathVariable ingredientGroupId: Int
    ): IngredientGroupResponse {
        authorizationService.checkIsOwnerOfRecipe(recipeId)
        return recipeFacade.getIngredientGroup(recipeId, ingredientGroupId)
    }

    @DeleteMapping("/{recipeId}/ingredientGroups/{ingredientGroupId}/ingredients/{ingredientId}")
    fun deleteIngredient(
        @PathVariable recipeId: Int,
        @PathVariable ingredientGroupId: Int,
        @PathVariable ingredientId: Int
    ): SuccessResponse {
        authorizationService.checkIsOwnerOfRecipe(recipeId)
        return recipeFacade.deleteIngredient(recipeId, ingredientGroupId, ingredientId)
    }

    @PostMapping("/categories")
    fun addCategory(@Valid @RequestBody dto: NewCategoryDto): SuccessResponse {
        return recipeFacade.addCategory(dto)
    }

    @GetMapping("/categories")
    fun getCategories(@RequestParam(required = false) userId: Int?): CategoriesResponse {
        authorizationService.checkIsOwner(userId)
        return recipeFacade.getCategories(CategoryCriteria(userId))
    }

    @GetMapping("/tags")
    fun getTags(
        @RequestParam(required = false) userId: Int?,
        @RequestParam(required = false) name: String?
    ): TagsResponse {
        authorizationService.checkIsOwner(userId)
        return recipeFacade.getTags(TagCriteria(name, userId))
    }

    @GetMapping("/authors")
    fun getAuthors(
        @RequestParam(required = false) userId: Int?,
        @RequestParam(required = false) name: String?
    ): AuthorsResponse {
        authorizationService.checkIsOwner(userId)
        return recipeFacade.getAuthors(AuthorCriteria(name, userId))
    }

    @DeleteMapping("/categories/{categoryId}")
    fun deleteCategory(@PathVariable categoryId: Int): SuccessResponse {
        authorizationService.checkIsOwnerOfCategory(categoryId)
        return recipeFacade.deleteCategory(categoryId)
    }

    @PutMapping("/categories/{categoryId}")
    fun updateCategory(@PathVariable categoryId: Int, @Valid @RequestBody request: UpdateCategoryDto): SuccessResponse {
        authorizationService.checkIsOwnerOfCategory(categoryId)
        return recipeFacade.updateCategory(categoryId, request)
    }

    @PutMapping("/{recipeId}/favorite/{isFavorite}")
    fun markAsFavorite(@PathVariable recipeId: Int, @PathVariable isFavorite: Boolean): SuccessResponse {
        authorizationService.checkIsOwnerOfRecipe(recipeId)
        return recipeFacade.markAsFavorite(recipeId, isFavorite)
    }

}