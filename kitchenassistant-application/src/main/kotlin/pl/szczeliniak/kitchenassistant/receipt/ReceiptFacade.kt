package pl.szczeliniak.kitchenassistant.receipt

import pl.szczeliniak.kitchenassistant.receipt.commands.*
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.*
import pl.szczeliniak.kitchenassistant.receipt.queries.*
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.*
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

open class ReceiptFacade(
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

    open fun getReceipt(receiptId: Int): ReceiptResponse {
        return getReceiptQuery.execute(receiptId)
    }

    open fun getReceipts(page: Long?, limit: Int?, criteria: ReceiptCriteria): ReceiptsResponse {
        return getReceiptsQuery.execute(page, limit, criteria)
    }

    open fun addReceipt(dto: NewReceiptDto): SuccessResponse {
        return addReceiptCommand.execute(dto)
    }

    open fun updateReceipt(receiptId: Int, dto: UpdateReceiptDto): SuccessResponse {
        return updateReceiptCommand.execute(receiptId, dto)
    }

    open fun deleteReceipt(receiptId: Int): SuccessResponse {
        return deleteReceiptCommand.execute(receiptId)
    }

    open fun addStep(receiptId: Int, dto: NewStepDto): SuccessResponse {
        return addStepCommand.execute(receiptId, dto)
    }

    open fun updateStep(receiptId: Int, stepId: Int, dto: UpdateStepDto): SuccessResponse {
        return updateStepCommand.execute(receiptId, stepId, dto)
    }

    open fun deleteStep(receiptId: Int, stepId: Int): SuccessResponse {
        return deleteStepCommand.execute(receiptId, stepId)
    }

    open fun addIngredientGroup(receiptId: Int, dto: NewIngredientGroupDto): SuccessResponse {
        return addIngredientGroupCommand.execute(receiptId, dto)
    }

    open fun deleteIngredientGroup(receiptId: Int, ingredientGroupId: Int): SuccessResponse {
        return deleteIngredientGroupCommand.execute(receiptId, ingredientGroupId)
    }

    open fun addIngredient(receiptId: Int, ingredientGroupId: Int, dto: NewIngredientDto): SuccessResponse {
        return addIngredientCommand.execute(receiptId, ingredientGroupId, dto)
    }

    open fun updateIngredient(
        receiptId: Int,
        ingredientGroupId: Int,
        ingredientId: Int,
        dto: UpdateIngredientDto
    ): SuccessResponse {
        return updateIngredientCommand.execute(receiptId, ingredientGroupId, ingredientId, dto)
    }

    open fun deleteIngredient(receiptId: Int, ingredientGroupId: Int, ingredientId: Int): SuccessResponse {
        return deleteIngredientCommand.execute(receiptId, ingredientGroupId, ingredientId)
    }

    open fun addCategory(dto: NewCategoryDto): SuccessResponse {
        return addCategoryCommand.execute(dto)
    }

    open fun getCategories(criteria: CategoryCriteria): CategoriesResponse {
        return getCategoriesQuery.execute(criteria)
    }

    open fun deleteCategory(categoryId: Int): SuccessResponse {
        return deleteCategoryCommand.execute(categoryId)
    }

    open fun updateCategory(categoryId: Int, request: UpdateCategoryDto): SuccessResponse {
        return updateCategoryCommand.execute(categoryId, request)
    }

    open fun getTags(criteria: TagCriteria): TagsResponse {
        return getTagsQuery.execute(criteria)
    }

    open fun getAuthors(criteria: AuthorCriteria): AuthorsResponse {
        return getAuthorsQuery.execute(criteria)
    }

    open fun assignReceiptPhotos(receiptId: Int, request: AssignPhotosToReceiptDto): SuccessResponse {
        return assignReceiptPhotosCommand.execute(receiptId, request)
    }

    open fun markReceiptAsFavorite(receiptId: Int, isFavorite: Boolean): SuccessResponse {
        return markReceiptAsFavoriteCommand.execute(receiptId, isFavorite)
    }

    open fun uploadPhoto(userId: Int, name: String, bytes: ByteArray): SuccessResponse {
        return uploadPhotoCommand.execute(name, bytes, userId)
    }

    open fun downloadPhoto(photoId: Int): GetPhotoResponse {
        return downloadPhotoQuery.execute(photoId)
    }

    open fun deletePhoto(photoId: Int): SuccessResponse {
        return deletePhotoCommand.execute(photoId)
    }

}