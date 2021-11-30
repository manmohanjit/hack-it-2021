import {useFormik} from "formik";
import {useMutation} from "react-query";
import queryClient from "../services/queryClient";
import api from "../services/api";

const useUpdateOrder = (orderId, options = {}) => {
  const mutation = useMutation(({name, email}) => {
    const formData = new FormData();
    formData.append('name', name);
    formData.append('email', email);

    return api.patch(`v1/orders/${orderId}`, formData);
  }, {
    onSuccess: data => {
      queryClient.setQueryData(['orders', data.id], data);

      options.onSuccess && options.onSuccess(data);
    }
  })

  const formik = useFormik({
    initialValues: {
      name: "",
      email: "",
    },
    validate: values => {
      const errors = {};

      if(values.name.length === 0) {
        errors.name = "The name field is required";
      }
      if(values.email.length === 0) {
        errors.email = "The email field is required";
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

export default useUpdateOrder;
