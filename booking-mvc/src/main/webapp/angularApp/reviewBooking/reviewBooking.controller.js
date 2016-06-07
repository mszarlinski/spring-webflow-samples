'use strict';

angular.module('reviewBooking')
    .controller('ReviewBookingController', function () {
        var vm = this;

        vm.booking = {
            description: 'Sample booking',
            creditCard: {
                number: '0000111122223333'
            }
        }
    });
    