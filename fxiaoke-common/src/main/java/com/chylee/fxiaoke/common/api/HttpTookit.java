package com.chylee.fxiaoke.common.api;

import com.chylee.fxiaoke.common.event.RequestEvent;
import com.chylee.fxiaoke.common.event.fxiaoke.BaseRespEvent;
import com.chylee.fxiaoke.common.event.fxiaoke.ObjectRespEvent;
import com.chylee.fxiaoke.common.exception.FxiaokeException;
import com.chylee.fxiaoke.common.util.fxiaoke.SigUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

/**
 * https 请求工具类
 * 
 * @author huanghp
 * @date 2015年8月28日
 */
public class HttpTookit {

    private static final Logger LOG = LoggerFactory.getLogger(HttpTookit.class);

    private static final CloseableHttpClient httpClient;

    public static final String CHARSET = "UTF-8";

    static {
        // 饱含模式实现 httpClient 单例
        httpClient = createSSLClientDefault();
    }

    private HttpTookit() {}

    public static CloseableHttpClient createSSLClientDefault() {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(600000).setSocketTimeout(150000).build();

        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                // 信任所有
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();

            SSLConnectionSocketFactory sslsf =
                    new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            return HttpClients.custom().setSSLSocketFactory(sslsf).setDefaultRequestConfig(config).build();
        } catch (Exception e) {
            LOG.error("init httpClient error, details:", e);
        }

        return HttpClients.createDefault();
    }

    /**
     * post + json 发送请求
     * 
     * @param url
     * @param parameters
     * @return @see HttpResponseMessageVO
     * @throws FxiaokeException
     */
    public static HttpResponseMessageVO sendPostByJson(String url, String parameters) throws FxiaokeException {
        if (StringUtils.isEmpty(url)) {
            throw new FxiaokeException(Constants.interfaceException.ILLEGAL_ARGUMENT.code,
                    Constants.interfaceException.ILLEGAL_ARGUMENT.msg + ": url is illegal !");
        }

        HttpResponseMessageVO httpResponseMessageVO = new HttpResponseMessageVO();
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        try {
            StringEntity params = new StringEntity(parameters, CHARSET);
            response = getResponse(url, params);;

            int statusCode = response.getStatusLine().getStatusCode();
            entity = response.getEntity();
            httpResponseMessageVO.setHttpCode(Integer.toString(statusCode));
            
            if (statusCode == HttpStatus.SC_OK && entity != null) {
                httpResponseMessageVO.setContent(EntityUtils.toString(entity, CHARSET));
            }
        } catch (Exception e) {
            LOG.error("sendPostByJson error, details:", e);
            throw new FxiaokeException(Constants.interfaceException.INTERFACE_EXCEPTION.code,
                    Constants.interfaceException.INTERFACE_EXCEPTION.msg);
        }finally{
            try{
                if(entity != null){
                    EntityUtils.consume(entity);
                }
                if(response != null){
                    response.close();
                }
            }catch(Exception e){
                
            }
        }

        return httpResponseMessageVO;
    }

    private static ObjectRespEvent<String> sendPostByJson(String url, RequestEvent reqEvent) {
        ObjectRespEvent<String> result = new ObjectRespEvent<>();
        if (StringUtils.isEmpty(url)) {
            result.setErrorCode(Constants.interfaceException.ILLEGAL_ARGUMENT.code);
            result.setErrorMessage(Constants.interfaceException.ILLEGAL_ARGUMENT.msg + ":" + url);
            return result;
        }
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        try {
            StringEntity params = new StringEntity(new ObjectMapper().writeValueAsString(reqEvent), CHARSET);
            response = getResponse(url, params);

            int statusCode = response.getStatusLine().getStatusCode();
            entity = response.getEntity();
            
            if (statusCode == HttpStatus.SC_OK && entity != null) {
                result.setData(EntityUtils.toString(entity, CHARSET));
            } else {
                result.setErrorCode(Constants.interfaceException.INTERFACE_EXCEPTION.code);
                result.setErrorMessage(Constants.interfaceException.INTERFACE_EXCEPTION.msg + ":" + url + ",HTTP Status Code:"
                        + statusCode);
            }
        } catch (Exception e) {
            LOG.error("sendPostByJson error, details:", e);
            result.setErrorCode(Constants.interfaceException.INTERFACE_EXCEPTION.code);
            result.setErrorMessage("发送请求异常,请检查url、参数的合法性！异常错误:" + e.getMessage());
        }finally{
            try{
                if(entity != null){
                    EntityUtils.consume(entity);
                }
                if(response != null){
                    response.close();
                }
            }catch(Exception e){
                
            }
        }
        return result;
    }

    private static CloseableHttpResponse getResponse(String url, StringEntity params) throws IOException {
        HttpPost request = new HttpPost(url);
        request.addHeader("Content-type", "application/json");
        request.setEntity(params);
        return httpClient.execute(request);
    }


    public static <T extends BaseRespEvent> T sendPostByJson(String url, RequestEvent reqEvent, Class<T> clazz)
            throws JsonProcessingException {
        ObjectRespEvent<String> result = sendPostByJson(url, reqEvent);

        if (result.getErrorCode() == 0) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(result.getData(), clazz);
        }

        T t = null;

        try {
            t = clazz.newInstance();
            t.setErrorCode(result.getErrorCode());
            t.setErrorMessage(result.getErrorMessage());
        } catch (Exception e) {
            LOG.error("sendPostByJson error, details:", e);
        }

        return t;
    }

    public static void main(String[] args) throws Exception {
        String token = "testToken";
        String nonce = "9890d0eb-5aa9-4f45-9a28-0b05fdfb2588";
        long timestamp = System.currentTimeMillis();
        String content =
                "4FE34659FB17F8AEB8971177F9FF04B581B83A0A6E5F5271C1BA41BD112C9F3C21C8E3AF0978FEB57323B5A903997327D65F7C1D1FF6F4308B720F8E52248223CC6E693CC3029B4A9A4784C21D4A064956C883CE64DE09486F053957827D4B8F2D10AAABB9083B806B5F6507887BF8F9";

        String deStr =
                SigUtils.decodeAES(
                        "4FE34659FB17F8AEB8971177F9FF04B581B83A0A6E5F5271C1BA41BD112C9F3C21C8E3AF0978FEB57323B5A903997327D65F7C1D1FF6F4308B720F8E52248223CC6E693CC3029B4A9A4784C21D4A064956C883CE64DE09486F053957827D4B8F2D10AAABB9083B806B5F6507887BF8F9",
                        "x45sdf3sd1231231232xs");

        System.out.println(deStr);

        String sig = SigUtils.getSHA1(token, "" + timestamp, nonce, content);

        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("nonce", nonce);
        paramMap.put("timeStamp", "" + timestamp);
        paramMap.put("content", content);
        paramMap.put("sig", sig);

        String jsonStr = new ObjectMapper().writeValueAsString(paramMap);

        System.out.println(jsonStr);
        HttpResponseMessageVO httpResponseMessage =
                sendPostByJson("http://localhost:8080/third-sys/parse/authorize", jsonStr);
        System.out.println(httpResponseMessage.getContent());
    }

}
