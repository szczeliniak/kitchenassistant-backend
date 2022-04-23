package pl.szczeliniak.kitchenassistant.receipt.commands.factories

import org.assertj.core.api.Assertions
import pl.szczeliniak.kitchenassistant.receipt.File
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class FileFactoryTest extends Specification {

    @Subject
    def photoFactory = new FileFactory()

    def 'should create photo'() {

        when:
        def result = photoFactory.create("PHOTO_NAME", 4)

        then:
        Assertions.assertThat(result).usingRecursiveComparison()
                .ignoringFields("createdAt_", "modifiedAt_")
                .isEqualTo(photo())
    }

    private static File photo() {
        return new File(0, "PHOTO_NAME", 4, false, LocalDateTime.now(), LocalDateTime.now())
    }

}
