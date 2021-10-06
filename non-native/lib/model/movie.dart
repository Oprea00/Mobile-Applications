
class Movie{
  int id;
  String title;
  String year;
  String genre;
  String director;
  String stars;
  String favouriteCharacter;
  String memorableQuotes;
  String rating;
  String opinion;

  Movie( this.id, this.title, this.year, this.genre, this.director, this.stars, this.favouriteCharacter, this.memorableQuotes, this.rating, this.opinion);

  Movie.withoutLocation(int id, String title, String year, String genre, String director, String stars, String favouriteCharacter, String memorableQuotes, String rating, String opinion){
    this.id = id;
    this.title = title;
    this.year = year;
    this.genre = genre;
    this.director = director;
    this.stars = stars;
    this.favouriteCharacter = favouriteCharacter;
    this.memorableQuotes = memorableQuotes;
    this.rating = rating;
    this.opinion = opinion;
  }

  int get _id => id;

  @override
  String toString() {
    return 'Movie{id: $id, title: $title, year: $year, }';
  }
}