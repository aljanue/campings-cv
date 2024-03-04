package xabier.alberto.uv.es.campingscv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.findNavController

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    fun showCampingDetail(camping: Camping) {
        val bundle = Bundle().apply {
            putSerializable("camping", camping)
        }
        findNavController(R.id.nav_host_fragment).navigate(R.id.navigateToCamping, bundle)
    }
}