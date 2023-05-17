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
  Divider,
  Typography,
} from "@mui/material";
import ExpenseList from "./ExpenseList";
import {
  getTransactions,
  settleGroup,
  settleTransaction,
} from "../services/expenseService";
import { toast } from "react-toastify";
import TransactionList from "./TransactionList";
import { COLOR } from "../utils/constants";

function ExpenseComponent() {
  const [showAddExpenseModal, setShowAddExpenseModal] = useState(false);
  const [userObject, setUserObject] = useContext(UserContext);
  const [selectedGroup, setSelectedGroup, groupObject, setGroupObject] =
    useContext(GroupDetailContext);
  const [showExpenseList, setShowExpenseList] = useState(true);
  const [transactionList, setTransactionList] = useState(null);

  const [paidSet, setPaidSet] = useState([]);
  const [owedSet, setOwedSet] = useState([]);

  useEffect(() => {
    console.log(groupObject.expenseList);
    setShowExpenseList(true);
  }, [groupObject.expenseList]);

  function openModal() {
    if (groupObject.userList.length <= 1) {
      alert("Group has only one member!");
      return;
    }
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

  const makeExpensesSettled = () => {
    console.log(groupObject.expenseList);
    let updateExpenseList = groupObject.expenseList.map((e, i) => {
      return {
        ...e,
        settled: true,
      };
    });
    setGroupObject((pv) => {
      return {
        ...pv,
        expenseList: updateExpenseList,
      };
    });
  };

  async function settleUp() {
    if (groupObject.expenseList.length === 0) {
      alert("Please add expense");
      return;
    } else {
      console.log("settling group");
      const responseData = await settleGroup(groupObject.group.groupID);
      if (responseData.data.success === true) {
        // update the expenses in expenseList by settle = true
        makeExpensesSettled();
        toast.success("settled group!");
      } else {
        toast.error(responseData.data.object);
      }
    }
  }

  async function showTransactions() {
    const responseData = await getTransactions(groupObject.group.groupID);
    if (responseData.data.success === true) {
      // store the transaction in a transaction list
      console.log(responseData.data.object);
      setShowExpenseList(false);
      setTransactionList(responseData.data.object);
    } else {
      toast.error("Cannot show transactions!");
    }
  }

  const amountReceived = async (transaction) => {
    if (window.confirm("Amount Received?")) {
      const responseData = await settleTransaction(transaction.transactionID);
      if (responseData.data.success === true) {
        transaction.settled = true;
        let newTransactions = transactionList.filter((t, i) => {
          return t.transactionID !== transaction.transactionID;
        });
        setTransactionList((pv) => {
          return [...newTransactions, transaction];
        });
        toast.success("Transaction settled!");
      } else {
        toast.error("Error settling the transaction");
      }
    }
  };

  return (
    <div
      style={{
        flex: 1,
        backgroundColor: COLOR.white,
        display: "flex",
        flexDirection: "column",
      }}
    >
      <div
        style={{
          backgroundColor: "white",
          display: "flex",
          flexDirection: "row",
          alignItems: "flex-end",
          justifyContent: "space-between",
          margin: "12px 0px",
        }}
      >
        <div>
          <label
            style={{
              marginLeft: "8px",
              fontSize: "28px",
              fontWeight: "lighter",
            }}
          >
            {groupObject.group.name}
          </label>
        </div>
        <div
          style={{
            display: "flex",
            flexDirection: "row",
          }}
        >
          <div
            style={{
              marginRight: "8px",
            }}
          >
            <button
              style={{
                backgroundColor: COLOR.secondaryColor,
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
          {groupObject && (
            <div
              style={{
                marginRight: "8px",
              }}
            >
              <button
                style={{
                  color: "white",
                  backgroundColor: COLOR.primaryColor,
                }}
                className="button"
                onClick={async () => {
                  if (window.confirm("Do you want to settle up the group?")) {
                    await settleUp();
                  }
                }}
              >
                Settle Up
              </button>
            </div>
          )}
          <div
            style={{
              marginRight: "8px",
            }}
          >
            {showExpenseList === true ? (
              <button
                style={{
                  color: "white",
                  backgroundColor: COLOR.primaryColor,
                }}
                className="button"
                onClick={async () => {
                  await showTransactions();
                }}
              >
                Show Transactions
              </button>
            ) : (
              <button
                style={{
                  color: "white",
                  backgroundColor: COLOR.primaryColor,
                }}
                className="button"
                onClick={() => {
                  setShowExpenseList(true);
                }}
              >
                Show Expense List
              </button>
            )}
          </div>
        </div>
      </div>
      <Divider
        variant="middle"
        style={{
          backgroundColor: COLOR.dividerColor,
        }}
      />
      <div
        style={{
          marginTop: "8px",
          marginLeft: "8px",
          marginRight: "8px",
          flex: 10,
          display: "flex",
        }}
      >
        {showExpenseList ? (
          groupObject.expenseList?.length !== 0 ? (
            <ExpenseList />
          ) : (
            <div
              style={{
                flex: 1,
                display: "flex",
                justifyContent: "center",
                alignItems: "center",
                color: COLOR.gray,
              }}
            >
              no expense added
            </div>
          )
        ) : (
          <TransactionList
            transactionList={transactionList}
            amountReceived={amountReceived}
          />
        )}
      </div>
    </div>
  );
}

export default ExpenseComponent;
