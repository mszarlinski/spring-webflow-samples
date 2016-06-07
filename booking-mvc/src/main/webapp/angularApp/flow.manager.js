'use strict';

angular.module('app')
    .factory('FlowManager', function ($window, $http, $httpParamSerializer, $log) {
        var FLOW_ID_PATTERN = /execution=(.*)/;

        return {
            getFlowId: function () {
                var urlParams = $window.location.search;
                return FLOW_ID_PATTERN.exec(urlParams)[1];
            },
            fireEvent: function (eventId) {
                $http({
                    url: $window.location.href,
                    method: 'POST',
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                    data: $httpParamSerializer({'_eventId': eventId})
                })
                .success(function (response) {
                    // We must rewrite the whole document, as flow outcome is lost otherwise.
                    document.open();
                    document.write(response);
                    document.close();
                })
                .catch(function (error) {
                    $log.error(error);
                });
            }
        }
    });