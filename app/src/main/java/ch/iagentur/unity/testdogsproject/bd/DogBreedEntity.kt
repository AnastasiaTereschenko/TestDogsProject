package ch.iagentur.unity.testdogsproject.bd

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class DogBreedEntity {
    @PrimaryKey
    var id: String = ""
    var jsonObject: String = ""
}