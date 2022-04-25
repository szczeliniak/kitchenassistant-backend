package pl.szczeliniak.kitchenassistant.file

import org.springframework.stereotype.Component

@Component
class FileDaoImpl(
    private val fileRepository: FileRepository,
    private val fileMapper: FileMapper
) : FileDao {

    override fun save(file: File): File {
        return fileMapper.toDomain(fileRepository.save(fileMapper.toEntity(file)))
    }

    override fun findById(id: Int): File? {
        return fileRepository.findById(id)?.let { fileMapper.toDomain(it) }
    }

}