package pl.szczeliniak.kitchenassistant.receipt.commands.dto

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class AssignPhotosToReceiptDto(
    @field:NotEmpty var names: List<@NotNull @Length(min = 1, max = 100) String> = listOf()
)