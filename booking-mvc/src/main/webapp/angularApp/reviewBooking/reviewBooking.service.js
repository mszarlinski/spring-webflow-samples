'use strict';

angular.module('reviewBooking')
    .factory('ReviewBookingService', function (FlowManager, $resource, $log) {

        var bookingResource = $resource('/rest/bookings', {}, {});

        return {
            loadBooking: function () {
                $log.debug('Loading booking');
                var flowId = FlowManager.getFlowId();
                return bookingResource.get({execution: flowId}).$promise;
            },
            saveBooking: function (booking) {
                $log.debug('Saving booking');
                return bookingResource.save(booking).$promise;
            }
        }
    });
    