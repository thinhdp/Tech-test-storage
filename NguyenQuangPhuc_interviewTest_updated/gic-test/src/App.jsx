import {
  Route,
  Navigate,
  createBrowserRouter,
  createRoutesFromElements,
  RouterProvider,
} from "react-router-dom";
import HomePage from "./pages/HomePage";
import ActionPage from "./pages/ActionPage";
import "../src/css/App.css";

const router = createBrowserRouter(
  createRoutesFromElements(
    <>
      <Route path="/employee/list" element={<HomePage />} />
      <Route path="/employee/add" element={<ActionPage type="add" />} />
      <Route
        path="/employee/edit/:employeeId"
        element={<ActionPage type="edit" />}
      />

      <Route exact path="/" element={<Navigate to="/employee/list" />} />
    </>
  )
);

const App = () => <RouterProvider router={router} />;

export default App;
