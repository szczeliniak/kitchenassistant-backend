package pl.szczeliniak.kitchenassistant.receipt

import org.springframework.web.bind.annotation.*
import pl.szczeliniak.kitchenassistant.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.receipt.commands.AddNewReceiptCommand
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.AddNewReceiptDto
import pl.szczeliniak.kitchenassistant.receipt.queries.GetReceiptQuery
import pl.szczeliniak.kitchenassistant.receipt.queries.GetReceiptsQuery
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.ReceiptCriteriaDto
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.ReceiptResponse
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.ReceiptsResponse

@RestController
@RequestMapping("/receipts")
class ReceiptController(
    private val getReceiptQuery: GetReceiptQuery,
    private val getReceiptsQuery: GetReceiptsQuery,
    private val addNewReceiptCommand: AddNewReceiptCommand
) {

    @GetMapping("/{id}")
    fun getReceipt(@PathVariable id: Int): ReceiptResponse {
        return getReceiptQuery.execute(id)
    }

    @GetMapping
    fun getReceipts(@RequestParam(required = false) userId: Int): ReceiptsResponse {
        return getReceiptsQuery.execute(ReceiptCriteriaDto(userId))
    }

    @PostMapping
    fun addReceipt(@RequestBody dto: AddNewReceiptDto): SuccessResponse {
        return addNewReceiptCommand.execute(dto)
    }

}