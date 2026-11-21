package com.example.loopit.domain.model

import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

data class UserCoordinates(
    val latitude: Double,
    val longitude: Double
)

data class AcceptedDeviceModel(
    val id: Int,
    val name: String,
    val iconName: String
)

data class CollectionPointWithDistance(
    val id: Int,
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val distanceKm: Double?,
    val acceptedDevices: List<AcceptedDeviceModel>
)

// Calculate straight-line distance in kilometers
fun calculateStraightLineDistanceKm(
    fromLat: Double,
    fromLng: Double,
    toLat: Double,
    toLng: Double
): Double {
    val earthRadiusKm = 6371.0

    val dLat = Math.toRadians(toLat - fromLat)
    val dLng = Math.toRadians(toLng - fromLng)

    val radFromLat = Math.toRadians(fromLat)
    val radToLat = Math.toRadians(toLat)

    val a = (
            sin(dLat / 2).pow(2) +
                    cos(radFromLat) * cos(radToLat) *
                    sin(dLng / 2).pow(2)
            ).coerceIn(0.0, 1.0)

    val c = 2 * atan2(sqrt(a), sqrt(1 - a))

    return earthRadiusKm * c
}