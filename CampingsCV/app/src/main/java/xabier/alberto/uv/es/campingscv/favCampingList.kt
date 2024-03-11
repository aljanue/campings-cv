package xabier.alberto.uv.es.campingscv

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import xabier.alberto.uv.es.campingscv.database.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class favCampingList : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainFragment", "ESTOY EN onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fav_camping_list, container, false)

        // Obtén una referencia a tu base de datos
        val db = Room.databaseBuilder(requireContext(), AppDatabase::class.java, "database").build()
        CoroutineScope(Dispatchers.IO).launch {
            val userDao = db.userDao()

            val campings = userDao.getAll()

            withContext(Dispatchers.Main) {
                val rv: RecyclerView = view.findViewById(R.id.rv)
                rv.layoutManager = LinearLayoutManager(context)
                rv.adapter = favAdapter(campings) { camping ->
                    (requireActivity() as MainActivity).showCampingDetail(camping as Camping)
                }
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCampingList(view)
    }

    private fun setupCampingList(view: View) {
        // Obtén una referencia a tu base de datos
        val db = Room.databaseBuilder(requireContext(), AppDatabase::class.java, "database").build()
        CoroutineScope(Dispatchers.IO).launch {
            val userDao = db.userDao()

            val campings = userDao.getAll()

            withContext(Dispatchers.Main) {
                val rv: RecyclerView = view.findViewById(R.id.rv)
                rv.layoutManager = LinearLayoutManager(context)
                rv.adapter = favAdapter(campings) { camping ->
                    (requireActivity() as MainActivity).showCampingDetail(camping as Camping)
                }
            }
        }
    }
}