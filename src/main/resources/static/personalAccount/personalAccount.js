angular.module('gift_service').controller('personalAccountController', function ($scope, $http, $localStorage) {
    const cartPath = 'http://...:.../cart';

    $scope.loadCart = function () {
        $http.get(cartPath + '/api/v1/cart/' + $localStorage.marketGuestCartId).then(function (response) {
            $scope.cart = response.data;
        });
    }

    $scope.deleteFromCart = function (giftId) {
        $http.get(cartPath + '/api/v1/cart/' + $localStorage.marketGuestCartId + '/delete/' + giftId).then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.loadCart();
});