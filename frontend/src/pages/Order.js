import {useParams} from "react-router-dom";
import useFetchOrder from "../hooks/useFetchOrder";
import OrderSummary from "../components/OrderSummary";
import useUpdateOrder from "../hooks/useUpdateOrder";
import useCompleteOrder from "../hooks/useCompleteOrder";
import QRCode from "react-qr-code";
import useFetchShow from "../hooks/useFetchShow";
import useFetchMovie from "../hooks/useFetchMovie";
import MovieHeading from "../components/MovieHeading";
import ErrorList from "../components/ErrorList";
import LoadingPage from "./LoadingPage";
import ErrorPage from "./ErrorPage";

const Order = () => {
  const {orderId} = useParams();
  const order = useFetchOrder(orderId);
  const show = useFetchShow(order.data?.showId);
  const movie = useFetchMovie(show.data?.movieId);
  const {formik, mutation} = useUpdateOrder(orderId);
  const completeMutation = useCompleteOrder(orderId);


  if(order.isLoading) {
    return <LoadingPage />
  }
  if(order.isError) {
    return <ErrorPage error={movie.error} />
  }

  return (
    <div className="container">
      <MovieHeading movie={movie.data} />

      <div className="row">
        <div className="col-md-8">
          <div className="mb-4">
            <h4>Showtime</h4>
            <p>{show.data?.startsAt && (new Date(show.data?.startsAt)).toString()}</p>
          </div>

          <div className="mb-4">
            <h4>Order</h4>
            <p>{order.data?.id}</p>
          </div>

          <div className="mb-4">
            <h4 className="mb-2">Confirm Order</h4>

            {mutation.isError && <ErrorList error={mutation.error} />}

            {formik.isSubmitting && <h1>Loading...</h1>}

            {order.data?.status === 'INITIAL' && (
              <form onSubmit={formik.handleSubmit}>
                <div className="mb-3">
                  <label className="form-label">Name</label>
                  <input type="text" className="form-control" {...formik.getFieldProps("name")} disabled={formik.isSubmitting} required />
                  {formik.errors.name && (<div className="invalid-feedback">{formik.errors.name}</div>)}
                </div>
                <div className="mb-3">
                  <label className="form-label">Email</label>
                <input type="email" className="form-control" {...formik.getFieldProps("email")} disabled={formik.isSubmitting} required />
                  {formik.errors.email && (<div className="invalid-feedback">{formik.errors.email}</div>)}
                </div>
              </form>
            )}
            {order.data?.status === 'PENDING' && (
              <div>
                <h6>Payment Simulator</h6>
                <p>Since we have no payment gateway integrated, you can manually trigger an order expiration or completion using the following buttons:</p>
                <div className="d-grid gap-2">
                  <button className="btn d-block btn-success" disabled={completeMutation.isLoading} onClick={() => completeMutation.mutate()}>Complete</button>
                  <button className="btn d-block btn-danger" disabled={completeMutation.isLoading} onClick={() => completeMutation.mutate({expire: 1})}>Expire</button>
                </div>
              </div>
            )}
            {order.data?.status === 'EXPIRED' && (
              <p className="alert alert-warning">Your order has expired!</p>
            )}
            {order.data?.status === 'COMPLETED' && (
              <div>
                <p className="alert alert-success">Your order is complete, you will receive a confirmation email!</p>

                <QRCode size={128} value={window.location} />
                <p className="text-muted align-middle mt-2">Show this QR Code to reception</p>
              </div>
            )}
          </div>
        </div>
        <div className="col-md-4">
          <h5>Seats Selected</h5>
          <div className={"d-flex flex-wrap"}>
            {order.data && order.data.items.map(item => (
              <span className="seat seat-text me-2 mb-2" key={item.id} style={{"--bg": item.category.colour}}>{item.seat.label}</span>
            ))}
          </div>
          <p className="text-muted small">{order.data?.items?.length} seats selected</p>

          <h5>Order Summary</h5>
          <OrderSummary items={order.data?.items} />

          {order.data?.status === 'INITIAL' && (
            <button type="button" className="btn btn-primary" onClick={formik.handleSubmit} disabled={formik.isSubmitting}>Proceed to Payment &raquo;</button>
          )}
        </div>
      </div>

    </div>
  )
}

export default Order;
