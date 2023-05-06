import React from "react";

function InputField(props) {
  return (
    <>
      <span className="details">{props.title}</span>
      <input
        name={props.name}
        type={props.type}
        value={props.value}
        onChange={props.onChange}
        placeholder={props.placeholder}
        required
      />
    </>
  );
}

export default InputField;
