package dev.enderman.minecraft.plugins.template

import org.mockbukkit.mockbukkit.MockBukkit
import org.mockbukkit.mockbukkit.ServerMock
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class TemplatePluginTest {

    private lateinit var server: ServerMock
    private lateinit var plugin: TemplatePlugin

    @BeforeTest fun setUp() {
        server = MockBukkit.mock()
        plugin = MockBukkit.load(TemplatePlugin::class.java)
    }

    @Test fun onEnableTest() {}

    @AfterTest fun tearDown() {
        MockBukkit.unmock()
    }
}
