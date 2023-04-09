package pl.szczeliniak.kitchenassistant.shared

enum class ErrorCode(val message: String, val code: Int) {

    PASSWORDS_DO_NOT_MATCH("Passwords do not match", 400),
    UNSUPPORTED_MEDIA_TYPE("Unsupported media type", 400),
    FILE_TOO_LARGE("File too large", 413),
    FTP_UPLOAD_ERROR("Error while uploading to FTP", 424),
    FTP_DOWNLOAD_ERROR("Error while downloading from FTP", 424),
    FTP_DELETE_ERROR("Error while deleting from FTP", 424),
    FTP_FILE_CHECK_ERROR("Error while checking for file existence from FTP", 424),
    FTP_FILE_NOT_FOUND("File not found", 404),
    FTP_GENERIC_ERROR("Error on communication with FTP", 424),
    USER_ALREADY_EXISTS("User with email already exists", 409),
    DAY_PLAN_ALREADY_EXISTS("Day plan already exists", 409),
    DAY_PLAN_DATE_TOO_OLD("Day plan cannot be set for past date", 409),
    RECIPE_NOT_FOUND("Recipe not found", 404),
    USER_NOT_FOUND("User not found", 404),
    PHOTO_NOT_FOUND("Photo not found", 404),
    INGREDIENT_NOT_FOUND("Ingredient not found", 404),
    DAY_PLAN_NOT_FOUND("Day plan not found", 404),
    STEP_NOT_FOUND("Step not found", 404),
    CATEGORY_NOT_FOUND("Category not found", 404),
    SHOPPING_LIST_NOT_FOUND("Shopping list not found", 404),
    SHOPPING_LIST_ITEM_NOT_FOUND("Shopping list item not found", 404),
    CANNOT_LOGIN_WITH_FACEBOOK("Cannot login with Facebook", 400),
    MISSING_USER_ID("Missing user id", 400),
    JWT_MISSING_TOKEN("Token is missing", 400),
    JWT_EXPIRED_TOKEN("Token is expired", 400),
    JWT_MALFORMED_TOKEN("Token is malformed", 400),
    JWT_GENERIC_ERROR("Unknown token error", 400),
    INGREDIENT_GROUP_NOT_FOUND("Ingredient group not found", 404)
}