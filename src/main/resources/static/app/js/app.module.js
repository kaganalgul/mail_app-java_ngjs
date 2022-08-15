var app = angular.module("mail-app",
    [
        'ngRoute',
        'ngResource',
        'ui.bootstrap'
    ]);

    app.config(function($routeProvider, $locationProvider){
        $locationProvider.html5Mode(true);

        $routeProvider
            .when('/login', {
                template: '<login></login>'
            })
            .when('/register', {
                template: '<register></register>'
            })
            .when('/', {
                template: '<login></login>'
            })
            .when('/inbox', {
                template: '<inbox></inbox>'
            })
            .when('/send-mail', {
                template: '<send-mail></send-mail>'
            })
            .when('/trash', {
                template: '<trash></trash>'
            })
            .when('/mail-content/:id', {
                template: '<mail-content></mail-content>'
            })
            .when('/sendbox', {
                template: '<sendbox></sendbox>'
            })
            .when('/reply', {
                template: '<reply></reply>'
            })            
            .otherwise({
                redirectTo: '/'
            });
    })

    app.filter("timeago", function () {

        //time: the time
    
        //local: compared to what time? default: now
    
        //raw: wheter you want in a format of "5 minutes ago", or "5 minutes"
    
        return function (time, local, raw) {
    
            if (!time) return "never";
    
    
    
            if (!local) {
    
                (local = Date.now())
    
            }
    
    
    
            if (angular.isDate(time)) {
    
                time = time.getTime();
    
            } else if (typeof time === "string") {
    
                time = new Date(time).getTime();
    
            }
            if (angular.isDate(local)) {

                local = local.getTime();
    
            }else if (typeof local === "string") {
    
                local = new Date(local).getTime();
    
            }
    
    
    
            if (typeof time !== 'number' || typeof local !== 'number') {
    
                return;
    
            }
    
    
    
            var
    
                offset = Math.abs((local - time) / 1000),
    
                span = [],
    
                MINUTE = 60,
    
                HOUR = 3600,
    
                DAY = 86400,
    
                WEEK = 604800,
    
                MONTH = 2629744,
    
                YEAR = 31556926,
    
                DECADE = 315569260;
                if (offset <= MINUTE)              span = [ '', raw ? 'now' : 'less than a minute' ];

                else if (offset < (MINUTE * 60))   span = [ Math.round(Math.abs(offset / MINUTE)), 'min' ];
        
                else if (offset < (HOUR * 24))     span = [ Math.round(Math.abs(offset / HOUR)), 'hr' ];
        
                else if (offset < (DAY * 7))       span = [ Math.round(Math.abs(offset / DAY)), 'day' ];
        
                else if (offset < (WEEK * 52))     span = [ Math.round(Math.abs(offset / WEEK)), 'week' ];
        
                else if (offset < (YEAR * 10))     span = [ Math.round(Math.abs(offset / YEAR)), 'year' ];
        
                else if (offset < (DECADE * 100))  span = [ Math.round(Math.abs(offset / DECADE)), 'decade' ];
        
                else                               span = [ '', 'a long time' ];

                span[1] += (span[0] === 0 || span[0] > 1) ? 's' : '';

    span = span.join(' ');



    if (raw === true) {

        return span;

    }

    return (time <= local) ? span + ' ago' : 'in ' + span;

}

});

    app.factory('AccountApi', ['$resource', function($resource){
        var baseUrl = "/user"

        return $resource('/', { }, {
            login: {
                method: 'POST',
                url: baseUrl + '/login'
            },
            register: {
                method: 'POST',
                url: baseUrl + '/register'
            }
        });
    }]);

    app.factory('MailApi', ['$resource', function($resource) {
        var baseUrl = "/mail"

        return $resource('/:id', { id: '@id' }, {
            list: {
                method: 'GET',
                url: baseUrl + '/inbox',
                isArray: true
            },
            send: {
                method: 'POST',
                url: baseUrl + '/send-mail'
            },
            read: {
                method: 'GET',
                url: baseUrl + '/mail-content/:id'
            },
            delete: {
                method: 'DELETE',
                url: baseUrl + '/delete'
            },
            sendbox: {
                method: 'GET',
                url: baseUrl + '/sendbox',
                isArray: true
            } 
        })
    }])

    app.factory('AccountService', function() {
        let user;
        let loggedIn = false;
        let observers = [];

        function setUser(user1) {
            user = user1;
        }

        function getUser() {
            return user;
        }

        function getLoggedIn() {
            return loggedIn;
        }

        function setLoggedIn() {
            loggedIn = l;
            for(let i = 0; i < observers.length; i++) {
                observers[i](l);
            }
        }

        function addObserver(o) {
            observers.push(o);
        }

        return {
            setL : setLoggedIn,
            gelL : getLoggedIn,
            addO : addObserver,
            setU : setUser,
            getU : getUser
        }
    });
    
    app.controller("MenuController", function ($scope) {

        let pathsWithMenu = ['<inbox></inbox>', '<send-mail></send-mail>'];
    
    
        $scope.init = function () {
    
            $scope.showMenu = false;
    
            //angular.element("#menu").show();
    
            $scope.$on('$routeChangeSuccess', function ($event, current, prev) {
                // ... you could trigger something here ...
                if (pathsWithMenu.includes(current.template)) {
                    $scope.showMenu = true;
                }
    
            });
    
        };
    
        $scope.init();
    });
    