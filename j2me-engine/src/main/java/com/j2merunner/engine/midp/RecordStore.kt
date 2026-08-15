package com.j2merunner.engine.midp

import android.content.Context
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

/**
 * Bridge for javax.microedition.rms.RecordStore
 * Persistent storage for J2ME games
 */
class RecordStore private constructor(
    private val storeName: String,
    private val context: Context
) {

    companion object {
        private val openStores = mutableMapOf<String, RecordStore>()

        fun openRecordStore(recordStoreName: String, createIfNecessary: Boolean): RecordStore {
            return openStores.getOrPut(recordStoreName) {
                RecordStore(recordStoreName, com.j2merunner.app.J2MERunnerApp.instance)
            }
        }

        fun openRecordStore(recordStoreName: String, createIfNecessary: Boolean, authmode: Int, writable: Boolean): RecordStore {
            return openRecordStore(recordStoreName, createIfNecessary)
        }

        fun openRecordStore(recordStoreName: String, vendorName: String, suiteName: String): RecordStore {
            return openRecordStore(recordStoreName, false)
        }

        fun deleteRecordStore(recordStoreName: String) {
            openStores.remove(recordStoreName)
            val dir = File(com.j2merunner.app.J2MERunnerApp.instance.filesDir, "rms")
            File(dir, "$recordStoreName.rms").delete()
        }

        fun listRecordStores(): Array<String>? {
            val dir = File(com.j2merunner.app.J2MERunnerApp.instance.filesDir, "rms")
            return dir.listFiles()?.map { it.nameWithoutExtension }?.toTypedArray()
        }
    }

    private val records = mutableMapOf<Int, ByteArray>()
    private var nextRecordId = 1
    private val listeners = mutableListOf<RecordListener>()

    init {
        loadRecords()
    }

    private fun getStoreFile(): File {
        val dir = File(context.filesDir, "rms")
        dir.mkdirs()
        return File(dir, "$storeName.rms")
    }

    private fun loadRecords() {
        val file = getStoreFile()
        if (!file.exists()) return

        try {
            FileInputStream(file).use { fis ->
                // Simple binary format: recordId (4 bytes) + length (4 bytes) + data
                // TODO: Implement proper deserialization
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun saveRecords() {
        try {
            FileOutputStream(getStoreFile()).use { fos ->
                // TODO: Implement proper serialization
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun addRecord(data: ByteArray, offset: Int, numBytes: Int): Int {
        val record = if (offset == 0 && numBytes == data.size) {
            data
        } else {
            data.copyOfRange(offset, offset + numBytes)
        }

        val recordId = nextRecordId++
        records[recordId] = record
        saveRecords()

        listeners.forEach { it.recordAdded(this, recordId) }
        return recordId
    }

    fun setRecord(recordId: Int, newData: ByteArray, offset: Int, numBytes: Int) {
        val record = if (offset == 0 && numBytes == newData.size) {
            newData
        } else {
            newData.copyOfRange(offset, offset + numBytes)
        }

        records[recordId] = record
        saveRecords()
        listeners.forEach { it.recordChanged(this, recordId) }
    }

    fun getRecord(recordId: Int): ByteArray {
        return records[recordId] ?: throw RecordStoreException("Record not found: $recordId")
    }

    fun getRecordSize(recordId: Int): Int {
        return records[recordId]?.size ?: throw RecordStoreException("Record not found: $recordId")
    }

    fun deleteRecord(recordId: Int) {
        records.remove(recordId)
        saveRecords()
        listeners.forEach { it.recordDeleted(this, recordId) }
    }

    fun getNumRecords(): Int = records.size

    fun getNextRecordID(): Int = nextRecordId

    fun getRecordSizeAvailable(): Int = 65536 // Arbitrary limit

    fun getName(): String = storeName

    fun getVersion(): Int = 1

    fun getLastModified(): Long = getStoreFile().lastModified()

    fun addRecordListener(listener: RecordListener) {
        listeners.add(listener)
    }

    fun removeRecordListener(listener: RecordListener) {
        listeners.remove(listener)
    }

    fun closeRecordStore() {
        saveRecords()
        openStores.remove(storeName)
    }

    fun getSize(): Int {
        return records.values.sumOf { it.size }
    }

    fun getSizeAvailable(): Int = 1024 * 1024 // 1MB limit
}

class RecordStoreException(message: String) : Exception(message)
class RecordStoreFullException(message: String) : Exception(message)
class RecordStoreNotFoundException(message: String) : Exception(message)
class InvalidRecordIDException(message: String) : Exception(message)

interface RecordListener {
    fun recordAdded(recordStore: RecordStore, recordId: Int)
    fun recordChanged(recordStore: RecordStore, recordId: Int)
    fun recordDeleted(recordStore: RecordStore, recordId: Int)
}
