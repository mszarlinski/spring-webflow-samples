'use strict';

angular.module('app')
    .directive('numberOpts', function () {
        return {
            require: 'ngModel',
            link: function (scope, element, attrs, ngModelCtrl) {
                ngModelCtrl.$parsers.push(function (val) {
                    return val ? parseInt(val, 10) : null;
                });
                ngModelCtrl.$formatters.push(function (val) {
                    return val ? '' + val : null;
                });
            }
        };
    });