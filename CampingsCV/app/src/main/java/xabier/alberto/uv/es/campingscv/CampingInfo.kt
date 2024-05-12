package xabier.alberto.uv.es.campingscv

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.room.Room
import kotlinx.coroutines.withContext
import xabier.alberto.uv.es.campingscv.database.AppDatabase

@Suppress("DEPRECATION")
class CampingInfo : Fragment() {
    var web: String? = null
    var direccion: String? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.camping_info, container, false)

        Log.d("InfoFragment", "ESTOY EN onCreateView")

        // Obtenemos la información del camping seleccionado y la asignamos a los elementos de la vista
        val camping = arguments?.getSerializable("camping") as? Camping

        val nombreID = view.findViewById<TextView>(R.id.nombre)
        val estrellasID = view.findViewById<TextView>(R.id.estrellas)
        val direccionID = view.findViewById<TextView>(R.id.direccion)
        val provinciaID = view.findViewById<TextView>(R.id.provincia)
        val municipioID = view.findViewById<TextView>(R.id.municipio)
        val webID = view.findViewById<TextView>(R.id.web)
        val emailID = view.findViewById<TextView>(R.id.email)
        val periodoID = view.findViewById<TextView>(R.id.periodo)
        val diasID = view.findViewById<TextView>(R.id.dias)
        val modalidadID = view.findViewById<TextView>(R.id.modalidad)
        val plazas_parcelaID = view.findViewById<TextView>(R.id.parcela)
        val plazas_bungalowID = view.findViewById<TextView>(R.id.bungalow)
        val libre_acampadaID = view.findViewById<TextView>(R.id.libre)

        web = camping?.web
        direccion = camping?.direccion

        nombreID.text = camping?.nombre
        estrellasID.text = camping?.estrellas
        direccionID.text = Html.fromHtml("<b>DIRECCIÓN</b><br><br>" + direccion, Html.FROM_HTML_MODE_COMPACT)
        provinciaID.text = Html.fromHtml("<b>PROVINCIA</b><br><br>" + camping?.provincia, Html.FROM_HTML_MODE_COMPACT)
        municipioID.text = Html.fromHtml("<b>MUNICIPIO</b><br><br>" + camping?.municipio, Html.FROM_HTML_MODE_COMPACT)
        webID.text = Html.fromHtml("<b>WEB</b><br>" + web, Html.FROM_HTML_MODE_COMPACT)
        emailID.text = Html.fromHtml("<b>EMAIL</b><br>" + camping?.email, Html.FROM_HTML_MODE_COMPACT)
        periodoID.text = Html.fromHtml("<b>PERIODO</b><br><br>" + camping?.periodo, Html.FROM_HTML_MODE_COMPACT)
        diasID.text = Html.fromHtml("<b>DÍAS</b><br><br>" + camping?.dias, Html.FROM_HTML_MODE_COMPACT)
        modalidadID.text = Html.fromHtml("<b>" + camping?.modalidad + "<b>", Html.FROM_HTML_MODE_COMPACT)
        plazas_parcelaID.text = camping?.plazas_parcela
        plazas_bungalowID.text = camping?.plazas_bungalow
        libre_acampadaID.text = camping?.libre_acampada

        // Referencia al botón de favoritos
        val imageView = view.findViewById<ImageView>(R.id.favorito)

        // Conectamos a la base de datos
        val db = Room.databaseBuilder(requireContext(),AppDatabase::class.java, "database").fallbackToDestructiveMigration().build()
        val userDao = db.userDao()

        // Creamos un objeto camping temporal
        var campingData = Camping(camping?.cid, camping?.nombre, camping?.estrellas, direccion, camping?.provincia, camping?.municipio, web, camping?.email, camping?.periodo, camping?.dias, camping?.modalidad, camping?.plazas_parcela, camping?.plazas_bungalow, camping?.libre_acampada, camping?.isFavorito)

        // Corrutina para comprobar la base de datos
        CoroutineScope(Dispatchers.IO).launch {
            // Comprueba si el camping está en la lista de favoritos
            val favoriteCamping = campingData.nombre?.let { userDao.findByName(it) }
            if (favoriteCamping != null) {
                campingData.isFavorito = true
            }

            withContext(Dispatchers.Main) {
                if (campingData.isFavorito == true) {
                    // El camping está en favoritos, hacemos el botón rojo
                    imageView.setImageResource(R.drawable.red_like)
                    imageView.setTag(R.drawable.red_like)
                } else {
                    // El camping no está en favoritos, hacemos el botón negro
                    imageView.setImageResource(R.drawable.black_like)
                    imageView.setTag(R.drawable.black_like)
                }
            }
        }


        imageView.setOnClickListener {
            // Comprueba el color del botón
            if (imageView.getTag() == R.drawable.black_like) {
                // Cambiamos el botón a rojo
                imageView.setImageResource(R.drawable.red_like)
                imageView.setTag(R.drawable.red_like)

                // Agrega el camping a favoritos
                CoroutineScope(Dispatchers.IO).launch {
                    campingData.isFavorito = true
                    userDao.insert(campingData)
                }
            } else {
                // Cambiamos el botón a negro
                imageView.setImageResource(R.drawable.black_like)
                imageView.setTag(R.drawable.black_like)

                // Elimina el camping de favoritos
                CoroutineScope(Dispatchers.IO).launch {
                    campingData.isFavorito = false
                    userDao.delete(campingData)
                }
            }
        }

        // Activamos el menú de opciones
        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        Log.d("InfoFragment", "ESTOY EN onCreateOptionsMenu ANTES DE INFLAR EL MENU")
        inflater.inflate(R.menu.info_options, menu)
        Log.d("InfoFragment", "ESTOY EN onCreateOptionsMenu DESPUES DE INFLAR EL MENU")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            // Botón de dirección en el mapa
            R.id.maps -> {
                if (direccion != null && direccion != "" && direccion != " ") {
                    // La dirección está incluida
                    val mapIntent = android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse("geo:0,0?q=" + direccion))
                    startActivity(mapIntent)
                }
                else {
                    // La dirección no está incluida
                    val toast = android.widget.Toast.makeText(context, "No hay dirección", android.widget.Toast.LENGTH_SHORT)
                    toast.show()
                }
            }
            // Botón de página web
            R.id.website -> {
                if (web != null && web != "" && web != " ") {
                    // La pagina web existe
                    if (!web!!.startsWith("http://") && !web!!.startsWith("https://"))
                        web = "http://" + web
                    val browserIntent = android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(web))
                    startActivity(browserIntent)
                }
                else {
                    // No tiene página web
                    val toast = android.widget.Toast.makeText(context, "No hay página web", android.widget.Toast.LENGTH_SHORT)
                    toast.show()
                }
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }
}