'use strict';

angular.module('reviewBooking', ['ngResource', 'LocalStorageModule'])
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
    .controller('ReviewBookingController', function ($scope, ReviewBookingService, FlowExecutionService, localStorageService, $log) {

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
         * LOAD BOOKING DATA FROM THE SERVER OR RESTORE FROM SESSION STORAGE
         */
        var SESSION_STORAGE_KEY = 'BookingData';
        var storedValueModel = localStorageService.get(SESSION_STORAGE_KEY);
        if (!storedValueModel) {
            ReviewBookingService.loadBooking()
                .then(function (booking) {
                    $log.debug('Booking loaded: ', booking);
                    vm.booking = booking;

                    setupAmenities(booking.amenities);
                    
                    // rebind booking variable
                    localStorageService.remove('Booking');
                    localStorageService.bind($scope, 'vm', vm, SESSION_STORAGE_KEY);
                });
        } else {
            vm.booking = storedValueModel.booking;
            localStorageService.bind($scope, 'vm', vm, SESSION_STORAGE_KEY);
            
            setupAmenities(vm.booking.amenities);
        }

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
        
        vm.revise = function() {
            FlowExecutionService.publishEvent('revise');
        };
        
        vm.cancel = function() {
            FlowExecutionService.publishEvent('cancel');
        };

        vm.amenityChanged = function (amenityName) {
            if (vm.amenities[amenityName]) {
                vm.booking.amenities.push(amenityName);
            } else {
                var idx = vm.booking.amenities.indexOf(amenityName);
                vm.booking.amenities.splice(idx, 1);
            }
        };

        $scope.$watch('vm.reviewBookingForm.$valid', function(isValid) {
            if (vm.booking) {
                vm.booking.valid = isValid;
            }
        })
    });