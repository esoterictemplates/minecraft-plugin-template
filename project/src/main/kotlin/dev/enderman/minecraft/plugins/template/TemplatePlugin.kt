package dev.enderman.minecraft.plugins.template

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.Command
import org.bukkit.command.defaults.VersionCommand
import org.bukkit.plugin.java.JavaPlugin

open class TemplatePlugin : JavaPlugin() {

    init {
        logger.info("Initialising plugin...")

        logger.info("Initialised plugin")
    }

    override fun onEnable() {
        var versionCommand: Command? = null

        server.commandMap.knownCommands.forEach { entry -> if (entry.value is VersionCommand) versionCommand = entry.value }

        versionCommand?.execute(
            server.consoleSender,
            versionCommand!!.name,
            arrayOf(pluginMeta.name)
        )

        server.consoleSender.sendMessage(
            Component.text("API: v")
                .append(Component.text(pluginMeta.apiVersion!!, NamedTextColor.GREEN))
        )

        logger.info("Plugin has been fully enabled")
    }

    override fun onDisable() {
        logger.info("Disabled plugin")
    }
}
