import React from "react";

export default function Form({ type, children, onSubmit, handleSubmit }) {
  return (
    <div className="form flex">
      <h2 className="center">
        {type === "add" ? "Add new employee" : "Edit employee's information"}
      </h2>
      <form className="form__section flex" onSubmit={handleSubmit(onSubmit)}>
        {children}
      </form>
    </div>
  );
}
