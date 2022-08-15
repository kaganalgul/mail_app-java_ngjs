angular.module('mail-app')
.component('trash', {
    templateUrl: '/app/template/trash.html',
    controller: function ($scope, MailApi) {

        $scope.init = function() {
            $scope.mails = MailApi.list();
            
            // $scope.paging = {
            //     currentPage : 1,
            //     pageSize : 3
            // };
        };
        
        $scope.delete = function (mail) {
            let ret = MailApi.delete({ id: mail.id });
            ret.$promise.then(function(){
                _.delete($scope.mails, { id: mail.id });
            });
        }

        $scope.init();
    }
})