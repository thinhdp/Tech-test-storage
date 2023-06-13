import { yupResolver } from "@hookform/resolvers/yup";
import Button from "@mui/material/Button";
import React, { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate, useParams } from "react-router-dom";
import { toast } from "react-toastify";
import { v1 as uuidv1 } from "uuid";
import * as yup from "yup";
import InputItem from "../../components/InputItem";
import RadioBlock from "../../components/RadioBlock";
import {
  selectReducer,
  setMemberData,
} from "../../features/members/memberSlice";

const genderOption = [
  {
    value: "Female",
    label: "Female",
  },
  {
    value: "Male",
    label: "Male",
  },
];
const style = {
  field: {
    width: "30rem",
    marginTop: "12px",
  },
};
const fields = [
  { name: "firstName", type: "text", label: "First Name" },
  { name: "lastName", type: "text", label: "Last Name" },
  { name: "email", type: "text", label: "Email" },
  { name: "phone", type: "number", label: "Phone" },
];
const phoneRegExp = /^[689]\d{7}$/;
const emailRegExp = /^\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,3}$/;

const validationSchema = yup.object().shape({
  firstName: yup
    .string()
    .required("This field is required")
    .min(6, "This field must be greater than 6 digits")
    .max(10, "This field must be less than 10 digits"),
  lastName: yup
    .string()
    .required("This field is required")
    .min(6, "This field must be greater than 6 digits")
    .max(10, "This field must be less than 10 digits"),
  email: yup
    .string()
    .required("This field is required")
    .matches(emailRegExp, "This field must be email"),
  phone: yup
    .string()
    .required("This field is required")
    .matches(phoneRegExp, "This field must be Singapore phone number"),
});

function Add(props) {
  const { handleSubmit, reset, control } = useForm({
    resolver: yupResolver(validationSchema),
    defaultValues: { firstName: "", lastName: "", email: "", phone: "" },
  });
  const dispatch = useDispatch();
  const [gender, setGender] = useState("Female");
  const { memberData } = useSelector(selectReducer);
  const { id: editedId } = useParams();
  const navigate = useNavigate();

  const handleChange = (event) => {
    setGender(event.target.value);
  };

  const excuteSubmit = (data) => {
    const { firstName, lastName, email, phone } = data;
    const rawData = memberData || [];
    const newMember = {
      firstName,
      lastName,
      email,
      phone,
      gender: gender,
      id: uuidv1(),
    };
    if (editedId) {
      const newData = memberData.filter((item) => item?.id !== editedId);
      dispatch(setMemberData([{ ...newMember, id: editedId }, ...newData]));
      toast.success("save changes of employee successfully!");
      navigate(`/empoloyee/list`);
    } else {
      dispatch(setMemberData([newMember, ...rawData]));
      reset({
        firstName: "",
        lastName: "",
        email: "",
        phone: "",
      });
      setGender("Female");
      toast.success("Create new employee successfully!");
    }
  };

  useEffect(() => {
    if (memberData && editedId) {
      const editedMember = memberData?.find((item) => item?.id === editedId);
      if (editedMember) {
        const {
          firstName,
          lastName,
          email,
          phone,
          gender: genderData,
        } = editedMember;
        reset({
          firstName,
          lastName,
          email,
          phone,
        });
        setGender(genderData);
      }
    }
  }, [editedId, memberData]);

  return (
    <form onSubmit={handleSubmit(excuteSubmit)}>
      <div
        style={{
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
        }}
      >
        {fields.map((item, index) => (
          <InputItem
            key={index}
            name={item.name}
            control={control}
            label={item.label}
            type={item.type}
            styles={style.field}
          />
        ))}
        <div style={{ ...style.field }}>
          <RadioBlock
            title="Gender"
            value={gender}
            handleChange={handleChange}
            option={genderOption}
          />
        </div>

        <div
          style={{
            ...style.field,
            display: "flex",
            justifyContent: "flex-end",
          }}
        >
          <Button variant="contained" type="submit">
            {editedId ? "Save Changes" : "Add new"}
          </Button>
        </div>
      </div>
    </form>
  );
}

export default Add;
