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

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_INTERNET -> {
                // Si la solicitud es cancelada, el array de resultados estará vacío.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permiso concedido
                } else {
                    // Permiso denegado
                }
                return
            }
            // Comprobar otros permisos si los hay
        }
    }

    fun showCampingDetail(camping: Camping) {
        val bundle = Bundle().apply {
            putSerializable("camping", camping)
        }
        findNavController(R.id.nav_host_fragment).navigate(R.id.navigateToCamping, bundle)
    }
}