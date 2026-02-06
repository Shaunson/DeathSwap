package com.shaunson.deathswap


import com.shaunson.deathswap.listeners.PlayerDeathListener
import org.bukkit.*
import org.bukkit.plugin.java.*

var totalTime = 0

class Main : JavaPlugin() {
    
    override fun onEnable() {
        // Plugin startup logic
        logger.info("'Death Swap' plugin has been enabled!")
        Bukkit.getWorld("world")?.setGameRule(GameRules.IMMEDIATE_RESPAWN, true)
        // Command Registration
        getCommand("death-swap")?.setExecutor(Commands())
        
        // Listener Registration
        server.pluginManager.registerEvents(PlayerDeathListener(), this)
    }
    
    override fun onDisable() {
        // Plugin shutdown logic
    }
}
