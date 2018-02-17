var app = angular.module("play")

app.config([ '$stateProvider', '$urlRouterProvider',
		function($stateProvider, $urlRouterProvider) {
			$stateProvider.state('index.scala', {
				url : '/index.scala',
				templateUrl : 'views/index.scala.html',
				controller : 'main'
			})

			$stateProvider.state('login', {
				url : '/login',
				templateUrl : 'views/login.html',
				controller : 'main'
			})
		} ])