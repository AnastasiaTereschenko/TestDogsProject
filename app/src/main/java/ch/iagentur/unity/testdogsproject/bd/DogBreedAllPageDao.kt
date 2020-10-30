package ch.iagentur.unity.testdogsproject.bd

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DogBreedAllPageDao {

    @Query("SELECT * FROM dogbreedallpageentity")
    fun getAllDogBreeds(): Flow<List<DogBreedAllPageEntity>>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dogBreedEntity: DogBreedAllPageEntity?)
}