package xabier.alberto.uv.es.campingscv.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CampingDAO {
    @Query("SELECT * FROM camping")
    fun getAll(): List<CampingData>

    @Query("SELECT * FROM camping WHERE cid IN (:campingIds)")
    fun loadAllByIds(campingIds: IntArray): List<CampingData>

    @Query("SELECT * FROM camping WHERE nombre LIKE :first AND " + "estrellas LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): CampingData

    @Insert
    fun insertAll(vararg campings: CampingData)

    @Delete
    fun delete(camping: CampingData)
}