import useFetchMovies from "../hooks/useFetchMovies";
import {Link} from "react-router-dom";

const Home = () => {
  const movies = useFetchMovies();

  return (
    <div className="container">
      <h1 className="mb-5">Latest movies</h1>

      {movies.data && (
        <div className="row">
          {movies.data.length === 0 && (
            <div className="col-12"><p>No items...</p></div>
          )}

          {movies.data.map(movie => (
            <div className="col-md-4" key={movie.id}>
              <div className="card">
                <div className="card-body">
                  <h5 className="card-title">{movie.title}</h5>
                  <p className="card-text text-limit" dangerouslySetInnerHTML={{__html: movie.body}} />
                  <Link to={`/movies/${movie.id}`} className="btn btn-primary">View showtimes &raquo;</Link>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  )
}

export default Home;
