

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_movies_app/model/movie.dart';
import 'package:flutter_movies_app/service/service.dart';

import 'movie_list.dart';

class EditMovie extends StatefulWidget{
  final Movie movie;
  EditMovie(this.movie);

  @override
  State<StatefulWidget> createState() {
    return EditMovieState(this.movie);
  }
}

class EditMovieState extends State<EditMovie>{
  Movie movie;
  EditMovieState(this.movie);

  MovieService service = MovieService();
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
        child: SingleChildScrollView(

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
                controller: opinionController,
                style: TextStyle(fontSize: 20),
                decoration: InputDecoration(
                  labelText: "Opinion",
                  border: OutlineInputBorder(borderRadius: BorderRadius.circular(5)),
                ),
              ),
              Row(
                children: <Widget>[
                  Expanded(
                    flex: 1,
                    child: Padding(
                      padding: EdgeInsets.all(15),
                      child: OutlineButton(
                        child: Text("Save"),
                        onPressed: (){
                          updateMovie(context);
                        },
                      ),
                    ),
                  ),
                ],
              ),
            ],
          ),
        ),
      ),
      ),
    );
  }

  void updateMovie(BuildContext context) {
    service.updateMovie(Movie(movie.id, titleController.text, yearController.text, genreController.text, directorController.text, starsController.text, favoriteCharacterController.text, quotesController.text, ratingController.text, opinionController.text ));
    Navigator.of(context)
        .push(MaterialPageRoute(builder: (context) => MovieList()));
  }
}