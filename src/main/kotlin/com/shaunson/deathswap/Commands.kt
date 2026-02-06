package com.shaunson.deathswap

import net.kyori.adventure.text.*
import net.kyori.adventure.text.format.*
import org.bukkit.*
import org.bukkit.command.*

class Commands : CommandExecutor, TabCompleter {
    override fun onCommand(
        sender: CommandSender, command: Command, label: String, args: Array<out String>
    ): Boolean {
        if (args.isEmpty()) {
            CommandError(sender).syntax("/death-swap <swap-time|start|reset>")
            return true
        }
        when (args[0]) {
            "time" -> {
                if (!sender.isOp) {
                    CommandError(sender).permission("change swapping period")
                    return true
                }
                if (args.size != 2) {
                    CommandError(sender).syntax("/death-swap time <SecondsBetweenSwap>")
                    return true
                }
                try {
                    totalTime = args[1].toInt()
                } catch (_: NumberFormatException) {
                    CommandError(sender).syntax("/death-swap time <SecondsBetweenSwap>")
                    return true
                }
                Bukkit.getServer().broadcast(
                    Component.text("Time between swaps has been set to $totalTime seconds!", NamedTextColor.GREEN)
                )
            }
            
            "reset" -> {
                if (!sender.isOp) {
                    CommandError(sender).permission("reset the game")
                    return true
                }
                Game().reset()
            }
            
            "start" -> {
                if (!sender.isOp) {
                    CommandError(sender).permission("start the game")
                    return true
                }
                if (totalTime == 0) {
                    CommandError(sender).other("Please set the swapping period before starting! (with '/death-swap time')")
                    return true
                }
                if (timer != null) {
                    CommandError(sender).other("The game is already running!")
                    return true
                }
                Timer().start()
                Bukkit.getServer().broadcast(
                    Component.text("The game has started!", NamedTextColor.GREEN)
                )
            }
        }
        return true
    }
    
    override fun onTabComplete(
        sender: CommandSender, command: Command, alias: String, args: Array<out String>
    ): List<String?>? {
        return when (args.size) {
            1 -> listOf("time", "start", "reset")
            2 -> {
                when (args[1]) {
                    "time" -> listOf("300")
                    else -> emptyList()
                }
            }
            
            else -> emptyList()
        }
    }
}

class CommandError(val sender: CommandSender) {
    fun permission(theyCannot: String = "perform this command") {
        sender.sendMessage(Component.text("You don't have permission to $theyCannot.", NamedTextColor.RED))
    }
    
    fun syntax(correctSyntax: String) {
        sender.sendMessage(Component.text("Wrong syntax - correct syntax: '$correctSyntax'.", NamedTextColor.RED))
    }
    
    fun other(errorMessage: String) {
        sender.sendMessage(Component.text(errorMessage).color(NamedTextColor.RED))
    }
}