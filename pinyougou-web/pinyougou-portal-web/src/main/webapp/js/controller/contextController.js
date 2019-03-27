/** 定义首页控制器层 */
app.controller("contextController", function($scope,$controller, baseService){
    $controller("baseController",{$scope:$scope});
    $scope.findContentByCategoryId = function (categoryId) {
        baseService.sendGet("/content/findContentByCategoryId?categoryId="+categoryId)
            .then(function (response) {
                $scope.contentList = response.data;
            });
    };
    /** 跳转到搜索系统 */
    $scope.search = function(){
        var keyword = $scope.keywords ? $scope.keywords : "";
        location.href="http://search.pinyougou.com?keywords=" + keyword;
    };
});