import React from "react";
import ErrorMessage from "./ErrorMessage";

export function Input({ register, errors, name, config, type, ...rest }) {
  return (
    <>
      <input
        className={errors[name] ? "input-error" : ""}
        type={type}
        {...register(name, { ...config })}
        {...rest}
      />
      {type !== "radio" && <ErrorMessage name={name} errors={errors} />}
    </>
  );
}
