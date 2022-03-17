package pl.szczeliniak.kitchenassistant.receipt

import org.springframework.web.bind.annotation.*
import pl.szczeliniak.kitchenassistant.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.receipt.commands.*
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.NewIngredientDto
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.NewReceiptDto
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.NewStepDto
import pl.szczeliniak.kitchenassistant.receipt.queries.GetReceiptQuery
import pl.szczeliniak.kitchenassistant.receipt.queries.GetReceiptsQuery
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.ReceiptResponse
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.ReceiptsResponse

@RestController
@RequestMapping("/receipts")
class ReceiptController(
    private val getReceiptQuery: GetReceiptQuery,
    private val getReceiptsQuery: GetReceiptsQuery,
    private val addReceiptCommand: AddReceiptCommand,
    private val addIngredientCommand: AddIngredientCommand,
    private val addStepCommand: AddStepCommand,
    private val deleteReceiptCommand: DeleteReceiptCommand,
    private val deleteIngredientCommand: DeleteIngredientCommand,
    private val deleteStepCommand: DeleteStepCommand
) {

    @GetMapping("/{id}")
    fun getReceipt(@PathVariable id: Int): ReceiptResponse {
        return getReceiptQuery.execute(id)
    }

    @GetMapping
    fun getReceipts(): ReceiptsResponse {
        return getReceiptsQuery.execute(ReceiptCriteria(null))
    }

    @PostMapping
    fun addReceipt(@RequestBody dto: NewReceiptDto): SuccessResponse {
        return addReceiptCommand.execute(dto)
    }

    @DeleteMapping("/{id}")
    fun deleteReceipt(@PathVariable id: Int): SuccessResponse {
        return deleteReceiptCommand.execute(id)
    }

    @PostMapping("{id}/steps")
    fun addStep(@PathVariable id: Int, @RequestBody dto: NewStepDto): SuccessResponse {
        return addStepCommand.execute(id, dto)
    }

    @DeleteMapping("/{id}/steps/{stepId}")
    fun deleteStep(@PathVariable id: Int, @PathVariable stepId: Int): SuccessResponse {
        return deleteStepCommand.execute(id, stepId)
    }

    @PostMapping("{id}/ingredients")
    fun addIngredient(@PathVariable id: Int, @RequestBody dto: NewIngredientDto): SuccessResponse {
        return addIngredientCommand.execute(id, dto)
    }

    @DeleteMapping("/{id}/ingredients/{ingredientId}")
    fun deleteIngredient(@PathVariable id: Int, @PathVariable ingredientId: Int): SuccessResponse {
        return deleteIngredientCommand.execute(id, ingredientId)
    }


}