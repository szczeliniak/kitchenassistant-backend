package pl.szczeliniak.kitchenassistant.receipt

import org.hibernate.validator.constraints.Length
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import pl.szczeliniak.kitchenassistant.common.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.receipt.commands.*
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.*
import pl.szczeliniak.kitchenassistant.receipt.queries.GetCategoriesQuery
import pl.szczeliniak.kitchenassistant.receipt.queries.GetReceiptQuery
import pl.szczeliniak.kitchenassistant.receipt.queries.GetReceiptsQuery
import pl.szczeliniak.kitchenassistant.receipt.queries.GetTagsQuery
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.CategoriesResponse
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.ReceiptResponse
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.ReceiptsResponse
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.TagsResponse
import javax.validation.Valid

@RestController
@RequestMapping("/receipts")
@Validated
class ReceiptController(
    private val getReceiptQuery: GetReceiptQuery,
    private val getReceiptsQuery: GetReceiptsQuery,
    private val addReceiptCommand: AddReceiptCommand,
    private val addCategoryCommand: AddCategoryCommand,
    private val deleteReceiptCommand: DeleteReceiptCommand,
    private val updateReceiptCommand: UpdateReceiptCommand,
    private val addIngredientCommand: AddIngredientCommand,
    private val deleteIngredientCommand: DeleteIngredientCommand,
    private val updateIngredientCommand: UpdateIngredientCommand,
    private val addStepCommand: AddStepCommand,
    private val deleteStepCommand: DeleteStepCommand,
    private val assignPhotosToReceiptStepCommand: AssignPhotosToReceiptStepCommand,
    private val divestPhotoFromReceiptStepCommand: DivestPhotoFromReceiptStepCommand,
    private val deleteCategoryCommand: DeleteCategoryCommand,
    private val updateCategoryCommand: UpdateCategoryCommand,
    private val updateStepCommand: UpdateStepCommand,
    private val getCategoriesQuery: GetCategoriesQuery,
    private val assignPhotosToReceiptCommand: AssignPhotosToReceiptCommand,
    private val divestPhotoFromReceiptCommand: DivestPhotoFromReceiptCommand,
    private val getTagsQuery: GetTagsQuery
) {

    @GetMapping("/{id}")
    fun getReceipt(@PathVariable id: Int): ReceiptResponse {
        return getReceiptQuery.execute(id)
    }

    @GetMapping
    fun getReceipts(
        @RequestParam(required = false) userId: Int?,
        @RequestParam(required = false) categoryId: Int?,
        @RequestParam(required = false) @Length(max = 50) name: String?,
        @RequestParam(required = false) @Length(max = 50) tag: String?,
        @RequestParam(required = false) page: Long?,
        @RequestParam(required = false) limit: Int?,
    ): ReceiptsResponse {
        return getReceiptsQuery.execute(page, limit, ReceiptCriteria(userId, categoryId, name, tag))
    }

    @PostMapping
    fun addReceipt(@Valid @RequestBody dto: NewReceiptDto): SuccessResponse {
        return addReceiptCommand.execute(dto)
    }

    @PutMapping("/{id}")
    fun updateReceipt(@PathVariable id: Int, @Valid @RequestBody dto: UpdateReceiptDto): SuccessResponse {
        return updateReceiptCommand.execute(id, dto)
    }

    @DeleteMapping("/{id}")
    fun deleteReceipt(@PathVariable id: Int): SuccessResponse {
        return deleteReceiptCommand.execute(id)
    }

    @PostMapping("{id}/steps")
    fun addStep(@PathVariable id: Int, @Valid @RequestBody dto: NewStepDto): SuccessResponse {
        return addStepCommand.execute(id, dto)
    }

    @PutMapping("/{id}/steps/{stepId}")
    fun updateStep(
        @PathVariable id: Int,
        @PathVariable stepId: Int,
        @Valid @RequestBody dto: UpdateStepDto
    ): SuccessResponse {
        return updateStepCommand.execute(id, stepId, dto)
    }

    @DeleteMapping("/{id}/steps/{stepId}")
    fun deleteStep(@PathVariable id: Int, @PathVariable stepId: Int): SuccessResponse {
        return deleteStepCommand.execute(id, stepId)
    }

    @PutMapping("/{id}/steps/{stepId}/photos")
    fun assignPhotosToReceiptStep(
        @PathVariable id: Int,
        @PathVariable stepId: Int,
        @Valid @RequestBody request: AssignPhotosToReceiptStepDto
    ): SuccessResponse {
        return assignPhotosToReceiptStepCommand.execute(id, stepId, request)
    }

    @DeleteMapping("/{id}/steps/{stepId}/photos/{name}")
    fun divestPhotoFromReceiptStep(
        @PathVariable id: Int,
        @PathVariable stepId: Int,
        @PathVariable @Length(max = 150) name: String
    ): SuccessResponse {
        return divestPhotoFromReceiptStepCommand.execute(id, stepId, name)
    }

    @PostMapping("{id}/ingredients")
    fun addIngredient(@PathVariable id: Int, @Valid @RequestBody dto: NewIngredientDto): SuccessResponse {
        return addIngredientCommand.execute(id, dto)
    }

    @PutMapping("/{id}/ingredients/{ingredientId}")
    fun updateIngredient(
        @PathVariable id: Int,
        @PathVariable ingredientId: Int,
        @Valid @RequestBody dto: UpdateIngredientDto
    ): SuccessResponse {
        return updateIngredientCommand.execute(id, ingredientId, dto)
    }

    @DeleteMapping("/{id}/ingredients/{ingredientId}")
    fun deleteIngredient(@PathVariable id: Int, @PathVariable ingredientId: Int): SuccessResponse {
        return deleteIngredientCommand.execute(id, ingredientId)
    }

    @PostMapping("/categories")
    fun addCategory(@Valid @RequestBody dto: NewCategoryDto): SuccessResponse {
        return addCategoryCommand.execute(dto)
    }

    @GetMapping("/categories")
    fun getCategories(@RequestParam(required = false) userId: Int?): CategoriesResponse {
        return getCategoriesQuery.execute(CategoryCriteria(userId))
    }

    @GetMapping("/tags")
    fun getTags(
        @RequestParam(required = false) userId: Int?,
        @RequestParam(required = false) name: String?
    ): TagsResponse {
        return getTagsQuery.execute(TagCriteria(name, userId))
    }

    @DeleteMapping("/categories/{id}")
    fun deleteCategory(@PathVariable id: Int): SuccessResponse {
        return deleteCategoryCommand.execute(id)
    }

    @PutMapping("/categories/{id}")
    fun updateCategory(@PathVariable id: Int, @Valid @RequestBody request: UpdateCategoryDto): SuccessResponse {
        return updateCategoryCommand.execute(id, request)
    }

    @PutMapping("/{id}/photos")
    fun assignPhotos(@PathVariable id: Int, @Valid @RequestBody request: AssignPhotosToReceiptDto): SuccessResponse {
        return assignPhotosToReceiptCommand.execute(id, request)
    }

    @DeleteMapping("/{id}/photos/{name}")
    fun divestPhoto(@PathVariable id: Int, @PathVariable @Length(max = 150) name: String): SuccessResponse {
        return divestPhotoFromReceiptCommand.execute(id, name)
    }

}