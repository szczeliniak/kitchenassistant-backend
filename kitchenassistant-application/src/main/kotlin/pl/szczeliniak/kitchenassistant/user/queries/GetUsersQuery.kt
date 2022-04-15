package pl.szczeliniak.kitchenassistant.user.queries

import pl.szczeliniak.kitchenassistant.common.dto.PaginationUtils
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.Pagination
import pl.szczeliniak.kitchenassistant.user.UserDao
import pl.szczeliniak.kitchenassistant.user.queries.dto.UserDto
import pl.szczeliniak.kitchenassistant.user.queries.dto.UsersResponse

class GetUsersQuery(private val userDao: UserDao) {

    fun execute(page: Long?, limit: Int?): UsersResponse {
        val currentPage = PaginationUtils.calculatePageNumber(page)
        val currentLimit = PaginationUtils.calculateLimit(limit)
        val offset = PaginationUtils.calculateOffset(currentPage, currentLimit)
        val totalNumberOfPages = PaginationUtils.calculateNumberOfPages(currentLimit, userDao.count())
        return UsersResponse(
            userDao.findAll(offset, currentLimit).map { UserDto.fromDomain(it) },
            Pagination(currentPage, currentLimit, totalNumberOfPages)
        )
    }

}