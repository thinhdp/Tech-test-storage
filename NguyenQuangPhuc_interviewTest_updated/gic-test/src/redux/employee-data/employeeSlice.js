import { createSlice } from "@reduxjs/toolkit";
import { generateUUID } from "../../helper/helper";
const employeeTestData = [
  {
    id: generateUUID(),
    firstName: "Johnnnnn",
    lastName: "Doeeeee",
    email: "john.doe@example.com",
    phoneNumber: "6563323659",
    gender: "Male",
  },
  {
    id: generateUUID(),
    firstName: "Janeeeee",
    lastName: "Smithhhhh",
    email: "jane.smith@example.com",
    phoneNumber: "6563323659",
    gender: "Female",
  },
  {
    id: generateUUID(),
    firstName: "Employee 1",
    lastName: "Last Name1",
    email: "employee1@example.com",
    phoneNumber: "6563323659",
    gender: "Male",
  },
  {
    id: generateUUID(),
    firstName: "Employee 2",
    lastName: "Last Name2",
    email: "employee2@example.com",
    phoneNumber: "6563323659",
    gender: "Female",
  },
  {
    id: generateUUID(),
    firstName: "Employee 3",
    lastName: "Last Name3",
    email: "employee3@example.com",
    phoneNumber: "6563323659",
    gender: "Male",
  },
  {
    id: generateUUID(),
    firstName: "Employee 4",
    lastName: "Last Name4",
    email: "employee4@example.com",
    phoneNumber: "6563323659",
    gender: "Female",
  },
  {
    id: generateUUID(),
    firstName: "Employee 5",
    lastName: "Last Name5",
    email: "employee5@example.com",
    phoneNumber: "6563323659",
    gender: "Male",
  },
];

const checkInitData = () => {
  if (localStorage.getItem("employee") !== null) {
    return JSON.parse(localStorage.getItem("employee"));
  } else {
    localStorage.setItem("employee", JSON.stringify(employeeTestData));
    return employeeTestData;
  }
};

const initData = checkInitData();

const initialState = {
  value: initData,
};

export const employeeSlice = createSlice({
  name: "employee",
  initialState,
  reducers: {
    addEmployee: (state, action) => {
      const newEmployee = action.payload;
      state.value = [...state.value, { ...newEmployee, id: generateUUID() }];
      localStorage.setItem("employee", JSON.stringify(state.value));
    },
    updateEmployee: (state, action) => {
      const updateEmployee = action.payload;
      const updatedValue = state.value.map((obj) => {
        if (obj.id === updateEmployee.id) {
          return { ...updateEmployee };
        }
        return obj;
      });
      state.value = updatedValue;
      localStorage.setItem("employee", JSON.stringify(state.value));
    },
    deleteEmployee: (state, action) => {
      const id = action.payload;
      state.value = state.value.filter((e) => e.id !== id);
      localStorage.setItem("employee", JSON.stringify(state.value));
    },
  },
});

export const { addEmployee, deleteEmployee, updateEmployee } =
  employeeSlice.actions;

export default employeeSlice.reducer;
