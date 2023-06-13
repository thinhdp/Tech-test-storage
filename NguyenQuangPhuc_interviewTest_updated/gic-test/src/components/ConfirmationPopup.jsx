import React from "react";
import ReactDOM from "react-dom";
import Button from "./Button";

const ConfirmationPopup = ({ handleConfirm, handleReject, name }) => {
  return ReactDOM.createPortal(
    <div className="popup__overlay">
      <div className="popup__content">
        <h2>{`Are you sure you want to delete ${name}?`}</h2>
        <div className="popup__actions flex">
          <Button text="Yes" action={handleConfirm} theme="edit" size="md" />
          <Button text="No" action={handleReject} theme="delete" size="md" />
        </div>
      </div>
    </div>,
    document.getElementById("popup-root")
  );
};

export default ConfirmationPopup;
