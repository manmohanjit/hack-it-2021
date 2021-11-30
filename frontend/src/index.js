import React from 'react';
import ReactDOM from 'react-dom';
import 'bootstrap/dist/css/bootstrap.css';
import './index.css';
import reportWebVitals from './reportWebVitals';
import { QueryClientProvider } from 'react-query'
import queryClient from "./services/queryClient";
import {
  BrowserRouter,
  Routes,
  Route
} from "react-router-dom";
import Movie from "./pages/Movie";
import Home from "./pages/Home";
import Purchase from "./pages/Purchase";
import Order from "./pages/Order";
import Layout from "./components/Layout";

const App = () => {
  return (
    <QueryClientProvider client={queryClient}>
      <BrowserRouter>
        <Routes>
          <Route path={"/"} element={<Layout />}>
            <Route path="/" element={<Home />} />
            <Route path="/movies/:movieId" element={<Movie />} />
            <Route path="/purchase/:showId" element={<Purchase />} />
            <Route path="/orders/:orderId" element={<Order />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </QueryClientProvider>
  );
}

ReactDOM.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
  document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
