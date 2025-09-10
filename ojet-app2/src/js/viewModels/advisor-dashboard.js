define([
  "require",
  "exports",
  "knockout",
  "ojs/ojbootstrap",
  "ojs/ojarraydataprovider",
  "ojs/ojknockout",
  "ojs/ojchart",
  "ojs/ojtable"
], function(require, exports, ko, ojbootstrap_1, ArrayDataProvider) {
  
  function AdvisorDashboardViewModel() {
    const self = this;

    // --- Pie Chart (Asset Type Distribution) ---
    self.pieChartData = ko.observableArray([]);
    self.pieDataProvider = ko.observable();

    // --- Bar Chart (Portfolio Value by User) ---
    self.barChartData = ko.observableArray([]);
    self.barDataProvider = ko.observable();

    self.lineChartData = ko.observableArray([]);
    self.lineDataProvider = ko.observable();

    // --- Table Data (Portfolios Summary) ---
    self.portfolioTableData = ko.observableArray([]);
    self.portfolioTableDataProvider = ko.observable();

    self.roleId = ko.observable(localStorage.getItem("userRole") || "N/A");
    self.username = ko.observable(localStorage.getItem("username") || "N/A");
    self.email = ko.observable(localStorage.getItem("email") || "N/A");

    fetch("http://localhost:8060/portfolios")
      .then(response => {
        if (!response.ok) {
          throw new Error("Failed to fetch portfolios");
        }
        return response.json();
      })
      .then(portfolios => {

        // Pie Chart Data Preparation
        const assetTypeMap = {};

        portfolios.forEach(portfolio => {
          if (portfolio.holdings) {
            portfolio.holdings.forEach(holding => {
              const type = holding.assetType;
              if (!assetTypeMap[type]) {
                assetTypeMap[type] = 0;
              }
              assetTypeMap[type] += holding.quantity*holding.currentPrice; // or += 1 to just count
            });
          }
        });

        const pieData  = Object.entries(assetTypeMap).map(([type, total]) => ({
          name: type,
          value: total
        }));

        self.pieChartData(pieData );
        self.pieDataProvider(new ArrayDataProvider(self.pieChartData(), { keyAttributes: "name" }));

        // Bar Chart Data Preparation
        const userValueMap = {};
        portfolios.forEach(p => {
          const userId = p.userId;
          if (!userValueMap[userId]) userValueMap[userId] = 0;

          p.holdings?.forEach(h => {
            const value = h.currentPrice * h.quantity;
            userValueMap[userId] += value;
          });
        });

        const barData = Object.entries(userValueMap).map(([userId, totalValue]) => ({
          user: [`User ${userId}`],
          value: totalValue,
          s:"Total"
        }));
        console.log("Bar Chart Data:", barData);
        self.barChartData(barData);
        self.barDataProvider(new ArrayDataProvider(self.barChartData(), { keyAttributes: "user" }));

        //Line Chart Data Preparation
        // Prepare line chart data
        const lineChartData = [];

        portfolios.forEach(p => {
          p.holdings?.forEach(h => {
            const label = `${h.assetName} (${p.name})`;

            lineChartData.push(
              { group: [label], series: "Purchase Price", value: h.purchasePrice },
              { group: [label], series: "Current Price", value: h.currentPrice }
            );
          });
        });

        self.lineChartData(lineChartData);
        self.lineDataProvider(new ArrayDataProvider(self.lineChartData(), { keyAttributes: "group" }));

        // Prepare table data
        const portfolioTableData = portfolios.map(p => {
          const totalValue = p.holdings?.reduce((sum, h) => sum + h.currentPrice * h.quantity, 0) || 0;

          return {
            portfolioId: p.portfolioId,
            name: p.name,
            description: p.description,
            userId: p.userId,
            numHoldings: p.holdings?.length || 0,
            totalValue: totalValue.toFixed(2),
            createdAt: p.createdAt?.split("T")[0] || "",
            updatedAt: p.updatedAt?.split("T")[0] || ""
          };
        });

        self.portfolioTableData(portfolioTableData);
        self.portfolioTableDataProvider(new ArrayDataProvider(self.portfolioTableData(), { keyAttributes: "portfolioId" }));


      })
      .catch(error => {
        console.error("Error loading portfolio data:", error);
      });

  }

  return AdvisorDashboardViewModel;
});
