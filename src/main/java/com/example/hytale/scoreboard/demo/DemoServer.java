package com.example.hytale.scoreboard.demo;

import com.example.hytale.scoreboard.MainPlugin;
import com.example.hytale.scoreboard.adapter.PlayerAdapter;
import com.example.hytale.scoreboard.adapter.impl.HytaleAdapterImpl;

public class DemoServer {
    public static void main(String[] args) throws InterruptedException {
        HytaleAdapterImpl adapter = new HytaleAdapterImpl("HytaleScoreboardTest");
        MainPlugin plugin = new MainPlugin(adapter);
        plugin.onEnable();

        PlayerAdapter alice = adapter.addDemoPlayer("Alice");
        PlayerAdapter bob = adapter.addDemoPlayer("Bob");

        adapter.dispatchCommand(alice, "scoreboard test");

        Thread.sleep(3000L);
        adapter.dispatchCommand(bob, "scoreboard off");

        Thread.sleep(3000L);
        adapter.removeDemoPlayer(bob.getUniqueId());

        Thread.sleep(3000L);
        plugin.onDisable();
        adapter.shutdown();
    }
}
