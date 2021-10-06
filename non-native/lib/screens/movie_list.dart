

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_movies_app/model/movie.dart';
import 'package:flutter_movies_app/screens/edit_movie.dart';
import 'package:flutter_movies_app/screens/movie_details.dart';
import 'package:flutter_movies_app/service/service.dart';

import 'add_movie.dart';

class MovieList extends StatefulWidget{
  MovieService movieService = MovieService();
  @override
  State<StatefulWidget> createState() {
    return MovieListState();
  }
}

class MovieListState  extends State<MovieList>{
  MovieService movieService = MovieService();
  List<Movie> movieList = List<Movie>();
  int count = 0;


  @override
  Widget build(BuildContext context) {
      loadMovies();
    return Scaffold(
      appBar: AppBar(
          title: Text('Flutter Movies & TV Shows ')
      ),
      body: getMoviesListView(),
      floatingActionButton: addMovieButton(),
    );
  }

  addMovieButton() {
    return Builder(
      builder: (context){
        return FloatingActionButton(
          child: Icon(Icons.add),
          onPressed: () {
            Navigator.of(context)
                .push(MaterialPageRoute(builder: (context) => AddMovie()));
          },
        );
      },
    );
  }

  loadMovies(){
    var movies = movieService.getMovies();
    movieList.clear();
    if (movies != null){
      for (Movie movie in movies){
        movieList.add(movie);
      }
    }
    setState(() {
      this.movieList = movieList;
      this.count = movieList.length;
    });
  }


  ListView getMoviesListView() {
    return ListView.builder(
      itemCount: count,
      itemBuilder: (BuildContext context, int position){
        return Card(
            color: Colors.indigo,
            elevation: 2.0,
            child: ListTile(
              title: Text(this.movieList[position].title,
                style: TextStyle(fontSize: 26, fontWeight: FontWeight.bold)
                ),
              trailing: Row(
              mainAxisSize: MainAxisSize.min,
              children: <Widget>[
                GestureDetector(
                  child: Icon(Icons.delete,color: Colors.red,),
                  onTap: (){
                    //deleteMovie(context, movieList[position]);
                    showDeleteDialog(context, movieList[position]);
                  },
                ),
                GestureDetector(
                  child: Icon(Icons.edit, color: Colors.black45,),
                  onTap: (){
                    Navigator.of(context)
                        .push(MaterialPageRoute(builder: (context) => EditMovie(this.movieList[position])));
                  },
                ),
              ],
            ),
              onTap: (){
                debugPrint("List Tile Tapped");
                Navigator.of(context)
                    .push(MaterialPageRoute(builder: (context) => MovieDetails(this.movieList[position])));
              },
            )
        );
      },
    );
  }

  void deleteMovie(BuildContext context, Movie movie) {
    //showDeleteDialog(context, movie);
    bool result = movieService.deleteMovie(movie.id);
    if (result){
      showSnackBar(context, "Movie deleted successfully");
      loadMovies();
    }
  }

  void showDeleteDialog(BuildContext context, Movie movie){
    AlertDialog alertDialog = AlertDialog(
      title: Text('Are you sure you want to delete?'),
      content: Text("This will permanently delete the item"),
      actions: [
        FlatButton(
          textColor: Color(0xFF6200EE),
          onPressed: () {
            Navigator.pop(context, true);
          },
          child: Text('NO'),
        ),
        FlatButton(
          textColor: Color(0xFF6200EE),
          onPressed: (){
            deleteMovie(context, movie);
            Navigator.pop(context, true);
          },
          child: Text('YES'),
        ),
      ],
    );

    showDialog(context: context, builder: (_) => alertDialog);
  }

  void showSnackBar(BuildContext context, String message) {
    final snackBar = SnackBar(content: Text(message));
    Scaffold.of(context).showSnackBar(snackBar);
  }

}