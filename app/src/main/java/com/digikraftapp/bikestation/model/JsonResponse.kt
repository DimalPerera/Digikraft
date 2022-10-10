package com.digikraftapp.bikestation.model
import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.parcelize.Parcelize

/**
 * Step 1
 * All the API-related JSON response data will convert to the data class
 */

/**
 * Step 2
 * data class need to be passed all over the application flow,
 * and it needs to be in parcelable format
 */

@JsonIgnoreProperties
@Parcelize
data class BikeJson(
    @JsonProperty("crs")
    val crs: Crs,
    @JsonProperty("features")
    val features: List<Feature>,
    @JsonProperty("type")
    val type: String // FeatureCollection
): Parcelable

@JsonIgnoreProperties
@Parcelize
data class Crs(
    @JsonProperty("properties")
    val properties: Properties,
    @JsonProperty("type")
    val type: String // EPSG
): Parcelable

@JsonIgnoreProperties
@Parcelize
data class Feature(
    @JsonProperty("geometry")
    val geometry: Geometry,
    @JsonProperty("id")
    val id: String, // 15570
    @JsonProperty("properties")
    val properties: PropertiesX,
    @JsonProperty("type")
    val type: String // Feature
): Parcelable

@JsonIgnoreProperties
@Parcelize
data class Properties(
    @JsonProperty("code")
    val code: String // 4326
): Parcelable

@JsonIgnoreProperties
@Parcelize
data class Geometry(
    @JsonProperty("coordinates")
    val coordinates: List<Double>,
    @JsonProperty("type")
    val type: String // Point
): Parcelable

@JsonIgnoreProperties
@Parcelize
data class PropertiesX(
    @JsonProperty("bike_racks")
    val bikeRacks: String, // 18
    @JsonProperty("bikes")
    val bikes: String, // 12
    @JsonProperty("free_racks")
    val freeRacks: String, // 7
    @JsonProperty("label")
    val label: String, // Poznań Główny
    @JsonProperty("updated")
    val updated: String // 2022-07-18 06:36
): Parcelable
    
