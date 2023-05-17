import React, { useContext, useEffect, useState } from "react";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import { GroupDetailContext } from "../contexts/GroupDetailContext";
import { COLOR } from "../utils/constants";
import { Button } from "@mui/material";
import { UserContext } from "../contexts/UserContext";

function TransactionList({ transactionList, amountReceived }) {
  const [userNameIDMap, setUserNameIDMap] = useState(null);
  const [userObject] = useContext(UserContext);
  const [selectedGroup, setSelectedGroup, groupObject, setGroupObject] =
    useContext(GroupDetailContext);

  useEffect(() => {
    let userIDMap = {};
    groupObject.userList.forEach((e, i) => {
      userIDMap[e.userID] = e.name;
    });
    setUserNameIDMap(userIDMap);
  }, [groupObject.userList]);

  return (
    <TableContainer
      component={Paper}
      style={{
        marginTop: "4px",
        borderRadius: "8px",
      }}
    >
      <Table>
        <TableHead
          style={{
            backgroundColor: COLOR.secondaryColor,
          }}
        >
          <TableRow>
            <TableCell
              style={{
                color: COLOR.white,
              }}
            >
              Payer Name
            </TableCell>
            <TableCell
              style={{
                color: COLOR.white,
              }}
            >
              Payee Name
            </TableCell>
            <TableCell
              style={{
                color: COLOR.white,
              }}
            >
              Amount (₹)
            </TableCell>
            <TableCell />
          </TableRow>
        </TableHead>
        <TableBody>
          {userNameIDMap &&
            transactionList.map((row) => (
              <TableRow
                style={{
                  backgroundColor: COLOR.dividerColor,
                }}
                key={row.transactionID}
                sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
              >
                <TableCell>{userNameIDMap[row.payerID]}</TableCell>
                <TableCell>{userNameIDMap[row.payeeID]}</TableCell>
                <TableCell>₹ {row.amount}</TableCell>
                {row.payeeID === userObject.user_id ? (
                  row.settled === false ? (
                    <TableCell>
                      <Button
                        variant="contained"
                        style={{
                          backgroundColor: COLOR.white,
                          color: COLOR.secondaryColor,
                          borderRadius: "8px",
                        }}
                        onClick={() => {
                          amountReceived(row);
                        }}
                      >
                        Received
                      </Button>
                    </TableCell>
                  ) : (
                    <TableCell
                      style={{
                        color: COLOR.white,
                      }}
                    >
                      settled
                    </TableCell>
                  )
                ) : (
                  <TableCell />
                )}
              </TableRow>
            ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}

export default TransactionList;
