import React, { useEffect, useState } from "react";
import Typography from "@mui/material/Typography";
import { GroupDetailContext } from "../contexts/GroupDetailContext";
import { Accordion, AccordionDetails, AccordionSummary } from "@mui/material";
import { timePattern } from "../utils/constants";
import DeleteIcon from "@mui/icons-material/Delete";
import { deleteExpense } from "../services/expenseService";
import { toast } from "react-toastify";

function ExpenseList() {
  const [selectedGroup, setSelectedGroup, groupObject, setGroupObject] =
    React.useContext(GroupDetailContext);

  const [userNameIDMap, setUserNameIDMap] = useState(null);

  useEffect(() => {
    let userIDMap = {};
    groupObject.userList.forEach((e, i) => {
      userIDMap[e.userID] = e.name;
    });
    setUserNameIDMap(userIDMap);
  }, [groupObject.userList]);

  const removeExpense = async (expenseID) => {
    console.log(expenseID);
    const responseData = await deleteExpense(expenseID);
    if (responseData.data.success === true) {
      // remove the expense from groupObject and update the groupObject
      let expenseList = groupObject.expenseList;
      expenseList = expenseList.filter((e, i) => {
        return e.expenseID !== expenseID;
      });
      setGroupObject((pv) => {
        return {
          ...pv,
          expenseList: expenseList,
        };
      });
      toast.success(responseData.data.object);
    } else {
      toast.error(responseData.data.object);
    }
  };

  return (
    <div>
      {userNameIDMap &&
        groupObject.expenseList &&
        groupObject.expenseList.map((e, i) => {
          return (
            <div key={e.expenseID}>
              <Accordion
                style={{
                  margin: "4px 8px",
                  backgroundColor: "#7b7b7b",
                }}
              >
                <AccordionSummary aria-controls="panel1d-content">
                  <div
                    style={{
                      margin: "0px 8px",
                      display: "flex",
                      flexDirection: "row",
                      justifyContent: "space-between",
                      flex: 1,
                    }}
                  >
                    <div
                      style={{
                        display: "flex",
                        flexDirection: "row",
                        justifyContent: "space-between",
                      }}
                    >
                      <Typography style={{ marginRight: "8px" }}>
                        <label>{e.expenseName}</label>
                      </Typography>
                      <Typography>
                        <label>₹{e.amount}</label>
                      </Typography>
                    </div>
                    <Typography
                      style={{
                        display: "flex",
                        flex: 1,
                        justifyContent: "flex-end",
                      }}
                    >
                      {userNameIDMap[e.addedBy] !== undefined && (
                        <label>
                          Added by {userNameIDMap[e.addedBy].substring(0, 10)}{" "}
                          on {timePattern(e.date)}
                        </label>
                      )}
                      {groupObject.group.settled === false && (
                        <DeleteIcon
                          htmlColor="#3d3d3d"
                          style={{
                            marginLeft: "16px",
                            alignSelf: "center",
                          }}
                          onClick={(c) => {
                            c.stopPropagation();
                            if (window.confirm("Delete Expense ? ")) {
                              removeExpense(e.expenseID);
                            }
                          }}
                        />
                      )}
                    </Typography>
                  </div>
                </AccordionSummary>
                <AccordionDetails
                  style={{
                    backgroundColor: "#c2c2c2",
                  }}
                >
                  <div
                    style={{
                      margin: "8px 0px",
                      display: "flex",
                      flexDirection: "column",
                    }}
                  >
                    {e.paidSet.map((p, i) => {
                      return (
                        <Typography key={p.paidID}>
                          {userNameIDMap[p.userID]} paid ₹{p.amount}
                        </Typography>
                      );
                    })}
                  </div>
                  <div
                    style={{
                      margin: "8px 0px",
                      display: "flex",
                      flexDirection: "column",
                    }}
                  >
                    {e.oweSet.map((o, i) => {
                      return (
                        <Typography key={o.oweID}>
                          {userNameIDMap[o.userID]} owes ₹{o.amount}
                        </Typography>
                      );
                    })}
                  </div>
                </AccordionDetails>
              </Accordion>
            </div>
          );
        })}
    </div>
  );
}

export default ExpenseList;
