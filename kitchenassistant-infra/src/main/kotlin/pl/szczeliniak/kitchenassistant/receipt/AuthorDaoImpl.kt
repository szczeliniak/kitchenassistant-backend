package pl.szczeliniak.kitchenassistant.receipt

import org.springframework.stereotype.Component

@Component
class AuthorDaoImpl(
    private val authorRepository: AuthorRepository,
    private val authorMapper: AuthorMapper
) : AuthorDao {

    override fun save(author: Author): Author {
        return authorMapper.toDomain(authorRepository.save(authorMapper.toEntity(author)))
    }

    override fun saveAll(authors: Set<Author>) {
        authors.forEach { authorRepository.save(authorMapper.toEntity(it)) }
    }

    override fun findByName(name: String, userId: Int): Author? {
        return authorRepository.findByName(name, userId)?.let { authorMapper.toDomain(it) }
    }

    override fun findAll(criteria: AuthorCriteria): Set<Author> {
        return authorRepository.findAll(AuthorRepository.SearchCriteria(criteria.name, criteria.userId))
            .map { authorMapper.toDomain(it) }
            .toSet()
    }

}