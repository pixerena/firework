package com.pixerena.firework;

import com.destroystokyo.paper.entity.ai.MobGoals;
import com.google.inject.AbstractModule;
import io.papermc.paper.datapack.DatapackManager;
import io.papermc.paper.plugin.configuration.PluginMeta;
import io.papermc.paper.threadedregions.scheduler.AsyncScheduler;
import io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler;
import io.papermc.paper.threadedregions.scheduler.RegionScheduler;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.Server;
import org.bukkit.command.CommandMap;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.help.HelpMap;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.potion.PotionBrewer;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.structure.StructureManager;

class PaperModule extends AbstractModule {
    private final JavaPlugin plugin;

    public PaperModule(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    protected void configure() {
        // Bind JavaPlugin
        bind(JavaPlugin.class).toInstance(this.plugin);

        // Methods from org.bukkit.plugin.java.JavaPlugin
        var server = this.plugin.getServer();
        bind(FileConfiguration.class).toInstance(this.plugin.getConfig());
        // Disable java.util.logging.Logger since it conflicts with Guice built-in Logger bindings
        // bind(Logger.class).toInstance(this.plugin.getLogger());
        //noinspection UnstableApiUsage
        bind(PluginMeta.class).toInstance(this.plugin.getPluginMeta());
        bind(Server.class).toInstance(server);

        // Inherited methods from org.bukkit.plugin.Plugin
        bind(ComponentLogger.class).toInstance(this.plugin.getComponentLogger());
        bind(org.slf4j.Logger.class).toInstance(this.plugin.getSLF4JLogger());

        // Methods from org.bukkit.Server
        bind(AsyncScheduler.class).toInstance(server.getAsyncScheduler());
        bind(CommandMap.class).toInstance(server.getCommandMap());
        bind(ConsoleCommandSender.class).toInstance(server.getConsoleSender());
        bind(DatapackManager.class).toInstance(server.getDatapackManager());
        bind(GlobalRegionScheduler.class).toInstance(server.getGlobalRegionScheduler());
        bind(HelpMap.class).toInstance(server.getHelpMap());
        bind(ItemFactory.class).toInstance(server.getItemFactory());
        bind(Messenger.class).toInstance(server.getMessenger());
        bind(MobGoals.class).toInstance(server.getMobGoals());
        bind(PluginManager.class).toInstance(server.getPluginManager());
        bind(PotionBrewer.class).toInstance(server.getPotionBrewer());
        bind(RegionScheduler.class).toInstance(server.getRegionScheduler());
        bind(BukkitScheduler.class).toInstance(server.getScheduler());
        // Disable ScoreboardManager binding since it violates @NotNull annotation and returns null
        // bind(ScoreboardManager.class).toInstance(server.getScoreboardManager());
        bind(ServicesManager.class).toInstance(server.getServicesManager());
        bind(StructureManager.class).toInstance(server.getStructureManager());
    }
}
