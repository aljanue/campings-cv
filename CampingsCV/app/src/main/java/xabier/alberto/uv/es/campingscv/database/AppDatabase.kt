package xabier.alberto.uv.es.campingscv.database

@Database(entities = [CampingData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): CampingDAO
}