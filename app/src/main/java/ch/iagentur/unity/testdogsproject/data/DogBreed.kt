package ch.iagentur.unity.testdogsproject.data

import com.google.gson.annotations.SerializedName


class DogBreed(
    val weight: DogParams?, val metric: DogParams?, val id: Int, val name: String?,
    @SerializedName("bred_for") val bredFor: String?,
    @SerializedName("breed_group") val breedGroup: String,
    @SerializedName("life_span") val lifeSpan: String?,
    @SerializedName("temperament") val temperament: String?,
    @SerializedName("origin") val origin: String?
)

