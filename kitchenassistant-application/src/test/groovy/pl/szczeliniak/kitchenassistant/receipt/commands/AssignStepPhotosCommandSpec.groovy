package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.file.queries.CheckIfFileExistsQuery
import pl.szczeliniak.kitchenassistant.file.queries.dtos.CheckIfFileExistsResponse
import pl.szczeliniak.kitchenassistant.receipt.*
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.AssignPhotosToReceiptStepDto
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.PhotoFactory
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class AssignStepPhotosCommandSpec extends Specification {

    def receiptDao = Mock(ReceiptDao)
    def stepDao = Mock(StepDao)
    def photoDao = Mock(PhotoDao)
    def checkIfFileExistsQuery = Mock(CheckIfFileExistsQuery)
    def photoFactory = Mock(PhotoFactory)

    @Subject
    def assignPhotosToReceiptStepCommand = new AssignStepPhotosCommand(receiptDao, stepDao, checkIfFileExistsQuery, photoFactory, photoDao)

    def 'should add photo to receipt'() {
        given:
        def photo = photo()
        def step = step(new HashSet<Photo>())
        def receipt = receipt(Set.of(step))
        receiptDao.findById(1) >> receipt
        checkIfFileExistsQuery.execute(99) >> checkIfFileExistsResponse()
        photoFactory.create(99) >> photo
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
        def step = step(Set.of(photo))
        def receipt = receipt(Set.of(step))
        checkIfFileExistsQuery.execute(99) >> checkIfFileExistsResponse()
        photoFactory.create(99) >> photo
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
        return new AssignPhotosToReceiptStepDto(Set.of(99))
    }

    private static Receipt receipt(Set<Step> steps) {
        return new Receipt(1, 0, "", "", "", "",
                new Category(0, "", 0, false, LocalDateTime.now(), LocalDateTime.now()),
                Collections.emptySet(), steps, Collections.emptySet(), Collections.emptySet(), false, LocalDateTime.now(), LocalDateTime.now())
    }

    private static Step step(Set<Photo> photos) {
        return new Step(2, "", "", 0, photos, false, LocalDateTime.now(), LocalDateTime.now())
    }

    private static Photo photo() {
        return new Photo(99, 4, LocalDateTime.now(), LocalDateTime.now())
    }

    private static CheckIfFileExistsResponse checkIfFileExistsResponse() {
        return new CheckIfFileExistsResponse(true)
    }
}
