package com.example.examtsira

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class ArtistAdapter(
    private val serialList: MutableList<Artist>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<ArtistAdapter.SerialViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(artist: Artist)
    }

    inner class SerialViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameText: TextView = itemView.findViewById(R.id.textView)
        private val periodYearText: TextView = itemView.findViewById(R.id.textView2)
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(artist: Artist) {
            nameText.text = artist.name ?: "No Title"
            periodYearText.text = artist.period ?: "Unknown"
            Picasso.get()
                .load(artist.imageUrl)
                .into(imageView)


            itemView.setOnClickListener {
                itemClickListener.onItemClick(artist)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SerialViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_artists, parent, false)
        return SerialViewHolder(view)
    }

    override fun onBindViewHolder(holder: SerialViewHolder, position: Int) {
        holder.bind(serialList[position])
    }

    override fun getItemCount(): Int = serialList.size
}
