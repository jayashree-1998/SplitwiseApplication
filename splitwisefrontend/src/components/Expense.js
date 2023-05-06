import React, { useContext, useState } from "react";
import { GroupObjectContext } from "../context.js/GroupObjectContext";
import { UserObjectContext } from "../context.js/UserObjectContext";
import AddExpenseModal from "./AddExpenseModal";

function Expense() {
  const [groupObject] = useContext(GroupObjectContext);
  const [userObject] = useContext(UserObjectContext);
  const [addExpenseClicked, setAddExpenseClicked] = useState(false);
  const [expenseDetails, setExpenseDetails] = useState({
    groupID: groupObject.groupID,
    expenseName: "",
    amount: 0,
    addedBy: userObject.user_id,
  });

  function openAddExpenseModal() {
    setAddExpenseClicked(true);
  }

  function closeAddExpenseModal() {
    setAddExpenseClicked(false);
  }

  function handleChange(event) {
    const { name, value } = event.target;
    setExpenseDetails((pv) => {
      return {
        ...pv,
        [name]: value,
      };
    });
  }

  function settleup() {}

  function addExpense() {}

  return (
    <div
      style={{
        flex: 7,
        height: "100vh",
        width: "300px",
        backgroundColor: "white",
        display: "flex",
        flexDirection: "column",
      }}
    >
      <div
        style={{
          padding: "8px 8px",
          backgroundColor: "white",
          flex: 1,
          display: "flex",
          flexDirection: "row",
          justifyContent: "space-between",
        }}
      >
        <div>
          <button
            style={{
              color: "white",
            }}
            className="button"
            onClick={() => openAddExpenseModal()}
          >
            Add Expense
          </button>
          {addExpenseClicked && (
            <AddExpenseModal
              closeModal={closeAddExpenseModal}
              modalHeading={"Add Expense"}
              handleChange={handleChange}
              expenseDetails={expenseDetails}
            />
          )}
        </div>

        <div>
          {groupObject.ownerID === userObject.user_id && (
            <button
              style={{
                color: "white",
              }}
              className="button"
              onClick={settleup}
            >
              Settle Up
            </button>
          )}
        </div>
      </div>
      <div
        style={{
          backgroundColor: "green",
          flex: 8,
        }}
      ></div>
    </div>
  );
}
export default Expense;
