'use strict';

angular.module('app', ['reviewBooking'])
    .controller('HelloController', function ($scope) {
        $scope.message = 'Angular ready!';
    })
    .factory('FlowExecutionResolver', function ($window) {
        var PATTERN = /execution=(.*)/;
        return {
            getFlowId: function() {
                var urlParams = $window.location.search;
                return PATTERN.exec(urlParams)[1];
            }
        }
    });