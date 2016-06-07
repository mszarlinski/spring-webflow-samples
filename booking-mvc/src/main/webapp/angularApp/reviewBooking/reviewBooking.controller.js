'use strict';

angular.module('reviewBooking')
    .controller('ReviewBookingController', function (ReviewBookingService, FlowManager, $log) {
        var vm = this;

        ReviewBookingService.loadBooking()
            .then(function (booking) {
                $log.debug('Booking loaded: ', booking);
                vm.booking = booking;
                vm.message = null;
            });

        vm.confirm = function () {
            if (vm.reviewBookingForm.$valid) {
                ReviewBookingService.saveBooking(vm.booking)
                    .then(function () {
                        FlowManager.fireEvent('CONFIRMED');
                    })
                    .catch(function (error) {
                        vm.message = 'Failed to save booking due to error: ' + error.statusText;
                    })
            } else {
                vm.message = 'Form is invalid. Please fill all required fields';
            }
        };
    });
    