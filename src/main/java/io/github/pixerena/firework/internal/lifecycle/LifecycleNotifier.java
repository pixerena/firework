package io.github.pixerena.firework.internal.lifecycle;

public interface LifecycleNotifier {
    void notifyPluginEnable() throws LifecycleMethodInvocationException;
    void notifyServerFirstTick() throws LifecycleMethodInvocationException;
    void notifyPluginDisable() throws LifecycleMethodInvocationException;
}
