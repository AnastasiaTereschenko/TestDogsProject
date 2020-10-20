package ch.iagentur.unity.testdogsproject.bd

import androidx.room.*

@Dao
interface DogBreedDao {

    @Query("SELECT * FROM dogbreedentity WHERE dogbreedentity.id = :id")
    fun getDogBreedById(id: String): DogBreedEntity

    @Update
    fun update(dogBreedEntity: DogBreedEntity?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dogBreedEntity: DogBreedEntity?)

    @Delete
    fun delete(dogBreedEntity: DogBreedEntity?)
}