(function () {
    angular
        .module('gift_service', ['ngRoute', 'ngStorage'])
        .config(config)
        .run(run);

    function config($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'main/main.html',
                controller: 'mainController'
            })
            .when('/registration', {
                templateUrl: 'registration/registration.html',
                controller: 'registrationController'
            })
            .when('/personalAccount', {
                templateUrl: 'personalAccount/personalAccount.html',
                controller: 'personalAccountController'
            })
            .otherwise({
                redirectTo: '/'
            });
    }

    function run($rootScope, $http, $localStorage) {
        if ($localStorage.serviceUser) {
            try {
                let jwt = $localStorage.serviceUser.token;
                let payload = JSON.parse(atob(jwt.split('.')[1]));
                let currentTime = parseInt(new Date().getTime() / 1000);
                if (currentTime > payload.exp) {
                    console.log("Token is expired!!!");
                    delete $localStorage.serviceUser;
                    $http.defaults.headers.common.Authorization = '';
                }
            } catch (e) {
            }

            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.serviceUser.token;

            if (!$localStorage.marketGuestCartId) {
                $http.get('http://localhost:5555/cart/api/v1/cart/generate_uuid')
                    .then(function successCallback(response) {
                        $localStorage.marketGuestCartId = response.data.value;
                    });
            }
        }
    }
})();

angular.module('market').controller('indexController', function ($rootScope, $scope, $http, $location, $localStorage) {
    $scope.tryToAuth = function () {
        $http.post('http://...:.../auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.serviceUser = {username: $scope.user.username, token: response.data.token};

                    $scope.user.username = null;
                    $scope.user.password = null;

                    $http.get('http://...:.../api/v1/')
                        .then(function successCallback(response) {
                        });

                    $location.path('/');
                }
            }, function errorCallback(response) {
            });
    };

    $rootScope.tryToLogout = function () {
        $scope.clearUser();
        $scope.user = null;
        $location.path('/');
    };

    $scope.clearUser = function () {
        delete $localStorage.serviceUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $rootScope.isUserLoggedIn = function () {
        if ($localStorage.serviceUser) {
            return true;
        } else {
            return false;
        }
    };
});