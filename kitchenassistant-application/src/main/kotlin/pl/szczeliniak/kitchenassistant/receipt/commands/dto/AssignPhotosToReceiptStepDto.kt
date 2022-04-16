package pl.szczeliniak.kitchenassistant.receipt.commands.dto

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class AssignPhotosToReceiptStepDto(
    @field:Size(min = 1, max = 20) var names: List<@NotNull @Length(min = 1, max = 100) String> = listOf()
)