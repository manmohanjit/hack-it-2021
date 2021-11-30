import {useQuery} from "react-query";
import api from "../services/api";

const useFetchOrder = (orderId) => {
  return useQuery(['orders', orderId], () => api.get(`v1/orders/${orderId}`), {
    enabled: !!orderId
  });
}

export default useFetchOrder;
