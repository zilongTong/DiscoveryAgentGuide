package org.example;

import java.util.Map;

import com.nepxion.discovery.plugin.strategy.agent.threadlocal.AbstractThreadLocalHook;

public class CustomContextHook extends AbstractThreadLocalHook {
    @Override
    public Object create() {
        // 从主线程的ThreadLocal里获取并返回上下文对象
        return CustomContext.getCurrentContext().getAttributes();
    }

    @Override
    public void before(Object object) {
        // 把create方法里获取到的上下文对象放置到子线程的ThreadLocal里
        if (object instanceof Map) {
            CustomContext.getCurrentContext().setAttributes((Map<String, String>) object);
        }
    }

    @Override
    public void after() {
        // 线程结束，销毁上下文对象
        CustomContext.clearCurrentContext();
    }
}