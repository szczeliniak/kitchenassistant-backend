package pl.szczeliniak.kitchenassistant.user.queries

import pl.szczeliniak.kitchenassistant.shared.PaginationUtils
import pl.szczeliniak.kitchenassistant.shared.dtos.Pagination
import pl.szczeliniak.kitchenassistant.user.db.UserCriteria
import pl.szczeliniak.kitchenassistant.user.db.UserDao
import pl.szczeliniak.kitchenassistant.user.queries.dto.UsersResponse

open class GetUsersQuery(private val userDao: UserDao, private val userConverter: UserConverter) {

    open fun execute(page: Long?, limit: Int?): UsersResponse {
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