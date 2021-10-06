package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast


class MainActivity : AppCompatActivity() {
    private var listMovies = ArrayList<Movie>()
    private val dbManager = MovieDBManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lvMovies.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                toast("Click on ${listMovies[position].title}")
                showMovie(listMovies[position])
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item != null){
            when(item.itemId){
                R.id.addMovie ->{
                    val intent = Intent(this, MovieActivity::class.java)
                    startActivity(intent)
                }
            }
        }
        return super.onOptionsItemSelected(item!!)
    }


    override fun onResume() {
        super.onResume()
        loadQueryAll()
    }

    override fun onDestroy() {
        dbManager.close()
        super.onDestroy()
    }

    fun loadQueryAll(){
        val cursor = dbManager.queryAll()
        listMovies.clear()
        if (cursor.moveToFirst()){
            do{

                val id = cursor.getInt(cursor.getColumnIndex("Id"))
                val title = cursor.getString(cursor.getColumnIndex("Title"))
                val year = cursor.getString(cursor.getColumnIndex("Year"))
                val genre = cursor.getString(cursor.getColumnIndex("Genre"))
                val director = cursor.getString(cursor.getColumnIndex("Director"))
                val stars = cursor.getString(cursor.getColumnIndex("Stars"))
                val favouriteCharacter = cursor.getString(cursor.getColumnIndex("FavoriteCharacter"))
                val memorableQuotes = cursor.getString(cursor.getColumnIndex("MemorableQuotes"))
                val rating = cursor.getString(cursor.getColumnIndex("Rating"))
                val opinion = cursor.getString(cursor.getColumnIndex("Opinion"))

                listMovies.add(Movie(id, title, year, genre, director, stars, favouriteCharacter, memorableQuotes, rating, opinion))
            }while (cursor.moveToNext())
        }
        val moviesAdapter = MoviesAdapter(this, listMovies)
        lvMovies.adapter = moviesAdapter
    }

    inner class MoviesAdapter(context: Context, private var moviesList: ArrayList<Movie>) : BaseAdapter(){
        private var context: Context? = context
        @RequiresApi(Build.VERSION_CODES.M)
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
            val view: View?
            val viewHolder: ViewHolder
            if(convertView == null){
                view = layoutInflater.inflate(R.layout.movie,parent,false)
                viewHolder = ViewHolder(view)
                view.tag = viewHolder
                logd("set Tag for ViewHolder, position: $position")
            }else {
                view = convertView
                viewHolder = view.tag as ViewHolder
            }
            val mMovie = moviesList[position]
            viewHolder.textViewTitle.text = mMovie.title
            viewHolder.imageViewEdit.setOnClickListener{
                if (isOnline(this@MainActivity)) {
                    updateMovie(mMovie)
                }
                else{
                    val builder = AlertDialog.Builder(this@MainActivity)
                    builder.setMessage("This action is not available")
                        .setCancelable(true)
                    val alert = builder.create()
                    alert.show()
                }
            }
            viewHolder.imageViewDelete.setOnClickListener {
                if (isOnline(this@MainActivity)){
                    val builder = AlertDialog.Builder(this@MainActivity)
                    builder.setMessage("Are you sure you want to delete?")
                        .setCancelable(false)
                        .setPositiveButton("Yes"){ dialog, id ->
                            val dbManager = MovieDBManager(this.context!!)
                            val selectionArgs = arrayOf(mMovie.id.toString())
                            dbManager.delete("Id=?", selectionArgs)
                            loadQueryAll()
                        }
                        .setNegativeButton("No") {dialog, id ->
                            dialog.dismiss()
                        }
                    val alert = builder.create()
                    alert.show()
                }else {
                    val builder = AlertDialog.Builder(this@MainActivity)
                    builder.setMessage("This action is not available")
                        .setCancelable(true)
                    val alert = builder.create()
                    alert.show()
                }
            }
            return view
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getItem(position: Int): Any {
            return moviesList[position]
        }

        override fun getCount(): Int {
            return moviesList.size
        }
    }

    private fun updateMovie(movie: Movie){
        val intent = Intent(this, MovieActivity::class.java)

        intent.putExtra("MainActId", movie.id)
        intent.putExtra("MainActTitle", movie.title)
        intent.putExtra("MainActYear", movie.year)
        intent.putExtra("MainActGenre", movie.genre)
        intent.putExtra("MainActDirector", movie.director)
        intent.putExtra("MainActStars", movie.stars)
        intent.putExtra("MainActFavCharacter", movie.favoriteCharacter)
        intent.putExtra("MainActMemQuotes", movie.memorableQuotes)
        intent.putExtra("MainActRating", movie.rating)
        intent.putExtra("MainActOpinion", movie.opinion)

        startActivity(intent)
    }

    private fun showMovie(movie: Movie){
        val intent = Intent(this,MovieDetailsActivity::class.java)

        intent.putExtra("MainActId", movie.id)
        intent.putExtra("MainActTitle", movie.title)
        intent.putExtra("MainActYear", movie.year)
        intent.putExtra("MainActGenre", movie.genre)
        intent.putExtra("MainActDirector", movie.director)
        intent.putExtra("MainActStars", movie.stars)
        intent.putExtra("MainActFavCharacter", movie.favoriteCharacter)
        intent.putExtra("MainActMemQuotes", movie.memorableQuotes)
        intent.putExtra("MainActRating", movie.rating)
        intent.putExtra("MainActOpinion", movie.opinion)

        startActivity(intent)
    }

    private class ViewHolder(view: View?){
        val textViewTitle: TextView = view?.findViewById(R.id.tvTitle) as TextView
        val imageViewEdit: ImageView = view?.findViewById(R.id.ivEdit) as ImageView
        val imageViewDelete: ImageView = view?.findViewById(R.id.ivDelete) as ImageView
    }

    //@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(Build.VERSION_CODES.M)
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
        return false
    }
}