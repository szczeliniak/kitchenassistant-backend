package pl.szczeliniak.cookbook.shared

enum class ErrorCode(val message: String, val code: Int) {
    PASSWORDS_DO_NOT_MATCH("Passwords do not match", 400),
    USER_ALREADY_EXISTS("User with email already exists", 409),
    DAY_PLAN_ALREADY_EXISTS("Day plan already exists", 409),
    DAY_PLAN_DATE_TOO_OLD("Day plan cannot be set for past date", 409),
    RECIPE_NOT_FOUND("Recipe not found", 404),
    USER_NOT_FOUND("User not found", 404),
    WRONG_TOKEN_TYPE("Wrong token type", 404),
    DAY_PLAN_NOT_FOUND("Day plan not found", 404),
    CATEGORY_NOT_FOUND("Category not found", 404),
    CANNOT_LOGIN_WITH_FACEBOOK("Cannot login with Facebook", 400),
    MISSING_USER_ID("Missing user id", 400),
    MISSING_REQUEST_ID("Missing request id", 400),
    MISSING_TOKEN_TYPE("Missing token type", 400),
    JWT_MISSING_TOKEN("Token is missing", 400),
    JWT_EXPIRED_TOKEN("Token is expired", 400),
    JWT_MALFORMED_TOKEN("Token is malformed", 400),
    INVALID_EMAIL("Invalid email", 400),
    JWT_GENERIC_ERROR("Unknown token error", 400),
    RECIPE_ALREADY_ADDED_TO_DAY_PLAN("Recipe already added to day plan", 400),
    GENERIC_INTERNAL_ERROR("Generic internal error", 400),
    FORBIDDEN("Forbidden", 403)
}