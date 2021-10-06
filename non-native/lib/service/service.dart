
import 'package:flutter_movies_app/model/movie.dart';

class MovieService{
  static List<Movie> movies = [];

  MovieService();

  addMovie(Movie movie){
    int idNext = nextId() + 1;
    movie.id = idNext;
    movies.add(movie);
  }

  int nextId(){
    int id = 0;
    for (Movie movie in movies){
      if(movie.id > id){
        id = movie.id;
      }
    }
    return id;
  }

  bool deleteMovie(int id){
    int position = -1;
    for (var index= 0; index < movies.length; index++){
      if(movies[index].id == id){
        position = index;
      }
    }
    if (position > -1){
      movies.removeAt(position);
      return true;
    }
    return false;
  }

  int updateMovie(Movie movie){
    for (Movie m in movies)
      if (m.id == movie.id){
        m.title = movie.title;
        m.year = movie.year;
        m.genre = movie.genre;
        m.director = movie.director;
        m.stars = movie.stars;
        m.favouriteCharacter = movie.favouriteCharacter;
        m.memorableQuotes = movie.memorableQuotes;
        m.rating = movie.rating;
        m.opinion = movie.opinion;
        return 1;
      }
    return 0;
  }

  List<Movie> getMovies(){
    return movies;
  }
}