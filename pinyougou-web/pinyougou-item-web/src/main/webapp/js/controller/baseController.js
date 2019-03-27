app.controller("baseController",function ($scope,$http) {
    $scope.loadUsername = function () {
        $scope.redirectUrl = window.encodeURIComponent(location.href);
        $http.get("/user/showName").then(function (response) {
            $scope.loginName = response.data.loginName;
        })
    }

})