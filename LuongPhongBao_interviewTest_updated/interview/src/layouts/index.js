import React, { useEffect, useState } from "react";
import { Outlet, useLocation } from 'react-router-dom';
const MainLayout = () => {
  return (
    <>
      <h1>header</h1>
      <div>
        <Outlet />
      </div>
    </>
  );
};

export default MainLayout;
