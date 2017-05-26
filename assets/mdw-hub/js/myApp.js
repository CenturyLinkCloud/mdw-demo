'use strict';

// Note: This is a completely standalone angular app (hosted here for convenience).
// The workflow piece is embedded via the hub-ui npm package
// (https://www.npmjs.com/package/hub-ui) -- see mdw-hub/myApp.html.
// Installation via npm: (in assets/mdw-hub) "npm install"

// Note also that the URL below is for a remote environment (the connected workflow
// does not have to be co-hosted -- although CORS access is required).
var mdwBase = 'https://lxdenvmtc144.dev.qintra.com:50550/mdw';

var myApp = angular.module('myApp', ['ngResource', 'mdw', 'util', 'constants', 'mdwWorkflow', 'mdwShape', 'mdwStep', 'mdwLink', 
  'mdwSubflow', 'mdwLabel', 'mdwNote', 'mdwMarquee', 'mdwInspector', 'mdwInspectorTabs']);

myApp.controller('myController', ['$scope', 'AnyCompletedProcess',
                                  function($scope, AnyCompletedProcess) {
  
  $scope.mdwServiceBase = mdwBase + '/services';  
  $scope.process = AnyCompletedProcess.get();
  
}]);

myApp.factory('AnyCompletedProcess', ['$resource', function($resource) {
  return $resource(mdwBase + '/services/Processes', { status: 'Completed', max: 1}, {
    get: { 
      method: 'GET',
      transformResponse: function(data, headers) {
        var inst = JSON.parse(data).processInstances[0];
        // transform to a single instance object
        return {
            name: inst.processName,
            packageName: inst.packageName,
            id: inst.id
        };
      }
    }
  });
}]);
