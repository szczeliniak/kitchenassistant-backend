package pl.szczeliniak.kitchenassistant.receipt.commands.factories


import org.assertj.core.api.Assertions
import pl.szczeliniak.kitchenassistant.receipt.File
import pl.szczeliniak.kitchenassistant.receipt.FileDao
import pl.szczeliniak.kitchenassistant.receipt.Step
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.NewStepDto
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class StepFactorySpec extends Specification {

    def fileDao = Mock(FileDao)

    @Subject
    def stepFactory = new StepFactory(fileDao)

    def 'should create step'() {
        given:
        def photo = photo()
        fileDao.findById(99) >> photo
        when:
        def result = stepFactory.create(newStepDto())

        then:
        Assertions.assertThat(result).usingRecursiveComparison()
                .ignoringFields("createdAt_", "modifiedAt_")
                .isEqualTo(step(Collections.singletonList(photo)))
    }

    private static NewStepDto newStepDto() {
        return new NewStepDto("NAME", "DESCRIPTION", 1, Collections.singletonList(99))
    }

    private static Step step(List<File> photos) {
        return new Step(0, "NAME", "DESCRIPTION", 1, photos, false, LocalDateTime.now(), LocalDateTime.now())
    }

    private static File photo() {
        return new File(0, "", false, LocalDateTime.now(), LocalDateTime.now())
    }

}