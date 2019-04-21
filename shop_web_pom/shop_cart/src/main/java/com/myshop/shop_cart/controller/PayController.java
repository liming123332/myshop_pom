package com.myshop.shop_cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.myshop.entity.Orders;
import com.myshop.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;

import static com.alipay.api.AlipayConstants.APP_ID;

@Controller
@RequestMapping("/pay")
public class PayController {

    @Reference
    private IOrderService orderService;

    @GetMapping("/alipay")
    public void alipay(String orderid, HttpServletResponse response) throws IOException {

        Orders orders = orderService.selectOrderByOrderid(orderid);
        Double allprice = orders.getAllprice().doubleValue();
        AlipayClient alipayClient =
                new DefaultAlipayClient(
                        "https://openapi.alipaydev.com/gateway.do",
                        "2016092700605461",
                        "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCk3RPb0x6W4/DQhNLx+g0Dy4cdIuEeilj9ApwoZAAvuqvcaTt3BceBBLmbAho7HqPcVBDm7/DZAw0Ix0r2Ju0ukz1H49OKR3enX0tWpDfUxGE3X1e1nxxlP3eCreFGKe0jKH+ryUFvVwvGfuZeSomPAoR+6JtDjdDc7OgLzCyLTRnHzAluzMZXjnFmc3xHsdp4QJlhfxTj7FTi+SQuHCQ0GbALKOszfdTFsOpPiwatBw5uFQicFO+9rgLuVeIExvtxNDaXO6x2qE4qW7C0OVH7s7/z9l7ZVgHZjLNyv9eatahZ1moDTBIIjonseyoPFvjpULxDYGX3ji3fFHUFTjgBAgMBAAECggEAcdw1sOZYBAIc+QYi4fe+MVbJU/gY3gzSdLruun6GGnCj7S/IX+Vf6d0nisSi9R2uMablnXktL9OJlvx86w/rUvVhp1rTaHb/TlrkH0x/OrT+J03zt33SEKMKmPgZ02l1W4jG1A/E326QtWBDHfAAnQIyG6vuP3OcQlhj0nIKeZGEYUG71K3FGTjbXSDz5FdtWp1UkktA16ciDREBG8sfdW86nAZmaukv3QvgVwgvWa0K698IPHpArzpFVv07G/n30h0Cdx3kH0GiVVy01CtgrJAD+iqIEynCFmkEPmYDcI/9gokEqIt606gjDWb76QVyIgCtA5Dhsw3sdJ0ZmnA2oQKBgQDRP7LlU7xp3SJVeoc1Nh8NNe8ObC7GelPWO78Q2uR4fVzj11BNLqUCFoQM0JJk3FdgL/Ve0NqhGZLw0y983I03YY0/qFcn9QX2114ZcrqE9ppqiDmMFSPiT7HJwNgiANJcIM1EnvMKADZnrDav2aHHw3vTNHyPE0ZAImOsW1tXLQKBgQDJsrLAL2qTNgIRXh1u9V2JI3HGanMYZ33m78luMG6YJwEfE+312Tr+pwJwDEyh2ZNAGsv9B5umppPg+KU5S0ONRAhyiavtqrKh8XnZ5pSMhQtNyMZ1rq50635MateaJNL2gmnEBX8fRawKpUtEtvi4bhw57Po62ByJ9oIe9xAopQKBgQCtbNrrgLw/DX1AVT4slv+jZlkMnhMigw5rF7ei05jaWCSd3b17SraOBXVKXa+HkquEWBtIi1c4gEat/Ip2NSGBjgOQiAg8YLKhxBwEgXcfbnfK5b60YG0OS7jxmzhG7mFq4/Dc99Q9LLHquOvWUUzpkRZWlUDLIFFwJFBx8IHCvQKBgQCvW0+RSygrSJ9PnjWUzHo/yrMWQGJEa2XIq9zCkhT076RH8rnGLtDdqTGzXN4bMOCWsTV8jWrD3rteso5jW44leWqmug7iGLApn5nC2nd4HAuR2ubAVdiTClLJ+XHtL+rCKuvtYqH8/EPxFXh1C2b+Yazkp6V+F3nocIr8hOxliQKBgAL5UinTb+wZJw0lLJK5ZJ3h5idGpmkSVPJI300VPTn2jzI1EvBSi0BnFYhIBORyhC/5Tt7VSDuoqMWQIYxHZZQsQ5DCiTXHGcJbJYnG8ERdGR777v7GNhgMTyoW2HrJ5OyTTOEMbR1ah76ZNpcosFeIXLBWqZDfcpm+XRUWHP+Y",
                        "json",
                        "utf-8",
                        "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApN0T29MeluPw0ITS8foNA8uHHSLhHopY/QKcKGQAL7qr3Gk7dwXHgQS5mwIaOx6j3FQQ5u/w2QMNCMdK9ibtLpM9R+PTikd3p19LVqQ31MRhN19XtZ8cZT93gq3hRintIyh/q8lBb1cLxn7mXkqJjwKEfuibQ43Q3OzoC8wsi00Zx8wJbszGV45xZnN8R7HaeECZYX8U4+xU4vkkLhwkNBmwCyjrM33UxbDqT4sGrQcObhUInBTvva4C7lXiBMb7cTQ2lzusdqhOKluwtDlR+7O/8/Ze2VYB2Yyzcr/XmrWoWdZqA0wSCI6J7HsqDxb46VC8Q2Bl944t3xR1BU44AQIDAQAB",
                        "RSA2"); //获得初始化的AlipayClient
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
        alipayRequest.setReturnUrl("www.baidu.com");
        alipayRequest.setNotifyUrl("http://muhuan.qicp.vip/pay/payNotify");//在公共参数中设置回跳和通知地址
        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\""+orderid+"\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":"+allprice+"," +
                "    \"subject\":\""+orderid+"\"," +
                "    \"body\":\""+orderid+"\"," +
                "    \"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\"," +
                "    \"extend_params\":{" +
                "    \"sys_service_provider_id\":\"2088511833207846\"" +
                "    }"+
                "  }");//填充业务参数
        String form="";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=utf-8" );
        response.getWriter().write(form);//直接将完整的表单html输出到页面
        response.getWriter().flush();
        response.getWriter().close();
    }

    @RequestMapping("/payNotify")
    @ResponseBody
    public void payNotify(String out_trade_no, String trade_status){
        //根据订单号获得订单信息
        Orders orders = orderService.selectOrderByOrderid(out_trade_no);
        if (trade_status.equals("TRADE_SUCCESS")){
            //交易成功
            orders.setStatus(1);
            orderService.updateOrders(orders);
        }
    }
}
