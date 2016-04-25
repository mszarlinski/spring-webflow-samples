'use strict';

angular.module('app')
    .directive('btnRadio', function () {
        return {
            require: 'ngModel',
            controller: function () {
            },
            controllerAs: 'buttons',
            link: function (scope, element, attrs, ngModelCtrl) {
                element.find('input').css({display: 'none'});

                //model -> UI
                ngModelCtrl.$render = function () {
                    element.toggleClass('active', angular.equals(ngModelCtrl.$modelValue, scope.$eval(attrs.btnRadio)));
                };

                //ui->model
                element.on('click', function () {
                    if (attrs.disabled) {
                        return;
                    }

                    var isActive = element.hasClass('active');

                    if (!isActive) {
                        scope.$apply(function () {
                            ngModelCtrl.$setViewValue(isActive ? null : scope.$eval(attrs.btnRadio));
                            ngModelCtrl.$render();
                        });
                    }
                });
            }
        };
    });