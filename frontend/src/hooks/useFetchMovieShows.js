import {useQuery} from "react-query";
import api from "../services/api";

const useFetchMovieShows = (movieId) => {
  return useQuery(['movies', movieId, 'shows'], () => api.get(`v1/movies/${movieId}/shows`), {
    enabled: !!movieId
  });
}

export default useFetchMovieShows;
