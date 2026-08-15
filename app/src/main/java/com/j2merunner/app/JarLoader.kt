package com.j2merunner.app

import com.j2merunner.engine.midp.MIDlet
import dalvik.system.DexClassLoader
import java.io.File
import java.util.jar.JarFile

/**
 * Loads JAR files, extracts metadata, and prepares for execution
 */
class JarLoader {

    data class JarInfo(
        val file: File,
        val name: String,
        val vendor: String,
        val version: String,
        val midletClass: String,
        val iconPath: String?,
        val extractedDir: File
    )

    fun loadJar(jarFile: File): JarInfo? {
        return try {
            val jar = JarFile(jarFile)
            val manifest = jar.manifest
            val attributes = manifest.mainAttributes

            val name = attributes.getValue("MIDlet-Name") ?: jarFile.nameWithoutExtension
            val vendor = attributes.getValue("MIDlet-Vendor") ?: "Unknown"
            val version = attributes.getValue("MIDlet-Version") ?: "1.0"

            // Parse MIDlet-1: Name, Icon, Class
            val midlet1 = attributes.getValue("MIDlet-1") ?: return null
            val parts = midlet1.split(",").map { it.trim() }
            val midletClass = parts.getOrNull(2) ?: parts.getOrNull(0) ?: return null
            val iconPath = parts.getOrNull(1)?.takeIf { it.isNotEmpty() && it != "null" }

            // Extract JAR contents
            val extractDir = File(jarFile.parentFile, ".extracted/${jarFile.nameWithoutExtension}")
            extractDir.mkdirs()

            jar.entries().asSequence().forEach { entry ->
                if (!entry.isDirectory) {
                    val outFile = File(extractDir, entry.name)
                    outFile.parentFile?.mkdirs()
                    jar.getInputStream(entry).use { input ->
                        outFile.outputStream().use { output ->
                            input.copyTo(output)
                        }
                    }
                }
            }
            jar.close()

            JarInfo(
                file = jarFile,
                name = name,
                vendor = vendor,
                version = version,
                midletClass = midletClass,
                iconPath = iconPath,
                extractedDir = extractDir
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun createClassLoader(extractedDir: File): ClassLoader {
        val dexFile = File(extractedDir, "classes.dex")
        return DexClassLoader(
            dexFile.absolutePath,
            extractedDir.absolutePath,
            null,
            javaClass.classLoader
        )
    }
}
