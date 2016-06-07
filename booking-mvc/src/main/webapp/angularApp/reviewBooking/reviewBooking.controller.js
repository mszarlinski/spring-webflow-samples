'use strict';

angular.module('reviewBooking')
    .controller('ReviewBookingController', function (ReviewBookingService, $log) {
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
                        vm.message = 'Booking has been saved';
                        vm.messageType = 'success';
                    })
                    .catch(function (error) {
                        vm.message = 'Failed to save booking due to error: ' + error.statusText;
                        vm.messageType = 'danger';
                    })
            } else {
                vm.message = 'Form is invalid. Please fill all required fields';
                vm.messageType = 'danger';
            }
        };
    });
    