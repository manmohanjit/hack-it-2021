const MovieHeading = ({movie, categories, children}) => {
  return (
    <div className="row">
      <div className="col-md-8">
        <h1>{movie?.title}</h1>
        <p dangerouslySetInnerHTML={{__html: movie?.body}} />
        {children}
      </div>
      {categories && (
        <div className="col-md-4">
          <h6>Ticket Categories</h6>
          <ul className="list-group">
            {categories.map(category => (
              <li className="list-group-item d-flex align-items-center" key={category.id}>
                <span className="seat me-2" style={{"--bg": category.colour}}></span>
                {category.label}
                <small className="ms-auto">RM {category.price/100}</small>
              </li>
            ))}
          </ul>
        </div>
      )}
    </div>
  )
}

export default MovieHeading;
