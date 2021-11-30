import {useQuery} from "react-query";
import api from "../services/api";

const useFetchShowInventory = (showId) => {
  return useQuery(['shows', showId, 'inventory'], () => api.get(`v1/shows/${showId}/inventory`), {
    enabled: !!showId
  });
}

export default useFetchShowInventory;
