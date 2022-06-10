package pl.szczeliniak.kitchenassistant.receipt

import org.hibernate.validator.constraints.Length
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import pl.szczeliniak.kitchenassistant.receipt.commands.*
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.*
import pl.szczeliniak.kitchenassistant.receipt.queries.*
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.*
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
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
    private val deleteCategoryCommand: DeleteCategoryCommand,
    private val updateCategoryCommand: UpdateCategoryCommand,
    private val updateStepCommand: UpdateStepCommand,
    private val getCategoriesQuery: GetCategoriesQuery,
    private val assignReceiptPhotosCommand: AssignReceiptPhotosCommand,
    private val getTagsQuery: GetTagsQuery,
    private val markReceiptAsFavoriteCommand: MarkReceiptAsFavoriteCommand,
    private val getAuthorsQuery: GetAuthorsQuery,
    private val uploadPhotoCommand: UploadPhotoCommand,
    private val deletePhotoCommand: DeletePhotoCommand,
    private val downloadPhotoQuery: DownloadPhotoQuery,
    private val addIngredientGroupCommand: AddIngredientGroupCommand,
    private val deleteIngredientGroupCommand: DeleteIngredientGroupCommand
) {

    @GetMapping("/{receiptId}")
    fun getReceipt(@PathVariable receiptId: Int): ReceiptResponse {
        return getReceiptQuery.execute(receiptId)
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

    @PutMapping("/{receiptId}")
    fun updateReceipt(@PathVariable receiptId: Int, @Valid @RequestBody dto: UpdateReceiptDto): SuccessResponse {
        return updateReceiptCommand.execute(receiptId, dto)
    }

    @DeleteMapping("/{receiptId}")
    fun deleteReceipt(@PathVariable receiptId: Int): SuccessResponse {
        return deleteReceiptCommand.execute(receiptId)
    }

    @PostMapping("{receiptId}/steps")
    fun addStep(@PathVariable receiptId: Int, @Valid @RequestBody dto: NewStepDto): SuccessResponse {
        return addStepCommand.execute(receiptId, dto)
    }

    @PutMapping("/{receiptId}/steps/{stepId}")
    fun updateStep(
        @PathVariable receiptId: Int,
        @PathVariable stepId: Int,
        @Valid @RequestBody dto: UpdateStepDto
    ): SuccessResponse {
        return updateStepCommand.execute(receiptId, stepId, dto)
    }

    @DeleteMapping("/{receiptId}/steps/{stepId}")
    fun deleteStep(@PathVariable receiptId: Int, @PathVariable stepId: Int): SuccessResponse {
        return deleteStepCommand.execute(receiptId, stepId)
    }

    @PostMapping("/{receiptId}/ingredientGroups")
    fun addIngredientGroup(
        @PathVariable receiptId: Int,
        @Valid @RequestBody dto: NewIngredientGroupDto
    ): SuccessResponse {
        return addIngredientGroupCommand.execute(receiptId, dto)
    }

    @DeleteMapping("/{receiptId}/ingredientGroups/{ingredientGroupId}")
    fun deleteIngredientGroup(
        @PathVariable receiptId: Int,
        @PathVariable ingredientGroupId: Int
    ): SuccessResponse {
        return deleteIngredientGroupCommand.execute(receiptId, ingredientGroupId)
    }

    @PostMapping("/{receiptId}/ingredientGroups/{ingredientGroupId}/ingredients")
    fun addIngredient(
        @PathVariable receiptId: Int,
        @PathVariable ingredientGroupId: Int,
        @Valid @RequestBody dto: NewIngredientDto
    ): SuccessResponse {
        return addIngredientCommand.execute(receiptId, ingredientGroupId, dto)
    }

    @PutMapping("/{receiptId}/ingredientGroups/{ingredientGroupId}/ingredients/{ingredientId}")
    fun updateIngredient(
        @PathVariable receiptId: Int,
        @PathVariable ingredientGroupId: Int,
        @PathVariable ingredientId: Int,
        @Valid @RequestBody dto: UpdateIngredientDto
    ): SuccessResponse {
        return updateIngredientCommand.execute(receiptId, ingredientGroupId, ingredientId, dto)
    }

    @DeleteMapping("/{receiptId}/ingredientGroups/{ingredientGroupId}/ingredients/{ingredientId}")
    fun deleteIngredient(
        @PathVariable receiptId: Int,
        @PathVariable ingredientGroupId: Int,
        @PathVariable ingredientId: Int
    ): SuccessResponse {
        return deleteIngredientCommand.execute(receiptId, ingredientGroupId, ingredientId)
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

    @GetMapping("/authors")
    fun getAuthors(
        @RequestParam(required = false) userId: Int?,
        @RequestParam(required = false) name: String?
    ): AuthorsResponse {
        return getAuthorsQuery.execute(AuthorCriteria(name, userId))
    }

    @DeleteMapping("/categories/{categoryId}")
    fun deleteCategory(@PathVariable categoryId: Int): SuccessResponse {
        return deleteCategoryCommand.execute(categoryId)
    }

    @PutMapping("/categories/{categoryId}")
    fun updateCategory(@PathVariable categoryId: Int, @Valid @RequestBody request: UpdateCategoryDto): SuccessResponse {
        return updateCategoryCommand.execute(categoryId, request)
    }

    @PutMapping("/{receiptId}/photos")
    fun assignReceiptPhotos(
        @PathVariable receiptId: Int,
        @Valid @RequestBody request: AssignPhotosToReceiptDto
    ): SuccessResponse {
        return assignReceiptPhotosCommand.execute(receiptId, request)
    }

    @PutMapping("/{receiptId}/favorite/{isFavorite}")
    fun markReceiptAsFavorite(@PathVariable receiptId: Int, @PathVariable isFavorite: Boolean): SuccessResponse {
        return markReceiptAsFavoriteCommand.execute(receiptId, isFavorite)
    }

    @PostMapping("/photos")
    fun uploadPhoto(@RequestParam userId: Int, @RequestParam("file") file: MultipartFile): SuccessResponse {
        return uploadPhotoCommand.execute(file.originalFilename ?: file.name, file.bytes, userId)
    }

    @GetMapping("/photos/{photoId}")
    fun downloadPhoto(@PathVariable photoId: Int): ResponseEntity<ByteArray> {
        val response = downloadPhotoQuery.execute(photoId)
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(response.mediaType.mimeType))
            .body(response.body)
    }

    @DeleteMapping("/photos/{photoId}")
    fun deletePhoto(@PathVariable photoId: Int): SuccessResponse {
        return deletePhotoCommand.execute(photoId)
    }

}