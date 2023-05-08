import axios from "axios";
import { IPAddress } from "../utils/constants";

const addGroupExpenseAPI = `http://${IPAddress}/expense/add-expense`;

async function addGroupExpense(expenseObject) {
  console.log(expenseObject);
  const responseData = await axios.post(addGroupExpenseAPI, expenseObject);
  return responseData;
}

export { addGroupExpense };
