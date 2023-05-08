import * as React from "react";
import { styled } from "@mui/material/styles";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import MuiAccordion from "@mui/material/Accordion";
import MuiAccordionSummary from "@mui/material/AccordionSummary";
import MuiAccordionDetails from "@mui/material/AccordionDetails";
import Typography from "@mui/material/Typography";
import { GroupDetailContext } from "../contexts/GroupDetailContext";
import { Accordion, AccordionDetails, AccordionSummary } from "@mui/material";

// const Accordion = styled((props) => (
//   <MuiAccordion disableGutters elevation={0} square {...props} />
// ))(({ theme }) => ({
//   border: `1px solid ${theme.palette.divider}`,
//   "&:not(:last-child)": {
//     borderBottom: 0,
//   },
//   "&:before": {
//     display: "none",
//   },
// }));

// const AccordionSummary = styled((props) => (
//   <MuiAccordionSummary
//     expandIcon={<ArrowForwardIosSharpIcon sx={{ fontSize: "0.9rem" }} />}
//     {...props}
//   />
// ))(({ theme }) => ({
//   backgroundColor:
//     theme.palette.mode === "dark"
//       ? "rgba(255, 255, 255, .05)"
//       : "rgba(0, 0, 0, .03)",
//   flexDirection: "row-reverse",
//   "& .MuiAccordionSummary-expandIconWrapper.Mui-expanded": {
//     transform: "rotate(90deg)",
//   },
//   "& .MuiAccordionSummary-content": {
//     marginLeft: theme.spacing(1),
//   },
// }));

// const AccordionDetails = styled(MuiAccordionDetails)(({ theme }) => ({
//   padding: theme.spacing(2),
//   borderTop: "1px solid rgba(0, 0, 0, .125)",
// }));

function ExpenseList() {
  const [selectedGroup, setSelectedGroup, groupObject, setGroupObject] =
    React.useContext(GroupDetailContext);

  return (
    <div>
      {groupObject.expenseList &&
        groupObject.expenseList.map((e, i) => {
          return (
            <div key={e.expenseID}>
              <Accordion>
                <AccordionSummary
                  expandIcon={<ExpandMoreIcon style={{ cursor: "pointer" }} />}
                  sx={{ cursor: "unset !important" }}
                  aria-controls="panel1d-content"
                >
                  <Typography>{e.expenseName}</Typography>
                </AccordionSummary>
                <AccordionDetails>
                  <Typography> {e.amount}</Typography>
                </AccordionDetails>
              </Accordion>
            </div>
          );
        })}
    </div>
  );
}

export default ExpenseList;
