package pl.szczeliniak.kitchenassistant.recipe

import org.hibernate.validator.constraints.Length
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
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
class RecipeController(
        private val recipeFacade: RecipeFacade
) {

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
    fun add(@Valid @RequestBody dto: NewRecipeDto): SuccessResponse {
        return recipeFacade.add(dto)
    }

    @PutMapping("/{recipeId}")
    fun update(@PathVariable recipeId: Int, @Valid @RequestBody dto: UpdateRecipeDto): SuccessResponse {
        return recipeFacade.update(recipeId, dto)
    }

    @DeleteMapping("/{recipeId}")
    fun delete(@PathVariable recipeId: Int): SuccessResponse {
        return recipeFacade.delete(recipeId)
    }

    @PostMapping("{recipeId}/steps")
    fun addStep(@PathVariable recipeId: Int, @Valid @RequestBody dto: NewStepDto): SuccessResponse {
        return recipeFacade.addStep(recipeId, dto)
    }

    @PutMapping("/{recipeId}/steps/{stepId}")
    fun updateStep(
            @PathVariable recipeId: Int,
            @PathVariable stepId: Int,
            @Valid @RequestBody dto: UpdateStepDto
    ): SuccessResponse {
        return recipeFacade.updateStep(recipeId, stepId, dto)
    }

    @DeleteMapping("/{recipeId}/steps/{stepId}")
    fun deleteStep(@PathVariable recipeId: Int, @PathVariable stepId: Int): SuccessResponse {
        return recipeFacade.deleteStep(recipeId, stepId)
    }

    @PostMapping("/{recipeId}/ingredientGroups")
    fun addIngredientGroup(
            @PathVariable recipeId: Int,
            @Valid @RequestBody dto: NewIngredientGroupDto
    ): SuccessResponse {
        return recipeFacade.addIngredientGroup(recipeId, dto)
    }

    @DeleteMapping("/{recipeId}/ingredientGroups/{ingredientGroupId}")
    fun deleteIngredientGroup(
            @PathVariable recipeId: Int,
            @PathVariable ingredientGroupId: Int
    ): SuccessResponse {
        return recipeFacade.deleteIngredientGroup(recipeId, ingredientGroupId)
    }

    @PostMapping("/{recipeId}/ingredientGroups/{ingredientGroupId}/ingredients")
    fun addIngredient(
            @PathVariable recipeId: Int,
            @PathVariable ingredientGroupId: Int,
            @Valid @RequestBody dto: NewIngredientDto
    ): SuccessResponse {
        return recipeFacade.addIngredient(recipeId, ingredientGroupId, dto)
    }

    @PutMapping("/{recipeId}/ingredientGroups/{ingredientGroupId}/ingredients/{ingredientId}")
    fun updateIngredient(
            @PathVariable recipeId: Int,
            @PathVariable ingredientGroupId: Int,
            @PathVariable ingredientId: Int,
            @Valid @RequestBody dto: UpdateIngredientDto
    ): SuccessResponse {
        return recipeFacade.updateIngredient(recipeId, ingredientGroupId, ingredientId, dto)
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
    fun addCategory(@Valid @RequestBody dto: NewCategoryDto): SuccessResponse {
        return recipeFacade.addCategory(dto)
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
    fun updateCategory(@PathVariable categoryId: Int, @Valid @RequestBody request: UpdateCategoryDto): SuccessResponse {
        return recipeFacade.updateCategory(categoryId, request)
    }

    @PutMapping("/{recipeId}/favorite/{isFavorite}")
    fun markAsFavorite(@PathVariable recipeId: Int, @PathVariable isFavorite: Boolean): SuccessResponse {
        return recipeFacade.markAsFavorite(recipeId, isFavorite)
    }

    @PostMapping("/photo")
    fun uploadPhoto(@RequestParam("file") file: MultipartFile): UploadPhotoResponse {
        return recipeFacade.uploadPhoto(file.originalFilename ?: file.name, file.bytes)
    }

    @GetMapping("/{recipeId}/photo")
    fun downloadPhoto(@PathVariable recipeId: Int): ResponseEntity<ByteArray> {
        val response = recipeFacade.downloadPhoto(recipeId)
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(response.mediaType.mimeType))
                .body(response.body)
    }

    @DeleteMapping("/{recipeId}/photo")
    fun deletePhoto(@PathVariable recipeId: Int): SuccessResponse {
        return recipeFacade.deletePhoto(recipeId)
    }

}