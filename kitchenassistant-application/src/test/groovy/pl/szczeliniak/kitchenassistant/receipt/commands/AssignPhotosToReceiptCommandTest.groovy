package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.common.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.receipt.*
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.AssignPhotosToReceiptDto
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.PhotoFactory
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class AssignPhotosToReceiptCommandTest extends Specification {

    def receiptDao = Mock(ReceiptDao)
    def photoDao = Mock(PhotoDao)
    def photoFactory = Mock(PhotoFactory)

    @Subject
    def addReceiptPhotosCommand = new AssignPhotosToReceiptCommand(receiptDao, photoDao, photoFactory)

    def 'should add photo to receipt'() {
        given:
        def photo = photo()
        def receipt = receipt(new ArrayList())
        receiptDao.findById(1) >> receipt
        photoFactory.create("NAME") >> photo
        photoDao.save(photo) >> photo
        receiptDao.save(receipt) >> receipt

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
        def receipt = receipt(Collections.singletonList(photo))
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
        return new AssignPhotosToReceiptDto(Collections.singletonList("NAME"))
    }

    private static Receipt receipt(List<Photo> photos) {
        return new Receipt(1, 0, "", "", "", "",
                new Category(0, "", 0, false, LocalDateTime.now(), LocalDateTime.now()),
                Collections.emptyList(), Collections.emptyList(), photos, false, LocalDateTime.now(), LocalDateTime.now())
    }

    private static Photo photo() {
        return new Photo(2, "NAME", false, LocalDateTime.now(), LocalDateTime.now())
    }
}
