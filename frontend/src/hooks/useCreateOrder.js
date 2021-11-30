import {useFormik} from "formik";
import {useMutation} from "react-query";
import queryClient from "../services/queryClient";
import api from "../services/api";

const useCreateOrder = (showId, options = {}) => {
  const mutation = useMutation(({seats}) => {
    return api.post("/v1/orders", {
      showId,
      items: seats,
    });
  }, {
    onSuccess: data => {
      queryClient.setQueryData(['orders', data.id], data);

      options.onSuccess && options.onSuccess(data);
    }
  })

  const formik = useFormik({
    initialValues: {
      seats: [],
    },
    validate: values => {
      const errors = {};

      if(values.seats.length === 0) {
        errors.seats = "You need to select a seat";
      }
      if(values.seats.length > 10) {
        errors.seats = "You may select a maximum of 10 seats";
      }

      return errors;
    },
    onSubmit: values => mutation.mutateAsync(values),
  })

  return {
    formik,
    mutation,
  }
};

export default useCreateOrder;
