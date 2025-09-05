define([
  "require",
  "exports",
  "knockout",
  "appController",
  "ojs/ojbootstrap",
  "ojs/ojknockout",
  "ojs/ojbutton",
  "ojs/ojformlayout",
  "ojs/ojinputtext"
], function (require, exports, ko, app) {
  function LoginViewModel() {
    var self = this;
    self.username = ko.observable("");
    self.pwd = ko.observable("");
    self.signup=()=>{
      app.goToPage("signuppage");
    }

    self.login = () => {
      const payload = {
        username: self.username(),
        password: self.pwd()
      };

      // 👇 Adjust URL based on your backend's port and path
      fetch("http://localhost:8060/users/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(payload)
      })
        .then(async (response) => {
          if (response.ok) {
            const data = await response.json();

            // ✅ Successful login
            localStorage.setItem("isConnected", true);
            localStorage.setItem("userRole", data.roleId); // assuming roleId is returned
            localStorage.setItem("userName", data.username);
            localStorage.setItem("userId", data.userId);

            app.isConnected(true);
            app.userLogin(data.username);

            if (data.roleId.toLowerCase() === "admin") {
              app.setAdminMenu();
              app.goToPage("products");
            } else {
              app.goToPage("dashboard");
            }
          } else {
            // ❌ Invalid credentials
            alert("Invalid username or password");
          }
        })
        .catch((error) => {
          console.error("Login error:", error);
          alert("Error connecting to server. Please try again.");
        });
    };
  }

  return LoginViewModel;
});
