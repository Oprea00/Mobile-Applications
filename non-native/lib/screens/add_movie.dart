
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_movies_app/model/movie.dart';
import 'package:flutter_movies_app/screens/movie_list.dart';
import 'package:flutter_movies_app/service/service.dart';

class AddMovie extends StatelessWidget{
  final _formKey = GlobalKey<FormState>();
  MovieService service = MovieService();
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Add new movie"),
      ),
      body:
        SafeArea(
          child: SingleChildScrollView(
            child: Form(
              key: _formKey,
              child: Padding(
                padding: EdgeInsets.all(10),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: <Widget>[
                    Text(
                      "Movie Details",
                      style: TextStyle(fontSize: 26, fontWeight: FontWeight.w500),
                    ),
                    getDivider(),
                    title(),
                    getDivider(),
                    year(),
                    getDivider(),
                    genre(),
                    getDivider(),
                    director(),
                    getDivider(),
                    stars(),
                    getDivider(),
                    favoriteCharacter(),
                    getDivider(),
                    memorableQuotes(),
                    getDivider(),
                    rating(),
                    getDivider(),
                    opinion(),
                    getDivider(),
                    Row(
                      children: <Widget>[
                        Expanded(
                          flex: 1,
                          child: Padding(
                            padding: EdgeInsets.all(15),
                            child: OutlineButton(
                              child: Text("Save"),
                              onPressed: (){
                                createMovie(context);
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
        ),
    );

  }

  getDivider() {
    return Padding(
      padding: EdgeInsets.only(bottom: 5.0),
    );
  }

  final TextEditingController titleController = TextEditingController();
  title() {
    return TextFormField(
      controller: titleController,
      style: TextStyle(fontSize: 20),
      decoration: InputDecoration(
        hintText: "Movie title",
        border: OutlineInputBorder(borderRadius: BorderRadius.circular(5)),
      ),
    );
  }

  final TextEditingController yearController = TextEditingController();
  year() {
    return TextFormField(
      controller: yearController,
      style: TextStyle(fontSize: 20),
      decoration: InputDecoration(
        hintText: "Year of release",
        border: OutlineInputBorder(borderRadius: BorderRadius.circular(5)),
      ),
    );
  }

  final TextEditingController genreController = TextEditingController();
  genre() {
    return TextFormField(
      controller: genreController,
      style: TextStyle(fontSize: 20),
      decoration: InputDecoration(
        hintText: "Movie genre",
        border: OutlineInputBorder(borderRadius: BorderRadius.circular(5)),
      ),
    );
  }

  final TextEditingController directorController = TextEditingController();
  director() {
    return TextFormField(
      controller: directorController,
      style: TextStyle(fontSize: 20),
      decoration: InputDecoration(
        hintText: "Movie director",
        border: OutlineInputBorder(borderRadius: BorderRadius.circular(5)),
      ),
    );
  }

  final TextEditingController starsController = TextEditingController();
  stars() {
    return TextFormField(
      controller: starsController,
      style: TextStyle(fontSize: 20),
      decoration: InputDecoration(
        hintText: "Movie stars",
        border: OutlineInputBorder(borderRadius: BorderRadius.circular(5)),
      ),
    );
  }

  final TextEditingController favoriteCharacterController = TextEditingController();
  favoriteCharacter() {
    return TextFormField(
      controller: favoriteCharacterController,
      style: TextStyle(fontSize: 20),
      decoration: InputDecoration(
        hintText: "Your favorite character",
        border: OutlineInputBorder(borderRadius: BorderRadius.circular(5)),
      ),
    );
  }

  final TextEditingController quotesController = TextEditingController();
  memorableQuotes() {
    return TextFormField(
      controller: quotesController,
      style: TextStyle(fontSize: 20),
      decoration: InputDecoration(
        hintText: "Some memorable quotes",
        border: OutlineInputBorder(borderRadius: BorderRadius.circular(5)),
      ),
    );
  }

  final TextEditingController ratingController = TextEditingController();
  rating() {
    return TextFormField(
      controller: ratingController,
      style: TextStyle(fontSize: 20),
      decoration: InputDecoration(
        hintText: "Movie rating",
        border: OutlineInputBorder(borderRadius: BorderRadius.circular(5)),
      ),
    );
  }

  final TextEditingController opinionController = TextEditingController();
  opinion() {
    return TextFormField(
      controller: opinionController,
      style: TextStyle(fontSize: 20),
      decoration: InputDecoration(
        hintText: "Your opinion about the movie",
        border: OutlineInputBorder(borderRadius: BorderRadius.circular(5)),
      ),
    );
  }


  void createMovie(BuildContext context) {
    service.addMovie(Movie(0, titleController.text, yearController.text, genreController.text, directorController.text, starsController.text, favoriteCharacterController.text, quotesController.text, ratingController.text, opinionController.text ));
    Navigator.of(context)
        .push(MaterialPageRoute(builder: (context) => MovieList()));
  }
    
}