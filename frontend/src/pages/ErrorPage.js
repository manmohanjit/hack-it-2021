const ErrorPage = ({error}) => {
  return (
    <div className="text-center">
      <h5>{error?.message || "Unable to complete your request"}</h5>
    </div>
  )
}

export default ErrorPage;
