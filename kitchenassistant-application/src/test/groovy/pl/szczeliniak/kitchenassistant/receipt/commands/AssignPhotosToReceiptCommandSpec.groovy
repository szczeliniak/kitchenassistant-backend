package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.common.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.receipt.*
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.AssignPhotosToReceiptDto
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class AssignPhotosToReceiptCommandSpec extends Specification {

    def receiptDao = Mock(ReceiptDao)
    def fileDao = Mock(FileDao)

    @Subject
    def addReceiptPhotosCommand = new AssignPhotosToReceiptCommand(receiptDao, fileDao)

    def 'should add photo to receipt'() {
        given:
        def photo = photo()
        def receipt = receipt(new ArrayList())
        receiptDao.findById(1) >> receipt
        fileDao.findById(99) >> photo
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
        return new AssignPhotosToReceiptDto(Collections.singletonList(99))
    }

    private static Receipt receipt(List<File> photos) {
        return new Receipt(1, 0, "", "", "", "",
                new Category(0, "", 0, false, LocalDateTime.now(), LocalDateTime.now()),
                Collections.emptyList(), Collections.emptyList(), photos, Collections.emptyList(), false, LocalDateTime.now(), LocalDateTime.now())
    }

    private static File photo() {
        return new File(99, "NAME", 4, false, LocalDateTime.now(), LocalDateTime.now())
    }
}
