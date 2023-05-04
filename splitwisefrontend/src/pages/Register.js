import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import InputField from "../components/InputField";
import { register } from "../services/loginService";

function Register() {
  const navigate = useNavigate();
  const [confirmPassword, setconfirmPassword] = useState("");
  const [userData, setUserData] = useState({
    name: "",
    email: "",
    password: "",
    mobileNumber: "",
  });

  async function onRegister(event) {
    event.preventDefault();
    if (confirmPassword !== userData.password) {
      toast.error("New password and Confirm Password don't match");
    } else {
      const responseData = await register(userData);
      const data = responseData.data;
      console.log(data);
      if (data && data.success === true) {
        toast.success(data.object);
        navigate("/login");
      } else {
        toast.error(data.object);
      }
    }
  }

  function handleChange(event) {
    const { name, value } = event.target;
    if (name === "confirmPassword") {
      setconfirmPassword(value);
    } else {
      setUserData((pv) => {
        return {
          ...pv,
          [name]: value,
        };
      });
    }
  }

  return (
    <div className="formPage">
      <div className="container">
        <div className="title">REGISTER</div>
        <div
          className="content"
          style={{ display: "flex", justifyContent: "center" }}
        >
          <form onSubmit={onRegister}>
            <div className="user-details">
              <div className="input-box">
                <span className="details">Name</span>
                <input
                  name="name"
                  type="text"
                  placeholder="Name"
                  value={userData.name}
                  onChange={handleChange}
                  required
                />
              </div>
              <div className="input-box">
                <span className="details">Mobile Number</span>
                <input
                  name="mobileNumber"
                  type="text"
                  minLength={10}
                  maxLength={10}
                  pattern="[1-9]{1}[0-9]{9}"
                  title="mobile no can only be between 0 to 9"
                  placeholder="+91"
                  value={userData.mobileNumber}
                  onChange={handleChange}
                  required
                />
              </div>

              <div className="input-box">
                <span className="details">Email</span>
                <input
                  name="email"
                  type="email"
                  placeholder="Email"
                  value={userData.email}
                  onChange={handleChange}
                  required
                />
              </div>
              <div className="input-box">
                <span className="details">Password</span>
                <input
                  name="password"
                  type="password"
                  placeholder="Password"
                  value={userData.password}
                  onChange={handleChange}
                  required
                />
              </div>
              <div className="input-box">
                <InputField
                  title={"Confirm Password"}
                  name="confirmPassword"
                  type="password"
                  placeholder="ReEnter Password"
                  value={confirmPassword}
                  onChange={handleChange}
                />
              </div>
            </div>
            <div className="button">
              <input type="submit" value="REGISTER" />
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}

export default Register;
