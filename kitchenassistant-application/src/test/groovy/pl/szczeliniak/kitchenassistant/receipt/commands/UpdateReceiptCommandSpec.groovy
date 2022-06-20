package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.receipt.*
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.UpdateReceiptDto
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.AuthorFactory
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.TagFactory
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZoneId
import java.time.ZonedDateTime

class UpdateReceiptCommandSpec extends Specification {

    def receiptDao = Mock(ReceiptDao)
    def categoryDao = Mock(CategoryDao)
    def tagDao = Mock(TagDao)
    def tagFactory = Mock(TagFactory)
    def authorDao = Mock(AuthorDao)
    def authorFactory = Mock(AuthorFactory)

    @Subject
    def updateReceiptCommand = new UpdateReceiptCommand(receiptDao, categoryDao, tagDao, tagFactory, authorFactory, authorDao)

    def 'should update receipt'() {
        given:
        def tagToRemove = tag(10, "TAG_TO_REMOVE")
        def assignedTag = tag(11, "ASSIGNED_TAG")
        def newTag = tag(12, "NEW_TAG")
        def existingTag = tag(13, "EXISTING_TAG")
        def receipt = receipt(new HashSet<Tag>(List.of(tagToRemove, assignedTag)))
        def newCategory = category(3)
        def author = author()

        receiptDao.findById(1) >> receipt
        receiptDao.save(receipt) >> receipt
        categoryDao.findById(3) >> newCategory
        tagDao.findByName("EXISTING_TAG", 4) >> existingTag
        tagDao.findByName("NEW_TAG", 4) >> null
        tagFactory.create("NEW_TAG", 4) >> newTag
        authorDao.findByName("AUTHOR", 4) >> null
        tagDao.save(newTag) >> newTag
        authorFactory.create("AUTHOR", 4) >> author
        authorDao.save(author) >> author

        when:
        def result = updateReceiptCommand.execute(1, updateReceiptDto())

        then:
        receipt.name == "NAME"
        receipt.description == "DESC"
        receipt.author == author
        receipt.source == "SOURCE"
        receipt.category == newCategory
        receipt.tags == Set.of(assignedTag, existingTag, newTag)
        result == new SuccessResponse(1)
    }

    private static UpdateReceiptDto updateReceiptDto() {
        return new UpdateReceiptDto("NAME", 3, "DESC", "AUTHOR", "SOURCE", Set.of("ASSIGNED_TAG", "EXISTING_TAG", "NEW_TAG",))
    }

    private static Receipt receipt(Set<Tag> tags) {
        return new Receipt(1, 4, "", "", new Author(2, "", 1, ZonedDateTime.now(), ZonedDateTime.now()), "", false, category(0), Collections.emptySet(),
                Collections.emptySet(), Collections.emptySet(), tags, false, ZonedDateTime.now(), ZonedDateTime.now())
    }

    static Category category(Integer id) {
        return new Category(id, "", 3, 4, false,
                ZonedDateTime.of(2000, 1, 1, 1, 1, 0, 0, ZoneId.systemDefault()),
                ZonedDateTime.of(2000, 1, 1, 1, 2, 0, 0, ZoneId.systemDefault()))
    }

    static Tag tag(Integer id, String name) {
        return new Tag(id, name, 4, ZonedDateTime.now(), ZonedDateTime.now())
    }

    static Photo photo(Integer id) {
        return new Photo(id, "", 1, false, ZonedDateTime.now(), ZonedDateTime.now())
    }

    static Author author() {
        return new Author(2, "", 1, ZonedDateTime.now(), ZonedDateTime.now())
    }
}
