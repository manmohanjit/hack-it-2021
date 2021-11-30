import {useParams, useNavigate} from "react-router-dom";
import useFetchShow from "../hooks/useFetchShow";
import useFetchMovieCategories from "../hooks/useFetchMovieCategories";
import useFetchMovie from "../hooks/useFetchMovie";
import useFetchHall from "../hooks/useFetchHall";
import SeatMap from "../components/SeatMap";
import useFetchShowInventory from "../hooks/useFetchShowInventory";
import useCreateOrder from "../hooks/useCreateOrder";
import OrderSummary from "../components/OrderSummary";
import MovieHeading from "../components/MovieHeading";
import {useEffect} from "react";
import LoadingPage from "./LoadingPage";
import ErrorPage from "./ErrorPage";

const MAX_SEATS = 10;

const Purchase = () => {
  const navigate = useNavigate()
  const {showId} = useParams();
  const show = useFetchShow(showId);
  const movie = useFetchMovie(show.data?.movieId);
  const categories = useFetchMovieCategories(show.data?.movieId);
  const hall = useFetchHall(show.data?.hallId);
  const seats = useFetchShowInventory(show.data?.id);
  const {formik} = useCreateOrder(showId, {
    onSuccess: (data) => {
      navigate(`/orders/${data.id}`);
    }
  });

  useEffect(() => {
    let unavailable = 0;
    const unavailableSeats = [];
    formik.setFieldValue(
      'seats',
      formik.values.seats.filter(i => {
        const item = seats.data.find(s => s.id === i);

        if(!item || item.status !== 'AVAILABLE') {
          unavailable++;
          item && unavailableSeats.push(item.seat.label);

          return false;
        }

        return true;
      })
    )
    if(unavailable > 0) {
      alert(unavailableSeats.length === unavailable ? `The following seats are no longer available: ${unavailableSeats.join(', ')}` : "Some of the selected seats are no longer available!")
    }
  }, [seats.isFetching]);

  if(show.isLoading) {
    return <LoadingPage />
  }
  if(show.isError) {
    return <ErrorPage error={movie.error} />
  }

  const selectedSeats = formik.values.seats.map(i => seats.data && seats.data.find(s => s.id === i)).filter(i => !!i) || [];


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
            <h4>Purchase Tickets</h4>
            {hall.data && (
              <SeatMap
                title={hall.data.title}
                svg={hall.data.seatMap}
                items={seats.data || []}
                categories={categories.data}
                selected={formik.values.seats}
                isLoading={seats.isLoading}
                isDisabled={formik.isSubmitting}
                onSelect={item => {
                  if(formik.values.seats.includes(item.id)) {
                    formik.setFieldValue('seats', [...formik.values.seats].filter(i => i !== item.id));
                    return;
                  }
                  if(item.status !== 'AVAILABLE') {
                    return;
                  }
                  if(formik.values.seats.length >= MAX_SEATS) {
                    alert("You have selected too many items!");
                    return;
                  }

                  formik.setFieldValue('seats', [...formik.values.seats, item.id])
                }}
                onRefreshClick={() => {
                  formik.setFieldValue('seats', []);
                  seats.remove();
                }}
              />
            )}
          </div>
        </div>
        <div className="col-md-4">
          <div className="sticky-top">
            <h5 className="d-flex align-items-center justify-content-between">
              Seats Selected
              {formik.values?.seats?.length > 0 && (<button type="button" className="ml-2 btn btn-outline-primary btn-sm" onClick={() => formik.setFieldValue('seats', [])}>Clear</button>)}
            </h5>
            <div className={"d-flex flex-wrap"}>
              {selectedSeats.map(item => (
                <span className="seat seat-text me-2 mb-2" style={{"--bg": item.category.colour}} id={'seat-'+item.id.toString()}>{item.seat.label}</span>
              ))}
            </div>
            <p className="text-muted small">{selectedSeats.length} of {MAX_SEATS} seats selected</p>

            <h5>Order Summary</h5>
            <OrderSummary items={selectedSeats} />
            <button className="btn btn-primary" type="submit" onClick={formik.handleSubmit}>Confirm Seats &raquo;</button>
          </div>
          </div>
      </div>
    </div>
  )
}

export default Purchase;
