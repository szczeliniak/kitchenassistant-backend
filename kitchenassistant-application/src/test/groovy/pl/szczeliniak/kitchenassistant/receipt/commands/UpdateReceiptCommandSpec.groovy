package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.common.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.receipt.*
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.UpdateReceiptDto
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.TagFactory
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class UpdateReceiptCommandSpec extends Specification {

    def receiptDao = Mock(ReceiptDao)
    def categoryDao = Mock(CategoryDao)
    def tagDao = Mock(TagDao)
    def tagFactory = Mock(TagFactory)

    @Subject
    def updateReceiptCommand = new UpdateReceiptCommand(receiptDao, categoryDao, tagDao, tagFactory)

    def 'should update receipt'() {
        given:
        def tagToRemove = tag(10, "TAG_TO_REMOVE")
        def assignedTag = tag(11, "ASSIGNED_TAG")
        def newTag = tag(12, "NEW_TAG")
        def existingTag = tag(13, "EXISTING_TAG")
        def receipt = receipt(new ArrayList<Tag>(List.of(tagToRemove, assignedTag)))
        def newCategory = category(3)

        receiptDao.findById(1) >> receipt
        receiptDao.save(receipt) >> receipt
        categoryDao.findById(3) >> newCategory
        tagDao.findByName("EXISTING_TAG", 4) >> existingTag
        tagDao.findByName("NEW_TAG", 4) >> null
        tagFactory.create("NEW_TAG", 4) >> newTag
        tagDao.save(newTag) >> newTag
        tagDao.saveAll(List.of(tagToRemove, assignedTag, existingTag, newTag))

        when:
        def result = updateReceiptCommand.execute(1, updateReceiptDto())

        then:
        receipt.name == "NAME"
        receipt.description == "DESC"
        receipt.author == "AUTHOR"
        receipt.source == "SOURCE"
        receipt.category == newCategory
        receipt.tags == List.of(assignedTag, existingTag, newTag)
        result == new SuccessResponse(1)
    }

    private static UpdateReceiptDto updateReceiptDto() {
        return new UpdateReceiptDto("NAME", 3, "DESC", "AUTHOR", "SOURCE", List.of("ASSIGNED_TAG", "EXISTING_TAG", "NEW_TAG",))
    }

    private static Receipt receipt(List<Tag> tags) {
        return new Receipt(1, 4, "", "", "", "", category(0), Collections.emptyList(),
                Collections.emptyList(), Collections.emptyList(), tags, false, LocalDateTime.now(), LocalDateTime.now())
    }

    static Category category(Integer id) {
        return new Category(id, "", 3, false,
                LocalDateTime.of(2000, 1, 1, 1, 1),
                LocalDateTime.of(2000, 1, 1, 1, 2))
    }

    static Tag tag(Integer id, String name) {
        return new Tag(id, name, 4, false, LocalDateTime.now(), LocalDateTime.now())
    }

}
