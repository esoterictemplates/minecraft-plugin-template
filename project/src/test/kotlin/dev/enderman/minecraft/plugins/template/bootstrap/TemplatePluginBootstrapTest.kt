package dev.enderman.minecraft.plugins.template.bootstrap

import dev.enderman.minecraft.plugins.template.AbstractTemplatePluginTest
import io.mockk.mockk
import io.papermc.paper.plugin.bootstrap.BootstrapContext
import org.mockbukkit.mockbukkit.MockBukkit
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotNull

class TemplatePluginBootstrapTest : AbstractTemplatePluginTest() {
    private lateinit var pluginBootstrap: TemplatePluginBootstrap

    @BeforeTest fun initializeBootstrap() {
        pluginBootstrap = TemplatePluginBootstrap()
    }

    @Test fun `plugin bootstrap exists`() {
        assertNotNull(pluginBootstrap, "Plugin bootstrap should not be null.")
    }

    @Test fun `bootstrap method works`() {
        val contextMock = mockk<BootstrapContext>()

        pluginBootstrap.bootstrap(contextMock)
    }
}
