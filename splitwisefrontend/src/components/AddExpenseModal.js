import React, { useContext, useEffect, useState } from "react";
import PaidByModal from "./PaidByModal";
import { GroupDetailContext } from "../contexts/GroupDetailContext";
import { ExpenseContext } from "../contexts/ExpenseContext";
import { UserContext } from "../contexts/UserContext";
import OwedByModal from "./OwedByModal";
import { addGroupExpense } from "../services/expenseService";
import { toast } from "react-toastify";

const AddExpenseModal = ({ closeModal, modalHeading }) => {
  const [showPaidModal, setShowPaidModal] = useState(false);
  const [showOwedModal, setShowOwedModal] = useState(false);
  const [selectedGroup, setSelectedGroup, groupObject, setGroupObject] =
    useContext(GroupDetailContext);

  const [userObject, setUserObject] = useContext(UserContext);

  const [expenseObject, setExpenseObject] = useState({
    groupID: groupObject.group.groupID.toString(),
    expenseName: "",
    amount: 0,
    addedBy: userObject.user_id.toString(),
  });
  const { paidObject, owedObject } = useContext(ExpenseContext);

  const [paidByList, setPaidByList] = paidObject;
  const [owedByList, setOwedByList] = owedObject;

  const customHandle = (name, value) => {
    if (name === "amount") {
      if (isNaN(value) || Number(value) < 0) {
        setExpenseObject((pv) => {
          return {
            ...pv,
            [name]: 0,
          };
        });
      } else {
        setExpenseObject((pv) => {
          return {
            ...pv,
            [name]: Number(value),
          };
        });
      }
    } else {
      setExpenseObject((pv) => {
        return {
          ...pv,
          [name]: value,
        };
      });
    }
  };

  function handleChange(event) {
    const { name, value } = event.target;
    customHandle(name, value);
  }

  const addExpense = async (paidSet, owedSet) => {
    expenseObject["paidBySet"] = paidSet;
    expenseObject["owedBySet"] = owedSet;
    // returns expenseID
    const responseData = await addGroupExpense(expenseObject);
    if (responseData.data.success === true) {
      let expenseList = groupObject.expenseList;
      let newExpenseObject = {
        expenseID: responseData.data.object,
        expenseName: expenseObject.expenseName,
        groupID: groupObject.group.groupID,
        amount: expenseObject.amount,
        date: new Date().toISOString(),
        addedBy: userObject.user_id,
        paidSet: paidSet,
        oweSet: owedSet,
      };
      expenseList.push(newExpenseObject);
      setGroupObject((pv) => {
        return {
          ...pv,
          expenseList: expenseList,
        };
      });
      toast.success("Expense Added!");
      closeModal();
    } else {
      toast.error(responseData.data.object);
    }
  };

  const createSet = () => {
    // convert to our requirement
    let paidSum = 0;
    let oweSum = 0;
    let paidSet = [];
    let owedSet = [];
    for (const [key, value] of Object.entries(paidByList)) {
      paidSum = paidSum + value;
      if (value > 0) {
        paidSet.push({
          userID: key,
          amount: value,
        });
      }
    }
    for (const [key, value] of Object.entries(owedByList)) {
      oweSum = oweSum + value;
      if (value > 0) {
        owedSet.push({
          userID: key,
          amount: value,
        });
      }
    }
    console.log(paidSum, oweSum, expenseObject.amount);
    if (expenseObject.amount === oweSum && expenseObject.amount === oweSum) {
      console.log(owedSet);
      console.log(paidSet);
      addExpense(paidSet, owedSet);
    } else if (
      expenseObject.amount === oweSum &&
      expenseObject.amount !== oweSum
    ) {
      alert("Amount not equal to total pay amount");
    } else if (
      expenseObject.amount !== oweSum &&
      expenseObject.amount === oweSum
    ) {
      alert("Amount not equal to total owe amount");
    } else {
      alert("Amount not equal to total owe and total pay amount");
    }
  };

  const checkExpensePayload = () => {
    if (expenseObject.expenseName.length <= 0) {
      alert("Enter Expense Name");
      return;
    }
    if (expenseObject.amount <= 0) {
      alert("Enter Amount");
      return;
    }
    createSet();
  };

  function openPaidByModal() {
    setShowPaidModal(true);
  }
  function closePaidByModal() {
    setShowPaidModal(false);
  }

  function openOwedByModal() {
    setShowOwedModal(true);
  }
  function closeOwedByModal() {
    setShowOwedModal(false);
  }

  return (
    <div className="modal-wrapper" style={{ padding: 10 }}>
      <div
        className="modal-container"
        style={{
          height: "auto",
        }}
      >
        <div
          style={{
            display: "flex",
            flexDirection: "row",
            justifyContent: "space-between",
          }}
        >
          <label>{modalHeading}</label>
          <button className="button" onClick={closeModal}>
            Close
          </button>
        </div>
        <div className="input-box" style={{ display: "flex" }}>
          <input
            name="expenseName"
            type="text"
            value={expenseObject.expenseName}
            placeholder={"Expense Name"}
            onChange={handleChange}
          />
        </div>
        <div className="input-box" style={{ display: "flex" }}>
          <input
            name="amount"
            type="text"
            min={"0"}
            maxLength={6}
            value={expenseObject.amount}
            placeholder={"Amount"}
            onChange={handleChange}
          />
        </div>

        <div>
          {/* paid by button */}
          <button className="button" onClick={() => openPaidByModal()}>
            Paid By
          </button>

          {showPaidModal && (
            <PaidByModal
              modalHeading={"Select Payers"}
              closeModal={closePaidByModal}
            />
          )}
        </div>

        <div>
          {/* owed by button */}
          <button className="button" onClick={() => openOwedByModal()}>
            Owed By
          </button>
          {showOwedModal && (
            <OwedByModal
              modalHeading={"Select Payers"}
              closeModal={closeOwedByModal}
            />
          )}
        </div>

        <button
          className="button"
          onClick={() => {
            checkExpensePayload();
          }}
        >
          Add
        </button>
      </div>
    </div>
  );
};

export default AddExpenseModal;
