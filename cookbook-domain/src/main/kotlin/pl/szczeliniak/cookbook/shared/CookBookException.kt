package pl.szczeliniak.cookbook.shared

class CookBookException(val error: ErrorCode) : Exception(error.message)