import { configureStore } from "@reduxjs/toolkit";

import employeeReducer from "./employee-data/employeeSlice";

export const store = configureStore({
  reducer: {
    employee: employeeReducer,
  },
});
