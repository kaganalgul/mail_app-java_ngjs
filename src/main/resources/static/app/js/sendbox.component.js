angular.module('mail-app')
.component('sendbox', {
    templateUrl: '/app/template/sendbox.html',
    controller: function ($scope, MailApi) {

        $scope.init = function() {
            $scope.mails = MailApi.sendbox();
            
            // $scope.paging = {
            //     currentPage : 1,
            //     pageSize : 3
            // };
        };

        $scope.read = function(mail) {
            $location.path('/mail-content/' + (mail.id))
        }
        
        $scope.delete = function (mail) {
            let ret = MailApi.delete({ id: mail.id });
            ret.$promise.then(function(){
                _.delete($scope.mails, { id: mail.id });
            });
        }

        $scope.init();
    }
})