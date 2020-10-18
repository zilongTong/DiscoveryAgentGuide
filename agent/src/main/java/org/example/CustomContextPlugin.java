package org.example;

import com.nepxion.discovery.plugin.strategy.agent.plugin.AbstractPlugin;

public class CustomContextPlugin extends AbstractPlugin {
    private Boolean threadCustomEnabled = Boolean.valueOf(System.getProperty("thread.custom.enabled", "false"));

    @Override
    protected String getMatcherClassName() {
        // 返回存储ThreadLocal对象的类名，由于插件是可以插拔的，所以必须是字符串形式，不允许是显式引入类
        return "org.example.CustomContext";
    }

    @Override
    protected String getHookClassName() {
        // 返回ThreadLocalHook类名
        return CustomContextHook.class.getName();
    }

    @Override
    protected boolean isEnabled() {
        // 通过外部-Dthread.custom.enabled=true/false的运行参数来控制当前Plugin是否生效。该方法在父类中定义的返回值为true，即缺省为生效
        return threadCustomEnabled;
    }
}