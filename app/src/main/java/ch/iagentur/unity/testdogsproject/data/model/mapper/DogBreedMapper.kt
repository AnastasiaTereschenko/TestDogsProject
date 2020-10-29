package ch.iagentur.unity.testdogsproject.data.model.mapper

import ch.iagentur.unity.testdogsproject.bd.DogBreedAllPageEntity
import ch.iagentur.unity.testdogsproject.data.DogBreed
import ch.iagentur.unity.testdogsproject.misc.Mapper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DogBreedMapper : Mapper<DogBreedAllPageEntity, List<DogBreed>> {
    val gson = Gson()
    override fun map(origin: DogBreedAllPageEntity) = gson.fromJson(
        origin.jsonObject,
        object : TypeToken<ArrayList<DogBreed>>() {}.type
    ) as List<DogBreed>
}