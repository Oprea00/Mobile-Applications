
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_movies_app/model/movie.dart';
import 'package:flutter_movies_app/screens/movie_list.dart';

import 'service/service.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  MovieService movieService = MovieService();

  @override
  Widget build(BuildContext context) {
    addInitialMovies();
    return MaterialApp(
      title: 'Flutter Movies & TV Shows ',
      theme: ThemeData.dark(
        //primarySwatch: Colors.green,
      ),
      home: MovieList(),
    );
  }

  addInitialMovies(){
    movieService.addMovie(Movie(0,"Tenet","2020","Action","Nolan","Robert Pattinson","tipu negru","","10","cel mai complex film al lui nolan"));
    movieService.addMovie(Movie(0,"Barbarians","2020","Action","","","fle","","okish","fain ca vorbesc in latina"));
    movieService.addMovie(Movie(0,"Castlevania","","Anime","","Dracula","Dracula, Isaac, Belmond","","10","super cartoon"));
    movieService.addMovie(Movie(0,"Fight Club","97","Action","Fincher","Brad Pitt","Will","You do not talk about Fight Club-1'st rule","9","blana"));
  }
}








