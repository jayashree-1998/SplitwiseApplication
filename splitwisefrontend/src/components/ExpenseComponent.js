import React, { useContext, useEffect, useState } from "react";
import { GroupDetailContext } from "../contexts/GroupDetailContext";
import { UserContext } from "../contexts/UserContext";
import { getGroupDetail } from "../services/groupService";
import AddExpenseModal from "./AddExpenseModal";
import ExpenseProvider from "../contexts/ExpenseContext";
import {
  Accordion,
  AccordionDetails,
  AccordionSummary,
  Typography,
} from "@mui/material";
import ExpenseList from "./ExpenseList";

function ExpenseComponent() {
  const [showAddExpenseModal, setShowAddExpenseModal] = useState(false);
  const [userObject, setUserObject] = useContext(UserContext);

  const [selectedGroup, setSelectedGroup, groupObject, setGroupObject] =
    useContext(GroupDetailContext);

  const [paidSet, setPaidSet] = useState([]);
  const [owedSet, setOwedSet] = useState([]);

  useEffect(() => {
    console.log(groupObject.expenseList);
  }, [groupObject.expenseList]);

  function openModal() {
    let paidList = {};
    let owedList = {};
    groupObject.userList.forEach((e, i) => {
      paidList[e.userID] = 0;
      owedList[e.userID] = 0;
    });
    setPaidSet(paidList);
    setOwedSet(owedList);
    setShowAddExpenseModal(true);
  }

  function closeModal() {
    setShowAddExpenseModal(false);
  }

  function settleUp() {}

  return (
    <div
      style={{
        flex: 1,
        backgroundColor: "white",
        display: "flex",
        flexDirection: "column",
      }}
    >
      <div
        style={{
          backgroundColor: "white",
          flex: 1,
          display: "flex",
          flexDirection: "row",
          alignItems: "center",
          justifyContent: "space-between",
        }}
      >
        <div>
          <label
            style={{
              margin: "0px 8px",
              fontSize: "28px",
              fontWeight: "lighter",
            }}
          >
            Dashboard
          </label>
        </div>
        <div
          style={{
            display: "flex",
            flexDirection: "row",
          }}
        >
          {groupObject.group.settled === false ? (
            <div>
              <button
                style={{
                  margin: "0px 8px",
                }}
                className="button"
                onClick={() => {
                  openModal();
                }}
              >
                Add Expense
              </button>
              {showAddExpenseModal && (
                <ExpenseProvider paidSet={paidSet} owedSet={owedSet}>
                  <AddExpenseModal
                    closeModal={closeModal}
                    modalHeading={"Add Expense"}
                  />
                </ExpenseProvider>
              )}
            </div>
          ) : null}

          <div
            style={{
              margin: "0px 8px",
            }}
          >
            {groupObject &&
              groupObject.group.ownerID === userObject.user_id && (
                <button
                  style={{
                    color: "white",
                  }}
                  className="button"
                  onClick={() => {
                    settleUp();
                  }}
                >
                  Settle Up
                </button>
              )}
          </div>
        </div>
      </div>
      <div
        style={{
          flex: 10,
        }}
      >
        <ExpenseList />
      </div>
    </div>
  );
}

export default ExpenseComponent;
