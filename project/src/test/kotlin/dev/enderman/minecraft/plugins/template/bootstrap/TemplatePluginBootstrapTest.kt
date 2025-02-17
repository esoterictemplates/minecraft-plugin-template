package dev.enderman.minecraft.plugins.template.bootstrap

import dev.enderman.minecraft.plugins.template.TemplatePlugin
import org.mockbukkit.mockbukkit.MockBukkit
import org.mockbukkit.mockbukkit.ServerMock
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotNull

class TemplatePluginBootstrapTest {

    private lateinit var server: ServerMock
    private lateinit var plugin: TemplatePlugin

    @BeforeTest fun setUp() {
        server = MockBukkit.mock()
        plugin = MockBukkit.loadWith(TemplatePlugin::class.java, "paper-plugin.yml")
    }

    @Test fun onEnableTest() {
        assertNotNull(server)
        assertNotNull(plugin)
    }

    @AfterTest fun tearDown() {
        MockBukkit.unmock()
    }
}
