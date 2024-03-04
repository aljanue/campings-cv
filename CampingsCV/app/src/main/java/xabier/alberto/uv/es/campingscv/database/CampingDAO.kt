package xabier.alberto.uv.es.campingscv.database

@Dao
interface CampingDAO {
    @Query("SELECT * FROM camping")
    fun getAll(): List<CampingData>

    @Query("SELECT * FROM camping WHERE uid IN (:campingIds)")
    fun loadAllByIds(campingIds: IntArray): List<CampingData>

    @Query("SELECT * FROM camping WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): CampingData

    @Insert
    fun insertAll(vararg campings: CampingData)

    @Delete
    fun delete(camping: CampingData)
}