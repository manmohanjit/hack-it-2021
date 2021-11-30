const ErrorsComponent = ({error}) => {
  return (
    <div className="alert alert-danger">
      <h6 className={error.errors?.length > 0 ? '' : 'mb-0'}>{error.message || "Unable to complete request"}</h6>
      {error.errors?.length > 0 && (
        <ul className="mb-0">
          {error.errors.map((val, index) => <li key={index}>{val}</li>)}
        </ul>
      )}
    </div>
  );
}

export default ErrorsComponent;
