import React from "react";

const AddMemberToGroupModal = ({
  closeModal,
  modalHeading,
  handleChange,
  emailID,
  addMember,
}) => {
  return (
    <div className="modal-wrapper" style={{ padding: 10 }}>
      <div className="modal-container">
        <div
          style={{
            display: "flex",
            flexDirection: "row",
            justifyContent: "space-between",
          }}
        >
          <label>{modalHeading}</label>
          <button className="button" onClick={closeModal}>
            x
          </button>
        </div>
        <div className="input-box" style={{ display: "flex" }}>
          <span className="details">Email ID</span>
          <input
            name="emailID"
            type="email"
            value={emailID}
            placeholder={"EmailID"}
            onChange={handleChange}
          />
        </div>
        <button className="button" onClick={() => addMember(emailID)}>
          Add
        </button>
      </div>
    </div>
  );
};

export default AddMemberToGroupModal;
