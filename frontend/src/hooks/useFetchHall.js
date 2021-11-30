import {useQuery} from "react-query";
import api from "../services/api";

const useFetchHall = (hallId) => {
  return useQuery(['halls', hallId], () => api.get( `v1/halls/${hallId}`), {
    enabled: !!hallId
  });
}

export default useFetchHall;
