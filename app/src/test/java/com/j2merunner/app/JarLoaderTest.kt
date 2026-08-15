package com.j2merunner.app

import org.junit.Test
import org.junit.Assert.*

class JarLoaderTest {

    @Test
    fun testJarLoaderCreation() {
        val loader = JarLoader()
        assertNotNull(loader)
    }
}
