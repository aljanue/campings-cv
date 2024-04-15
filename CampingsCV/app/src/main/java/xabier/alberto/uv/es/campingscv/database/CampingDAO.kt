package xabier.alberto.uv.es.campingscv.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import xabier.alberto.uv.es.campingscv.Camping

@Dao
interface CampingDAO {
    @Query("SELECT * FROM camping")
    fun getAll(): List<Camping>

    @Query("SELECT * FROM camping WHERE cid IN (:campingIds)")
    fun loadAllByIds(campingIds: IntArray): List<Camping>

    @Query("SELECT * FROM camping WHERE nombre LIKE :name LIMIT 1")
    fun findByName(name: String): Camping

    @Insert
    fun insert(camping: Camping)

    @Delete
    fun delete(camping: Camping)
}