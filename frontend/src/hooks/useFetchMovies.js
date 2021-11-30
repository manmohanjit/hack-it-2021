import {useQuery} from "react-query";
import api from "../services/api";

const useFetchMovies = () => {
  return useQuery('movies', () => api.get(`v1/movies`));
}

export default useFetchMovies;
