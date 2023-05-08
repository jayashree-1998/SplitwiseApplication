import React, { useContext, useEffect, useState } from "react";
import { GroupDetailContext } from "../contexts/GroupDetailContext";
import { ExpenseContext } from "../contexts/ExpenseContext";

function PaidByModal({ modalHeading, closeModal }) {
  const [selectedGroup, setSelectedGroup, groupObject, setGroupObject] =
    useContext(GroupDetailContext);

  const { paidObject, owedObject } = useContext(ExpenseContext);

  const [paidByList, setPaidByList] = paidObject;

  useEffect(() => {
    console.log(paidByList);
  }, [paidByList]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    if (isNaN(value) || Number(value) < 0) {
      setPaidByList((pv) => {
        return {
          ...pv,
          [name]: 0,
        };
      });
    } else {
      setPaidByList((pv) => {
        return {
          ...pv,
          [name]: Number(value),
        };
      });
    }
  };

  return (
    <div className="modal-wrapper-child">
      <div
        className="modal-container-child"
        style={{
          margin: "0px 8px",
          display: "flex",
        }}
      >
        <div
          style={{
            display: "flex",
            alignContent: "space-between",
            flexDirection: "column",
          }}
        >
          <div
            style={{
              display: "flex",
              whiteSpace: "nowrap",
              flexDirection: "column",
              flex: 1,
            }}
          >
            {groupObject.userList.map((e, i) => {
              return (
                <div
                  key={e.userID}
                  style={{
                    padding: "4px 0px",
                    margin: "4px 0px",
                    marginTop: "8px",
                    display: "flex",
                    flexDirection: "row",
                    alignContent: "space-evenly",
                  }}
                >
                  <div
                    style={{
                      flex: 1,
                    }}
                  >
                    <label
                      style={{
                        marginRight: "8px",
                      }}
                    >
                      {e.name}
                    </label>
                  </div>
                  <div>
                    <input
                      style={{
                        width: "72px",
                      }}
                      name={e.userID}
                      type="text"
                      min={"0"}
                      maxLength={6}
                      value={paidByList[e.userID]}
                      onChange={handleChange}
                    />
                  </div>
                </div>
              );
            })}
          </div>
          <div>
            <button
              className="button"
              onClick={() => {
                closeModal();
              }}
            >
              Confirm
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default PaidByModal;
