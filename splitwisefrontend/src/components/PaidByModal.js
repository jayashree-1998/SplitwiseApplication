import React, { useContext } from "react";
import GroupUserListContext from "../context.js/GroupUserListContext";

const PaidByModal = ({ modalHeading, closeModal }) => {
  const [groupUserList, setGroupUserList] = useContext(GroupUserListContext);

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
          <div>
            {groupUserList !== null &&
              groupUserList.map((u, i) => {
                return (
                  <div key={i}>
                    {/* 1. Checkbox 2.name of the user 3.Amount */}
                    <p>{u.name}</p>
                  </div>
                );
              })}
          </div>
        </div>
      </div>
    </div>
  );
};

export default PaidByModal;
