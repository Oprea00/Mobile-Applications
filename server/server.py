import json

from flask import Flask, jsonify, request

app = Flask(__name__)


def get_id():
    with open('movies.txt', 'r') as file:
        data = file.read()
        movies = json.loads(data)
        return int(movies[-1]['id']) + 1


movieID = get_id()


@app.route('/')
def hello_world():
    return 'Welcome to Movies | TV Shows App'


@app.route('/movies', methods=['GET'])
def get_movies():
    with open('movies.txt', 'r') as file:
        data = file.read()
        movies = json.loads(data)
        return jsonify(movies)


@app.route('/movies/<id>', methods=['GET'])
def get_movie(id):
    id = int(request.view_args['id'])
    with open('movies.txt', 'r') as file:
        data = file.read()
        movies = json.loads(data)
        for movie in movies:
            if int(movie['id']) == id:
                return jsonify(movie)
        return jsonify({'error': 'movie not found'})


@app.route('/movies', methods=['POST'])
def add_movie():
    global movieID
    movie = json.loads(request.data)
    movie['id'] = str(movieID)
    movieID +=1
    with open('movies.txt', 'r') as file:
        data = file.read()
    if not data:
        movies = [movie]
    else:
        movies = json.loads(data)
        movies.append(movie)
    with open('movies.txt', 'w') as file:
        file.write(json.dumps(movies, indent=2))
    movie['id'] = int(movie['id'])
    return jsonify(movie)


@app.route('/movies', methods=['PUT'])
def update_movie():
    movie = json.loads(request.data)
    updated_movies = []
    with open('movies.txt', 'r') as file:
        data = file.read()
        movies = json.loads(data)
    for m in movies:
        if int(m['id']) == movie['id']:
            m['title'] = movie['title']
            m['year'] = movie['year']
            m['director'] = movie['director']
            m['genre'] = movie['genre']
            m['stars'] = movie['stars']
            m['favourite'] = movie['favourite']
            m['quotes'] = movie['quotes']
            m['rating'] = movie['rating']
            m['opinion'] = movie['opinion']
        updated_movies.append(m)
    with open('movies.txt', 'w') as file:
        file.write(json.dumps(updated_movies, indent=2))
    return jsonify(movie)


@app.route('/movies/<id>', methods=['DELETE'])
def delete_movie(id):
    id = int(request.view_args['id'])
    updated_movies = []
    with open('movies.txt', 'r') as file:
        data = file.read();
        movies = json.loads(data)
        for movie in movies:
            if int(movie['id']) == id:
                continue
            updated_movies.append(movie)
    with open('movies.txt', 'w') as file:
        file.write(json.dumps(updated_movies, indent=2))
    return jsonify()


if __name__ == '__main__':
    app.run()
