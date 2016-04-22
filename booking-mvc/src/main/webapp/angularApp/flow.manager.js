'use strict';

angular.module('app')
    .factory('FlowManager', function ($window) {
        var FLOW_ID_PATTERN = /execution=(.*)/;

        return {
            getFlowId: function () {
                var urlParams = $window.location.search;
                return FLOW_ID_PATTERN.exec(urlParams)[1];
            }
        }
    });