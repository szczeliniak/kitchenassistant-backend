package pl.szczeliniak.kitchenassistant.file.queries.dtos

import pl.szczeliniak.kitchenassistant.file.SupportedMediaType

data class GetFileResponse(val mediaType: SupportedMediaType, val body: ByteArray) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GetFileResponse

        if (mediaType != other.mediaType) return false
        if (!body.contentEquals(other.body)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = mediaType.hashCode()
        result = 31 * result + body.contentHashCode()
        return result
    }

}