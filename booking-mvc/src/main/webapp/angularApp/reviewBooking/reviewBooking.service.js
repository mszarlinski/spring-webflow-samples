'use strict';

angular.module('reviewBooking')
    .factory('ReviewBookingService', function ($resource, $log) {
        
        var bookingResource = $resource('/rest/bookings', {}, {});
        
        return {
            loadBooking: function () {
                $log.debug('Loading booking');
                return bookingResource.get().$promise;
            },
            saveBooking: function (booking) {
                $log.debug('Saving booking');
                return bookingResource.save(booking).$promise;
            }
        }
    });
    