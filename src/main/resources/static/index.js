var bgApp = angular.module('gift_service', ["ngRoute"])
    .config(function ($routeProvider) {
        $routeProvider
            .when('/mainPage',
                {
                    templateUrl: 'mainPage/mainPage.html',
                    controller: 'mainPageController'
                })

            .when('/registration',
                {
                    templateUrl: 'registration/registration.html',
                    controller: 'registrationController'
                })

            .when('/auth',
                {
                    templateUrl: 'auth/auth.html',
                    controller: 'authController'
                })

            .otherwise({
                redirectTo: '/'
            });
    });
