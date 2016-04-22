'use strict';

angular.module('reviewBooking', ['ngResource'])
    .factory('ReviewBookingService', function (FlowExecutionResolver, $resource, $log) {
        var bookingResource = $resource('/rest/bookings', {}, {});
        return {
            loadBooking: function () {
                var flowId = FlowExecutionResolver.getFlowId();

                $log.debug('Loading booking');
                return bookingResource.get({execution: flowId}).$promise;
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