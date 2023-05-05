import React from "react";

const CreateGroupModal = ({
  closeModal,
  modalHeading,
  handleChange,
  groupName,
  addGroup,
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
          <span className="details">Name</span>
          <input
            name="groupName"
            type="text"
            value={groupName}
            placeholder={"Group name"}
            onChange={handleChange}
          />
        </div>
        <button className="button" onClick={addGroup}>
          Add
        </button>
      </div>
    </div>
  );
};

export default CreateGroupModal;
