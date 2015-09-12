var chatApp = angular.module('chatApp', ['ngFileUpload']);


chatApp.controller('ChatController', function ($scope, $interval, $http, Upload) {
	
	$scope.submitMessage = function(file) {
		if (($scope.room==undefined) || ($scope.room == "none")) {
			return;
		}
		if (($scope.room.id==undefined) || ($scope.room.id == "none")) {
			return;
		}
		
		console.log($scope.file);
		Upload.upload({
	        url: '/message/submitMessage',
	        fields: {'message': $scope.message,
	        		 'room': $scope.room.id},
	        file: file
	    }).success(function (data, status, headers, config) {
			$scope.message = "";
			$scope.file = "";
			$scope.getMessages();
	    });
		 
		
		/*$http.post("/message/submitMessage", {
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
			$scope.message = "";
			$scope.getMessages();
		});*/
	}
	
	$scope.getMessages = function() {
		if ($scope.room == undefined) {
			return;
		}
		var getMessagesPromise = $http.get("/message/getMessages/"+$scope.room.id);
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
		$scope.room = $scope.rooms[0];
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