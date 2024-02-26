package xabier.alberto.uv.es.campingscv

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject
import java.io.InputStream

class CampingList : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainFragment", "ESTOY EN onCreate")
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.camping_list, container, false)

        Log.d("MainFragment", "ESTOY EN onCreateView")

        val campings = getData()
        val rv: RecyclerView = view.findViewById(R.id.rv)
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = MyAdapter(campings) { camping ->
            (requireActivity() as MainActivity).showCampingDetail(camping as Camping)
        }

        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        Log.d("MenuCreado", "ESTOY EN onCreateOptionsMenu ANTES DE INFLAR EL MENU")
        inflater.inflate(R.menu.options_menu, menu)
        Log.d("MenuCreado", "ESTOY EN onCreateOptionsMenu DESPUES DE INFLAR EL MENU")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val rv: RecyclerView = view?.findViewById(R.id.rv) ?: return false
        val adapter = rv.adapter as MyAdapter
        when (item.itemId) {
            R.id.order_by_stars -> {
                adapter.data = adapter.data.sortedByDescending {
                    try {
                        it.estrellas?.replace(" ⭐", "")?.toInt()
                    } catch (e: NumberFormatException) {
                        Int.MIN_VALUE
                    }
                }
            }
            R.id.order_by_name_a_z -> {
                adapter.data = adapter.data.sortedBy { it.nombre }
            }
            R.id.order_by_name_z_a -> {
                adapter.data = adapter.data.sortedByDescending { it.nombre }
            }
            else -> return super.onOptionsItemSelected(item)
        }
        adapter.notifyDataSetChanged()
        return true
    }

    fun readJsonFromRaw(resources: Resources, rawResourceId: Int): String {
        val inputStream: InputStream = resources.openRawResource(rawResourceId)
        val buffer = ByteArray(inputStream.available())
        inputStream.read(buffer)
        inputStream.close()
        return String(buffer, Charsets.UTF_8)
    }

    private fun getData(): ArrayList<Camping> {
        val listaCampings = ArrayList<Camping>()
        val rawResourceId = R.raw.datastore_search
        val jsonFileContent = readJsonFromRaw(resources, rawResourceId)
        // Parsear el contenido JSON utilizando JSONObject o JSONArray
        val jsonObject = JSONObject(jsonFileContent)
        val jsonResult = jsonObject.getJSONObject("result")
        //TODO: obtener el JsonArray “records”
        val records = jsonResult.getJSONArray("records")
        // Ahora puedes iterar a través del array o acceder a valores específicos
        // TODO: read the data of each camping from records array, create a new Camping
        for (i in 0 until records.length()) {
            val camping = records.getJSONObject(i)
            val nombre = camping.getString("Nombre")
            var estrellas = camping.getString("Cod. Categoria")
            when (estrellas) {
                "1", "1e" -> estrellas = "1 ⭐"
                "2", "2e" -> estrellas = "2 ⭐"
                "3", "3e" -> estrellas = "3 ⭐"
                "4", "4e" -> estrellas = "4 ⭐"
                "5", "5e" -> estrellas = "5 ⭐"
            }
            val direccion = camping.getString("Direccion")
            val provincia = camping.getString("Provincia")
            val municipio = camping.getString("Municipio")
            val web = camping.getString("Web")
            val email = camping.getString("Email")
            val periodo = camping.getString("Periodo")
            val dias = camping.getString("Días Periodo")
            val modalidad = camping.getString("Modalidad")
            var plazas_parcela = camping.getString("Plazas Parcela")
            if (plazas_parcela == "" || plazas_parcela == " ") {
                plazas_parcela = "0"
            }
            var plazas_bungalow = camping.getString("Plaza Bungalows")
            if (plazas_bungalow == "" || plazas_bungalow == " ") {
                plazas_bungalow = "0"
            }
            var libre_acampada = camping.getString("Plazas Libre Acampada")
            if (libre_acampada == "" || libre_acampada == " ") {
                libre_acampada = "0"
            }
            val campingObject = Camping(nombre, estrellas, direccion, provincia, municipio, web, email, periodo, dias, modalidad, plazas_parcela, plazas_bungalow, libre_acampada)

            listaCampings.add(campingObject)
        }
        // object, and insert it into the campings ArrayList.
        return listaCampings
    }
}