package ch.iagentur.unity.testdogsproject.data.network

import com.google.gson.annotations.SerializedName


data class DogBreed(
    val weight: DogParams = DogParams(),
    val metric: DogParams = DogParams(),
    val id: Int = 0,
    val name: String = "",
    @SerializedName("bred_for") val bredFor: String = "",
    @SerializedName("breed_group") val breedGroup: String = "",
    @SerializedName("life_span") val lifeSpan: String = "",
    @SerializedName("temperament") val temperament: String = "",
    @SerializedName("origin") val origin: String = ""
)


