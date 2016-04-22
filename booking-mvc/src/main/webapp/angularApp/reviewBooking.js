'use strict';

angular.module('reviewBooking', ['ngResource'])
    .factory('ReviewBookingService', function (FlowExecutionService, $resource, $log) {
        var bookingResource = $resource('/rest/bookings', {}, {});
        return {
            loadBooking: function () {
                var flowId = FlowExecutionService.getFlowId();

                $log.debug('Loading booking');
                return bookingResource.get({execution: flowId}).$promise;
            },
            saveBooking: function (booking) {
                $log.debug('Saving booking');
                return bookingResource.save(booking).$promise;
            }
        }
    })
    .controller('ReviewBookingController', function (ReviewBookingService, FlowExecutionService, $log) {
        var vm = this;

        ReviewBookingService.loadBooking()
            .then(function (booking) {
                $log.debug('Booking loaded: ', booking);
                vm.booking = booking;
            });

        vm.confirm = function () {
            if (vm.reviewBookingForm.$valid) {
                ReviewBookingService.saveBooking(vm.booking).then(function() {
                    $log.debug('Booking has been saved');
                    FlowExecutionService.publishEvent('confirm');
                });
            }
        }
    });