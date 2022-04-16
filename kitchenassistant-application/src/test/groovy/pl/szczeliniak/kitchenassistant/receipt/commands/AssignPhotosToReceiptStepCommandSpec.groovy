package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.common.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.receipt.*
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.AssignPhotosToReceiptStepDto
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.PhotoFactory
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class AssignPhotosToReceiptStepCommandSpec extends Specification {

    def receiptDao = Mock(ReceiptDao)
    def photoDao = Mock(PhotoDao)
    def stepDao = Mock(StepDao)
    def photoFactory = Mock(PhotoFactory)

    @Subject
    def assignPhotosToReceiptStepCommand = new AssignPhotosToReceiptStepCommand(receiptDao, stepDao, photoDao, photoFactory)

    def 'should add photo to receipt'() {
        given:
        def photo = photo()
        def step = step(new ArrayList())
        def receipt = receipt(Collections.singletonList(step))
        receiptDao.findById(1) >> receipt
        photoFactory.create("NAME") >> photo
        photoDao.save(photo) >> photo
        stepDao.save(step) >> step

        when:
        def result = assignPhotosToReceiptStepCommand.execute(1, 2, assignPhotosToReceiptStepDto())

        then:
        result == new SuccessResponse(2)
        step.photos.size() == 1
        step.photos.contains(photo)
    }

    def 'should not add photo to receipt when photo already added'() {
        given:
        def photo = photo()
        def step = step(Collections.singletonList(photo))
        def receipt = receipt(Collections.singletonList(step))
        receiptDao.findById(1) >> receipt
        stepDao.save(step) >> step

        when:
        def result = assignPhotosToReceiptStepCommand.execute(1, 2, assignPhotosToReceiptStepDto())

        then:
        result == new SuccessResponse(2)
        step.photos.size() == 1
        step.photos.contains(photo)
    }

    private static AssignPhotosToReceiptStepDto assignPhotosToReceiptStepDto() {
        return new AssignPhotosToReceiptStepDto(Collections.singletonList("NAME"))
    }

    private static Receipt receipt(List<Step> steps) {
        return new Receipt(1, 0, "", "", "", "",
                new Category(0, "", 0, false, LocalDateTime.now(), LocalDateTime.now()),
                Collections.emptyList(), steps, Collections.emptyList(), false, LocalDateTime.now(), LocalDateTime.now())
    }

    private static Step step(List<Photo> photos) {
        return new Step(2, "", "", 0, photos, false, LocalDateTime.now(), LocalDateTime.now())
    }

    private static Photo photo() {
        return new Photo(2, "NAME", false, LocalDateTime.now(), LocalDateTime.now())
    }
}
