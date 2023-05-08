import React, { useState } from "react";
export const ExpenseContext = React.createContext();

export function ExpenseProvider({ children, paidSet, owedSet }) {
  const [paidByList, setPaidByList] = useState(paidSet);
  const [owedByList, setOwedByList] = useState(owedSet);
  return (
    <ExpenseContext.Provider
      value={{
        paidObject: [paidByList, setPaidByList],
        owedObject: [owedByList, setOwedByList],
      }}
    >
      {children}
    </ExpenseContext.Provider>
  );
}
export default ExpenseProvider;
