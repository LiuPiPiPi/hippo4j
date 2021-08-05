package com.github.dynamic.threadpool.starter.remote;

import com.github.dynamic.threadpool.common.web.base.Result;
import com.github.dynamic.threadpool.starter.config.DynamicThreadPoolProperties;
import com.github.dynamic.threadpool.starter.toolkit.HttpClientUtil;

import java.util.Map;

/**
 * Server Http Agent.
 *
 * @author chen.ma
 * @date 2021/6/23 20:50
 */
public class ServerHttpAgent implements HttpAgent {

    private final DynamicThreadPoolProperties dynamicThreadPoolProperties;

    private final ServerListManager serverListManager;

    private final HttpClientUtil httpClientUtil;

    public ServerHttpAgent(DynamicThreadPoolProperties properties, HttpClientUtil httpClientUtil) {
        this.dynamicThreadPoolProperties = properties;
        this.httpClientUtil = httpClientUtil;
        this.serverListManager = new ServerListManager(dynamicThreadPoolProperties);
    }

    @Override
    public void start() {

    }

    @Override
    public String getTenantId() {
        return dynamicThreadPoolProperties.getNamespace();
    }

    @Override
    public String getEncode() {
        return null;
    }

    @Override
    public Result httpPostByDiscovery(String url, Object body) {
        return httpClientUtil.restApiPost(url, body, Result.class);
    }

    @Override
    public Result httpGetByConfig(String path, Map<String, String> headers, Map<String, String> paramValues, long readTimeoutMs) {
        return httpClientUtil.restApiGetByThreadPool(buildUrl(path), headers, paramValues, readTimeoutMs, Result.class);
    }

    @Override
    public Result httpPostByConfig(String path, Map<String, String> headers, Map<String, String> paramValues, long readTimeoutMs) {
        return httpClientUtil.restApiPostByThreadPool(buildUrl(path), headers, paramValues, readTimeoutMs, Result.class);
    }

    @Override
    public Result httpDeleteByConfig(String path, Map<String, String> headers, Map<String, String> paramValues, long readTimeoutMs) {
        return null;
    }

    private String buildUrl(String path) {
        return serverListManager.getCurrentServerAddr() + path;
    }

}
