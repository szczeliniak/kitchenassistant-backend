package pl.szczeliniak.kitchenassistant.shared

abstract class BaseService(val requestContext: RequestContext) {

    protected fun requireTokenType(tokenType: TokenType) {
        if (requestContext.tokenType() != tokenType) {
            throw KitchenAssistantException(ErrorCode.WRONG_TOKEN_TYPE)
        }
    }

}