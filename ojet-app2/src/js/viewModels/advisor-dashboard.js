define(['knockout'], function(ko) {
  function AdvisorDashboardViewModel() {
    this.message = ko.observable("Welcome, Advisor!");
    // Add any advisor-specific logic here
  }

  return AdvisorDashboardViewModel;
});
