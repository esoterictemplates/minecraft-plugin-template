package dev.enderman.minecraft.plugins.template

import org.junit.jupiter.api.assertDoesNotThrow
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

    @Test fun `server mocked correctly`() {
        assertDoesNotThrow("Server should be mocked correctly.") {
            MockBukkit.ensureMocking()
        }
    }

    @Test fun `server exists`() {
        assertNotNull(server, "Server should not be null.")
    }

    @Test fun `plugin exists`() {
        assertNotNull(plugin, "Plugin should not be null.")
    }

    @Test fun `plugin enabled`() {
        assertTrue(plugin.isEnabled, "Plugin should be enabled.")
    }

    @AfterTest fun tearDown() {
        MockBukkit.unmock()
    }
}
