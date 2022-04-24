package pl.szczeliniak.kitchenassistant.file.queries

import pl.szczeliniak.kitchenassistant.file.FileDao
import pl.szczeliniak.kitchenassistant.file.queries.dtos.CheckIfFileExistsResponse

open class CheckIfFileExistsQuery(private val fileDao: FileDao) {

    open fun execute(id: Int): CheckIfFileExistsResponse {
        return CheckIfFileExistsResponse(fileDao.findById(id) != null)
    }

}