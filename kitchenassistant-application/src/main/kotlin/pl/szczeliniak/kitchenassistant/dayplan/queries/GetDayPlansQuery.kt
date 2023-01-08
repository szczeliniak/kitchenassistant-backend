package pl.szczeliniak.kitchenassistant.dayplan.queries

import pl.szczeliniak.kitchenassistant.dayplan.DayPlanDao
import pl.szczeliniak.kitchenassistant.dayplan.queries.dto.DayPlanCriteria
import pl.szczeliniak.kitchenassistant.dayplan.queries.dto.DayPlansResponse
import pl.szczeliniak.kitchenassistant.shared.dtos.Pagination
import pl.szczeliniak.kitchenassistant.shared.PaginationUtils

class GetDayPlansQuery(private val dayPlanDao: DayPlanDao, private val dayPlanConverter: DayPlanConverter) {

    fun execute(page: Long?, limit: Int?, criteria: DayPlanCriteria): DayPlansResponse {
        val currentPage = PaginationUtils.calculatePageNumber(page)
        val currentLimit = PaginationUtils.calculateLimit(limit)
        val offset = PaginationUtils.calculateOffset(currentPage, currentLimit)
        val totalNumberOfPages = PaginationUtils.calculateNumberOfPages(currentLimit, dayPlanDao.count(criteria))
        return DayPlansResponse(
            dayPlanDao.findAll(criteria, offset, currentLimit).map { dayPlanConverter.map(it) },
            Pagination(currentPage, currentLimit, totalNumberOfPages)
        )
    }

}