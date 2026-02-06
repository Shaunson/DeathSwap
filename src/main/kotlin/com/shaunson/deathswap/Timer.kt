package com.shaunson.deathswap

import net.kyori.adventure.text.*
import net.kyori.adventure.text.format.*
import net.kyori.adventure.title.*
import org.bukkit.*
import org.bukkit.plugin.java.*

var leftSeconds = 0
var timer: Runnable? = null

class Timer {
    fun start() {
        leftSeconds = totalTime
        timer = Runnable {
            leftSeconds--
            when (leftSeconds) {
                in 31..60 -> {
                    for (player in Bukkit.getOnlinePlayers()) {
                        player.sendActionBar(
                            Component.text("$leftSeconds seconds", NamedTextColor.YELLOW).append(
                                Component.text(" left until swapping", NamedTextColor.WHITE)
                            )
                        )
                    }
                }

                in 11..30 -> {
                    for (player in Bukkit.getOnlinePlayers()) {
                        player.sendActionBar(
                            Component.text("$leftSeconds seconds", NamedTextColor.GOLD)
                                .append(
                                    Component.text(" left until swapping", NamedTextColor.WHITE)
                                )
                        )
                    }
                }

                in 6..10 -> {
                    for (player in Bukkit.getOnlinePlayers()) {
                        player.sendActionBar(
                            Component.text("$leftSeconds seconds", NamedTextColor.RED)
                                .append(
                                    Component.text(" left until swapping", NamedTextColor.WHITE)
                                )
                        )
                    }
                }

                in 1..5 -> {
                    for (player in Bukkit.getOnlinePlayers()) {
                        player.sendActionBar(Component.empty())
                        player.showTitle(
                            Title.title(
                                Component.text("Swapping in ", NamedTextColor.WHITE)
                                    .append(
                                        Component.text("$leftSeconds seconds", NamedTextColor.DARK_RED)
                                    )
                                    .decorate(TextDecoration.BOLD),

                                Component.text("Get ready!")
                            )
                        )
                    }
                }

                0 -> {
                    Game().swap()
                    leftSeconds = totalTime
                }

                else -> {
                    for (player in Bukkit.getOnlinePlayers()) {
                        player.sendActionBar(
                            Component.text("$leftSeconds seconds", NamedTextColor.WHITE)
                                .append(
                                    Component.text(" left until swapping", NamedTextColor.WHITE)
                                )
                        )
                    }
                }
            }
        }
        Bukkit.getScheduler().runTaskTimer(JavaPlugin.getPlugin(Main::class.java), timer!!, 0L, 20L)
    }
}