angular.module('mail-app')
    .component('login',{
        templateUrl: '/app/template/login.html',
        controller: function($scope, $location, AccountApi, AccountService) {


            $scope.login = function() {
                AccountApi.login($scope.loginRequest, function(authenticationResponse) {
                    if (authenticationResponse.code == 1) {
                        AccountService.setU($scope.loginRequest.mailAddress);
                        $location.path('/inbox');
                    } else {
                        switch(authenticationResponse.code) {
                            case 0:
                                toastr.info("password incorrect")
                                break;
                            case -1:
                                toastr.error("user not found")
                                break;
                        }
                    }
                })
            }

            $scope.init = function () {
                $scope.loginRequest = {};

            };

            $scope.init();
        }
    })