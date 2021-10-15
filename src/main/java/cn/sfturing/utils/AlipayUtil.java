package cn.sfturing.utils;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

/* 支付宝 */
@Component
public class AlipayUtil {

    @Value("#{systemConfigProperties[gatewayUrl]}")
    private String gatewayUrl;

    @Value("#{systemConfigProperties[app_id]}")
    private String app_id;

    @Value("#{systemConfigProperties[merchant_private_key]}")
    private String merchant_private_key;

    @Value("#{systemConfigProperties[charset]}")
    private String charset;

    @Value("#{systemConfigProperties[alipay_public_key]}")
    private String alipay_public_key;

    @Value("#{systemConfigProperties[sign_type]}")
    private String sign_type;

    @Value("#{systemConfigProperties[return_url]}")
    private String return_url;

    @Value("#{systemConfigProperties[notify_url]}")
    private String notify_url;

    private AlipayClient alipayClient;

    @PostConstruct
    public void init() {
        //1、获得初始化的AlipayClient
        alipayClient = new DefaultAlipayClient(
                gatewayUrl,//支付宝网关
                app_id,//appid
                merchant_private_key,//商户私钥
                "json",
                charset,//字符编码格式
                alipay_public_key,//支付宝公钥
                sign_type//签名方式
        );
    }

    public String getGatewayUrl() {
        return gatewayUrl;
    }

    public void setGatewayUrl(String gatewayUrl) {
        this.gatewayUrl = gatewayUrl;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getMerchant_private_key() {
        return merchant_private_key;
    }

    public void setMerchant_private_key(String merchant_private_key) {
        this.merchant_private_key = merchant_private_key;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getAlipay_public_key() {
        return alipay_public_key;
    }

    public void setAlipay_public_key(String alipay_public_key) {
        this.alipay_public_key = alipay_public_key;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getReturn_url() {
        return return_url;
    }

    public void setReturn_url(String return_url) {
        this.return_url = return_url;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public void connect(AlipayBean alipayBean, HttpServletResponse response) throws Exception {
        //2、设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        //页面跳转同步通知页面路径
        alipayRequest.setReturnUrl(return_url);
        // 服务器异步通知页面路径
        alipayRequest.setNotifyUrl(notify_url);
        //封装参数
        alipayRequest.setBizContent(JSON.toJSONString(alipayBean));
        //3、请求支付宝进行付款，并获取支付结果
        //返回付款信息
        response.setContentType("text/html;charset=" + charset);
        response.getWriter().write(alipayClient.pageExecute(alipayRequest).getBody()); //直接将完整的表单html输出到页面
        response.getWriter().flush();
        response.getWriter().close();
    }

    public void refund(AlipayBean alipayBean) throws AlipayApiException {
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizContent(JSON.toJSONString(alipayBean));
        AlipayTradeRefundResponse response = alipayClient.execute(request);
        if (!response.isSuccess()) {
            throw new RuntimeException("退款失败");
        }
    }
}