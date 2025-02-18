package dev.enderman.minecraft.plugins.template

import org.mockbukkit.mockbukkit.MockBukkit
import org.mockbukkit.mockbukkit.ServerMock
import kotlin.test.*

abstract class AbstractTemplatePluginTest {
    protected lateinit var server: ServerMock
    protected lateinit var plugin: TemplatePlugin

    @BeforeTest fun setUp() {
        server = MockBukkit.mock()
        plugin = MockBukkit.loadWith(TemplatePlugin::class.java, "paper-plugin.yml")
    }

    @Test fun `server should be mocked correctly`() {
        MockBukkit.ensureMocking()
    }

    @Test fun `server should exist`() {
        assertNotNull(server)
    }

    @Test fun `plugin should exist`() {
        assertNotNull(plugin)
    }

    @Test fun `plugin should be enabled`() {
        assertTrue(plugin.isEnabled)
    }

    @AfterTest fun tearDown() {
        MockBukkit.unmock()
    }
}
