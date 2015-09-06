var chatApp = angular.module('chatApp', []);


chatApp.controller('ChatController', function ($scope, $interval, $http) {
	
	$scope.submitMessage = function() {
		console.log($scope.room);
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
	
	getMessages = $interval(function() {
		var getMessagesPromise = $http.get("/message/getMessages/"+$scope.room);
		getMessagesPromise.success(function(data, status, headers, config) {
			$scope.messages = data;
        });
	}, 2000);
	
	var getRoomsPromise = $http.get("/room/getAll");
	getRoomsPromise.success(function(data, status, headers, config) {
		$scope.rooms = data;
		console.log(data);
	});
	
});