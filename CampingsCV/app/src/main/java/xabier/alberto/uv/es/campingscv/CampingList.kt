package xabier.alberto.uv.es.campingscv

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException
import org.json.JSONObject
import java.io.InputStream

class CampingList : Fragment() {

    private lateinit var campings: List<Camping>
    private lateinit var campingAdapter: MyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainFragment", "ESTOY EN onCreate")
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.camping_list, container, false)

        getData { campings ->
            this.campings = campings
            val rv: RecyclerView = view.findViewById(R.id.rv)
            rv.layoutManager = LinearLayoutManager(context)
            campingAdapter = MyAdapter(campings) { camping ->
                (requireActivity() as MainActivity).showCampingDetail(camping as Camping)
            }
            rv.adapter = campingAdapter
        }

        setHasOptionsMenu(true)

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCampingList(view)
        val favListButton: FloatingActionButton = view.findViewById(R.id.fav_list)
        favListButton.setOnClickListener {
            findNavController().navigate(R.id.favList)
        }
        val searchView: SearchView = view.findViewById(R.id.search_view)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredList = campings.filter { camping ->
                    camping.nombre!!.contains(newText ?: "", ignoreCase = true) ||
                            camping.provincia!!.contains(newText ?: "", ignoreCase = true) ||
                            camping.municipio!!.contains(newText ?: "", ignoreCase = true)
                }

                campingAdapter.updateList(filteredList)
                return false
            }
        })
    }

    private fun setupCampingList(view: View) {
        val rv: RecyclerView = view.findViewById(R.id.rv)
        rv.layoutManager = LinearLayoutManager(context)
        getData { campings ->
            val rv: RecyclerView = view.findViewById(R.id.rv)
            rv.layoutManager = LinearLayoutManager(context)
            rv.adapter = MyAdapter(campings) { camping ->
                (requireActivity() as MainActivity).showCampingDetail(camping as Camping)
            }
        }
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
            // Botón para ordenar por estrellas
            R.id.order_by_stars -> {
                adapter.data = adapter.data.sortedByDescending {
                    try {
                        it.estrellas?.replace(" ⭐", "")?.toInt()
                    } catch (e: NumberFormatException) {
                        Int.MIN_VALUE
                    }
                }
            }
            // Botón para ordenar por nombre
            R.id.order_by_name_a_z -> {
                adapter.data = adapter.data.sortedBy { it.nombre }
            }
            // Botón para ordenar por nombre a la inversa
            R.id.order_by_name_z_a -> {
                adapter.data = adapter.data.sortedByDescending { it.nombre }
            }
            else -> return super.onOptionsItemSelected(item)
        }
        adapter.notifyDataSetChanged()
        return true
    }


    private fun getData(callback: (List<Camping>) -> Unit) {
        val listaCampings = ArrayList<Camping>()
        val url = "https://dadesobertes.gva.es/api/3/action/datastore_search?id=2ddaf823-5da4-4459-aa57-5bfe9f9eb474"

        val request = Request.Builder()
            .url(url)
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36")
            .addHeader("Accept", "application/json;")
            .addHeader("Accept-Language", "es")
            .build()

        val client = OkHttpClient()

        CoroutineScope(Dispatchers.IO).launch {
            val response = client.newCall(request).execute()

            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            val responseBody = response.body?.string()
            val jsonObject = JSONObject(responseBody)
            val jsonResult = jsonObject.getJSONObject("result")
            val records = jsonResult.getJSONArray("records")

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
                val isFavorito = false
                val campingObject = Camping(i+1, nombre, estrellas, direccion, provincia, municipio, web, email, periodo, dias, modalidad, plazas_parcela, plazas_bungalow, libre_acampada, isFavorito)

                listaCampings.add(campingObject)
            }
            withContext(Dispatchers.Main) {
                callback(listaCampings)
            }
        }
    }
}