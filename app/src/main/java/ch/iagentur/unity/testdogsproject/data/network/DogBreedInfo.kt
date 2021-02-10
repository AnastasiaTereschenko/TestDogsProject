package ch.iagentur.unity.testdogsproject.data.network

import ch.iagentur.unity.testdogsproject.data.network.DogBreed

data class DogBreedInfo(
    val breeds: List<DogBreed>,
    val id: String,
    val url: String,
    val width: Int,
    val height: Int
)
