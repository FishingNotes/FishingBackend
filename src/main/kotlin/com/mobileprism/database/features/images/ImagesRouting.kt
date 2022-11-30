package com.mobileprism.database.features.images

import io.ktor.server.application.*
import io.ktor.server.routing.*


fun Routing.imagesRouting() {
    route("/images") {
        get {
            UserImagesController().getAllImages(call)
        }
        get("{imageId?}") {
            UserImagesController().getImage(call)
        }
        delete("{imageId?}") {
            UserImagesController().deleteImage(call)
        }
        post("upload") {
            UserImagesController().uploadImage(call)
        }

    }

}




