package com.example.sampletest.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class User(
    val id: Int= 0 ,
    val name: String = "",
    val username: String ="",
    val email: String= "",
    val address: Address= Address(),
    val phone: String = "",
    val website: String ="",
    val company: Company = Company()
)

data class Address(
    val street: String = "",
    val suite: String ="",
    val city: String = "",
    val zipCode: String = "",
    val latLng: LatLng = LatLng()
)

data class Company(
    val name: String = "",
    val catchPhrase: String = "",
    val bs: String = ""
)

data class LatLng(
    val lat: String = "",
    val lng: String = ""
)

data class Photo(
    val albumId: Int = 0,
    val id: Int = 0,
    val title: String = "",
    val url: String = "",
    val thumbnailUrl: String = ""
)

