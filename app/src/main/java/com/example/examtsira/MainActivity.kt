package com.example.examtsira

import android.content.Intent
import androidx.appcompat.widget.Toolbar
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), ArtistAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var artistAdapter: ArtistAdapter
    private lateinit var progressBar: ProgressBar

    private val artistList = mutableListOf<Artist>()
    private var currentPage = 1
    private var totalPages = 1

    private val client = ResClient()
    override fun onItemClick(artist: Artist) {
        Log.d("MainActivity", "Clicked artist: ${artist.name}")
        Toast.makeText(this, "Clicked: ${artist.name}", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("artist_name", artist.name)
        intent.putExtra("artist_period", artist.period)
        intent.putExtra("artist_image_url", artist.imageUrl)
        intent.putExtra("artist_descriprion",artist.description)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)

        artistAdapter = ArtistAdapter(artistList, this)
        recyclerView.adapter = artistAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)


        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            val backgroundPaint = Paint().apply { color = Color.BLACK }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                artistList.removeAt(position)
                artistAdapter.notifyItemRemoved(position)
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    val itemView = viewHolder.itemView
                    if (dX > 0) {
                        c.drawRect(
                            itemView.left.toFloat(),
                            itemView.top.toFloat(),
                            itemView.left + dX,
                            itemView.bottom.toFloat(),
                            backgroundPaint
                        )
                    } else {
                        c.drawRect(
                            itemView.right + dX,
                            itemView.top.toFloat(),
                            itemView.right.toFloat(),
                            itemView.bottom.toFloat(),
                            backgroundPaint
                        )
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        client.init()
        loadArtists(currentPage)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount &&
                    firstVisibleItemPosition >= 0 &&
                    currentPage < totalPages) {
                    currentPage++
                    loadArtists(currentPage)
                }
            }
        })
    }

    private fun loadArtists(page: Int) {
        progressBar.visibility = View.VISIBLE

        val service = client.getReqResService()
        service.getSerial(page).enqueue(object : Callback<ReqresObj<List<Artist>>> {
            override fun onResponse(
                call: Call<ReqresObj<List<Artist>>>,
                response: Response<ReqresObj<List<Artist>>>
            ) {
                progressBar.visibility = View.GONE

                if (response.isSuccessful) {
                    response.body()?.let {
                        totalPages = it.totalPages ?: 1
                        it.data?.let { newArtists ->
                            artistList.addAll(newArtists)
                            artistAdapter.notifyDataSetChanged()
                        }
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Failed to load data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ReqresObj<List<Artist>>>, t: Throwable) {
                progressBar.visibility = View.GONE
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("MainActivity", "Error fetching artists", t)
            }
        })
    }


}
