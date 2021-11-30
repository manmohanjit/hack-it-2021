import {useQuery} from "react-query";
import api from "../services/api";

const useFetchMovie = (movieId) => {
  return useQuery(['movies', movieId], () => api.get(`v1/movies/${movieId}`), {
    enabled: !!movieId
  });
}

export default useFetchMovie;
