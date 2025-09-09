require([
  'ojs/ojbootstrap',
  'knockout',
  './appController',
  'ojs/ojlogger',
  'text!views/advisor-dashboard.html',
  'viewModels/advisor-dashboard'
], function (
  Bootstrap,
  ko,
  app,
  Logger,
  advisorTemplate,
  AdvisorViewModel
) {
  Bootstrap.whenDocumentReady().then(function () {
    // ✅ Register the custom component before bindings
    ko.components.register('advisor-dashboard', {
      viewModel: AdvisorViewModel,
      template: advisorTemplate
    });

    function init() {
      // Bind your ViewModel for the content of the whole page body.
      ko.applyBindings(app, document.getElementById('globalBody'));
    }

    // Wait for deviceready if in hybrid mode
    if (document.body.classList.contains('oj-hybrid')) {
      document.addEventListener('deviceready', init);
    } else {
      init();
    }
  });
});
