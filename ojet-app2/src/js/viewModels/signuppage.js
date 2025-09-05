define([
  "require",
  "exports",
  "knockout",
  "appController",
  "ojs/ojbootstrap",
  "ojs/ojknockout",
  "ojs/ojbutton",
  "ojs/ojformlayout",
  "ojs/ojinputtext",
  "ojs/ojselectcombobox"
], function (require, exports, ko, app) {
  function SignupViewModel() {
    var self = this;

    // Observables for form fields
    self.userid=ko.observable("");
    self.username = ko.observable("");
    self.email = ko.observable("");
    self.password = ko.observable("");
    self.roleId = ko.observable("user"); // default role

    self.roles = [
      { value: "user", label: "User" },
      { value: "admin", label: "Admin" },
      { value: "advisor", label: "Advisor" }
    ];

    self.signup = () => {
      const payload = {
        userId: self.userid(),
        username: self.username(),
        email: self.email(),
        password: self.password(),
        roleId: self.roleId(),
        currentBalance: 0 // Default starting balance (or remove if backend assigns it)
      };

      fetch("http://localhost:8060/users", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(payload)
      })
        .then(async (response) => {
          if (response.ok) {
            const data = await response.json();
            alert("Signup successful. Please login.");
            app.goToPage("loginpage");
          } else {
            alert("Signup failed. Try again.");
          }
        })
        .catch((err) => {
          console.error("Signup error:", err);
          alert("Error connecting to server.");
        });
    };
  }

  return SignupViewModel;
});
