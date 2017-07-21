'use strict';

var app = angular.module('adminApp');

app.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/heldOrders', {
    templateUrl: 'demo/heldOrdersChart.html',
    controller: 'HeldOrdersController'
  }).when('/bugs', {
    templateUrl: 'demo/bugs.html',
    controller: 'BugsController'
  });
}]);

app.controller('BugsController', ['$scope', '$controller', function ($scope, $controller) {
  // Initialize the super class and extend it.
  angular.extend(this, $controller('TasksController', {$scope: $scope}));
  
  $scope.tasksLabel = 'Bugs';
  $scope.model.taskFilter.category = 'Bug';
}]);

app.controller('HeldOrdersController', ['$scope', '$http', 'mdw', 'util',
                                        function($scope, $http, mdw, util) {
  // labels for one-week span ending today
  $scope.dates = [];
  $scope.labels = [];
  var d = new Date();
  for (var h = 0; h < 7; h++) {
    $scope.labels.unshift(util.monthAndDay(d));
    $scope.dates.unshift(util.serviceDate(d));    
    if (h < 6)
      d.setTime(d.getTime() - util.dayMs);
  }
  
  // series
  $scope.series = ['All Orders', 'Held Orders'];
  
  $scope.data = [[], []];

  // retrieve data -- all orders
  var procIdUrl = mdw.roots.services + '/services/Assets/com.centurylink.mdw.demo.intro/HandleOrder.proc?app=mdw-admin';
  // get process id for HandleOrder.proc
  $http.get(procIdUrl).error(function(data, status) {
    console.log('HTTP ' + status + ': ' + procIdUrl);
  }).success(function(data, status, headers, config) {
    var procId = data.id;
    // get instance count for HandleOrder.proc
    var instCountUrl = mdw.roots.services + '/services/Processes/instanceCounts?app=mdw-admin&startDate=2016-Jul-09&processIds=[' + procId + ']';
    $http.get(instCountUrl).error(function(data, status) {
      console.log('HTTP ' + status + ': ' + instCountUrl);
    }).success(function(data, status, headers, config) {
      var dateCounts = data;
      for (var i = 0; i < $scope.dates.length; i++) {
        var d = $scope.dates[i];
        var countForDate = 0;
        if (dateCounts[d])
          countForDate = dateCounts[d][0].count;
        $scope.data[0].push(countForDate);
      }
    });
  });

  // retrieve data -- held orders
  var taskIdUrl = mdw.roots.services + '/services/Assets/com.centurylink.mdw.demo.intro/Contact%20Customer.task?app=mdw-admin';
  // get task id for "Contact Customer.task"
  $http.get(taskIdUrl).error(function(data, status) {
    console.log('HTTP ' + status + ': ' + taskIdUrl);
  }).success(function(data, status, headers, config) {
    var taskId = data.id;
    // get instance count for HandleOrder.proc
    var taskCountUrl = mdw.roots.services + '/services/Tasks/instanceCounts?app=mdw-admin&startDate=2016-Jul-09&taskIds=[' + taskId + ']';
    $http.get(taskCountUrl).error(function(data, status) {
      console.log('HTTP ' + status + ': ' + taskCountUrl);
    }).success(function(data, status, headers, config) {
      var taskDateCounts = data;
      for (var j = 0; j < $scope.dates.length; j++) {
        var td = $scope.dates[j];
        var taskCountForDate = 0;
        if (taskDateCounts[td])
          taskCountForDate = taskDateCounts[td][0].count;
        $scope.data[1].push(taskCountForDate);
      }
    });
  });
  
  // chart boilerplate
  $scope.$on('chart-create', function (event, chart) {
    $scope.chartLegend = chart.generateLegend();
  });
  
  $scope.chartOptions = {
    legendCallback: function(chart) {
      var text = [];
      text.push('<ul class="mdw-chart-legend">');
      for (var i = 0; i < chart.data.datasets.length; i++) {
        text.push('  <li>');
        text.push('    <span class="mdw-chart-legend-icon" style="background-color:' + chart.data.datasets[i].backgroundColor + ';' + 
            'border-color:' + chart.data.datasets[i].borderColor + '"></span>');
        var label = chart.data.datasets[i].label;
        if (label)
          text.push('    <span class="mdw-chart-legend-text">' + label + '</span>');
        text.push('  </li>');
      }
      text.push('</ul>');
      return text.join('\n  ');
    }
  };
  
}]); 