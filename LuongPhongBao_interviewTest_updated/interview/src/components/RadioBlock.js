import React from "react";
import FormControlLabel from "@mui/material/FormControlLabel";
import Radio from "@mui/material/Radio";
import RadioGroup from "@mui/material/RadioGroup";

const RadioBlock = ({ value, handleChange, option = [], title }) => {
  return (
    <>
      <div style={{ display: "flex" }}>
        <h4>{title}</h4>
      </div>
      <RadioGroup
        value={value}
        onChange={handleChange}
        name="radio-buttons-group"
      >
        {option &&
          Array.isArray(option) &&
          option?.map((item, index) => (
            <FormControlLabel
              key={index}
              value={item.value}
              control={<Radio />}
              label={item.label}
            />
          ))}
      </RadioGroup>
    </>
  );
};

export default RadioBlock;
