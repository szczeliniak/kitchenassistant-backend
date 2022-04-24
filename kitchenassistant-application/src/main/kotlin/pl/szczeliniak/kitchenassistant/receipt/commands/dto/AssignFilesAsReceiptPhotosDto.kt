package pl.szczeliniak.kitchenassistant.receipt.commands.dto

import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class AssignFilesAsReceiptPhotosDto(
    @field:Size(min = 1, max = 20) var fileIds: Set<@NotNull Int> = setOf()
)