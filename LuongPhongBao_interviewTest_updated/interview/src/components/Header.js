import React from "react";
import { useNavigate } from "react-router-dom";
import Button from "@mui/material/Button";

const Header = () => {
  const navigate = useNavigate();
  const menu = [
    {
      name: "Add new employee",
      path: "/empoloyee/add",
    },
    {
      name: "Employee list",
      path: "/empoloyee/list",
    },
  ];
  const navigateExcute = (path) => {
    navigate(path);
  };
  return (
    <>
      <h1>Employee's Infomation System</h1>
      <div>
        {menu.map((item) => (
          <Button
            variant="outlined"
            onClick={() => navigateExcute(item.path)}
            style={{ margin: "12px" }}
          >
            {item.name}
          </Button>
        ))}
      </div>
    </>
  );
};

export default Header;
