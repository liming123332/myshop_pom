$(function () {
    $.ajax({
        url:"http://localhost:8084/sso/isLogin",
        success:function (data) {
            //alert(data);
            if(data){
                $("#pid").html("您好"+JSON.parse(data).username+"" +
                    "，欢迎来到<b><a href=\"/\">ShopCZ商城</a></b>" +
                    "[<a href=\"http://localhost:8084/sso/loginout\">注销</a>]")
            }else {
                $("#pid").html("[<a href='javascript:login();'>登录</a>]" +
                    "[<a href=\"http://localhost:8084/sso/toRegister\">注册</a>]")
            }
        },
        dataType:"jsonp",
        jsonpCallback:"isLogin",

    })
})
function login() {
    var returnUrl=location.href;
    returnUrl=encodeURI(returnUrl);
    location.href="http://localhost:8084/sso/toLogin?returnUrl=" + returnUrl;
}