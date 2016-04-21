'use strict';

angular.module('app', [])
    .controller('HelloController', function ($scope) {
        $scope.message = 'Angular ready!';
    });