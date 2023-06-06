package pl.szczeliniak.kitchenassistant.recipe.commands.factories

import org.assertj.core.api.Assertions
import pl.szczeliniak.kitchenassistant.recipe.db.Tag
import pl.szczeliniak.kitchenassistant.user.db.User
import pl.szczeliniak.kitchenassistant.user.db.UserDao
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

class TagFactoryTest extends Specification {

    def userDao = Mock(UserDao)

    @Subject
    def tagFactory = new TagFactory(userDao)

    def 'should create tag'() {
        given:
        def user = user()
        userDao.findById(4) >> user

        when:
        def result = tagFactory.create("TAG_NAME", 4)

        then:
        Assertions.assertThat(result).usingRecursiveComparison()
                .ignoringFields("createdAt", "modifiedAt")
                .isEqualTo(tag(user))
    }

    private static Tag tag(User user) {
        return new Tag(0, "TAG_NAME", user, ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static User user() {
        return new User(4, "", "", "", ZonedDateTime.now(), ZonedDateTime.now())
    }

}
