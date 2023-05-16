import React from "react";

const CreateGroupModal = ({
  closeModal,
  modalHeading,
  handleChange,
  groupName,
  addGroup,
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
        <div
          className="input-box"
          style={{ display: "flex", padding: "8px 0px" }}
        >
          <input
            style={{
              borderRadius: "8px",
              padding: "12px 4px",
              fontSize: "16px",
            }}
            name="groupName"
            type="text"
            value={groupName}
            placeholder={"Group name"}
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
            onClick={addGroup}
          >
            Create
          </button>
          <button
            style={{
              flex: 1,
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

export default CreateGroupModal;
