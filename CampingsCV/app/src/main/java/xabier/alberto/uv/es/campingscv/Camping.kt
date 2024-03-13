package xabier.alberto.uv.es.campingscv
import java.io.Serializable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "camping")
data class Camping(
    @PrimaryKey val cid: Int?,
    @ColumnInfo(name = "nombre") val nombre: String?,
    @ColumnInfo(name = "estrellas") val estrellas: String?,
    val direccion: String?,
    val provincia: String?,
    val municipio: String?,
    val web: String?,
    val email: String?,
    val periodo: String?,
    val dias: String?,
    val modalidad: String?,
    val plazas_parcela: String?,
    val plazas_bungalow: String?,
    val libre_acampada: String?,
    @ColumnInfo(name = "isFavorito") var isFavorito: Boolean? // Nueva propiedad
) : Serializable