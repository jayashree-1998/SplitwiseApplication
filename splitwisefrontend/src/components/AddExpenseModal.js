import React, { useState } from "react";
import PaidByModal from "./PaidByModal";

const AddExpenseModal = ({
  closeModal,
  modalHeading,
  handleChange,
  expenseDetails,
}) => {
  const [paidByClicked, setPaidByClicked] = useState(false);
  const [owedByClicked, setOwedByClicked] = useState(false);

  function openPaidByModal() {
    setPaidByClicked(true);
  }
  function closePaidByModal() {
    setPaidByClicked(false);
  }

  function openOwedByModal() {
    setOwedByClicked(true);
  }
  function closeOwedByModal() {
    setOwedByClicked(false);
  }

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
            Close
          </button>
        </div>
        <div className="input-box" style={{ display: "flex" }}>
          <span className="details">Name</span>
          <input
            name="expenseName"
            type="text"
            value={expenseDetails.expenseName}
            placeholder={"Expense name"}
            onChange={handleChange}
          />
        </div>
        <div className="input-box" style={{ display: "flex" }}>
          <span className="details">Amount</span>
          <input
            name="amount"
            type="number"
            minvalue={1}
            value={expenseDetails.amount}
            placeholder={"amount"}
            onChange={handleChange}
          />
        </div>

        <div>
          {/* paid by button */}
          <button className="button" onClick={() => openPaidByModal()}>
            Paid By
          </button>

          {paidByClicked && (
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
          {/* {owedByClicked && <OwedByModal modalHeading= {"Select Payees"} closeModal={closeOwedByModal}/>} */}
        </div>

        <button className="button" onClick={() => {}}>
          Add
        </button>
      </div>
    </div>
  );
};

export default AddExpenseModal;
