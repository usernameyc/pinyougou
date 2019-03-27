app.controller("baseController",function ($scope , $http) {
    $scope.loadUsername = function () {
        //定义重定向url;
        $scope.redirectUrl = window.encodeURIComponent(location.href);
        $http.get("/user/loginName")
            .then(function (response) {
                $scope.loginName = response.data.loginName;
            });
    }
})