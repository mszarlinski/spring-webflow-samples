'use strict';

angular.module('app')
    .factory('FlowExecutionService', function ($window, $http, $httpParamSerializer, $log) {
        var PATTERN = /execution=(.*)/;
        return {
            getFlowId: function () {
                var urlParams = $window.location.search;
                return PATTERN.exec(urlParams)[1];
            },
            publishEvent: function (eventId) {
                $http({
                    url: $window.location.href,
                    method: 'POST',
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                    data: $httpParamSerializer({'_eventId': eventId})
                })
                    .success(function (reponse) {
                        // We must rewrite the whole document, flow outcome is lost otherwise.
                        document.open();
                        document.write(reponse);
                        document.close();
                    })
                    .catch(function (error) {
                        $log.error(error);
                    });
            }
        }
    });