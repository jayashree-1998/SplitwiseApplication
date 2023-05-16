import React, { useEffect, useState } from "react";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import { GroupDetailContext } from "../contexts/GroupDetailContext";
import { COLOR } from "../utils/constants";

function TransactionList({ transactionList }) {
  const [userNameIDMap, setUserNameIDMap] = useState(null);
  const [selectedGroup, setSelectedGroup, groupObject, setGroupObject] =
    React.useContext(GroupDetailContext);

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
              </TableRow>
            ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}

export default TransactionList;
