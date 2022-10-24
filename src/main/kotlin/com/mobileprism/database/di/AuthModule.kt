package com.mobileprism.database.di

import com.mobileprism.database.features.auth.LoginController
import org.koin.dsl.module

val authModule = module {

    factory<LoginController> { LoginController() }



}