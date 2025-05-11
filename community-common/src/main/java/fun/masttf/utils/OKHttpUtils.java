package fun.masttf.utils;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;


import fun.masttf.entity.enums.ResponseCodeEnum;
import fun.masttf.exception.BusinessException;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OKHttpUtils {
    private static final int TIME_OUT_SECONDS = 5;
    private static Logger logger = org.slf4j.LoggerFactory.getLogger(OKHttpUtils.class);

    private static OkHttpClient.Builder getClientBuilder() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder().followRedirects(false).addInterceptor(new RedirectInterceptor()).retryOnConnectionFailure(false);
        clientBuilder.connectTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS).readTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS);
        return clientBuilder;
    }

    private static Request.Builder getRequestBuilder(Map<String, String> header) {
        Request.Builder requestBuilder = new Request.Builder();
        if(header != null) {
            for (Map.Entry<String, String> entry : header.entrySet()) {
                String key = entry.getKey();
                String value;
                if(entry.getValue() == null) {
                    value = "";
                } else {
                    value = entry.getValue();
                }
                requestBuilder.addHeader(key, value);
            }
        }
        return requestBuilder;
    }

    private static FormBody.Builder getBuilder(Map<String, String> params) {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        if(params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value;
                if(entry.getValue() == null) {
                    value = "";
                } else {
                    value = entry.getValue();
                }
                formBodyBuilder.add(key, value);
            }
        }
        return formBodyBuilder;
    }

    public static String getRequest(String url) throws BusinessException {
        ResponseBody responseBody = null;
        try {
            OkHttpClient.Builder clientBuilder = getClientBuilder();
            Request.Builder requestBuilder = getRequestBuilder(null);
            OkHttpClient client = clientBuilder.build();
            Request request = requestBuilder.url(url).build();
            Response response = client.newCall(request).execute();
            responseBody = response.body();
            return responseBody.string();
        } catch (SocketTimeoutException | ConnectException e) {
            logger.error("OKHttp POST 请求超时, url:{}", url, e);
            throw new BusinessException(ResponseCodeEnum.CODE_900);
        } catch (Exception e) {
            logger.error("OKHttp GET 请求异常", e);
            return null;
        } finally {
            if (responseBody != null) {
                try {
                    responseBody.close();
                } catch (Exception e) {
                    logger.error("OKhttp 关闭响应体失败", e);
                }
            }
        }
    }

    public static String postRequest(String url,Map<String, String> header, Map<String, String> params) throws BusinessException {
        ResponseBody responseBody = null;
        try {
            OkHttpClient.Builder clientBuilder = getClientBuilder();
            Request.Builder requestBuilder = getRequestBuilder(header);
            FormBody.Builder builder = getBuilder(params);
            OkHttpClient client = clientBuilder.build();
            RequestBody requestBody = builder.build();
            Request request = requestBuilder.url(url).post(requestBody).build();
            Response response = client.newCall(request).execute();
            responseBody = response.body();
            return responseBody.string();
        } catch (SocketTimeoutException | ConnectException e) {
            logger.error("OKHttp POST 请求超时, url:{}", url, e);
            throw new BusinessException(ResponseCodeEnum.CODE_900);
        } catch (Exception e) {
            logger.error("OKHttp POST 请求异常", e);
            return null;
        } finally {
            if (responseBody != null) {
                try {
                    responseBody.close();
                } catch (Exception e) {
                    logger.error("OKhttp 关闭响应体失败", e);
                }
            }
        }
    }
}

class RedirectInterceptor implements Interceptor {
    private static Logger logger = org.slf4j.LoggerFactory.getLogger(RedirectInterceptor.class);
    
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        int code = response.code();
        if(code == 307 || code == 301 || code == 302) {
            String location = response.header("Location");
            logger.info("重定向地址:location{}", location);
            Request newRequest = request.newBuilder().url(location).build();
            response = chain.proceed(newRequest);
        }
        return response;
    }
}
