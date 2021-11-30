import {useQuery} from "react-query";
import api from "../services/api";

const useFetchMovieCategories = (movieId) => {
  return useQuery(['movies', movieId, 'categories'], () => api.get(`v1/movies/${movieId}/categories`), {
    enabled: !!movieId
  });
}

export default useFetchMovieCategories;
