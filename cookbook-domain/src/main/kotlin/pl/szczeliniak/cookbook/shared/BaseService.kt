package pl.szczeliniak.cookbook.shared

abstract class BaseService(val requestContext: RequestContext) {

    protected fun requireTokenType(tokenType: TokenType) {
        if (requestContext.tokenType() != tokenType) {
            throw CookBookException(ErrorCode.WRONG_TOKEN_TYPE)
        }
    }

}