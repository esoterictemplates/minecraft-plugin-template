package dev.enderman.minecraft.plugins.template.bootstrap

import dev.enderman.minecraft.plugins.template.TemplatePlugin
import io.papermc.paper.plugin.bootstrap.BootstrapContext
import org.bukkit.plugin.PluginDescriptionFile
import org.mockbukkit.mockbukkit.MockBukkit
import org.mockbukkit.mockbukkit.ServerMock
import org.mockbukkit.mockbukkit.plugin.MockBukkitPluginLoader
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotNull

class TemplatePluginBootstrapTest {

    private lateinit var server: ServerMock
    private lateinit var plugin: TemplatePlugin
    private lateinit var pluginBootstrap: TemplatePluginBootstrap

    @BeforeTest fun setUp() {
        server = MockBukkit.mock()
        plugin = MockBukkit.loadWith(TemplatePlugin::class.java, "paper-plugin.yml")
        pluginBootstrap = TemplatePluginBootstrap()

        MockBukkit.ensureMocking()
    }

    @Test fun bootstrapTest() {
        assertNotNull(server)
        assertNotNull(plugin)
        assertNotNull(pluginBootstrap)
    }

    @AfterTest fun tearDown() {
        MockBukkit.unmock()
    }
}
