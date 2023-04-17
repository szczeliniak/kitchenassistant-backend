package pl.szczeliniak.kitchenassistant.photo

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import pl.szczeliniak.kitchenassistant.photo.commands.dto.DeletePhotoResponse
import pl.szczeliniak.kitchenassistant.photo.commands.dto.UploadPhotoResponse

@RestController
@RequestMapping("/photos")
@Validated
class PhotoController(private val photoFacade: PhotoFacade) {

    @PostMapping
    fun uploadPhoto(@RequestParam("file") file: MultipartFile): UploadPhotoResponse {
        return photoFacade.uploadPhoto(file.originalFilename ?: file.name, file.bytes)
    }

    @GetMapping("/{fileName}")
    fun downloadPhoto(@PathVariable fileName: String): ResponseEntity<ByteArray> {
        val response = photoFacade.downloadPhoto(fileName)
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(response.mediaType.mimeType))
                .body(response.body)
    }

    @DeleteMapping("/{fileName}")
    fun deletePhoto(@PathVariable fileName: String): DeletePhotoResponse {
        return photoFacade.deletePhoto(fileName)
    }

}