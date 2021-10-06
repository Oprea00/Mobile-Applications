package com.example.myapplication

import android.provider.BaseColumns

object MovieContract {
    const val DB_NAME = "MovieDB"
    const val DB_VERSION = 1

    object MovieEntry : BaseColumns{
        const val DB_TABLE = "Movie"
        const val COLUMN_ID = "Id"
        const val COLUMN_TITLE = "Title"
        const val COLUMN_YEAR = "Year"
        const val COLUMN_GENRE = "Genre"
        const val COLUMN_DIRECTOR = "Director"
        const val COLUMN_STARS = "Stars"
        const val COLUMN_FAVORTITECHARACTER = "FavoriteCharacter"
        const val COLUMN_MEMORABLEQUOTES = "MemorableQuotes"
        const val COLUMN_RATING = "Rating"
        const val COLUMN_OPINION = "Opinion"
    }
}