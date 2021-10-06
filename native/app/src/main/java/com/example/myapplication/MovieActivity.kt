package com.example.myapplication

import android.content.ContentValues
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_movie.*
import org.jetbrains.anko.toast

class MovieActivity: AppCompatActivity() {
    var id=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            id = bundle.getInt("MainActId", 0)
            if (id != 0) {
                editTitle.setText(bundle.getString("MainActTitle"))
                editYear.setText(bundle.getString("MainActYear"))
                editGenre.setText(bundle.getString("MainActGenre"))
                editDirector.setText(bundle.getString("MainActDirector"))
                editStars.setText(bundle.getString("MainActStars"))
                editFavCharacter.setText(bundle.getString("MainActFavCharacter"))
                editMemQuotes.setText(bundle.getString("MainActMemQuotes"))
                editRating.setText(bundle.getString("MainActRating"))
                editOpinion.setText(bundle.getString("MainActOpinion"))
            }
    }

    buttonAdd.setOnClickListener{
        val dbManager = MovieDBManager(this)
        val values = ContentValues()

        values.put("Title", editTitle.text.toString())
        values.put("Year", editYear.text.toString())
        values.put("Genre", editGenre.text.toString())
        values.put("Director", editDirector.text.toString())
        values.put("Stars", editStars.text.toString())
        values.put("FavoriteCharacter", editFavCharacter.text.toString())
        values.put("MemorableQuotes", editMemQuotes.text.toString())
        values.put("Rating", editRating.text.toString())
        values.put("Opinion", editOpinion.text.toString())

        if (id == 0){
            val mID = dbManager.insert(values)
            if(mID > 0){
                toast("Add movie successfully")
                finish()
            }else{
                toast("Fail to add movie")
            }
        }else{
            val selectionArs = arrayOf(id.toString())
            val mID = dbManager.update(values, "Id=?", selectionArs)
            if(mID > 0){
                toast("Update movie successfully")
                finish()
            }else{
                toast("Fail to update movie")
            }
        }
    }
    }
}