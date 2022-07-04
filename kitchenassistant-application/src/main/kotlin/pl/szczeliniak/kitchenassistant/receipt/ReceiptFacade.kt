package pl.szczeliniak.kitchenassistant.receipt

import pl.szczeliniak.kitchenassistant.receipt.commands.dto.*
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.*
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

interface ReceiptFacade {

    fun getReceipt(receiptId: Int): ReceiptResponse
    fun getReceipts(page: Long?, limit: Int?, criteria: ReceiptCriteria): ReceiptsResponse
    fun addReceipt(dto: NewReceiptDto): SuccessResponse
    fun updateReceipt(receiptId: Int, dto: UpdateReceiptDto): SuccessResponse
    fun deleteReceipt(receiptId: Int): SuccessResponse
    fun addStep(receiptId: Int, dto: NewStepDto): SuccessResponse
    fun updateStep(receiptId: Int, stepId: Int, dto: UpdateStepDto): SuccessResponse
    fun deleteStep(receiptId: Int, stepId: Int): SuccessResponse
    fun addIngredientGroup(receiptId: Int, dto: NewIngredientGroupDto): SuccessResponse
    fun deleteIngredientGroup(receiptId: Int, ingredientGroupId: Int): SuccessResponse
    fun addIngredient(receiptId: Int, ingredientGroupId: Int, dto: NewIngredientDto): SuccessResponse
    fun updateIngredient(
        receiptId: Int,
        ingredientGroupId: Int,
        ingredientId: Int,
        dto: UpdateIngredientDto
    ): SuccessResponse

    fun deleteIngredient(receiptId: Int, ingredientGroupId: Int, ingredientId: Int): SuccessResponse
    fun addCategory(dto: NewCategoryDto): SuccessResponse
    fun getCategories(criteria: CategoryCriteria): CategoriesResponse
    fun deleteCategory(categoryId: Int): SuccessResponse
    fun updateCategory(categoryId: Int, request: UpdateCategoryDto): SuccessResponse
    fun getTags(criteria: TagCriteria): TagsResponse
    fun getAuthors(criteria: AuthorCriteria): AuthorsResponse
    fun assignReceiptPhotos(receiptId: Int, request: AssignPhotosToReceiptDto): SuccessResponse
    fun markReceiptAsFavorite(receiptId: Int, isFavorite: Boolean): SuccessResponse
    fun uploadPhoto(userId: Int, name: String, bytes: ByteArray): SuccessResponse
    fun downloadPhoto(photoId: Int): GetPhotoResponse
    fun deletePhoto(photoId: Int): SuccessResponse

}