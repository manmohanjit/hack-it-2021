import {useFormik} from "formik";
import {useMutation} from "react-query";
import queryClient from "../services/queryClient";
import api from "../services/api";

const useCreateOrder = (showId, options = {}) => {
  const mutation = useMutation(({seats}) => {
    const formData = new FormData();
    formData.append('showId', showId);
    seats.forEach(item => formData.append('items[]', item));

    return api.post("/v1/orders", formData);
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
