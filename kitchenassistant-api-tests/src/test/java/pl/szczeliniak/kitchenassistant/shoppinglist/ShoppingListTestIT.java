package pl.szczeliniak.kitchenassistant.shoppinglist;

import org.junit.jupiter.api.Test;
import pl.szczeliniak.kitchenassistant.BaseTest;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class ShoppingListTestIT extends BaseTest {

    @Test
    public void shouldAddShoppingList() {
        Integer userId = addUser(addUserDto()).getId();

        SuccessResponse response = addShoppingList(addShoppingListDto(userId));

        assertThat(response.getId()).isNotNull();
    }

    @Test
    public void shouldUpdateShoppingList() {
        Integer userId = addUser(addUserDto()).getId();
        Integer shoppingListId = addShoppingList(addShoppingListDto(userId)).getId();

        SuccessResponse response = updateShoppingList(shoppingListId, updateShoppingListDto());

        assertThat(response.getId()).isEqualTo(shoppingListId);
    }

    @Test
    public void shouldMarkShoppingListAsArchived() {
        Integer userId = addUser(addUserDto()).getId();
        Integer shoppingListId = addShoppingList(addShoppingListDto(userId)).getId();

        SuccessResponse response = markShoppingListAsArchived(shoppingListId);

        assertThat(response.getId()).isEqualTo(shoppingListId);
    }

    @Test
    public void shouldDeleteShoppingList() {
        Integer userId = addUser(addUserDto()).getId();
        Integer shoppingListId = addShoppingList(addShoppingListDto(userId)).getId();

        SuccessResponse response = deleteShoppingList(shoppingListId);

        assertThat(response.getId()).isEqualTo(shoppingListId);
    }

    @Test
    public void shouldReturnShoppingLists() {
        Integer userId = addUser(addUserDto()).getId();
        Integer shoppingListId = addShoppingList(addShoppingListDto(userId)).getId();
        Integer shoppingListId2 = addShoppingList(addShoppingListDto2(userId)).getId();

        ShoppingListsResponse response = getShoppingLists();

        assertThat(response.getShoppingLists())
                .usingRecursiveComparison()
                .ignoringFields("items.id")
                .isEqualTo(List.of(shoppingList(shoppingListId), shoppingList2(shoppingListId2)));
    }

    @Test
    public void shouldReturnShoppingListById() {
        Integer userId = addUser(addUserDto()).getId();
        Integer shoppingListId = addShoppingList(addShoppingListDto(userId)).getId();

        ShoppingListResponse response = getShoppingList(shoppingListId);

        assertThat(response.getShoppingList())
                .usingRecursiveComparison()
                .ignoringFields("items.id")
                .isEqualTo(shoppingList(shoppingListId));
    }

    @Test
    public void shouldAddItemStepToShoppingList() {
        Integer userId = addUser(addUserDto()).getId();
        Integer shoppingListId = addShoppingList(addShoppingListDto(userId)).getId();

        SuccessResponse response = addShoppingListItem(shoppingListId, addShoppingListItemDto2());

        assertThat(response.getId()).isNotNull();
    }

    @Test
    public void shouldUpdateShoppingListItem() {
        Integer userId = addUser(addUserDto()).getId();
        Integer shoppingListId = addShoppingList(addShoppingListDto(userId)).getId();
        Integer shoppingListItemId = addShoppingListItem(shoppingListId, addShoppingListItemDto2()).getId();

        SuccessResponse response = updateShoppingListItem(shoppingListId, shoppingListItemId, updateShoppingListItemDto());

        assertThat(response.getId()).isEqualTo(shoppingListItemId);
    }

    @Test
    public void shouldDeleteShoppingListItem() {
        Integer userId = addUser(addUserDto()).getId();
        Integer shoppingListId = addShoppingList(addShoppingListDto(userId)).getId();
        Integer shoppingListItemId = addShoppingListItem(shoppingListId, addShoppingListItemDto2()).getId();

        SuccessResponse response = deleteShoppingListItem(shoppingListId, shoppingListItemId);

        assertThat(response.getId()).isEqualTo(shoppingListItemId);
    }

    @Test
    public void shouldMarkShoppingListItemAsDone() {
        Integer userId = addUser(addUserDto()).getId();
        Integer shoppingListId = addShoppingList(addShoppingListDto(userId)).getId();
        Integer shoppingListItemId = addShoppingListItem(shoppingListId, addShoppingListItemDto2()).getId();

        SuccessResponse response = markShoppingListItemAsDone(shoppingListId, shoppingListItemId);

        assertThat(response.getId()).isEqualTo(shoppingListItemId);
    }

    private ShoppingList shoppingList(Integer shoppingListId) {
        return ShoppingList.builder()
                .id(shoppingListId)
                .name("Name")
                .description("Description")
                .date(LocalDate.now())
                .archived(false)
                .items(Collections.singletonList(shoppingListItem()))
                .build();
    }

    private ShoppingListItem shoppingListItem() {
        return ShoppingListItem.builder()
                .name("Shopping list item name")
                .quantity("Quantity")
                .sequence(1)
                .done(false)
                .build();
    }

    private ShoppingList shoppingList2(Integer shoppingListId) {
        return ShoppingList.builder()
                .id(shoppingListId)
                .name("Name 2")
                .description("Description 2")
                .date(LocalDate.now())
                .items(Collections.singletonList(shoppingListItem2()))
                .archived(false)
                .build();
    }

    private ShoppingListItem shoppingListItem2() {
        return ShoppingListItem.builder()
                .name("Shopping list item name 2")
                .quantity("Quantity 2")
                .sequence(2)
                .done(false)
                .build();
    }

    private SuccessResponse addShoppingList(AddShoppingListDto addShoppingListDto) {
        return spec()
                .body(addShoppingListDto)
                .post("/shoppinglists")
                .then()
                .statusCode(200)
                .extract()
                .as(SuccessResponse.class);
    }

    private SuccessResponse updateShoppingList(Integer id, UpdateShoppingListDto updateShoppingListDto) {
        return spec()
                .body(updateShoppingListDto)
                .put("/shoppinglists/" + id)
                .then()
                .statusCode(200)
                .extract()
                .as(SuccessResponse.class);
    }

    private AddShoppingListDto addShoppingListDto(Integer userId) {
        return AddShoppingListDto.builder()
                .name("Name")
                .description("Description")
                .userId(userId)
                .date(LocalDate.now())
                .items(Collections.singletonList(addShoppingListItemDto()))
                .build();
    }

    private AddShoppingListDto addShoppingListDto2(Integer userId) {
        return AddShoppingListDto.builder()
                .name("Name 2")
                .description("Description 2")
                .date(LocalDate.now())
                .userId(userId)
                .items(Collections.singletonList(addShoppingListItemDto2()))
                .build();
    }

    private UpdateShoppingListDto updateShoppingListDto() {
        return UpdateShoppingListDto.builder()
                .name("Name")
                .description("Description")
                .date(LocalDate.now())
                .build();
    }

    private AddShoppingListItemDto addShoppingListItemDto() {
        return AddShoppingListItemDto.builder()
                .name("Shopping list item name")
                .quantity("Quantity")
                .sequence(1)
                .build();
    }

    private AddShoppingListItemDto addShoppingListItemDto2() {
        return AddShoppingListItemDto.builder()
                .name("Shopping list item name 2")
                .quantity("Quantity 2")
                .sequence(2)
                .build();
    }

    private UpdateShoppingListItemDto updateShoppingListItemDto() {
        return UpdateShoppingListItemDto.builder()
                .name("Shopping list item name")
                .quantity("Quantity")
                .sequence(1)
                .build();
    }

    private SuccessResponse updateShoppingListItem(Integer shoppingListId, Integer itemId, UpdateShoppingListItemDto dto) {
        return spec()
                .body(dto)
                .put("/shoppinglists/" + shoppingListId + "/items/" + itemId)
                .then()
                .statusCode(200)
                .extract()
                .as(SuccessResponse.class);
    }

    private SuccessResponse deleteShoppingList(Integer shoppingListId) {
        return spec()
                .delete("/shoppinglists/" + shoppingListId)
                .then()
                .statusCode(200)
                .extract()
                .as(SuccessResponse.class);
    }

    private ShoppingListsResponse getShoppingLists() {
        return spec()
                .get("/shoppinglists")
                .then()
                .statusCode(200)
                .extract()
                .as(ShoppingListsResponse.class);
    }

    private ShoppingListResponse getShoppingList(Integer shoppingListId) {
        return spec()
                .get("/shoppinglists/" + shoppingListId)
                .then()
                .statusCode(200)
                .extract()
                .as(ShoppingListResponse.class);
    }

    private SuccessResponse addShoppingListItem(Integer shoppingListId, AddShoppingListItemDto addShoppingListItemDto) {
        return spec()
                .body(addShoppingListItemDto)
                .post("/shoppinglists/" + shoppingListId + "/items")
                .then()
                .statusCode(200)
                .extract()
                .as(SuccessResponse.class);
    }

    private SuccessResponse markShoppingListItemAsDone(Integer shoppingListId, Integer shoppingListItemId) {
        return spec()
                .post("/shoppinglists/" + shoppingListId + "/items/" + shoppingListItemId + "/done/true")
                .then()
                .statusCode(200)
                .extract()
                .as(SuccessResponse.class);
    }

    private SuccessResponse markShoppingListAsArchived(Integer shoppingListId) {
        return spec()
                .post("/shoppinglists/" + shoppingListId + "/archived/true")
                .then()
                .statusCode(200)
                .extract()
                .as(SuccessResponse.class);
    }

    private SuccessResponse deleteShoppingListItem(Integer shoppingListId, Integer shoppingListItemId) {
        return spec()
                .delete("/shoppinglists/" + shoppingListId + "/items/" + shoppingListItemId)
                .then()
                .statusCode(200)
                .extract()
                .as(SuccessResponse.class);
    }

}