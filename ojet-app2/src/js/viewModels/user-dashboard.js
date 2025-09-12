define([
  "require",
  "exports",
  "knockout",
  "ojs/ojbootstrap",
  "ojs/ojarraydataprovider",
  "ojs/ojknockout",
  "ojs/ojchart",
  "ojs/ojselectsingle",
  "ojs/ojlabel"
], function (require, exports, ko, Bootstrap, ArrayDataProvider) {

  function UserDashboardViewModel() {
    const self = this;
    const userId = localStorage.getItem("userId") || "1";

    console.log('🚀 Initializing Dashboard for user:', userId);

    // =================== User Info ===================
    self.roleId = ko.observable(localStorage.getItem("userRole") || "User");
    self.username = ko.observable(localStorage.getItem("username") || "Guest");
    self.email = ko.observable(localStorage.getItem("email") || "guest@example.com");

    // Gender dropdown
    self.genderOptions = ko.observableArray([
      { value: 'male', label: '👨 Male' },
      { value: 'female', label: '👩 Female' }
    ]);
    self.selectedGender = ko.observable('male');
    self.genderDataProvider = new ArrayDataProvider(self.genderOptions, { keyAttributes: 'value' });

    // =================== Data Providers ===================
    self.pieDataProvider = ko.observable();
    self.barDataProvider = ko.observable();
    self.lineDataProvider = ko.observable();
    self.transactionsDataProvider = ko.observable();

    // Loading states
    self.isLoadingBudgets = ko.observable(true);
    self.isLoadingTransactions = ko.observable(true);
    self.isLoadingGoals = ko.observable(true);

    // Helpers
    const safeString = (val, fallback = "Unknown") => val ? String(val) : fallback;
    const safeNumber = (val) => isNaN(Number(val)) ? 0 : Number(val);

    // Enhanced color palette
    const chartColors = {
      primary: ['#667eea', '#764ba2', '#f093fb', '#f5576c', '#4facfe', '#00f2fe'],
      success: '#48bb78',
      warning: '#ed8936',
      error: '#f56565'
    };

    // =================== Load Sample Data Function ===================
    const loadSampleData = () => {
      console.log('📊 Loading sample data...');

      // Sample Pie Chart Data
      const samplePieData = [
        {
          id: "housing",
          name: "🏠 Housing",
          value: 1200,
          group: "Budget"
        },
        {
          id: "food",
          name: "🍔 Food",
          value: 600,
          group: "Budget"
        },
        {
          id: "transport",
          name: "🚗 Transport",
          value: 400,
          group: "Budget"
        },
        {
          id: "entertainment",
          name: "🎬 Entertainment",
          value: 300,
          group: "Budget"
        }
      ];
      self.pieDataProvider(new ArrayDataProvider(samplePieData, { keyAttributes: "id" }));

      // Sample Bar Chart Data
      const sampleBarData = [
        { id: "jan-income", group: "January", series: "💰 Income", value: 5000 },
        { id: "jan-expense", group: "January", series: "💸 Expenses", value: 3500 },
        { id: "feb-income", group: "February", series: "💰 Income", value: 5200 },
        { id: "feb-expense", group: "February", series: "💸 Expenses", value: 3800 },
        { id: "mar-income", group: "March", series: "💰 Income", value: 4800 },
        { id: "mar-expense", group: "March", series: "💸 Expenses", value: 3200 }
      ];
      self.barDataProvider(new ArrayDataProvider(sampleBarData, { keyAttributes: "id" }));

      // Sample Line Chart Data (Goals)
      const sampleLineData = [
        { id: "goal1", group: "Emergency Fund", series: "🎯 Target", value: 10000 },
        { id: "goal2", group: "Vacation", series: "🎯 Target", value: 5000 },
        { id: "goal3", group: "Car", series: "🎯 Target", value: 25000 },
        { id: "goal4", group: "House Down Payment", series: "🎯 Target", value: 50000 }
      ];
      self.lineDataProvider(new ArrayDataProvider(sampleLineData, { keyAttributes: "id" }));

      // Sample Transactions Data
      const sampleTransactionData = [
        { id: "txn1", group: "Dec 10", series: "💚 Credit", value: 5000 },
        { id: "txn2", group: "Dec 11", series: "💳 Debit", value: 120 },
        { id: "txn3", group: "Dec 12", series: "💳 Debit", value: 80 },
        { id: "txn4", group: "Dec 13", series: "💳 Debit", value: 250 },
        { id: "txn5", group: "Dec 14", series: "💚 Credit", value: 1500 },
        { id: "txn6", group: "Dec 15", series: "💳 Debit", value: 95 }
      ];
      self.transactionsDataProvider(new ArrayDataProvider(sampleTransactionData, { keyAttributes: "id" }));

      console.log('✅ Sample data loaded successfully');
    };

    // =================== Fetch Budgets ===================
    const loadBudgets = () => {
      console.log('🔄 Loading budgets for user:', userId);
      self.isLoadingBudgets(true);

      fetch(`http://localhost:7000/budget/user/${userId}`)
        .then(res => {
          if (!res.ok) throw new Error(`HTTP ${res.status}: ${res.statusText}`);
          return res.json();
        })
        .then(budgets => {
          console.log('✅ Budgets loaded successfully:', budgets.length);

          if (budgets.length === 0) {
            console.log('⚠️ No budgets found, using sample data');
            loadSampleData();
            self.isLoadingBudgets(false);
            return;
          }

          // ---- Pie Chart Data ----
          const totalAllocated = budgets.reduce((sum, b) => sum + safeNumber(b.allocatedAmount), 0);
          const totalSpent = budgets.reduce((sum, b) => sum + safeNumber(b.spentAmount), 0);
          const remaining = Math.max(0, totalAllocated - totalSpent);

          const pieData = [
            {
              id: "allocated",
              name: "💰 Total Budget",
              value: totalAllocated,
              group: "Budget"
            },
            {
              id: "spent",
              name: "💸 Amount Spent",
              value: totalSpent,
              group: "Budget"
            }
          ];

          if (remaining > 0) {
            pieData.push({
              id: "remaining",
              name: "💎 Remaining",
              value: remaining,
              group: "Budget"
            });
          }

          self.pieDataProvider(new ArrayDataProvider(pieData, { keyAttributes: "id" }));

          // ---- Bar Chart Data ----
          const barData = budgets.flatMap((b, i) => [
            {
              id: "alloc-" + i,
              group: safeString(b.budgetName),
              series: "💰 Allocated",
              value: safeNumber(b.allocatedAmount)
            },
            {
              id: "spent-" + i,
              group: safeString(b.budgetName),
              series: "💸 Spent",
              value: safeNumber(b.spentAmount)
            }
          ]);
          self.barDataProvider(new ArrayDataProvider(barData, { keyAttributes: "id" }));

          self.isLoadingBudgets(false);
        })
        .catch(err => {
          console.error("❌ Error loading budget data:", err);
          console.log('📊 Loading sample budget data instead');
          loadSampleData();
          self.isLoadingBudgets(false);
        });
    };

    // =================== Fetch Goals ===================
    const loadGoals = () => {
      console.log('🔄 Loading goals for user:', userId);
      self.isLoadingGoals(true);

      fetch(`http://localhost:7000/goals/user/${userId}`)
        .then(response => {
          if (!response.ok) throw new Error(`HTTP ${response.status}: ${response.statusText}`);
          return response.json();
        })
        .then(goals => {
          console.log('✅ Goals loaded successfully:', goals.length);

          if (goals.length === 0) {
            console.log('⚠️ No goals found, using sample data');
            // Sample goals data is loaded in loadSampleData()
            self.isLoadingGoals(false);
            return;
          }

          const lineData = goals.map((g, idx) => ({
            id: "goal-" + idx,
            group: safeString(g.budgetname || g.goalName, `Goal ${idx + 1}`),
            series: "🎯 Target Amount",
            value: safeNumber(g.targetAmount)
          }));

          self.lineDataProvider(new ArrayDataProvider(lineData, { keyAttributes: "id" }));
          self.isLoadingGoals(false);
        })
        .catch(error => {
          console.error("❌ Error loading goals:", error);
          console.log('📊 Loading sample goals data instead');
          self.isLoadingGoals(false);
        });
    };

    // =================== Fetch Transactions ===================
    const loadTransactions = () => {
      console.log('🔄 Loading transactions for user:', userId);
      self.isLoadingTransactions(true);

      fetch(`http://localhost:8500/transactions/user/${userId}`)
        .then(res => {
          if (!res.ok) throw new Error(`HTTP ${res.status}: ${res.statusText}`);
          return res.json();
        })
        .then(transactions => {
          console.log('✅ Transactions loaded successfully:', transactions.length);

          if (transactions.length === 0) {
            console.log('⚠️ No transactions found, using sample data');
            // Sample transaction data is loaded in loadSampleData()
            self.isLoadingTransactions(false);
            return;
          }

          const chartData = transactions.map((t, idx) => {
            const isCredit = t.transactionType && t.transactionType.toUpperCase() === 'CREDIT';

            return {
              id: "txn-" + idx,
              group: t.transactionDate ?
                new Date(t.transactionDate).toLocaleDateString('en-US', {
                  month: 'short',
                  day: 'numeric'
                }) :
                `Day ${idx + 1}`,
              series: isCredit ? "💚 Credit" : "💳 Debit",
              value: safeNumber(t.amount)
            };
          });

          self.transactionsDataProvider(new ArrayDataProvider(chartData, { keyAttributes: "id" }));
          self.isLoadingTransactions(false);
        })
        .catch(err => {
          console.error("❌ Error loading transactions:", err);
          console.log('📊 Loading sample transaction data instead');
          self.isLoadingTransactions(false);
        });
    };

    // =================== Initialize Dashboard ===================
    const initializeDashboard = () => {
      console.log('🚀 Initializing Enhanced Dashboard...');

      // Load sample data first (immediate feedback)
      loadSampleData();

      // Set initial loading states
      self.isLoadingBudgets(true);
      self.isLoadingGoals(true);
      self.isLoadingTransactions(true);

      // Try to load real data
      setTimeout(() => {
        loadBudgets();
        loadGoals();
        loadTransactions();
      }, 1000); // Small delay to show loading states
    };

    // =================== Utility Functions ===================
    self.formatAmount = (amount) => {
      return new Intl.NumberFormat('en-US', {
        style: 'currency',
        currency: 'USD',
        minimumFractionDigits: 0,
        maximumFractionDigits: 0
      }).format(safeNumber(amount));
    };

    self.getStatusIcon = (status) => {
      const statusMap = {
        'active': '✅',
        'completed': '🎉',
        'pending': '⏳',
        'overdue': '⚠️'
      };
      return statusMap[status?.toLowerCase()] || '📊';
    };

    self.getProgressPercentage = (spent, allocated) => {
      if (allocated === 0) return 0;
      return Math.min(100, Math.round((spent / allocated) * 100));
    };

    // =================== Theme Integration ===================
    self.getCurrentTheme = ko.computed(() => {
      return document.body.getAttribute('data-theme') || 'light';
    });

    // Initialize dashboard
    initializeDashboard();

    console.log('✨ Enhanced Dashboard ViewModel initialized successfully!');
  }

  return UserDashboardViewModel;
});