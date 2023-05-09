package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.recipe.FtpClient
import pl.szczeliniak.kitchenassistant.recipe.commands.dto.UpdateRecipeDto
import pl.szczeliniak.kitchenassistant.recipe.commands.factories.AuthorFactory
import pl.szczeliniak.kitchenassistant.recipe.commands.factories.TagFactory
import pl.szczeliniak.kitchenassistant.recipe.db.*
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
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
        def tagToRemove = tag(10, "TAG_TO_REMOVE")
        def assignedTag = tag(11, "ASSIGNED_TAG")
        def newTag = tag(12, "NEW_TAG")
        def existingTag = tag(13, "EXISTING_TAG")
        def recipe = recipe(new HashSet<Tag>(List.of(tagToRemove, assignedTag)))
        def newCategory = category(3)
        def author = author()

        recipeDao.findById(1) >> recipe
        recipeDao.save(recipe) >> recipe
        categoryDao.findById(3) >> newCategory
        tagDao.findByName("EXISTING_TAG", 4) >> existingTag
        tagDao.findByName("NEW_TAG", 4) >> null
        tagFactory.create("NEW_TAG", 4) >> newTag
        authorDao.findByName("AUTHOR", 4) >> null
        tagDao.save(newTag) >> newTag
        authorFactory.create("AUTHOR", 4) >> author
        authorDao.save(author) >> author
        ftpClient.exists("PHOTO_NAME") >> true

        when:
        def result = updateRecipeCommand.execute(1, updateRecipeDto())

        then:
        recipe.name == "NAME"
        recipe.description == "DESC"
        recipe.author == author
        recipe.source == "SOURCE"
        recipe.category == newCategory
        recipe.tags == Set.of(assignedTag, existingTag, newTag)
        recipe.photoName == "PHOTO_NAME"
        result == new SuccessResponse(1)
    }

    private static UpdateRecipeDto updateRecipeDto() {
        return new UpdateRecipeDto("NAME", 3, "DESC", "AUTHOR", "SOURCE", Set.of("ASSIGNED_TAG", "EXISTING_TAG", "NEW_TAG"), "PHOTO_NAME")
    }

    private static Recipe recipe(Set<Tag> tags) {
        return new Recipe(1, "", 4, "", new Author(2, "", 1, ZonedDateTime.now(), ZonedDateTime.now()), "", false, category(0), Collections.emptySet(),
                Collections.emptySet(), null, tags, ZonedDateTime.now(), ZonedDateTime.now())
    }

    static Category category(Integer id) {
        return new Category(id, "", 3, 4,
                ZonedDateTime.of(2000, 1, 1, 1, 1, 0, 0, ZoneId.systemDefault()),
                ZonedDateTime.of(2000, 1, 1, 1, 2, 0, 0, ZoneId.systemDefault()))
    }

    static Tag tag(Integer id, String name) {
        return new Tag(id, name, 4, ZonedDateTime.now(), ZonedDateTime.now())
    }

    static Author author() {
        return new Author(2, "", 1, ZonedDateTime.now(), ZonedDateTime.now())
    }
}
