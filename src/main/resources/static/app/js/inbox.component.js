angular.module('mail-app')
.component('inbox', {
    templateUrl: '/app/template/inbox.html',
    controller: function ($scope, MailApi, $location) {

        $scope.read = function(mail) {
            $location.path('/mail-content/' + (mail.id))
        }

        $scope.init = function() {
            $scope.mails = MailApi.list();
            // $scope.paging = {
            //     currentPage : 1,
            //     pageSize : 3
            // };
        };

        $scope.delete = function (id) {
            let ret = MailApi.delete({ id: id });
            ret.$promise.then(function(){
                _.remove($scope.mails, { id: id });
            });
        }

        $scope.init();

        
    }

    
})