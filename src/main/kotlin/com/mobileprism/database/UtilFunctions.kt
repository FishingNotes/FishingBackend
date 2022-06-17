package com.mobileprism.database

import com.mobileprism.database.model.tokens.Tokens
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.util.reflect.*
import java.util.UUID

suspend inline fun validateToken(call: ApplicationCall, function: (token: String) -> Unit) {
    val token = call.request.headers["Fishing-AUTH"]

    if (token.isNullOrBlank() || Tokens.getToken(token)?.isActive != true) {
        call.respond(HttpStatusCode.Forbidden, "Token $token is not valid")
        return
    }

    function(token)
}

suspend inline fun <reified T : Any> ApplicationCall.receiveModel(): T {
    return try {
        this.receive<T>(typeInfo<T>())
    } catch (e: java.lang.Exception) {
        this.respond(HttpStatusCode.MethodNotAllowed, e.localizedMessage)
        throw e
    }

    /*val result = runCatching {
        call.receive<T>(typeInfo<T>())
    }.onFailure {
        call.respond(HttpStatusCode.BadRequest, it.localizedMessage)
    }.onSuccess {
        return it
    }*/

}

suspend inline fun parameterRequired(call: ApplicationCall, parameter: String, function: (String) -> Unit) {
    val receivedParameter: String = call.parameters[parameter]
        ?: return call.respond(status = HttpStatusCode.BadRequest, "Missing $parameter parameter")
    function(receivedParameter)
}

suspend inline fun parameterUUIDRequired(call: ApplicationCall, parameter: String, function: (UUID) -> Unit) {
    val receivedParameter: String = call.parameters[parameter]
        ?: return call.respond(status = HttpStatusCode.BadRequest, "Missing $parameter parameter")

        runCatching { UUID.fromString(receivedParameter) }
            .onFailure {
            call.respond(status = HttpStatusCode.NotAcceptable, "Invalid $parameter parameter")
            return
        }.onSuccess {
            function(it)
        }
}

suspend inline fun parametersRequired(
    call: ApplicationCall,
    vararg parameters: String,
    function: (Array<String>) -> Unit
) {
    val parametersArray = arrayOf<String>()
    parameters.forEachIndexed { index, parameter ->
        call.parameters[parameter]?.let {
            parametersArray[index] = it
        } ?: return call.respond(status = HttpStatusCode.BadRequest, "Missing $parameter parameter")
    }

    function(parametersArray)
}