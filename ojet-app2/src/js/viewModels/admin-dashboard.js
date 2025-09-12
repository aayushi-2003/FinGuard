

// define([
//   'knockout',
//   'ojs/ojarraydataprovider',
//   'ojs/ojchart',
//   'ojs/ojtable'
// ], function(ko, ArrayDataProvider) {
//   function AdminDashboardViewModel() {
//     var self = this;

//     // -----------------------------
//     // Profile Observables
//     // -----------------------------
//     self.username = ko.observable("Admin User");
//     self.email = ko.observable("admin@example.com");
//     self.userId = ko.observable("5");
//     self.roleId = ko.observable("admin");
//     self.balance = ko.observable("8500.25");

//     // -----------------------------
//     // Users Table
//     // -----------------------------
//     self.users = ko.observableArray([]);
//     self.userDataProvider = new ArrayDataProvider(self.users, {
//       keyAttributes: 'userId'
//     });

//     // -----------------------------
//     // Chart 1: Users by Role
//     // -----------------------------
//     self.roleSeries = ko.observableArray([]);
//     self.updateRoleChart = function(users) {
//       let counts = {};
//       users.forEach(u => {
//         counts[u.roleId] = (counts[u.roleId] || 0) + 1;
//       });
//       let series = Object.keys(counts).map(role => ({
//         name: role,
//         items: [counts[role]]
//       }));
//       self.roleSeries(series);
//     };

//     // -----------------------------
//     // Chart 2: Top 5 Users by Balance
//     // -----------------------------
//     self.topUserSeries = ko.observableArray([]);
//     self.topUserGroups = ko.observableArray([]);
//     self.updateTopUsersChart = function(users) {
//       let sorted = [...users].sort((a, b) => b.currentBalance - a.currentBalance);
//       let top5 = sorted.slice(0, 5);
//       let groups = top5.map(u => u.username);
//       let balances = top5.map(u => u.currentBalance);
//       self.topUserGroups(groups);
//       self.topUserSeries([{ name: "Balance", items: balances }]);
//     };

//     // -----------------------------
//     // Chart 3: Balance Distribution
//     // -----------------------------
//     self.balanceDistSeries = ko.observableArray([]);
//     self.balanceDistGroups = ko.observableArray([]);
//     self.updateBalanceDistChart = function(users) {
//       let ranges = { "0 - 5k": 0, "5k - 10k": 0, "10k - 20k": 0, "20k+": 0 };
//       users.forEach(u => {
//         let bal = u.currentBalance;
//         if (bal <= 5000) ranges["0 - 5k"]++;
//         else if (bal <= 10000) ranges["5k - 10k"]++;
//         else if (bal <= 20000) ranges["10k - 20k"]++;
//         else ranges["20k+"]++;
//       });
//       self.balanceDistGroups(Object.keys(ranges));
//       self.balanceDistSeries([{ name: "Users", items: Object.values(ranges) }]);
//     };

//     // -----------------------------
//     // Chart 4: Total Balance Share by Role
//     // -----------------------------
//     self.balanceShareSeries = ko.observableArray([]);
//     self.updateBalanceShareChart = function(users) {
//       let totals = {};
//       users.forEach(u => {
//         totals[u.roleId] = (totals[u.roleId] || 0) + u.currentBalance;
//       });
//       let series = Object.keys(totals).map(role => ({
//         name: role,
//         items: [totals[role]]
//       }));
//       self.balanceShareSeries(series);
//     };

//     // -----------------------------
//     // Fetch Users
//     // -----------------------------
//     self.fetchUsers = function() {
//       fetch("http://localhost:8060/users")
//         .then(res => res.json())
//         .then(data => {
//           console.log("Fetched users:", data);
//           self.users(data);

//           // Update profile card with first admin (example)
//           let adminUser = data.find(u => u.roleId === "admin");
//           if (adminUser) {
//             self.username(adminUser.username);
//             self.email(adminUser.email);
//             self.userId(adminUser.userId);
//             self.roleId(adminUser.roleId);
//             self.balance(adminUser.currentBalance);
//           }

//           // Update charts
//           self.updateRoleChart(data);
//           self.updateTopUsersChart(data);
//           self.updateBalanceDistChart(data);
//           self.updateBalanceShareChart(data);
//         })
//         .catch(err => console.error("Error fetching users:", err));
//     };

//     self.fetchUsers();
//   }
//   return AdminDashboardViewModel;
// });

define([
  'knockout',
  'ojs/ojarraydataprovider',
  'ojs/ojchart',
  'ojs/ojtable'
], function(ko, ArrayDataProvider) {
  function AdminDashboardViewModel() {
    var self = this;

    // -----------------------------
    // Profile Observables (empty initially)
    // -----------------------------
    self.username = ko.observable("");
    self.email = ko.observable("");
    self.userId = ko.observable("");
    self.roleId = ko.observable("");
    self.balance = ko.observable("");

    // -----------------------------
    // Users Table
    // -----------------------------
    self.users = ko.observableArray([]);
    self.userDataProvider = new ArrayDataProvider(self.users, {
      keyAttributes: 'userId'
    });

    // -----------------------------
    // Chart Observables
    // -----------------------------
    self.roleSeries = ko.observableArray([]);
    self.topUserSeries = ko.observableArray([]);
    self.topUserGroups = ko.observableArray([]);
    self.balanceDistSeries = ko.observableArray([]);
    self.balanceDistGroups = ko.observableArray([]);
    self.balanceShareSeries = ko.observableArray([]);

    // -----------------------------
    // Fetch Logged-in User Profile
    // -----------------------------
    self.fetchProfile = function() {
      let uid = localStorage.getItem("userId");
      if (!uid) {
        console.warn("No userId found in localStorage");
        return;
      }

      fetch(`http://localhost:8060/users/${uid}`)
        .then(res => res.json())
        .then(data => {
          self.username(data.username);
          self.email(data.email);
          self.userId(data.userId);
          self.roleId(data.roleId);
          self.balance(data.currentBalance);
        })
        .catch(err => console.error("Error fetching profile:", err));
    };

    // -----------------------------
    // Chart Update Functions
    // -----------------------------
    self.updateRoleChart = function(users) {
      let counts = {};
      users.forEach(u => counts[u.roleId] = (counts[u.roleId] || 0) + 1);
      self.roleSeries(Object.keys(counts).map(role => ({
        name: role, items: [counts[role]]
      })));
    };

    self.updateTopUsersChart = function(users) {
      let sorted = [...users].sort((a, b) => b.currentBalance - a.currentBalance);
      let top5 = sorted.slice(0, 5);
      self.topUserGroups(top5.map(u => u.username));
      self.topUserSeries([{ name: "Balance", items: top5.map(u => u.currentBalance) }]);
    };

    self.updateBalanceDistChart = function(users) {
      let ranges = { "0 - 5k": 0, "5k - 10k": 0, "10k - 20k": 0, "20k+": 0 };
      users.forEach(u => {
        let bal = u.currentBalance;
        if (bal <= 5000) ranges["0 - 5k"]++;
        else if (bal <= 10000) ranges["5k - 10k"]++;
        else if (bal <= 20000) ranges["10k - 20k"]++;
        else ranges["20k+"]++;
      });
      self.balanceDistGroups(Object.keys(ranges));
      self.balanceDistSeries([{ name: "Users", items: Object.values(ranges) }]);
    };

    self.updateBalanceShareChart = function(users) {
      let totals = {};
      users.forEach(u => totals[u.roleId] = (totals[u.roleId] || 0) + u.currentBalance);
      self.balanceShareSeries(Object.keys(totals).map(role => ({
        name: role, items: [totals[role]]
      })));
    };

    // -----------------------------
    // Fetch All Users
    // -----------------------------
    self.fetchUsers = function() {
      fetch("http://localhost:8060/users")
        .then(res => res.json())
        .then(data => {
          console.log("Fetched users:", data);
          self.users(data);

          // Update charts
          self.updateRoleChart(data);
          self.updateTopUsersChart(data);
          self.updateBalanceDistChart(data);
          self.updateBalanceShareChart(data);
        })
        .catch(err => console.error("Error fetching users:", err));
    };

    // -----------------------------
    // Init Calls
    // -----------------------------
    self.fetchProfile();
    self.fetchUsers();
  }

  return AdminDashboardViewModel;
});
