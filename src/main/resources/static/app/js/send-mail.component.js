angular.module('mail-app')
.component('sendMail', {
    templateUrl: '/app/template/send-mail.html',
    controller: function ($scope, $routeParams, MailApi, $location) {

        $scope.send = function() {
            $scope.mail.receiverList = $scope.mail.receiverList.split(',');
            MailApi.send($scope.mail, function() {
                $location.path("/inbox");
            })
        };

        
        $scope.init = function() {
            $scope.mail = {};
        };
        $scope.init();
    }
})