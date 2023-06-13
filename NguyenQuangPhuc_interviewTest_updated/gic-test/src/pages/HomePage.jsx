import React from "react";
import EmployeeTable from "../components/EmployeeTable";
import { Link } from "react-router-dom";
import { useSelector } from "react-redux";

const HomePage = () => {
  const employeeData = useSelector((state) => {
    return state.employee.value;
  });
  return (
    <div className="home">
      <div className="home__header ">
        <h2>Summary page</h2>
      </div>
      <div className="home__container">
        <div className="home__nav__container">
          <Link to={"/employee/add"}>Add Employee</Link>
        </div>
        <EmployeeTable data={employeeData} />
      </div>
    </div>
  );
};

export default HomePage;
