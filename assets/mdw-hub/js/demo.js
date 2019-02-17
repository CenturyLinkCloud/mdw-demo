'use strict';

var app = angular.module('adminApp');

app.controller('BugsController', ['$scope', '$controller',
      function ($scope, $controller, TasksController) {
  // initialize and extend built-in TasksController
  angular.extend(this, $controller('TasksController', {$scope: $scope}));
  
  $scope.authUser.setActiveTab('/issues');
  $scope.tasksLabel = 'Issues';
  $scope.model.taskFilter.category = 'ISSUE';
}]);