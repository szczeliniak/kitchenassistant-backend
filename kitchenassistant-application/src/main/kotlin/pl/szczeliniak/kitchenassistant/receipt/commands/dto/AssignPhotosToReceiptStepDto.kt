package pl.szczeliniak.kitchenassistant.receipt.commands.dto

import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class AssignPhotosToReceiptStepDto(
    @field:Size(min = 1, max = 20) var names: List<@NotNull Int> = listOf()
)