package pl.szczeliniak.kitchenassistant.receipt;

import org.junit.jupiter.api.Test;
import pl.szczeliniak.kitchenassistant.BaseTest;

import java.util.Collections;
import java.util.List;

public class ReceiptTestIT extends BaseTest {

    @Test
    public void shouldAddReceipt() {
        Integer userId = addUser(addUserDto()).getId();
        Integer categoryId = addCategory(addCategoryDto(userId)).getId();

        SuccessResponse response = addReceipt(addReceiptDto(userId, categoryId));

        assertThat(response.getId()).isNotNull();
    }

    @Test
    public void shouldUpdateReceipt() {
        Integer userId = addUser(addUserDto()).getId();
        Integer categoryId = addCategory(addCategoryDto(userId)).getId();
        Integer receiptId = addReceipt(addReceiptDto(userId, categoryId)).getId();

        SuccessResponse response = updateReceipt(receiptId, updateReceiptDto(categoryId));

        assertThat(response.getId()).isEqualTo(receiptId);
    }

    @Test
    public void shouldDeleteReceipt() {
        Integer userId = addUser(addUserDto()).getId();
        Integer categoryId = addCategory(addCategoryDto(userId)).getId();
        Integer receiptId = addReceipt(addReceiptDto(userId, categoryId)).getId();

        SuccessResponse response = deleteReceipt(receiptId);

        assertThat(response.getId()).isEqualTo(receiptId);
    }

    @Test
    public void shouldReturnReceipts() {
        Integer userId = addUser(addUserDto()).getId();
        Integer categoryId = addCategory(addCategoryDto(userId)).getId();
        Integer receiptId = addReceipt(addReceiptDto(userId, categoryId)).getId();
        Integer receiptId2 = addReceipt(addReceiptDto2(userId)).getId();

        ReceiptsResponse response = getReceipts();

        assertThat(response.getReceipts())
                .usingRecursiveComparison()
                .ignoringFields("ingredients.id", "steps.id", "category.id")
                .isEqualTo(List.of(receipt(receiptId, category()), receipt2(receiptId2)));
    }

    @Test
    public void shouldReturnReceiptById() {
        Integer userId = addUser(addUserDto()).getId();
        Integer categoryId = addCategory(addCategoryDto(userId)).getId();
        Integer receiptId = addReceipt(addReceiptDto(userId, categoryId)).getId();

        ReceiptResponse response = getReceiptWithSuccess(receiptId);

        assertThat(response.getReceipt())
                .usingRecursiveComparison()
                .ignoringFields("ingredients.id", "steps.id", "category.id")
                .isEqualTo(receipt(receiptId, category()));
    }

    @Test
    public void shouldAddStepToReceipt() {
        Integer userId = addUser(addUserDto()).getId();
        Integer categoryId = addCategory(addCategoryDto(userId)).getId();
        Integer receiptId = addReceipt(addReceiptDto(userId, categoryId)).getId();

        SuccessResponse response = addStep(receiptId, addStepDto2());

        assertThat(response.getId()).isNotNull();
    }

    @Test
    public void shouldDeleteStep() {
        Integer userId = addUser(addUserDto()).getId();
        Integer categoryId = addCategory(addCategoryDto(userId)).getId();
        Integer receiptId = addReceipt(addReceiptDto(userId, categoryId)).getId();
        Integer stepId = addStep(receiptId, addStepDto2()).getId();

        SuccessResponse response = deleteStep(receiptId, stepId);

        assertThat(response.getId()).isEqualTo(stepId);
    }

    @Test
    public void shouldUpdateStep() {
        Integer userId = addUser(addUserDto()).getId();
        Integer categoryId = addCategory(addCategoryDto(userId)).getId();
        Integer receiptId = addReceipt(addReceiptDto(userId, categoryId)).getId();
        Integer stepId = addStep(receiptId, addStepDto2()).getId();

        SuccessResponse response = updateStep(receiptId, stepId, updateStepDto());

        assertThat(response.getId()).isEqualTo(stepId);
    }

    @Test
    public void shouldAddIngredientToReceipt() {
        Integer userId = addUser(addUserDto()).getId();
        Integer categoryId = addCategory(addCategoryDto(userId)).getId();
        Integer receiptId = addReceipt(addReceiptDto(userId, categoryId)).getId();

        SuccessResponse response = addIngredient(receiptId, addIngredientDto2());

        assertThat(response.getId()).isNotNull();
    }

    @Test
    public void shouldUpdateIngredient() {
        Integer userId = addUser(addUserDto()).getId();
        Integer categoryId = addCategory(addCategoryDto(userId)).getId();
        Integer receiptId = addReceipt(addReceiptDto(userId, categoryId)).getId();
        Integer ingredientId = addIngredient(receiptId, addIngredientDto2()).getId();

        SuccessResponse response = updateIngredient(receiptId, ingredientId, updateIngredientDto());

        assertThat(response.getId()).isEqualTo(ingredientId);
    }

    @Test
    public void shouldDeleteIngredient() {
        Integer userId = addUser(addUserDto()).getId();
        Integer categoryId = addCategory(addCategoryDto(userId)).getId();
        Integer receiptId = addReceipt(addReceiptDto(userId, categoryId)).getId();
        Integer ingredientId = addIngredient(receiptId, addIngredientDto2()).getId();

        SuccessResponse response = deleteIngredient(receiptId, ingredientId);

        assertThat(response.getId()).isEqualTo(ingredientId);
    }

    @Test
    public void shouldAddCategory() {
        Integer userId = addUser(addUserDto()).getId();

        SuccessResponse response = addCategory(addCategoryDto(userId));

        assertThat(response.getId()).isNotNull();
    }

    @Test
    public void shouldDeleteCategory() {
        Integer userId = addUser(addUserDto()).getId();
        Integer categoryId = addCategory(addCategoryDto(userId)).getId();

        SuccessResponse response = deleteCategory(categoryId);

        assertThat(response.getId()).isEqualTo(categoryId);
    }

    @Test
    public void shouldReturnCategories() {
        Integer userId = addUser(addUserDto()).getId();
        addCategory(addCategoryDto(userId));

        CategoriesResponse response = getCategories();

        assertThat(response.getCategories()).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(Collections.singletonList(category()));
    }

    private Category category() {
        return Category.builder()
                .name("Name")
                .build();
    }

    private Receipt receipt(Integer id, Category category) {
        return Receipt.builder()
                .id(id)
                .name("Name")
                .author("Author")
                .description("Description")
                .source("Source")
                .category(category)
                .steps(Collections.singletonList(step()))
                .ingredients(Collections.singletonList(ingredient()))
                .build();
    }

    private Step step() {
        return Step.builder()
                .name("Name")
                .description("Description")
                .sequence(1)
                .build();
    }

    private Ingredient ingredient() {
        return Ingredient.builder()
                .name("Name")
                .quantity("Quantity")
                .build();
    }

    private Receipt receipt2(Integer id) {
        return Receipt.builder()
                .id(id)
                .name("Name2")
                .author("Author2")
                .description("Description2")
                .source("Source2")
                .steps(Collections.singletonList(step2()))
                .ingredients(Collections.singletonList(ingredient2()))
                .build();
    }

    private Step step2() {
        return Step.builder()
                .name("Name2")
                .description("Description2")
                .sequence(1)
                .build();
    }

    private Ingredient ingredient2() {
        return Ingredient.builder()
                .name("Name2")
                .quantity("Quantity2")
                .build();
    }

    private AddReceiptDto addReceiptDto2(Integer userId) {
        return AddReceiptDto.builder()
                .name("Name2")
                .author("Author2")
                .description("Description2")
                .source("Source2")
                .userId(userId)
                .steps(Collections.singletonList(addStepDto2()))
                .ingredients(Collections.singletonList(addIngredientDto2()))
                .build();
    }

    private ReceiptResponse getReceiptWithSuccess(Integer receiptId) {
        return spec()
                .get("/receipts/" + receiptId)
                .then()
                .statusCode(200)
                .extract()
                .as(ReceiptResponse.class);
    }

    private ReceiptsResponse getReceipts() {
        return spec()
                .get("/receipts")
                .then()
                .statusCode(200)
                .extract()
                .as(ReceiptsResponse.class);
    }

    private SuccessResponse deleteReceipt(Integer receiptId) {
        return spec().delete("/receipts/" + receiptId)
                .then()
                .statusCode(200)
                .extract()
                .as(SuccessResponse.class);
    }

    private SuccessResponse addStep(Integer receiptId, AddStepDto addStepDto) {
        return spec().body(addStepDto)
                .post("/receipts/" + receiptId + "/steps")
                .then()
                .statusCode(200)
                .extract()
                .as(SuccessResponse.class);
    }

    private SuccessResponse updateStep(Integer receiptId, Integer stepId, UpdateStepDto updateStepDto) {
        return spec().body(updateStepDto)
                .put("/receipts/" + receiptId + "/steps/" + stepId)
                .then()
                .statusCode(200)
                .extract()
                .as(SuccessResponse.class);
    }

    private SuccessResponse deleteStep(Integer receiptId, Integer stepId) {
        return spec().delete("/receipts/" + receiptId + "/steps/" + stepId)
                .then()
                .statusCode(200)
                .extract()
                .as(SuccessResponse.class);
    }

    private SuccessResponse addIngredient(Integer receiptId, AddIngredientDto addIngredientDto) {
        return spec().body(addIngredientDto)
                .post("/receipts/" + receiptId + "/ingredients")
                .then()
                .statusCode(200)
                .extract()
                .as(SuccessResponse.class);
    }

    private SuccessResponse updateIngredient(Integer receiptId, Integer ingredientId, UpdateIngredientDto updateIngredientDto) {
        return spec().body(updateIngredientDto)
                .put("/receipts/" + receiptId + "/ingredients/" + ingredientId)
                .then()
                .statusCode(200)
                .extract()
                .as(SuccessResponse.class);
    }

    private SuccessResponse deleteIngredient(Integer receiptId, Integer ingredientId) {
        return spec().delete("/receipts/" + receiptId + "/ingredients/" + ingredientId)
                .then()
                .statusCode(200)
                .extract()
                .as(SuccessResponse.class);
    }

    private SuccessResponse addReceipt(AddReceiptDto addReceiptDto) {
        return spec().body(addReceiptDto)
                .post("/receipts")
                .then()
                .statusCode(200)
                .extract()
                .as(SuccessResponse.class);
    }

    private SuccessResponse updateReceipt(Integer id, UpdateReceiptDto dto) {
        return spec().body(dto)
                .put("/receipts/" + id)
                .then()
                .statusCode(200)
                .extract()
                .as(SuccessResponse.class);
    }

    private SuccessResponse addCategory(AddCategoryDto addCategoryDto) {
        return spec().body(addCategoryDto)
                .post("/receipts/categories")
                .then()
                .statusCode(200)
                .extract()
                .as(SuccessResponse.class);
    }

    private SuccessResponse deleteCategory(Integer id) {
        return spec()
                .delete("/receipts/categories/" + id)
                .then()
                .statusCode(200)
                .extract()
                .as(SuccessResponse.class);
    }

    private AddReceiptDto addReceiptDto(Integer userId, Integer categoryId) {
        return AddReceiptDto.builder()
                .name("Name")
                .author("Author")
                .description("Description")
                .source("Source")
                .userId(userId)
                .categoryId(categoryId)
                .steps(Collections.singletonList(addStepDto()))
                .ingredients(Collections.singletonList(addIngredientDto()))
                .build();
    }

    private UpdateReceiptDto updateReceiptDto(Integer categoryId) {
        return UpdateReceiptDto.builder()
                .name("Name")
                .author("Author")
                .description("Description")
                .source("Source")
                .categoryId(categoryId)
                .build();
    }

    private AddStepDto addStepDto() {
        return AddStepDto.builder()
                .name("Name")
                .description("Description")
                .sequence(1)
                .build();
    }

    private AddStepDto addStepDto2() {
        return AddStepDto.builder()
                .name("Name2")
                .description("Description2")
                .sequence(1)
                .build();
    }

    private UpdateStepDto updateStepDto() {
        return UpdateStepDto.builder()
                .name("Name")
                .description("Description")
                .sequence(1)
                .build();
    }

    private AddIngredientDto addIngredientDto() {
        return AddIngredientDto.builder()
                .name("Name")
                .quantity("Quantity")
                .build();
    }

    private AddIngredientDto addIngredientDto2() {
        return AddIngredientDto.builder()
                .name("Name2")
                .quantity("Quantity2")
                .build();
    }

    private UpdateIngredientDto updateIngredientDto() {
        return UpdateIngredientDto.builder()
                .name("Name")
                .quantity("Quantity")
                .build();
    }

    private AddCategoryDto addCategoryDto(Integer userId) {
        return AddCategoryDto.builder()
                .name("Name")
                .userId(userId)
                .build();
    }

    private CategoriesResponse getCategories() {
        return spec()
                .get("/receipts/categories")
                .then()
                .statusCode(200)
                .extract()
                .as(CategoriesResponse.class);
    }

}