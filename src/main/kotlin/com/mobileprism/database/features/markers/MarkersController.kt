package com.mobileprism.database.features.markers

import com.mobileprism.database.parameterUUIDRequired
import com.mobileprism.database.receiveModel
import com.mobileprism.database.validateToken
import com.mobileprism.database.model.markers.UserMarkers
import com.mobileprism.database.model.otps.OTPs
import com.mobileprism.database.model.tokens.Tokens
import com.mobileprism.models.markers.MarkersResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*


class MarkersController {

    suspend fun createNewMarker(call: ApplicationCall) {
        validateToken(call) { token ->
            val newMarkerRemote = call.receiveModel<NewMarkerRemote>()
            val currentUser = Tokens.getUserByToken(token)
            UserMarkers.createNewMarker(currentUser, newMarkerRemote)
            call.respond(HttpStatusCode.Created)
        }
    }

    suspend fun deleteMarker(call: ApplicationCall) {
        validateToken(call) { token ->
            parameterUUIDRequired(call, "markerId") { markerId ->
                UserMarkers.deleteMarker(markerId)
                call.respond(HttpStatusCode.Accepted)
            }
        }
    }

    suspend fun getUserMarkers(call: ApplicationCall) {
        validateToken(call) { token ->
            val currentUser = Tokens.getUserByToken(token)
            val markers = UserMarkers.getUserMarkers(currentUser)
                .map { it.mapToMarkerResponse() }
            call.respond(MarkersResponse(markers))
        }
    }

    suspend fun getMarkerById(call: ApplicationCall) {
        validateToken(call) { token ->
            parameterUUIDRequired(call, "markerId") { markerId ->
                val marker = UserMarkers.getMarkerById(markerId)?.mapToMarkerResponse()
                call.respond(marker ?: "")
            }
        }
    }
}

/*
INSERT INTO public.fish(name_ru, name_en, weight)
VALUES
('Ёрш', 'Ruffe', 0.035),
('Белуга', 'Beluga', 1250),
('Белый амур', 'Grass carp', 25),
('Бычок', 'Goby', 0.130),
('Вьюн', '', 0.130),
('Голавль', 'Сhub'),
('Голец', 'Char'),
('Гольян', 'Minnow'),
('Горбуша', 'Pink salmon', 1.5),
('Густера', 'White bream'),
('Елец', 'Dace'),
('Жерех', 'Asp'),
('Камбала', 'Flounder'),
('Карась', 'Сrucian'),
('Карп', 'Сarp'),
('Кета', 'Keta'),
('Корюшка', 'Smelt'),
('Красноперка', 'Rudd'),
('Кумжа', 'Trout'),
('Ленок', 'Lenok'),
('Лещ', 'Bream'),
('Линь', 'Tench'),
('Лосось', 'Salmon'),
('Налим', 'Burbot'),
('Нельма', 'Siberian white salmon'),
('Окунь', 'Perch'),
('Омуль', 'Omul'),
('Осетр', 'Sturgeon'),

;


INSERT INTO public.fish(name_ru, name_en, weight)
VALUES
('Ёрш', 'Ruffe', 0.035),
('Белуга', 'Beluga', 1250),
('Белый амур', 'Grass carp', 25),
('Бычок', 'Goby', 0.130),
('Вьюн', 'Loach', 0.130),
('Голавль', 'Сhub', null),
('Голец', 'Char', null),
('Гольян', 'Minnow', null),
('Горбуша', 'Pink Salmon', 1.5),
('Густера', 'White Bream', null),
('Елец', 'Dace', null),
('Жерех', 'Asp', null),
('Камбала', 'Turbot Brill', null),
('Карась', 'Crucian Carp', null),
('Карп', 'Сarp', null),
('Кета', 'Chum Salmon', null),
('Кефаль', 'Mullet', null),
('Кижуч', 'Coho Salmon', null),
('Кефаль', 'Mullet', null),
('Королевский лосось', 'Chinook Salmon', null),
('Корюшка', 'Smelt', null),
('Красноперка', 'Rudd', null),
('Кумжа', 'Sea Trout', null),
('Ленок', 'Lenok', null),
('Лещ', 'Bream', null),
('Линь', 'Tench', null),
('Лосось', 'Salmon', null),
('Налим', 'Burbot', null),
('Нельма', 'White Salmon', null),
('Окунь', 'Perch', null),
('Омуль', 'Omul', null),
('Осетр', 'Sturgeon', null);

('Пелядь', 'Peled', 4),
('Пескарь', 'Minnow', null),
('Плотва', 'Roach', 0.250),
('Подуст', 'Podust', 1.2),
('Рипус', 'Ripus', null),
('Ротан', 'Amur Sleeper', null),
('Ряпушка', 'Whitefish', null),
('Сазан', 'Carp', null),
('Сельдь', 'Herring', 0.3),
('Сиг', 'Whitefish', null),
('Синец', 'Blue Bream', null),
('Сом', 'Catfish', null),
('Ставрида', 'Scadfish', null),
('Стерлядь', 'Sterlet', null),
('Судак', 'Zander', null),
('Таймень', 'Taimen', null),
('Тарань', 'Sea-Roach', null),
('Толстолобик', 'Silver Carp', null),
('Треска', 'Cod', null),
('Угорь', 'Eel', null),
('Уклейка', 'Bleak', null),
('Усач', 'Barbel', null),
('Форель', 'Trout', null),
('Хариус', 'Grayling', null),
('Чебак', 'Rudd/Bream Hybrid', null),
('Чехонь', 'Ziege', null),
('Чир', 'Broad Whitefish', null),
('', '', null),
('', '', null),
('', '', null),


*/


