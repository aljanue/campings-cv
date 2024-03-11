package xabier.alberto.uv.es.campingscv.database

import androidx.room.Database
import androidx.room.RoomDatabase
import xabier.alberto.uv.es.campingscv.Camping

@Database(entities = [Camping::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): CampingDAO
}