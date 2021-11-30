import {Link, Outlet} from 'react-router-dom';

const Layout = () => {
  return (
    <div className="d-flex flex-column min-vh-100">
      <nav className="navbar navbar-dark bg-dark">
        <div className="container">
          <Link className="navbar-brand" to="/">Booking App</Link>
        </div>
      </nav>

      <div className="my-5">
        <Outlet />
      </div>

      <div className="bg-dark py-3 text-center mt-auto">
        <div className="container">
          <p className="mb-0 text-white">Copyright &copy; Booking App. All Rights Reserved.</p>
        </div>
      </div>
    </div>
  )
}
export default Layout;
