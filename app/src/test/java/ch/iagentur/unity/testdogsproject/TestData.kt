package ch.iagentur.unity.testdogsproject

import ch.iagentur.unity.testdogsproject.data.network.DogBreed
import ch.iagentur.unity.testdogsproject.network.Resource


object TestData {
    val dataEmpty = emptyList<Resource<List<DogBreed?>?>>()
    val dataList = listOf(
        Resource.loading(null),
        Resource.success(listOf(
            DogBreed(null, null, 1, "Akita",
            "Hunting bears", "Docile, Alert, Responsive", "10-14 years",
            null, null)
        ))
    )
}