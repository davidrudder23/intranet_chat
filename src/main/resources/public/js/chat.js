var chatApp = angular.module('chatApp', []);


chatApp.controller('ChatController', function ($scope, $interval, $http) {
	
	$scope.submitMessage = function() {
		if ($scope.room == "none") {
			return;
		}
		
		$http.post("/message/submitMessage", {
			message: $scope.message,
			room: $scope.room
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
	
	var getMessagesInterval = $interval(function() {
		var getMessagesPromise = $http.get("/message/getMessages/"+$scope.room);
		getMessagesPromise.success(function(data, status, headers, config) {
			$scope.messages = data;
        });
	}, 2000);
	
	var getRoomsPromise = $http.get("/room/getAll");
	getRoomsPromise.success(function(data, status, headers, config) {
		$scope.rooms = data;
		$scope.room = $scope.rooms[1].value;
	});
	
	var getLatestMessagesInterval = $interval(function() {
		var getLatestMessagesPromise = $http.get("/message/getLatestPerAccount");
		getLatestMessagesPromise.success(function(data, status, headers, config) {
			$scope.latestAccountMessages = data;
		});
	}, 10000);
	
});