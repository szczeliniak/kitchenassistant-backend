package pl.szczeliniak.kitchenassistant.file.queries

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.file.File
import pl.szczeliniak.kitchenassistant.file.FileDao

internal class CheckIfFileExistsQueryTest : JunitBaseClass() {

    @Mock
    private lateinit var fileDao: FileDao

    @InjectMocks
    private lateinit var checkIfFileExistsQuery: CheckIfFileExistsQuery

    @Test
    fun shouldReturnTrueWhenFileByIdFound() {
        whenever(fileDao.findById(1)).thenReturn(file())

        val result = checkIfFileExistsQuery.execute(1)

        Assertions.assertTrue(result.exists)
    }

    @Test
    fun shouldReturnFalseWhenFileByIdNotFound() {
        whenever(fileDao.findById(1)).thenReturn(null)

        val result = checkIfFileExistsQuery.execute(1)

        Assertions.assertFalse(result.exists)
    }

    private fun file(): File {
        return File(name_ = "NAME", userId_ = 2)
    }

}