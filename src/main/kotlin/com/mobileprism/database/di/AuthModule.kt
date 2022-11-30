package com.mobileprism.database.di

import com.mobileprism.database.features.auth.FirebaseMigrateController
import com.mobileprism.database.features.auth.LoginController
import com.mobileprism.database.features.auth.RestoreController
import org.koin.dsl.module

val authModule = module {

    factory<LoginController> { LoginController() }
    factory<RestoreController> { RestoreController() }
    factory<FirebaseMigrateController> { FirebaseMigrateController() }


}