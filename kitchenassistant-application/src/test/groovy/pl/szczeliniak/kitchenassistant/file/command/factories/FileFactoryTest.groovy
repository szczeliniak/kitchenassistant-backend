package pl.szczeliniak.kitchenassistant.file.command.factories

import org.assertj.core.api.Assertions
import pl.szczeliniak.kitchenassistant.file.File
import pl.szczeliniak.kitchenassistant.file.commands.factories.FileFactory
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

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
        return new File(0, "PHOTO_NAME", 4, false, ZonedDateTime.now(), ZonedDateTime.now())
    }

}
