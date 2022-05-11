package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.receipt.*
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.UpdateReceiptDto
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.PhotoFactory
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.TagFactory
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class UpdateReceiptCommandSpec extends Specification {

    def receiptDao = Mock(ReceiptDao)
    def categoryDao = Mock(CategoryDao)
    def tagDao = Mock(TagDao)
    def tagFactory = Mock(TagFactory)
    def photoFactory = Mock(PhotoFactory)
    def photoDao = Mock(PhotoDao)

    @Subject
    def updateReceiptCommand = new UpdateReceiptCommand(receiptDao, categoryDao, tagDao, tagFactory, photoFactory, photoDao)

    def 'should update receipt'() {
        given:
        def tagToRemove = tag(10, "TAG_TO_REMOVE")
        def assignedTag = tag(11, "ASSIGNED_TAG")
        def newTag = tag(12, "NEW_TAG")
        def existingTag = tag(13, "EXISTING_TAG")
        def photoToRemove = photo(15, 20)
        def assignedPhoto = photo(16, 21)
        def newPhoto = photo(17, 22)
        def receipt = receipt(
                new HashSet<Tag>(List.of(tagToRemove, assignedTag)),
                new HashSet<Photo>(List.of(photoToRemove, assignedPhoto)))
        def newCategory = category(3)

        receiptDao.findById(1) >> receipt
        receiptDao.save(receipt) >> receipt
        categoryDao.findById(3) >> newCategory
        tagDao.findByName("EXISTING_TAG", 4) >> existingTag
        tagDao.findByName("NEW_TAG", 4) >> null
        tagFactory.create("NEW_TAG", 4) >> newTag
        photoFactory.create(22) >> newPhoto
        tagDao.save(newTag) >> newTag
        photoDao.save(newPhoto) >> newPhoto
        tagDao.saveAll(Set.of(tagToRemove, assignedTag, existingTag, newTag))
        photoDao.saveAll(Set.of(photoToRemove, assignedPhoto, newPhoto))

        when:
        def result = updateReceiptCommand.execute(1, updateReceiptDto())

        then:
        receipt.name == "NAME"
        receipt.description == "DESC"
        receipt.author == "AUTHOR"
        receipt.source == "SOURCE"
        receipt.category == newCategory
        receipt.tags == Set.of(assignedTag, existingTag, newTag)
        receipt.photos == Set.of(assignedPhoto, newPhoto)
        result == new SuccessResponse(1)
    }

    private static UpdateReceiptDto updateReceiptDto() {
        return new UpdateReceiptDto("NAME", 3, "DESC", "AUTHOR", "SOURCE", Set.of("ASSIGNED_TAG", "EXISTING_TAG", "NEW_TAG",), Set.of(21, 22))
    }

    private static Receipt receipt(Set<Tag> tags, Set<Photo> photos) {
        return new Receipt(1, 4, "", "", "", "", false, category(0), Collections.emptySet(),
                Collections.emptySet(), photos, tags, false, LocalDateTime.now(), LocalDateTime.now())
    }

    static Category category(Integer id) {
        return new Category(id, "", 3, false,
                LocalDateTime.of(2000, 1, 1, 1, 1),
                LocalDateTime.of(2000, 1, 1, 1, 2))
    }

    static Tag tag(Integer id, String name) {
        return new Tag(id, name, 4, LocalDateTime.now(), LocalDateTime.now())
    }

    static Photo photo(Integer id, Integer fileId) {
        return new Photo(id, fileId, LocalDateTime.now(), LocalDateTime.now())
    }

}
