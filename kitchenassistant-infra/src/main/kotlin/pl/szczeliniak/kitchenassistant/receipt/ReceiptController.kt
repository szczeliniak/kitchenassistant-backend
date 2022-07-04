package pl.szczeliniak.kitchenassistant.receipt

import org.hibernate.validator.constraints.Length
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.*
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.*
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import javax.validation.Valid

@RestController
@RequestMapping("/receipts")
@Validated
class ReceiptController(
    private val receiptFacade: ReceiptFacade
) {

    @GetMapping("/{receiptId}")
    fun getReceipt(@PathVariable receiptId: Int): ReceiptResponse {
        return receiptFacade.getReceipt(receiptId)
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
        return receiptFacade.getReceipts(page, limit, ReceiptCriteria(userId, categoryId, name, tag))
    }

    @PostMapping
    fun addReceipt(@Valid @RequestBody dto: NewReceiptDto): SuccessResponse {
        return receiptFacade.addReceipt(dto)
    }

    @PutMapping("/{receiptId}")
    fun updateReceipt(@PathVariable receiptId: Int, @Valid @RequestBody dto: UpdateReceiptDto): SuccessResponse {
        return receiptFacade.updateReceipt(receiptId, dto)
    }

    @DeleteMapping("/{receiptId}")
    fun deleteReceipt(@PathVariable receiptId: Int): SuccessResponse {
        return receiptFacade.deleteReceipt(receiptId)
    }

    @PostMapping("{receiptId}/steps")
    fun addStep(@PathVariable receiptId: Int, @Valid @RequestBody dto: NewStepDto): SuccessResponse {
        return receiptFacade.addStep(receiptId, dto)
    }

    @PutMapping("/{receiptId}/steps/{stepId}")
    fun updateStep(
        @PathVariable receiptId: Int,
        @PathVariable stepId: Int,
        @Valid @RequestBody dto: UpdateStepDto
    ): SuccessResponse {
        return receiptFacade.updateStep(receiptId, stepId, dto)
    }

    @DeleteMapping("/{receiptId}/steps/{stepId}")
    fun deleteStep(@PathVariable receiptId: Int, @PathVariable stepId: Int): SuccessResponse {
        return receiptFacade.deleteStep(receiptId, stepId)
    }

    @PostMapping("/{receiptId}/ingredientGroups")
    fun addIngredientGroup(
        @PathVariable receiptId: Int,
        @Valid @RequestBody dto: NewIngredientGroupDto
    ): SuccessResponse {
        return receiptFacade.addIngredientGroup(receiptId, dto)
    }

    @DeleteMapping("/{receiptId}/ingredientGroups/{ingredientGroupId}")
    fun deleteIngredientGroup(
        @PathVariable receiptId: Int,
        @PathVariable ingredientGroupId: Int
    ): SuccessResponse {
        return receiptFacade.deleteIngredientGroup(receiptId, ingredientGroupId)
    }

    @PostMapping("/{receiptId}/ingredientGroups/{ingredientGroupId}/ingredients")
    fun addIngredient(
        @PathVariable receiptId: Int,
        @PathVariable ingredientGroupId: Int,
        @Valid @RequestBody dto: NewIngredientDto
    ): SuccessResponse {
        return receiptFacade.addIngredient(receiptId, ingredientGroupId, dto)
    }

    @PutMapping("/{receiptId}/ingredientGroups/{ingredientGroupId}/ingredients/{ingredientId}")
    fun updateIngredient(
        @PathVariable receiptId: Int,
        @PathVariable ingredientGroupId: Int,
        @PathVariable ingredientId: Int,
        @Valid @RequestBody dto: UpdateIngredientDto
    ): SuccessResponse {
        return receiptFacade.updateIngredient(receiptId, ingredientGroupId, ingredientId, dto)
    }

    @DeleteMapping("/{receiptId}/ingredientGroups/{ingredientGroupId}/ingredients/{ingredientId}")
    fun deleteIngredient(
        @PathVariable receiptId: Int,
        @PathVariable ingredientGroupId: Int,
        @PathVariable ingredientId: Int
    ): SuccessResponse {
        return receiptFacade.deleteIngredient(receiptId, ingredientGroupId, ingredientId)
    }

    @PostMapping("/categories")
    fun addCategory(@Valid @RequestBody dto: NewCategoryDto): SuccessResponse {
        return receiptFacade.addCategory(dto)
    }

    @GetMapping("/categories")
    fun getCategories(@RequestParam(required = false) userId: Int?): CategoriesResponse {
        return receiptFacade.getCategories(CategoryCriteria(userId))
    }

    @GetMapping("/tags")
    fun getTags(
        @RequestParam(required = false) userId: Int?,
        @RequestParam(required = false) name: String?
    ): TagsResponse {
        return receiptFacade.getTags(TagCriteria(name, userId))
    }

    @GetMapping("/authors")
    fun getAuthors(
        @RequestParam(required = false) userId: Int?,
        @RequestParam(required = false) name: String?
    ): AuthorsResponse {
        return receiptFacade.getAuthors(AuthorCriteria(name, userId))
    }

    @DeleteMapping("/categories/{categoryId}")
    fun deleteCategory(@PathVariable categoryId: Int): SuccessResponse {
        return receiptFacade.deleteCategory(categoryId)
    }

    @PutMapping("/categories/{categoryId}")
    fun updateCategory(@PathVariable categoryId: Int, @Valid @RequestBody request: UpdateCategoryDto): SuccessResponse {
        return receiptFacade.updateCategory(categoryId, request)
    }

    @PutMapping("/{receiptId}/photos")
    fun assignReceiptPhotos(
        @PathVariable receiptId: Int,
        @Valid @RequestBody request: AssignPhotosToReceiptDto
    ): SuccessResponse {
        return receiptFacade.assignReceiptPhotos(receiptId, request)
    }

    @PutMapping("/{receiptId}/favorite/{isFavorite}")
    fun markReceiptAsFavorite(@PathVariable receiptId: Int, @PathVariable isFavorite: Boolean): SuccessResponse {
        return receiptFacade.markReceiptAsFavorite(receiptId, isFavorite)
    }

    @PostMapping("/photos")
    fun uploadPhoto(@RequestParam userId: Int, @RequestParam("file") file: MultipartFile): SuccessResponse {
        return receiptFacade.uploadPhoto(userId, file.originalFilename ?: file.name, file.bytes)
    }

    @GetMapping("/photos/{photoId}")
    fun downloadPhoto(@PathVariable photoId: Int): ResponseEntity<ByteArray> {
        val response = receiptFacade.downloadPhoto(photoId)
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(response.mediaType.mimeType))
            .body(response.body)
    }

    @DeleteMapping("/photos/{photoId}")
    fun deletePhoto(@PathVariable photoId: Int): SuccessResponse {
        return receiptFacade.deletePhoto(photoId)
    }

}