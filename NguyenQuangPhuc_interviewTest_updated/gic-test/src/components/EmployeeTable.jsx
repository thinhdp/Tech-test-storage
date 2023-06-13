import React, { useState } from "react";
import Button from "./Button";
import { useNavigate } from "react-router-dom";
import { deleteEmployee } from "../redux/employee-data/employeeSlice";
import { useDispatch } from "react-redux";
import ConfirmationPopup from "./ConfirmationPopup";

function EmployeeTable({ data }) {
  const [isPopupOpen, setIsPopupOpen] = useState(false);
  const [selectEmployee, setSelectEmployee] = useState(null);

  const navigate = useNavigate();
  const dispatch = useDispatch();

  const handleConfirm = () => {
    dispatch(deleteEmployee(selectEmployee.id));
    setIsPopupOpen(false);
  };

  const handleReject = () => {
    setIsPopupOpen(false);
  };

  return (
    <>
      <table>
        <thead>
          <tr>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Email Address</th>
            <th>Phone Number</th>
            <th>Gender</th>
            <th className="table__action"></th>
          </tr>
        </thead>
        <tbody>
          {data.map((employee) => (
            <tr key={employee.id}>
              <td>{employee.firstName}</td>
              <td>{employee.lastName}</td>
              <td>{employee.email}</td>
              <td>{employee.phoneNumber}</td>
              <td>{employee.gender}</td>
              <td>
                <div className="flex action">
                  <Button
                    size="md"
                    theme="edit"
                    action={() => {
                      navigate(`/employee/edit/${employee.id}`);
                    }}
                    text="Edit"
                  />
                  <Button
                    size="md"
                    theme="delete"
                    action={() => {
                      setIsPopupOpen(true);
                      setSelectEmployee(employee);
                    }}
                    text="Delete"
                  />
                </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      {isPopupOpen && (
        <ConfirmationPopup
          handleConfirm={handleConfirm}
          name={selectEmployee.firstName + " " + selectEmployee.lastName}
          handleReject={handleReject}
        />
      )}
    </>
  );
}

export default EmployeeTable;
