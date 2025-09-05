
define(['../accUtils', "require", "exports", "knockout", "ojs/ojbootstrap", 
"text!../basicData.json",
 "ojs/ojarraydataprovider", "ojs/ojknockout", "ojs/ojchart", "ojs/ojtoolbar"],
 function(accUtils,require,exports, ko, ojbootstrap_1, data, ArrayDataProvider) {
    function DashboardViewModel() {
      console.log(data);
      this.stackValue = ko.observable('off');
      this.orientationValue = ko.observable('vertical');
      this.dataProvider = new ArrayDataProvider(JSON.parse(data), {
        keyAttributes: 'id'
    });
    }  
    return DashboardViewModel;
  }
);
