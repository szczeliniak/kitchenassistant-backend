package pl.szczeliniak.kitchenassistant.shared

class KitchenAssistantException(val error: ErrorCode) : Exception(error.message)