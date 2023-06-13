import React, { useEffect, useState } from "react";
import Form from "../components/Form";
import { Input } from "../components/Input";
import { useForm } from "react-hook-form";
import ErrorMessage from "../components/ErrorMessage";
import { useDispatch, useSelector } from "react-redux";
import {
  addEmployee,
  updateEmployee,
} from "../redux/employee-data/employeeSlice";
import { useNavigate, useParams } from "react-router-dom";
import Button from "../components/Button";
import Prompt from "../components/Prompt";

const formConfig = {
  required: {
    required: true,
  },
  name: {
    required: true,
    minLength: 6,
    maxLength: 10,
  },
  email: { required: true, pattern: /^\S+@\S+$/i },
  phone: {
    required: true,
    pattern: /\65(6|8|9)\d{7}/g,
  },
};

const getCurrentEmployee = (id, data) => {
  return data.find((e) => e.id === id);
};

const ActionPage = ({ type }) => {
  const [isSubmit, setIsSubmit] = useState(false);
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const {
    handleSubmit,
    register,
    setValue,
    formState: { errors, isDirty },
  } = useForm();
  let { employeeId } = useParams();
  const employeeData = useSelector((state) => {
    return state.employee.value;
  });
  const currentEmployee = getCurrentEmployee(employeeId, employeeData);

  useEffect(() => {
    if (currentEmployee) {
      for (let key in currentEmployee) {
        setValue(key, currentEmployee[key]);
      }
    }
  }, [currentEmployee]);

  const onSubmit = (data) => {
    setIsSubmit(() => true);
    dispatch(type === "add" ? addEmployee(data) : updateEmployee(data));
    setTimeout(() => {
      navigate("/");
    }, 100);
  };

  return (
    <>
      <Prompt
        when={isDirty && !isSubmit}
        message="Form has been modified. You will loose your unsaved changes. Are you sure you want to close this form?"
      />
      <Form type={type} handleSubmit={handleSubmit} onSubmit={onSubmit}>
        <Input
          register={register}
          errors={errors}
          type="text"
          placeholder="First name"
          config={formConfig.name}
          name="firstName"
        />
        <Input
          register={register}
          errors={errors}
          type="text"
          placeholder="Last name"
          config={formConfig.name}
          name="lastName"
        />
        <Input
          register={register}
          errors={errors}
          config={formConfig.email}
          type="text"
          placeholder="Email"
          name="email"
        />
        <Input
          register={register}
          errors={errors}
          config={formConfig.phone}
          type="tel"
          placeholder="Mobile number"
          name="phoneNumber"
        />
        <div className="form__section__radio flex">
          <div className="flex">
            <Input
              register={register}
              errors={errors}
              type="radio"
              value="Male"
              id="gender"
              name="gender"
              config={formConfig.required}
            />
            <p>Male</p>
          </div>
          <div className="flex">
            <Input
              register={register}
              errors={errors}
              type="radio"
              value="Female"
              id="gender"
              name="gender"
              config={formConfig.required}
            />
            <p>Female</p>
          </div>
        </div>
        {<ErrorMessage name="gender" errors={errors} />}
        <div className="form__section__submit flex">
          <Button
            theme="submit"
            type="submit"
            text={type === "add" ? "Add" : "Update"}
          />
        </div>
      </Form>
    </>
  );
};

export default ActionPage;
