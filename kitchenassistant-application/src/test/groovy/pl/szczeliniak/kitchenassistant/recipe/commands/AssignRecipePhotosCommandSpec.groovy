package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.recipe.*
import pl.szczeliniak.kitchenassistant.recipe.commands.dto.AssignPhotosToRecipeDto
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import spock.lang.Specification
import spock.lang.Subject

import java.time.ZonedDateTime

class AssignRecipePhotosCommandSpec extends Specification {

    def recipeDao = Mock(RecipeDao)
    def photoDao = Mock(PhotoDao)

    @Subject
    def addRecipePhotosCommand = new AssignRecipePhotosCommand(recipeDao, photoDao)

    def 'should add photo to recipe'() {
        given:
        def photo = photo()
        def recipe = recipe(new HashSet<Photo>())
        recipeDao.findById(1) >> recipe
        recipeDao.save(recipe) >> recipe
        photoDao.findById(99) >> photo
        photoDao.save(photo) >> photo

        when:
        def result = addRecipePhotosCommand.execute(1, addRecipePhotosDto())

        then:
        result == new SuccessResponse(1)
        recipe.photos.size() == 1
        recipe.photos.contains(photo)
    }

    def 'should not add photo to recipe when photo already added'() {
        given:
        def photo = photo()
        def recipe = recipe(Set.of(photo))
        recipeDao.findById(1) >> recipe
        recipeDao.save(recipe) >> recipe

        when:
        def result = addRecipePhotosCommand.execute(1, addRecipePhotosDto())

        then:
        result == new SuccessResponse(1)
        recipe.photos.size() == 1
        recipe.photos.contains(photo)
    }

    private static AssignPhotosToRecipeDto addRecipePhotosDto() {
        return new AssignPhotosToRecipeDto(Set.of(99))
    }

    private static Recipe recipe(Set<Photo> photos) {
        return new Recipe(1, "", 0, "", new Author(2, "", 1, ZonedDateTime.now(), ZonedDateTime.now()), "", false,
                new Category(0, "", 0, 1, false, ZonedDateTime.now(), ZonedDateTime.now()),
                Collections.emptySet(), Collections.emptySet(), photos, Collections.emptySet(), false, ZonedDateTime.now(), ZonedDateTime.now())
    }

    private static Photo photo() {
        return new Photo(99, "", 1, false, ZonedDateTime.now(), ZonedDateTime.now())
    }

}
