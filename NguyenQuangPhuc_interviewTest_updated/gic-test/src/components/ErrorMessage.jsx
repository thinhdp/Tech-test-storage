import React from "react";

const ErrorMessage = ({ name, errors }) => {
  return (
    <div className="error__messsage ">
      {errors[name] && errors[name].type === "required" && (
        <span>This is required</span>
      )}
      {errors[name] && errors[name].type === "minLength" && (
        <span>Minimum characters required.</span>
      )}
      {errors[name] && errors[name].type === "maxLength" && (
        <span>Max length exceeded</span>
      )}
      {errors[name] &&
        errors[name].type === "pattern" &&
        name === "phoneNumber" && (
          <span>Please provide valid Singapore phone number</span>
        )}
      {errors[name] && errors[name].type === "pattern" && name === "email" && (
        <span>Please provide valid email address</span>
      )}
    </div>
  );
};

export default ErrorMessage;
