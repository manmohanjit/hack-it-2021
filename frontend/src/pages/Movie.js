import useFetchMovie from "../hooks/useFetchMovie";
import {Link, useParams} from "react-router-dom";
import useFetchMovieShows from "../hooks/useFetchMovieShows";
import useFetchMovieCategories from "../hooks/useFetchMovieCategories";
import MovieHeading from "../components/MovieHeading";
import LoadingPage from "./LoadingPage";
import ErrorPage from "./ErrorPage";

const Movie = () => {
  const {movieId} = useParams();
  const movie = useFetchMovie(movieId);
  const shows = useFetchMovieShows(movie.data?.id);
  const categories = useFetchMovieCategories(movie.data?.id);

  if(movie.isLoading) {
    return <LoadingPage />
  }
  if(movie.isError) {
    return <ErrorPage error={movie.error} />
  }

  return (
    <div className="container">
      <MovieHeading movie={movie.data} categories={categories.data} />

      <hr className="w-75 mx-auto my-5"/>

      <h4 className="mb-4">Showtimes</h4>
      {shows.data && (
        <div className="row">
          {shows.data.length === 0 && (
            <div className="col-12">
              <p>No items...</p>
            </div>
          )}

          {shows.data.map(show => (
            <div className="col-md-12" key={show.id}>
              <div className="card mb-3">
                <div className="card-body">
                  <h5 className="card-title">{(new Date(show.startsAt)).toString()}</h5>
                  <p className="card-text" dangerouslySetInnerHTML={{__html: show.body}} />
                  <Link to={`/purchase/${show.id}`} className="btn btn-primary">Buy tickets &raquo;</Link>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  )
}

export default Movie;
