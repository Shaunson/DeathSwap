package com.shaunson.deathswap.listeners

import com.shaunson.deathswap.Game
import net.kyori.adventure.text.*
import net.kyori.adventure.text.format.*
import net.kyori.adventure.title.*
import org.bukkit.*
import org.bukkit.event.*
import org.bukkit.event.entity.*
import org.bukkit.event.player.*
import java.util.*

class PlayerDeathListener : Listener {
    
    private val deathLocations = mutableMapOf<UUID, Location>()
    
    @EventHandler
    fun onDeath(event: PlayerDeathEvent) {
        val player = event.player
        val location = player.location
        
        // Store the place where they died
        deathLocations[player.uniqueId] = location
        player.gameMode = GameMode.SPECTATOR
        event.deathMessage(
            Component.text("${player.name} has been eliminated!", NamedTextColor.RED)
        )
    }
    
    @EventHandler
    fun onRespawn(event: PlayerRespawnEvent) {
        val player = event.player
        val id = player.uniqueId
        val location = deathLocations.remove(id)
        
        if (location != null) {
            event.respawnLocation = location
        }
        val alive = Bukkit.getOnlinePlayers()
            .filter { it.gameMode == GameMode.SURVIVAL }
        
        if (alive.size <= 1) {
            val winner = alive.firstOrNull()
            if (winner != null) {
                for (p in Bukkit.getOnlinePlayers()) {
                    p.showTitle(
                        Title.title(
                            Component.text("${winner.name} has won the game!", NamedTextColor.GREEN),
                            Component.empty()
                        )
                    )
                }
            } else {
                for (p in Bukkit.getOnlinePlayers()) {
                    p.showTitle(
                        Title.title(
                            Component.text("Nobody won the game!", NamedTextColor.YELLOW),
                            Component.empty()
                        )
                    )
                }
            }
            event.respawnLocation = Bukkit.getWorld("world")?.spawnLocation!!
            Game().reset()
        }
        
    }
}
