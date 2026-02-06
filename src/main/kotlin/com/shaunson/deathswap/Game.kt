package com.shaunson.deathswap

import net.kyori.adventure.text.*
import net.kyori.adventure.text.format.*
import net.kyori.adventure.title.*
import org.bukkit.*
import org.bukkit.plugin.java.JavaPlugin
import kotlin.random.*

class Game {
    fun swap() {
        val playersLocations = Bukkit.getOnlinePlayers().associateWith { it.location }.toMutableMap()
        for (player in Bukkit.getOnlinePlayers()) {
            val destinationPlayer = Bukkit.getOnlinePlayers().toMutableList()[Random.nextInt(0, Bukkit.getOnlinePlayers().size)]
            player.teleport(playersLocations[destinationPlayer]!!)
            playersLocations.remove(destinationPlayer)
            player.showTitle(Title.title(Component.text("Swapped", NamedTextColor.GREEN), Component.text("to ${destinationPlayer.name}'s location")))
        }
    }

    fun reset() {
        Bukkit.getScheduler().cancelTasks(JavaPlugin.getPlugin(Main::class.java)) // Stop the timer
        timer = null
        for (player in Bukkit.getOnlinePlayers()) {
            player.teleport(Bukkit.getWorld("world")?.spawnLocation!!)
            player.activePotionEffects.forEach { potionEffect -> player.removePotionEffect(potionEffect.type) }
            player.gameMode = GameMode.SURVIVAL
            player.health = 20.0
        }
    }
}