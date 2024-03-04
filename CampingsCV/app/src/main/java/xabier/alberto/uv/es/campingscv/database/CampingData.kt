package xabier.alberto.uv.es.campingscv.database

@Entity
data class CampingData(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "nombre") val nombre: String?,
    @ColumnInfo(name = "estrellas") val estrellas: String?
)
