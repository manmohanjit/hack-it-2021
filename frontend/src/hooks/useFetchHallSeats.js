import {useQuery} from "react-query";
import api from "../services/api";

const useFetchHallSeats = (hallId) => {
  return useQuery(['halls', hallId, 'seats'], () => api.get(`v1/halls/${hallId}/seats`), {
    enabled: !!hallId
  });
}

export default useFetchHallSeats;
