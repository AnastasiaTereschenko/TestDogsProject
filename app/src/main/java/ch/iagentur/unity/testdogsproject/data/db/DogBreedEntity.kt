package ch.iagentur.unity.testdogsproject.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class DogBreedEntity {
    @PrimaryKey
    var id: String = ""
    var jsonObject: String = ""
}