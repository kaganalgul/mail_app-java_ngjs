angular.module('mail-app')
    .component('reply',{
        templateUrl:'/app/template/reply.html',
        controller: function($scope,$location,$routeParams,MailApi,UserApi){


            $scope.init = function (){
                $scope.id = $routeParams.id;
                $scope.mail  = MailApi.get({id:$scope.id},function () {
                    $scope.user = UserApi.get(function () {

                        if ($scope.mail.receiverAddresses.includes($scope.user.mailAddress)){
                            console.log("if state");
                            let arr = [].push($scope.mail.senderAddress);
                            $scope.mail.receiverAddresses = arr;
                            $scope.mail.senderAddress = $scope.user.mailAddress;
                        }

                    });

                });
            }
            $scope.init();
        }
    });