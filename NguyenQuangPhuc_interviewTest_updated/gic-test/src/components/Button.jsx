import React from "react";
import { getButtonClassName } from "../helper/helper";

const Button = ({ action = () => {}, type = "button", theme, size, text }) => {
  return (
    <button
      className={getButtonClassName(theme, size)}
      onClick={action}
      type={type}
    >
      {text}
    </button>
  );
};

export default Button;
