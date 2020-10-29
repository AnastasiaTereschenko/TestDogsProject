package ch.iagentur.unity.testdogsproject.bd

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dogbreedallpageentity")
class DogBreedAllPageEntity {
    @PrimaryKey
    var page: String = ""
    var jsonObject: String = ""
}