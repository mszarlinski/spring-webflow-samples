'use strict';

angular.module('reviewBooking')
    .controller('ReviewBookingController', function (ReviewBookingService, FlowManager, localStorageService, $scope, $log) {
        var vm = this;

        var initialize = function () {
            var SESSION_STORAGE_KEY = 'BookingData';

            var setAndBind = function (booking) {
                vm.booking = booking;
                localStorageService.bind($scope, 'vm', vm, SESSION_STORAGE_KEY);
            };
            
            var storedValueModel = localStorageService.get(SESSION_STORAGE_KEY);
            if (!storedValueModel) {
                ReviewBookingService.loadBooking()
                    .then(function (booking) {
                        $log.debug('Booking loaded: ', booking);
                        setAndBind(booking);
                    });
            } else {
                //FIXME: data change done in previous screen is lost
                setAndBind(storedValueModel.booking);
            }

            vm.message = null;
        };

        initialize();

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

        vm.revise = function () {
            FlowManager.fireEvent('REVISED');
        };

        vm.cancel = function () {
            FlowManager.fireEvent('CANCELLED');
        }
    });
    