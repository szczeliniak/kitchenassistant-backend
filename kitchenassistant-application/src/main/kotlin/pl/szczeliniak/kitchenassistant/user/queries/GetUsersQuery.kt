package pl.szczeliniak.kitchenassistant.user.queries

import pl.szczeliniak.kitchenassistant.shared.dtos.Pagination
import pl.szczeliniak.kitchenassistant.shared.dtos.PaginationUtils
import pl.szczeliniak.kitchenassistant.user.UserCriteria
import pl.szczeliniak.kitchenassistant.user.UserDao
import pl.szczeliniak.kitchenassistant.user.queries.dto.UsersResponse

class GetUsersQuery(private val userDao: UserDao, private val userConverter: UserConverter) {

    fun execute(page: Long?, limit: Int?): UsersResponse {
        val currentPage = PaginationUtils.calculatePageNumber(page)
        val currentLimit = PaginationUtils.calculateLimit(limit)
        val offset = PaginationUtils.calculateOffset(currentPage, currentLimit)
        val criteria = UserCriteria()
        val totalNumberOfPages = PaginationUtils.calculateNumberOfPages(currentLimit, userDao.count(criteria))
        return UsersResponse(
            userDao.findAll(criteria, offset, currentLimit).map { userConverter.map(it) }.toSet(),
            Pagination(currentPage, currentLimit, totalNumberOfPages)
        )
    }

}