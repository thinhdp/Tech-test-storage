import { configureStore } from '@reduxjs/toolkit';
import memberReducer from '../features/members/memberSlice';

export const store = configureStore({
  reducer: {
    members: memberReducer,
  },
});
