import React from "react";

const AddMemberToGroupModal = ({
  closeModal,
  modalHeading,
  handleChange,
  emailID,
  addMember,
}) => {
  return (
    <div className="modal-wrapper">
      <div className="modal-container">
        <div
          style={{
            display: "flex",
            flexDirection: "row",
            justifyContent: "space-between",
          }}
        >
          <label
            style={{
              fontSize: "24px",
            }}
          >
            {modalHeading}
          </label>
        </div>
        <div className="input-box" style={{ display: "flex" }}>
          <input
            style={{
              borderRadius: "8px",
              padding: "12px 4px",
              fontSize: "16px",
            }}
            name="emailID"
            type="email"
            value={emailID}
            placeholder={"EmailID"}
            onChange={handleChange}
          />
        </div>
        <div
          style={{
            display: "flex",
            flexDirection: "row",
            padding: "8px 0px",
          }}
        >
          <button
            style={{
              flex: 1,
              marginRight: "8px",
            }}
            className="button"
            onClick={() => addMember(emailID)}
          >
            Add
          </button>
          <button
            style={{
              flex: 1,
              marginRight: "8px",
            }}
            className="button"
            onClick={closeModal}
          >
            Cancel
          </button>
        </div>
      </div>
    </div>
  );
};

export default AddMemberToGroupModal;
