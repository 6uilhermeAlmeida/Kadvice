package com.gaa.data.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.http.ContentType
import kotlinx.serialization.json.Json

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Provides
    fun provideHttpClient(): HttpClient = HttpClient(CIO) {

        install(JsonFeature) {

            serializer = KotlinxSerializer(
                Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )

            accept(ContentType.Application.Json)
            accept(ContentType.Text.Html) // The advice api uses text/html *shrug*
        }
    }
}