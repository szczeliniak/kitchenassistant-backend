package pl.szczeliniak.kitchenassistant.receipt

import java.time.LocalDateTime

data class Step(
    var id: Int = 0,
    private var title_: String,
    private var description_: String?,
    private var sequence_: Int?,
    private val createdAt_: LocalDateTime = LocalDateTime.now(),
    private var modifiedAt_: LocalDateTime = LocalDateTime.now()
) {
    val title: String get() = title_
    val description: String? get() = description_
    val sequence: Int? get() = sequence_
    val createdAt: LocalDateTime get() = createdAt_
    val modifiedAt: LocalDateTime get() = modifiedAt_
}
