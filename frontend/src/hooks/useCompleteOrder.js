import {useMutation} from "react-query";
import queryClient from "../services/queryClient";
import api from "../services/api";

const useCompleteOrder = (orderId) => {
  return useMutation((values) => {
    return api({
      url: !!values?.expire ? `v1/orders/${orderId}` : `v1/orders/${orderId}/complete`,
      method: !!values?.expire ? 'DELETE' : 'POST',
    });
  }, {
    onSuccess: data => {
      queryClient.setQueryData(['orders', data.id], data);
    }
  })
};

export default useCompleteOrder;
