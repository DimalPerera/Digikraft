package com.digikraftapp.bikestation.repository

import android.content.Context
import android.util.Log
import com.digikraftapp.bikestation.model.BikeJson
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.SingletonSupport
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream

class StationRepository(private val context : Context) {

    private val TAG = StationRepository::class.java.simpleName

    var jsonResponse : BikeJson? = null

    /**
     * Decode local json string to model format
     */

    suspend fun getJsonObject() : BikeJson? {
        withContext(Dispatchers.IO) {
            try {

                val inputStream: InputStream = context.assets.open("data.json")
                val inputString = inputStream.bufferedReader().use { it.readText() }

                Log.d(TAG, inputString)

                val mapper = ObjectMapper().registerModule(
                    KotlinModule.Builder()
                        .withReflectionCacheSize(512)
                        .configure(KotlinFeature.NullToEmptyCollection, false)
                        .configure(KotlinFeature.NullToEmptyMap, false)
                        .configure(KotlinFeature.NullIsSameAsDefault, false)
                        .configure(KotlinFeature.SingletonSupport, false)
                        .configure(KotlinFeature.StrictNullChecks, false)
                        .build()
                )
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                jsonResponse = mapper.readValue(inputString, BikeJson::class.java)

            } catch (e: Exception) {
                Log.d(TAG, e.toString())
            }

            jsonResponse

        }

        return jsonResponse
    }
}