angular
	.module('angularjs')
	.controller('angularController', function ($scope) {
		$scope.lists = [
			{
			  name: 'Next week',
			  tickets: [
				{title: 'repair a car'},
				{title: 'wash the windows'}
			  ]
			},
			{
			  name: 'Tommorow',
			  tickets: [
				{title: 'vacuum the carpets'},
				{title: 'wash the dishes'}
			  ]
			}
		];
		
	  var x = 3;
	  
	  $scope.addList = function () {
		$scope.lists.push({name: 'list ' + x, tickets: []})
		x++;
	  }

	  $scope.addTicket = function (list) {
		list.tickets.push({})
	  }
});
