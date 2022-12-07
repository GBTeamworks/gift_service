angular.module('gift_service').controller('registrationController', function ($scope, $http, $localStorage, $location) {
    const authPath = 'http://...:.../auth';

    $scope.functionRegistration = function () {
        $http.post(authPath + '/registration', $scope.reguser).then(function (response) {
            if (response.data.token) {
                $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                $localStorage.serviceUser = {username: $scope.reguser.username, token: response.data.token};
                $localStorage.reguser = null;
                $location.path("/");
            }
        });
    }
});