import React, { useEffect } from "react";
import {
  BrowserRouter,
  Routes,
  Route,
  Outlet,
  Navigate,
} from "react-router-dom";
import Header from "../components/Header";
import Add from "../pages/AddPage/Add";
import List from "../pages/ListPage/List";
import { mockData } from "../mockdata";
import { useDispatch } from "react-redux";
import { setMemberData } from "../features/members/memberSlice";

const AppRouter = () => {
  function Dashboard() {
    const dispatch = useDispatch();
    useEffect(() => {
      const dataMember = localStorage.getItem("memberData");
      if (!dataMember) {
        localStorage.setItem("memberData", JSON.stringify(mockData));
        dispatch(setMemberData([...mockData]));
      }
    }, []);
    return (
      <div>
        <Header />
        <Outlet />
      </div>
    );
  }
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Dashboard />}>
          <Route path="empoloyee/list" element={<List />} />
          <Route path="empoloyee/add" element={<Add />} />
          <Route path="empoloyee/edit/:id" element={<Add />} />
          <Route index element={<Navigate to="empoloyee/list" replace />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
};

export default AppRouter;
