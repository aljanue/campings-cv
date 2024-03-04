package xabier.alberto.uv.es.campingscv.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CampingData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): CampingDAO
}