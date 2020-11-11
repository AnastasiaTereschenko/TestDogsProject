package ch.iagentur.unity.testdogsproject.bd

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DogBreedEntity::class], version = 5)
abstract class AppDatabase : RoomDatabase() {

    abstract fun dogBreedDao(): DogBreedDao?

    companion object {
        fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, AppDatabase::class.java, "dog.db"
        ).fallbackToDestructiveMigration()
            .build()
    }
}