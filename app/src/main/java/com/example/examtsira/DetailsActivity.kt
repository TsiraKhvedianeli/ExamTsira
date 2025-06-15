package com.example.examtsira

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detals_activity)

        val textArtistName = findViewById<TextView>(R.id.textArtistName)
        val textArtworks = findViewById<TextView>(R.id.textArtworks)


        val artistName = intent.getStringExtra("artist_name") ?: "Unknown Artist"
        val artworksDescription = intent.getStringExtra("artist_descriprion") ?: "No artworks available"


        textArtistName.text = artistName
        textArtworks.text = artworksDescription
    }
}
