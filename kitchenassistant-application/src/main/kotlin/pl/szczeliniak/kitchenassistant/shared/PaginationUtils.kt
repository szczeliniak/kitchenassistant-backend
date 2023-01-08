package pl.szczeliniak.kitchenassistant.shared

class PaginationUtils {

    companion object {

        private const val DEFAULT_PAGE_NUMBER = 1L
        private const val DEFAULT_LIMIT = 50
        private const val MAX_LIMIT = 200

        fun calculatePageNumber(pageNumber: Long?): Long {
            return if (pageNumber != null && pageNumber > 0) pageNumber else DEFAULT_PAGE_NUMBER
        }

        fun calculateLimit(limit: Int?): Int {
            return if (limit != null && limit > 0 && limit < MAX_LIMIT) limit else DEFAULT_LIMIT
        }

        fun calculateOffset(page: Long, limit: Int): Int {
            return (page.toInt() - 1) * limit
        }

        fun calculateNumberOfPages(limit: Int, numberOfRecords: Long): Long {
            val rest = numberOfRecords % limit
            return if (rest == 0L) numberOfRecords / limit else (numberOfRecords / limit) + 1
        }

    }

}