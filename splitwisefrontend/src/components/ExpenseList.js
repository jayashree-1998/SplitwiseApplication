import React, { useEffect, useState } from "react";
import Typography from "@mui/material/Typography";
import { GroupDetailContext } from "../contexts/GroupDetailContext";
import { Accordion, AccordionDetails, AccordionSummary } from "@mui/material";
import { COLOR, timePattern } from "../utils/constants";
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
    <div
      style={{
        flex: 1,
      }}
    >
      {userNameIDMap &&
        groupObject.expenseList &&
        groupObject.expenseList.map((e, i) => {
          console.log(e);
          return (
            <div key={e.expenseID}>
              <Accordion
                style={{
                  margin: "4px 0px",
                  backgroundColor: COLOR.dividerColor,
                  borderRadius: "8px",
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
                      <Typography
                        style={{
                          marginRight: "8px",
                        }}
                      >
                        <label>{e.expenseName}</label>
                      </Typography>
                      <Typography
                        style={{
                          color: COLOR.tertiaryColor,
                        }}
                      >
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
                          Added by{" "}
                          <label
                            style={{
                              color: COLOR.tertiaryColor,
                            }}
                          >
                            {userNameIDMap[e.addedBy].length > 10
                              ? `${userNameIDMap[e.addedBy].substring(
                                  0,
                                  10
                                )}.. `
                              : `${userNameIDMap[e.addedBy].substring(0, 10)} `}
                          </label>
                          on {timePattern(e.date)}
                        </label>
                      )}
                      {groupObject.group.settled === false && (
                        <DeleteIcon
                          htmlColor={COLOR.tertiaryColor}
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
                    backgroundColor: COLOR.secondaryColor,
                    borderBottomLeftRadius: "8px",
                    borderBottomRightRadius: "8px",
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
                        <Typography
                          key={p.userID}
                          style={{
                            color: COLOR.dividerColor,
                          }}
                        >
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
                        <Typography
                          key={o.userID}
                          style={{
                            color: COLOR.dividerColor,
                          }}
                        >
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
