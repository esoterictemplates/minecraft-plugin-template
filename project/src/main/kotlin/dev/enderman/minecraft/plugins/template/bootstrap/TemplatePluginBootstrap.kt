package dev.enderman.minecraft.plugins.template.bootstrap

import io.papermc.paper.plugin.bootstrap.BootstrapContext
import io.papermc.paper.plugin.bootstrap.PluginBootstrap

class TemplatePluginBootstrap : PluginBootstrap {
    override fun bootstrap(context: BootstrapContext) {
        context.logger.info("Bootstrapping plugin...")
        context.logger.info("Bootstrapping complete")
    }
}
