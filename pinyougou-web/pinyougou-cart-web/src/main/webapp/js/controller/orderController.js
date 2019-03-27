app.controller("orderController",function ($scope,$controller , $interval ,$location, baseService) {
    $controller("cartController",{$scope:$scope});
    $scope.findAddressByUser = function () {
        baseService.sendGet("/order/findAddressByUser")
            .then(function (response) {
                $scope.dataList = response.data;
                for (var i in response.data){
                    if(response.data[i].isDefault == 1){
                        $scope.address = response.data[i];
                        break;
                    }
                }
            });
    }

    $scope.selectAddress = function (item) {
        $scope.address = item;
    };
    $scope.isSelectAddress = function (item) {
        return item = $scope.address;
    }

    $scope.order = {paymentType : '1'}
    $scope.selectPayType = function (type) {
        $scope.order.paymentType = type;
    };

    $scope.saveOrder = function () {
        $scope.order.receiverAreaName = $scope.address.address ;
        $scope.order.receiverMobile = $scope.address.mobile ;
        $scope.order.receiver = $scope.address.contact;
        baseService.sendPost("/order/save" , $scope.order)
            .then(function (response) {
                if(response.data){
                    if($scope.order.paymentType == 1){
                        location.href = "/order/pay.html";
                    }else {
                        location.href = "/order/paysuccess.html";
                    }
                }else {alert("订单提交失败!")};
            });
    }

    $scope.genPayCode = function () {
        baseService.sendGet("/order/genPayCode")
            .then(function (response) {
                $scope.money = (response.data.totalFee/100).toFixed(2);
                $scope.outTradeNo = response.data.outTradeNo;
                var qr = new QRious({
                    element : document.getElementById("qrious"),
                    size : 250 ,
                    level : 'h',
                    value : response.data.codeUrl
                });
                var timer = $interval(function () {
                    baseService.sendGet("/order/checkPayStatus?outTradeNo="+$scope.outTradeNo)
                        .then(function(response){
                        if (response.data.status == 1){
                            $interval.cancel(timer);
                            location.href="/order/paysuccess.html?money="+$scope.money;
                        }
                        if(response.data.status == 3){
                            $interval.cancel(timer);
                            location.href="/order/payfail.html";
                        }
                    });
                },3000 ,6);
            });
        timer.then(function () {
            alert("微信支付二维码失效")
        })
    }
    $scope.getMoney = function () {
        return $location.search().money;
    }
});