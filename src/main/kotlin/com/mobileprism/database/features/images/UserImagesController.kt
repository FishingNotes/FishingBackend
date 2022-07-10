package com.mobileprism.database.features.images

import com.mobileprism.database.*
import com.mobileprism.database.model.images.UserImageDTO
import com.mobileprism.database.model.images.UserImages
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.io.File
import java.util.UUID
import kotlin.io.path.Path
import kotlin.io.path.exists


class UserImagesController() {

    suspend fun uploadImage(call: ApplicationCall) {
        validateTokenWithUser(call) { token, user ->

            var fileDescription = ""
            val fileName = UUID.randomUUID()

            val multipartData = call.receiveMultipart()

            multipartData.forEachPart { part ->
                when (part) {
                    is PartData.FormItem -> {
                        fileDescription = part.value
                    }
                    is PartData.FileItem -> {
                        //fileName = part.originalFileName as String
                        val fileBytes = part.streamProvider().readBytes()

                        // create a File object for the parent directory
                        val imagesDirectory = File("/images/${user.id.value}")
                        if (imagesDirectory.exists().not()) imagesDirectory.mkdirs()

                        // TODO: 08.07.2022 check if not photo received

                        File(imagesDirectory, "${fileName}.png").writeBytes(fileBytes)

                    }
                    else -> {
                        call.respond(HttpStatusCode.ExpectationFailed, "Error occurred")
                    }
                }
            }
            if (Path("/images/${user.id.value}/${fileName}.png").exists()) {
                val image = UserImages.addNewImage(user, fileName).mapToImageResponse()
                call.respond(image)
            } else {
                call.respondText("$fileDescription is uploaded to 'uploads/$fileName'")
            }

        }
    }

    suspend fun deleteImage(call: ApplicationCall) {
        validateTokenWithUser(call) { token, user ->
            parameterUUIDRequired(call, parameter = "imageId") { imageId ->
                UserImages.getImageById(imageId)?.let { imageDTO ->
                    if (imageDTO.user.id.value == user.id.value) {
                        imageDTO.delete()
                        call.respond(HttpStatusCode.OK)
                    } else call.respond(HttpStatusCode.Unauthorized)
                } ?: call.respond(HttpStatusCode.NotFound)
            }
        }
    }

    suspend fun getAllImages(call: ApplicationCall) {
        validateTokenWithUser(call) { token, user ->
            call.respond(UserImages.getAllImagesByUser(user).map { it.mapToImageResponse() })
        }
    }

    suspend fun getImage(call: ApplicationCall) {
        validateTokenWithUser(call) { token, user ->
            parameterUUIDRequired(call, "imageId") { imageId ->
                val file = File("images/${user.id.value}/$imageId.png")
                call.response.header(
                    HttpHeaders.ContentDisposition,
                    ContentDisposition.Attachment.withParameter(ContentDisposition.Parameters.FileName, "$imageId.png")
                        .toString()
                )
                call.respondFile(file)
                /*val userImageDTO = UserImages.getImageById(imageId = imageId)
                userImageDTO?.let {
                    call.respond(it.mapToImageResponse())
                } ?: call.respond(HttpStatusCode.NotFound)*/

                // TODO: 09.07.2022

            }
        }
    }
}

