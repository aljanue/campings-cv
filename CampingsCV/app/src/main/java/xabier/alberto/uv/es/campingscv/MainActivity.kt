package xabier.alberto.uv.es.campingscv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private val MY_PERMISSIONS_REQUEST_INTERNET = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Comprobar si ya tienes el permiso
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
            != PackageManager.PERMISSION_GRANTED) {
            // Si no tienes el permiso, solicitarlo
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.INTERNET),
                MY_PERMISSIONS_REQUEST_INTERNET)
        }
    }

    fun showCampingDetail(camping: Camping) {
        val bundle = Bundle().apply {
            putSerializable("camping", camping)
        }
        findNavController(R.id.nav_host_fragment).navigate(R.id.navigateToCamping, bundle)
    }
}