package pl.szczeliniak.cookbook.shared.dtos

data class Page<T>(
    val pageNumber: Long,
    val pageSize: Int,
    val totalNumberOfPages: Long,
    val items: Collection<T>
)
