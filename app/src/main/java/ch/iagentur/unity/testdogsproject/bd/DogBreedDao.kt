package ch.iagentur.unity.testdogsproject.bd

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DogBreedDao {

    @Query("SELECT * FROM dogbreedentity WHERE dogbreedentity.id = :id")
    fun getDogBreedById(id: String): DogBreedEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dogBreedEntity: DogBreedEntity?)

    @Delete
    fun delete(dogBreedEntity: DogBreedEntity?)
}