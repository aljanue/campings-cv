package xabier.alberto.uv.es.campingscv

import android.app.SearchManager
import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject
import java.io.InputStream

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("MainActivity", "ESTOY EN onCreate")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val campings = getData()
        val rv: RecyclerView = findViewById(R.id.rv)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = MyAdapter(campings)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val rv: RecyclerView = findViewById(R.id.rv)
        val adapter = rv.adapter as MyAdapter
        when (item.itemId) {
            R.id.order_by_stars -> {
                adapter.data = adapter.data.sortedByDescending {
                    try {
                        it.estrellas.replace("e", "").toInt()
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
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        Log.d("MenuCreado", "ESTOY EN onCreateOptionsMenu ANTES DE INFLAR EL MENU")
        menuInflater.inflate(R.menu.options_menu, menu)
        Log.d("MenuCreado", "ESTOY EN onCreateOptionsMenu DESPUES DE INFLAR EL MENU")

        return true
    }

    fun readJsonFromRaw(resources: Resources, rawResourceId: Int): String {
        val inputStream: InputStream = resources.openRawResource(rawResourceId)
        val buffer = ByteArray(inputStream.available())
        inputStream.read(buffer)
        inputStream.close()
        return String(buffer, Charsets.UTF_8)
    }
    private fun getData(): ArrayList<Camping>{
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
            val estrellas = camping.getString("Cod. Categoria")
            val direccion = camping.getString("Direccion")
            val provincia = camping.getString("Provincia")
            val municipio = camping.getString("Municipio")
            val web = camping.getString("Web")
            val email = camping.getString("Email")
            val periodo = camping.getString("Periodo")
            val dias = camping.getString("Días Periodo")
            val modalidad = camping.getString("Modalidad")
            val plazas_parcela = camping.getString("Plazas Parcela")
            val plazas_bungalow = camping.getString("Plaza Bungalows")
            val libre_acampada = camping.getString("Plazas Libre Acampada")
            val campingObject = Camping(nombre, estrellas, direccion, provincia, municipio, web, email, periodo, dias, modalidad, plazas_parcela, plazas_bungalow, libre_acampada)

            listaCampings.add(campingObject)
        }
       // object, and insert it into the campings ArrayList.
        return listaCampings
    }
}