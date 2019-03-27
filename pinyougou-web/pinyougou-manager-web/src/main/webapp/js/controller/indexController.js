app.controller('indexController',function ($scope , baseService) {
    $scope.showLoginName = function(){
        baseService.sendGet("/showLoginName")
            .then(function (response) {
                $scope.loginName = response.data.loginName;
            });
    }
});