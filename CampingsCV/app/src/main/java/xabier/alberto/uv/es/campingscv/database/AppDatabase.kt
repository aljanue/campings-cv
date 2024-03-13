package xabier.alberto.uv.es.campingscv.database

import androidx.room.Database
import androidx.room.RoomDatabase
import xabier.alberto.uv.es.campingscv.Camping

@Database(entities = [Camping::class], version = 2) // Incrementa la versión de la base de datos
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): CampingDAO
}