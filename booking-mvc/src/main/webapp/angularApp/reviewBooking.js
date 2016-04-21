'use strict';

angular.module('reviewBooking', ['ngResource'])
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
    })
    .controller('ReviewBookingController', function (ReviewBookingService, $log) {
        var vm = this;

        ReviewBookingService.loadBooking()
            .then(function (booking) {
                $log.debug('Booking loaded: ', booking);
                vm.booking = booking;
            });

        vm.confirm = function () {
            ReviewBookingService.saveBooking(vm.booking);
        }
    });