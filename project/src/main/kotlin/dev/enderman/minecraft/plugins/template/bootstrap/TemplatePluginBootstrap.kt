package dev.enderman.minecraft.plugins.template.bootstrap

import dev.enderman.minecraft.plugins.template.TemplatePlugin
import io.papermc.paper.plugin.bootstrap.BootstrapContext
import io.papermc.paper.plugin.bootstrap.PluginBootstrap
import io.papermc.paper.plugin.bootstrap.PluginProviderContext
import org.bukkit.plugin.java.JavaPlugin

class TemplatePluginBootstrap : PluginBootstrap {
    override fun bootstrap(context: BootstrapContext) {
        context.logger.info("Bootstrapping plugin...")
        context.logger.info("Bootstrapping complete")
    }

    override fun createPlugin(context: PluginProviderContext): JavaPlugin {
        context.logger.info("Creating plugin...")

        val plugin = TemplatePlugin()

        context.logger.info("Created plugin")

        return plugin
    }
}
