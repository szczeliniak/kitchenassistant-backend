package pl.szczeliniak.kitchenassistant.receipt

import org.springframework.web.bind.annotation.*
import pl.szczeliniak.kitchenassistant.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.receipt.commands.AddNewReceipt
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.AddNewReceiptDto
import pl.szczeliniak.kitchenassistant.receipt.queries.GetReceipt
import pl.szczeliniak.kitchenassistant.receipt.queries.GetReceipts
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.ReceiptCriteriaDto
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.ReceiptResponse
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.ReceiptsResponse

@RestController
@RequestMapping("/receipts")
class ReceiptController(
    private val getReceipt: GetReceipt,
    private val getReceipts: GetReceipts,
    private val addNewReceipt: AddNewReceipt
) {

    @GetMapping("/{id}")
    fun getReceipt(@PathVariable id: Int): ReceiptResponse {
        return getReceipt.execute(id)
    }

    @GetMapping
    fun getReceipts(@RequestParam(required = false) userId: Int): ReceiptsResponse {
        return getReceipts.execute(ReceiptCriteriaDto(userId))
    }

    @PostMapping
    fun addReceipt(@RequestBody dto: AddNewReceiptDto): SuccessResponse {
        return addNewReceipt.execute(dto)
    }

}