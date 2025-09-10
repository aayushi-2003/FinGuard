require([
  'ojs/ojbootstrap', 'knockout', './appController', 'ojs/ojlogger', 'ojs/ojknockout',
 'ojs/ojmodule', 'ojs/ojrouter', 'ojs/ojnavigationlist', 'ojs/ojbutton', 'ojs/ojtoolbar'
], function (
  Bootstrap,
  ko,
  app,
  Logger
) {
  Bootstrap.whenDocumentReady().then(function () {
    // :white_check_mark: Register the custom component before bindings
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












