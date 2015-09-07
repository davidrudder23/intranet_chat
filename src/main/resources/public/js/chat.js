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
		}).success(function() {
			$scope.getMessages();
		});
	}
	
	$scope.getMessages = function() {
		var getMessagesPromise = $http.get("/message/getMessages/"+$scope.room);
		getMessagesPromise.success(function(data, status, headers, config) {
			$scope.messages = data;
        });
	};
	
	var getMessagesInterval = $interval(function() {
		$scope.getMessages();
	}, 2000);
	$scope.getMessages();
	
	var getRoomsPromise = $http.get("/room/getAll");
	getRoomsPromise.success(function(data, status, headers, config) {
		$scope.rooms = data;
		$scope.room = $scope.rooms[1].value;
	});
	
	$scope.getLatestMessages = function() {
		var getLatestMessagesPromise = $http.get("/message/getLatestPerAccount");
		getLatestMessagesPromise.success(function(data, status, headers, config) {
			$scope.latestAccountMessages = data;
		});
	};
	
	var getLatestMessagesInterval = $interval(function() {
		$scope.getLatestMessages();
	}, 10000);
	$scope.getLatestMessages();
	
});