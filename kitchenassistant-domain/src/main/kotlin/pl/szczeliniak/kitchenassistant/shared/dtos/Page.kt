package pl.szczeliniak.kitchenassistant.shared.dtos

data class Page<T>(
    val pageNumber: Long,
    val totalNumberOfPages: Long,
    val items: Collection<T>
)
