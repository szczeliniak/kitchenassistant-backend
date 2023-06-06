package pl.szczeliniak.kitchenassistant.recipe.commands.factories

import org.assertj.core.api.Assertions
import pl.szczeliniak.kitchenassistant.recipe.commands.dto.NewCategoryDto
import pl.szczeliniak.kitchenassistant.recipe.db.Category
import pl.szczeliniak.kitchenassistant.user.db.User
import pl.szczeliniak.kitchenassistant.user.db.UserDao
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

class CategoryFactoryTest extends Specification {

    def userDao = Mock(UserDao)
    @Subject
    def categoryFactory = new CategoryFactory(userDao)

    def 'should create category'() {
        def user = user()
        given:
        userDao.findById(1) >> user
        when:
        def result = categoryFactory.create(newCategoryDto())

        then:
        Assertions.assertThat(result).usingRecursiveComparison()
                .ignoringFields("createdAt", "modifiedAt")
                .isEqualTo(category(user))
    }

    private static Category category(User user) {
        return new Category(0, "NAME", user, 3, ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static NewCategoryDto newCategoryDto() {
        return new NewCategoryDto("NAME", 1, 3)
    }

    private static user() {
        return new User(1, "", "", "", ZonedDateTime.now(), ZonedDateTime.now())
    }
}
