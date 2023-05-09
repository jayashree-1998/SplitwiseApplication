import React, { useState } from "react";
import { login } from "../services/loginService";
import InputField from "../components/InputField";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

function Login() {
  const navigate = useNavigate();
  const [loginData, setLoginData] = useState({
    email: "",
    password: "",
  });

  function handleChange(event) {
    const { name, value } = event.target;
    setLoginData((pv) => {
      return {
        ...pv,
        [name]: value,
      };
    });
  }

  async function onSubmit(event) {
    event.preventDefault();
    const responseData = await login(loginData);
    console.log(responseData.data);
    const data = responseData.data;
    if (data) {
      if (data.success === true && data.object.email === loginData.email) {
        toast.success("Welcome");
        navigate("/mainpage", {
          state: {
            loginResponse: data.object,
          },
        });
      } else {
        toast.error(data.object);
      }
    }
  }

  function register() {
    navigate("/register");
  }

  return (
    <div className="formPage">
      <div className="container">
        <div className="title">LOGIN</div>
        <div
          className="content"
          style={{ display: "flex", justifyContent: "center" }}
        >
          <form onSubmit={onSubmit}>
            <div className="user-details">
              <div className="input-box">
                <InputField
                  title={"Email"}
                  name={"email"}
                  type={"email"}
                  value={loginData.email}
                  placeholder={"Email"}
                  onChange={handleChange}
                />
              </div>
              <div className="input-box">
                <InputField
                  title={"Password"}
                  name="password"
                  type="password"
                  value={loginData.password}
                  placeholder={"Password"}
                  onChange={handleChange}
                />
              </div>
            </div>
            <div className="button">
              <input type="submit" value="LOGIN" />
            </div>
            <div
              style={{
                display: "flex",
                width: "100%",
                flex: 1,
                justifyContent: "flex-start",
                alignItems: "center",
                flexDirection: "row",
              }}
            >
              <div>
                <label
                  style={{
                    fontWeight: "100",
                    fontSize: "14px",
                  }}
                >
                  Don't have an account?&nbsp;
                </label>
              </div>
              <div>
                <button
                  className="button2"
                  style={{
                    color: "#636363",
                    fontWeight: "400",
                    fontSize: "14px",
                  }}
                  onClick={register}
                >
                  Create account
                </button>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}

export default Login;
