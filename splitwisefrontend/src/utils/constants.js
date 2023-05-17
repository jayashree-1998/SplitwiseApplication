export const IPAddress = "localhost:9084";

export const COLOR = {
  primaryColor: "#306eb1",
  secondaryColor: "#183a5f",
  dividerColor: "#4e76a2",
  tertiaryColor: "#c1cddb",
  white: "#ffffff",
  gray: "#878787",
};

export const MONTHS = [
  "Jan",
  "Feb",
  "March",
  "April",
  "May",
  "June",
  "July",
  "Aug",
  "Sept",
  "Oct",
  "Nov",
  "Dec",
];

const getMonth = (i) => MONTHS[i];

export const timePattern = (dateString) => {
  const today = new Date(Date.parse(dateString));
  const date =
    today.getDate() +
    " " +
    getMonth(today.getMonth()) +
    " " +
    today.getFullYear();
  return date;
};
