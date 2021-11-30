import {useQuery} from "react-query";
import api from "../services/api";

const useFetchShow = (showId) => {
  return useQuery(['shows', showId], () => api.get(`v1/shows/${showId}`), {
    enabled: !!showId
  });
}

export default useFetchShow;
