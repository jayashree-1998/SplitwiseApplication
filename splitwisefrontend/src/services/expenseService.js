import axios from "axios";
import { IPAddress } from "../utils/constants";

const addGroupExpenseAPI = `http://${IPAddress}/expense/add-expense`;

const deleteExpenseAPI = (expenseID) =>
  `http://${IPAddress}/expense/delete-expense/${expenseID}`;

const settleGroupAPI = (groupID) =>
  `http://${IPAddress}/expense/settle-up/${groupID}`;

const getTransactionsAPI = (groupID) =>
  `http://${IPAddress}/expense/show-transaction/${groupID}`;

async function addGroupExpense(expenseObject) {
  console.log(expenseObject);
  const responseData = await axios.post(addGroupExpenseAPI, expenseObject);
  return responseData;
}

async function deleteExpense(expenseID) {
  const responseData = await axios.delete(deleteExpenseAPI(expenseID));
  return responseData;
}

async function settleGroup(groupID) {
  const responseData = await axios.get(settleGroupAPI(groupID));
  return responseData;
}

async function getTransactions(groupID) {
  const responseData = await axios.get(getTransactionsAPI(groupID));
  return responseData;
}

export { addGroupExpense, deleteExpense, settleGroup, getTransactions };
