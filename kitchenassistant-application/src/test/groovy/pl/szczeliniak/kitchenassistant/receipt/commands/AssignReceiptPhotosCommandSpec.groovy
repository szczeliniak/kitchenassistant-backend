package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.receipt.*
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.AssignPhotosToReceiptDto
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

class AssignReceiptPhotosCommandSpec extends Specification {

    def receiptDao = Mock(ReceiptDao)
    def photoDao = Mock(PhotoDao)

    @Subject
    def addReceiptPhotosCommand = new AssignReceiptPhotosCommand(receiptDao, photoDao)

    def 'should add photo to receipt'() {
        given:
        def photo = photo()
        def receipt = receipt(new HashSet<Photo>())
        receiptDao.findById(1) >> receipt
        receiptDao.save(receipt) >> receipt
        photoDao.findById(99) >> photo
        photoDao.save(photo) >> photo

        when:
        def result = addReceiptPhotosCommand.execute(1, addReceiptPhotosDto())

        then:
        result == new SuccessResponse(1)
        receipt.photos.size() == 1
        receipt.photos.contains(photo)
    }

    def 'should not add photo to receipt when photo already added'() {
        given:
        def photo = photo()
        def receipt = receipt(Set.of(photo))
        receiptDao.findById(1) >> receipt
        receiptDao.save(receipt) >> receipt

        when:
        def result = addReceiptPhotosCommand.execute(1, addReceiptPhotosDto())

        then:
        result == new SuccessResponse(1)
        receipt.photos.size() == 1
        receipt.photos.contains(photo)
    }

    private static AssignPhotosToReceiptDto addReceiptPhotosDto() {
        return new AssignPhotosToReceiptDto(Set.of(99))
    }

    private static Receipt receipt(Set<Photo> photos) {
        return new Receipt(1, 0, "", "", new Author(2, "", 1, ZonedDateTime.now(), ZonedDateTime.now()), "", false,
                new Category(0, "", 0, 1, false, ZonedDateTime.now(), ZonedDateTime.now()),
                Collections.emptySet(), Collections.emptySet(), photos, Collections.emptySet(), false, ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static Photo photo() {
        return new Photo(99, "", 1, false, ZonedDateTime.now(), ZonedDateTime.now())
    }

}
