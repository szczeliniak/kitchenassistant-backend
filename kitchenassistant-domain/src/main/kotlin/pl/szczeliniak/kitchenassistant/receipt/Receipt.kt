package pl.szczeliniak.kitchenassistant.receipt

import pl.szczeliniak.kitchenassistant.user.User
import java.time.LocalDateTime

class Receipt(
    private var id_: Int = 0,
    private var user_: User,
    private var name_: String,
    private var description_: String?,
    private var author_: String?,
    private var source_: String?,
    private var ingredients_: List<Ingredient>,
    private var steps_: List<Step>,
    private var createdAt_: LocalDateTime = LocalDateTime.now(),
    private var modifiedAt_: LocalDateTime = LocalDateTime.now()
) {
    val id: Int get() = id_
    val user: User get() = user_
    val name: String get() = name_
    val description: String? get() = description_
    val author: String? get() = author_
    val source: String? get() = source_
    val ingredients: List<Ingredient> get() = ingredients_
    val steps: List<Step> get() = steps_
    val createdAt: LocalDateTime get() = createdAt_
    val modifiedAt: LocalDateTime get() = modifiedAt_
}