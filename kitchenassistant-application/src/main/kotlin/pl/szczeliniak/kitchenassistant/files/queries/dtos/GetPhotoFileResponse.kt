package pl.szczeliniak.kitchenassistant.files.queries.dtos

import pl.szczeliniak.kitchenassistant.files.SupportedMediaType

data class GetPhotoFileResponse(val mediaType: SupportedMediaType, val body: ByteArray) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GetPhotoFileResponse

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