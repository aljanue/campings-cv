package xabier.alberto.uv.es.campingscv
import java.io.Serializable

data class Camping(
    val nombre: String,
    val estrellas: String,
    val direccion: String,
    val provincia: String,
    val municipio: String,
    val web: String,
    val email: String,
    val periodo: String,
    val dias: String,
    val modalidad: String,
    val plazas_parcela: String,
    val plazas_bungalow: String,
    val libre_acampada: String
) : Serializable