
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_movies_app/model/movie.dart';

class MovieDetails extends StatefulWidget{
  final Movie movie;
  MovieDetails(this.movie);

  @override
  State<StatefulWidget> createState() {
    return MovieDetailsState(this.movie);
  }

}

class MovieDetailsState extends State<MovieDetails> {
  Movie movie;
  MovieDetailsState(this.movie);

  TextEditingController titleController = TextEditingController();
  TextEditingController yearController = TextEditingController();
  TextEditingController genreController = TextEditingController();
  TextEditingController directorController = TextEditingController();
  TextEditingController starsController = TextEditingController();
  TextEditingController favoriteCharacterController = TextEditingController();
  TextEditingController quotesController = TextEditingController();
  TextEditingController ratingController = TextEditingController();
  TextEditingController opinionController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    titleController.text = movie.title;
    yearController.text = movie.year;
    genreController.text = movie.genre;
    directorController.text = movie.director;
    starsController.text = movie.stars;
    favoriteCharacterController.text = movie.favouriteCharacter;
    quotesController.text = movie.memorableQuotes;
    ratingController.text = movie.rating;
    opinionController.text = movie.opinion;

    return Scaffold(
      body:
        SafeArea(
          child: Padding(
            padding: EdgeInsets.all(10),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.center,
              children: <Widget> [
                Text(
                  "Movie Details",
                  style: TextStyle(fontSize: 26, fontWeight: FontWeight.w500),
                ),
                Padding(
                 padding: EdgeInsets.only(bottom: 5.0),
                ),
                TextFormField(
                  readOnly: true,
                  controller: titleController,
                  style: TextStyle(fontSize: 20),
                  decoration: InputDecoration(
                    labelText: "Title",
                    border: OutlineInputBorder(borderRadius: BorderRadius.circular(5)),
                  ),
                ),
                Padding(
                  padding: EdgeInsets.only(bottom: 5.0),
                ),
                TextFormField(
                  readOnly: true,
                  controller: yearController,
                  style: TextStyle(fontSize: 20),
                  decoration: InputDecoration(
                    labelText: "Year",
                    border: OutlineInputBorder(borderRadius: BorderRadius.circular(5)),
                  ),
                ),
                Padding(
                  padding: EdgeInsets.only(bottom: 5.0),
                ),
                TextFormField(
                  readOnly: true,
                  controller: genreController,
                  style: TextStyle(fontSize: 20),
                  decoration: InputDecoration(
                    labelText: "Genre",
                    border: OutlineInputBorder(borderRadius: BorderRadius.circular(5)),
                  ),
                ),
                Padding(
                  padding: EdgeInsets.only(bottom: 5.0),
                ),
                TextFormField(
                  readOnly: true,
                  controller: directorController,
                  style: TextStyle(fontSize: 20),
                  decoration: InputDecoration(
                    labelText: "Director",
                    border: OutlineInputBorder(borderRadius: BorderRadius.circular(5)),
                  ),
                ),
                Padding(
                  padding: EdgeInsets.only(bottom: 5.0),
                ),
                TextFormField(
                  readOnly: true,
                  controller: starsController,
                  style: TextStyle(fontSize: 20),
                  decoration: InputDecoration(
                    labelText: "Stars",
                    border: OutlineInputBorder(borderRadius: BorderRadius.circular(5)),
                  ),
                ),
                Padding(
                  padding: EdgeInsets.only(bottom: 5.0),
                ),
                TextFormField(
                  readOnly: true,
                  controller: favoriteCharacterController,
                  style: TextStyle(fontSize: 20),
                  decoration: InputDecoration(
                    labelText: "Favorite Character",
                    border: OutlineInputBorder(borderRadius: BorderRadius.circular(5)),
                  ),
                ),
                Padding(
                  padding: EdgeInsets.only(bottom: 5.0),
                ),
                TextFormField(
                  readOnly: true,
                  controller: quotesController,
                  style: TextStyle(fontSize: 20),
                  decoration: InputDecoration(
                    labelText: "Memorable Quotes",
                    border: OutlineInputBorder(borderRadius: BorderRadius.circular(5)),
                  ),
                ),
                Padding(
                  padding: EdgeInsets.only(bottom: 5.0),
                ),
                TextFormField(
                  readOnly: true,
                  controller: ratingController,
                  style: TextStyle(fontSize: 20),
                  decoration: InputDecoration(
                    labelText: "Rating",
                    border: OutlineInputBorder(borderRadius: BorderRadius.circular(5)),
                  ),
                ),
                Padding(
                  padding: EdgeInsets.only(bottom: 5.0),
                ),
                TextFormField(
                  readOnly: true,
                  controller: opinionController,
                  style: TextStyle(fontSize: 20),
                  decoration: InputDecoration(
                    labelText: "Opinion",
                    border: OutlineInputBorder(borderRadius: BorderRadius.circular(5)),
                  ),
                ),
              ],
            ),
          ),
        ),
    );
  }

}