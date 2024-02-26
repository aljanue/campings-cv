package xabier.alberto.uv.es.campingscv

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class CampingInfo : Fragment() {

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.camping_info, container, false)

        // Aquí puedes obtener los datos del camping del Bundle y mostrarlos en la vista
        val camping = arguments?.getParcelable<Camping>("camping")

        // TODO: Mostrar los datos del camping en la vista
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

        nombreID.text = camping?.nombre
        estrellasID.text = camping?.estrellas
        direccionID.text = Html.fromHtml("<b>DIRECCIÓN</b><br><br>" + camping?.direccion, Html.FROM_HTML_MODE_COMPACT)
        provinciaID.text = Html.fromHtml("<b>PROVINCIA</b><br><br>" + camping?.provincia, Html.FROM_HTML_MODE_COMPACT)
        municipioID.text = Html.fromHtml("<b>MUNICIPIO</b><br><br>" + camping?.municipio, Html.FROM_HTML_MODE_COMPACT)
        webID.text = Html.fromHtml("<b>WEB</b><br>" + camping?.web, Html.FROM_HTML_MODE_COMPACT)
        emailID.text = Html.fromHtml("<b>EMAIL</b><br>" + camping?.email, Html.FROM_HTML_MODE_COMPACT)
        periodoID.text = Html.fromHtml("<b>PERIODO</b><br><br>" + camping?.periodo, Html.FROM_HTML_MODE_COMPACT)
        diasID.text = Html.fromHtml("<b>DÍAS</b><br><br>" + camping?.dias, Html.FROM_HTML_MODE_COMPACT)
        modalidadID.text = Html.fromHtml("<b>" + camping?.modalidad + "<b>", Html.FROM_HTML_MODE_COMPACT)
        plazas_parcelaID.text = camping?.plazas_parcela
        plazas_bungalowID.text = camping?.plazas_bungalow
        libre_acampadaID.text = camping?.libre_acampada


        return view
    }
}