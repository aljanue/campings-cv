package xabier.alberto.uv.es.campingscv.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "camping")
data class CampingData(
    @PrimaryKey val cid: Int,
    @ColumnInfo(name = "nombre") val nombre: String?,
    @ColumnInfo(name = "estrellas") val estrellas: String?
)
