package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_movie.*
import kotlinx.android.synthetic.main.movie_details.*

class MovieDetailsActivity: AppCompatActivity() {
    var id = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_details)

        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            id = bundle.getInt("MainActId", 0)
            if (id != 0) {

                viewTitle.text = bundle.getString("MainActTitle")
                viewYear.text = bundle.getString("MainActYear")
                viewGenre.text = bundle.getString("MainActGenre")
                viewDirector.text = bundle.getString("MainActDirector")
                viewStars.text = bundle.getString("MainActStars")
                viewFavCharacter.text = bundle.getString("MainActFavCharacter")
                viewMemQuotes.text = bundle.getString("MainActMemQuotes")
                viewRating.text = bundle.getString("MainActRating")
                viewOpinion.text = bundle.getString("MainActOpinion")

            }
        }
    }
}