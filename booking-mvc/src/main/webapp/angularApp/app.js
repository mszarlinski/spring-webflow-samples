'use strict';

angular.module('app', ['reviewBooking'])
    .controller('HelloController', function ($scope) {
        $scope.message = 'Angular ready!';
    });