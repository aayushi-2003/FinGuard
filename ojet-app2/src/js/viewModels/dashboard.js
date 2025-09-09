define([
  '../accUtils',
  "require", "exports", "knockout", "ojs/ojbootstrap", 
  "text!../roleData.json",
  "ojs/ojarraydataprovider", "ojs/ojknockout", "ojs/ojchart", "ojs/ojtoolbar"
], function(accUtils, require, exports, ko, ojbootstrap_1, data, ArrayDataProvider) {
  
  function DashboardViewModel() {
    accUtils.announce("Dashboard page loaded.", "assertive");

    // 🔸 Observable role for HTML bindings
    this.userRole = ko.observable(localStorage.getItem('userRole') || "guest");

    this.stackValue = ko.observable('off');
    this.orientationValue = ko.observable('vertical');

    var dashboardData = JSON.parse(data);

    let roleData;
    if (this.userRole() === 'admin') {
      roleData = dashboardData.adminData;
    } else if (this.userRole() === 'advisor') {
      roleData = dashboardData.advisorData;
    } else {
      roleData = dashboardData.userData;
    }

    // You can add more logic using `roleData` here if needed
  }

  return DashboardViewModel;
});
