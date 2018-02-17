var app = angular.module('registerForm');

app.controller("employeeController", function($scope) {
	$scope.register = function(employee) {
		console.log("Employee---->" + employee.name);
	}

	$scope.login = function(employee) {
		console.log("Employee---->" + employee.email)
	}
})