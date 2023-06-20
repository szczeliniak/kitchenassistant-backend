package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.recipe.FtpClient
import pl.szczeliniak.kitchenassistant.recipe.commands.dto.UpdateRecipeRequest
import pl.szczeliniak.kitchenassistant.recipe.commands.factories.AuthorFactory
import pl.szczeliniak.kitchenassistant.recipe.commands.factories.TagFactory
import pl.szczeliniak.kitchenassistant.recipe.db.*
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.user.db.User
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZoneId
import java.time.ZonedDateTime

class UpdateRecipeCommandSpec extends Specification {

    def recipeDao = Mock(RecipeDao)
    def categoryDao = Mock(CategoryDao)
    def tagDao = Mock(TagDao)
    def tagFactory = Mock(TagFactory)
    def authorDao = Mock(AuthorDao)
    def authorFactory = Mock(AuthorFactory)
    def ftpClient = Mock(FtpClient)

    @Subject
    def updateRecipeCommand = new UpdateRecipeCommand(recipeDao, categoryDao, tagDao, tagFactory, authorFactory, authorDao, ftpClient)

    def 'should update recipe'() {
        given:
        def user = user()
        def tagToRemove = tag(10, "TAG_TO_REMOVE", user)
        def assignedTag = tag(11, "ASSIGNED_TAG", user)
        def newTag = tag(12, "NEW_TAG", user)
        def existingTag = tag(13, "EXISTING_TAG", user)
        def recipe = recipe(new HashSet<Tag>(List.of(tagToRemove, assignedTag)), user)
        def newCategory = category(3, user)
        def author = author(user)

        recipeDao.findById(1) >> recipe
        recipeDao.save(recipe) >> recipe
        categoryDao.findById(3) >> newCategory
        tagDao.findByName("EXISTING_TAG", 4) >> existingTag
        tagDao.findByName("NEW_TAG", 4) >> null
        tagFactory.create("NEW_TAG", user) >> newTag
        authorDao.findByName("AUTHOR", 4) >> null
        tagDao.save(newTag) >> newTag
        authorFactory.create("AUTHOR", user) >> author
        authorDao.save(author) >> author
        ftpClient.exists("PHOTO_NAME") >> true

        when:
        def result = updateRecipeCommand.execute(1, updateRecipeRequest())

        then:
        recipe.name == "NAME"
        recipe.description == "DESC"
        recipe.author == author
        recipe.source == "SOURCE"
        recipe.category == newCategory
        recipe.tags == Set.of(assignedTag, existingTag, newTag)
        recipe.photoName == "PHOTO_NAME"
        recipe.user == user
        result == new SuccessResponse(1)
    }

    private static UpdateRecipeRequest updateRecipeRequest() {
        return new UpdateRecipeRequest("NAME", 3, "DESC", "AUTHOR", "SOURCE", Set.of("ASSIGNED_TAG", "EXISTING_TAG", "NEW_TAG"), "PHOTO_NAME")
    }

    private static Recipe recipe(Set<Tag> tags, User user) {
        return new Recipe(1, "", user, "", new Author(2, "", user, ZonedDateTime.now(), ZonedDateTime.now()), "", false, category(0, user), Collections.emptySet(),
                Collections.emptySet(), null, tags, ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static Category category(Integer id, User user) {
        return new Category(id, "", user, 4,
                ZonedDateTime.of(2000, 1, 1, 1, 1, 0, 0, ZoneId.systemDefault()),
                ZonedDateTime.of(2000, 1, 1, 1, 2, 0, 0, ZoneId.systemDefault()))
    }

    private static Tag tag(Integer id, String name, User user) {
        return new Tag(id, name, user, ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static Author author(User user) {
        return new Author(2, "", user, ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static User user() {
        return new User(4, "", "", "", ZonedDateTime.now(), ZonedDateTime.now())
    }
}
