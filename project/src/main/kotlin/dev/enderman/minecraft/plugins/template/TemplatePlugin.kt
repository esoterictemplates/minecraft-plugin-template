package dev.enderman.minecraft.plugins.template

import org.bukkit.plugin.java.JavaPlugin

open class TemplatePlugin : JavaPlugin() {

    init {
        logger.info("Initialising plugin...")

        logger.info("Initialised plugin")
    }

    override fun onEnable() {
        logger.info("Running version ${pluginMeta.version} for Minecraft ${pluginMeta.apiVersion} by ${pluginMeta.authors.joinToString(", ")}")

        logger.info("Plugin has been fully enabled")
    }

    override fun onDisable() {
        logger.info("Disabled plugin...")
    }
}
