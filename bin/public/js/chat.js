var chatApp = angular.module('chatApp', []);


chatApp.controller('ChatController', function ($scope, $interval, $http) {

	$scope.submitMessage = function() {
		$http.post("/message/submitMessage", {
			message: $scope.message
		}, {
	        headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
	        transformRequest: function(data) {
	        	if (data === undefined) {
	                return data;
	            }
	            return $.param(data);
	        } 
		});
	}
	
	getMessages = $interval(function() {
		var getMessagesPromise = $http.get("/message/getMessages");
		getMessagesPromise.success(function(data, status, headers, config) {
			$scope.messages = data;
        });
	}, 2000);
	
});