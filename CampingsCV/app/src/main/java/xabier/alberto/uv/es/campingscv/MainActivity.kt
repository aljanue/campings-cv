package xabier.alberto.uv.es.campingscv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Cargar el MainFragment en el contenedor
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CampingList())
                .commitNow()
        }
    }

    fun showCampingDetail(camping: Camping) {
        val fragment = CampingInfo().apply {
            arguments = Bundle().apply {
                putParcelable("camping", camping)
            }
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}