import React from "react";
import { Controller } from "react-hook-form";
import TextField from "@mui/material/TextField";

const InputItem = ({ name, control, label, type, styles }) => {
  return (
    <Controller
      control={control}
      name={name}
      render={(props) => {
        const { field, formState } = props;
        const { errors } = formState;
        const error = errors?.[name];
        const { onChange, value } = field;
        return (
          <TextField
            error={error}
            id="outlined-password-input"
            onChange={(e) => onChange(e?.target.value)}
            label={label}
            type={type}
            value={value}
            style={styles}
            helperText={error && error?.message}
          />
        );
      }}
    />
  );
};

export default InputItem;
