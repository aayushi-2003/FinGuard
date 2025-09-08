
define(['../accUtils', "require", "exports", "knockout", "ojs/ojbootstrap", 
"text!../roleData.json",
 "ojs/ojarraydataprovider", "ojs/ojknockout", "ojs/ojchart", "ojs/ojtoolbar"],
 function(accUtils,require,exports, ko, ojbootstrap_1, data, ArrayDataProvider) {
    function DashboardViewModel() {
      var userRole = localStorage.getItem('userRole'); 
      console.log(userRole);
      this.stackValue = ko.observable('off');
      this.orientationValue = ko.observable('vertical');

      var dashboardData = JSON.parse(data);

      let roleData;
      if (userRole === 'admin') {
        roleData = dashboardData.adminData;
      } else if (userRole === 'advisor') {
        roleData = dashboardData.advisorData;
      } else {
        roleData = dashboardData.userData;
      }

      
    }  
    return DashboardViewModel;
  }
);
