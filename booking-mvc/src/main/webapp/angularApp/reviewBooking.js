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

        var setupAmenities = function(amenities) {
            if (amenities) {
                for (var i = 0; i < amenities.length; i++) {
                    var amenityName = amenities[i];
                    vm.amenities[amenityName] = true;
                }
            }
        };

        var vm = this;

        vm.amenities = { };

        vm.roomPrefs = [
            { label: 'One king-size bed', value: 1 },
            { label: 'Two double beds', value: 2 },
            { label: 'Three beds', value: 3 }
        ];

        /**
         * LOAD BOOKING DATA FROM THE SERVER
         */
        ReviewBookingService.loadBooking()
            .then(function (booking) {
                $log.debug('Booking loaded: ', booking);
                vm.booking = booking;

                setupAmenities(booking.amenities);
            });

        /**
         * SEND BOOKING DATA TO THE SERVER
         */
        vm.confirm = function () {
            if (vm.reviewBookingForm.$valid) {
                ReviewBookingService.saveBooking(vm.booking).then(function() {
                    $log.debug('Booking has been saved');
                    FlowExecutionService.publishEvent('confirm');
                });
            }
        };

        vm.amenityChanged = function (amenityName) {
            if (vm.amenities[amenityName]) {
                vm.booking.amenities.push(amenityName);
            } else {
                var idx = vm.booking.amenities.indexOf(amenityName);
                vm.booking.amenities.splice(idx, 1);
            }
        }
    });