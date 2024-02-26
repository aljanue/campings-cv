package xabier.alberto.uv.es.campingscv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(var data: List<Camping>, val clickListener: (Any) -> Unit) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    class MyViewHolder(val row: View) : RecyclerView.ViewHolder(row) {
        val nombre = row.findViewById<TextView>(R.id.nombre)
        val estrellas = row.findViewById<TextView>(R.id.estrellas)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.camping_item,
            parent, false)
        return MyViewHolder(layout)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.nombre.text = data.get(position).nombre + ", " + data.get(position).municipio
        holder.estrellas.text = data.get(position).estrellas

        holder.row.setOnClickListener {
            clickListener(data[position])
        }
    }
    override fun getItemCount(): Int = data.size
}