import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";

const initialState = {
  memberData: localStorage.getItem("memberData")
    ? JSON.parse(localStorage.getItem("memberData"))
    : [],
};

export const memberSlice = createSlice({
  name: "counter",
  initialState,
  reducers: {
    increment: (state) => {
      state.value += 1;
    },
    decrement: (state) => {
      state.value -= 1;
    },
    setMemberData: (state, data) => {
      localStorage.setItem("memberData", JSON.stringify(data.payload));
      state.memberData = data.payload;
    },
  },
  extraReducers: (builder) => {
  
  },
});

export const {setMemberData } =
  memberSlice.actions;

export const selectReducer = (state) => state.members;

export default memberSlice.reducer;
