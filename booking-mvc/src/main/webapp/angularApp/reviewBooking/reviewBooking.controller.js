'use strict';

angular.module('reviewBooking')
    .controller('ReviewBookingController', function (ReviewBookingService, FlowManager, localStorageService, $scope, $log) {
        var vm = this;

        var setupIsValidWatcher = function (booking) {
            $scope.$watch('vm.reviewBookingForm.$valid', function (isValid) {
                booking.valid = isValid;
            })
        };
        
        var initialize = function () {
            var SESSION_STORAGE_KEY = 'BookingData';

            var setAndBind = function (booking) {
                vm.booking = booking;
                setupIsValidWatcher(vm.booking);
                localStorageService.remove(SESSION_STORAGE_KEY);
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
    });
    