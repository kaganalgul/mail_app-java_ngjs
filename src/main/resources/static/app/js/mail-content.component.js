angular.module('mail-app') 
.component('mailContent', {
    templateUrl: '/app/template/mail-content.html',
    controller: function($scope, MailApi, $routeParams, $location) {
        $scope.init = function() {
            let id = $routeParams.id;

           $scope.mail = MailApi.read({id: id});
        };
        
        $scope.reply = function(mail) {
            $scope.mail.receiverList = mail.sender;
            $location.path("/send-mail");
        }

        $scope.init();
    }
    
});



            