'use strict';

angular.module('app', ['reviewBooking'])
    .controller('HelloController', function ($scope) {
        $scope.message = 'Angular ready!';
    })
    .config(function (localStorageServiceProvider) {
        localStorageServiceProvider
            .setPrefix('BookingMvc')
            .setStorageType('sessionStorage')
            .setNotify(true, true);
    })