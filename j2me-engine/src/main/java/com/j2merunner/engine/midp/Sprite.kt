package com.j2merunner.engine.midp

/**
 * Bridge for javax.microedition.lcdui.game.Sprite
 * Basic animated sprite implementation
 */
class Sprite(image: Image) : Layer() {
    
    companion object {
        const val TRANS_NONE = 0
        const val TRANS_ROT90 = 5
        const val TRANS_ROT180 = 3
        const val TRANS_ROT270 = 6
        const val TRANS_MIRROR = 2
        const val TRANS_MIRROR_ROT90 = 7
        const val TRANS_MIRROR_ROT180 = 1
        const val TRANS_MIRROR_ROT270 = 4
    }
    
    private var frameSequence: IntArray? = null
    private var sequenceIndex = 0
    
    fun setFrameSequence(sequence: IntArray?) {
        frameSequence = sequence
        sequenceIndex = 0
    }
    
    fun nextFrame() {
        frameSequence?.let {
            sequenceIndex = (sequenceIndex + 1) % it.size
        }
    }
    
    fun prevFrame() {
        frameSequence?.let {
            sequenceIndex = if (sequenceIndex > 0) sequenceIndex - 1 else it.size - 1
        }
    }
    
    fun setFrame(index: Int) {
        sequenceIndex = index
    }
    
    fun getFrame(): Int = sequenceIndex
    
    fun getRawFrameCount(): Int = 1 // TODO: Implement frame extraction
    
    fun getFrameSequenceLength(): Int = frameSequence?.size ?: 0
    
    fun defineCollisionRectangle(x: Int, y: Int, width: Int, height: Int) {
        // TODO: Implement collision
    }
    
    fun setTransform(transform: Int) {
        // TODO: Implement transforms
    }
    
    fun collidesWith(sprite: Sprite, pixelLevel: Boolean): Boolean = false
    fun collidesWith(image: Image, x: Int, y: Int, pixelLevel: Boolean): Boolean = false
    fun collidesWith(layer: Layer, pixelLevel: Boolean): Boolean = false
    
    override fun paint(g: Graphics) {
        // TODO: Draw current frame
    }
}

/**
 * Base class for game layers
 */
abstract class Layer {
    @get:JvmName("getX")
    @set:JvmName("setX")
    var x: Int = 0
    
    @get:JvmName("getY")
    @set:JvmName("setY")
    var y: Int = 0
    
    @get:JvmName("getWidth")
    @set:JvmName("setWidth")
    var width: Int = 0
    
    @get:JvmName("getHeight")
    @set:JvmName("setHeight")
    var height: Int = 0
    
    var isVisible: Boolean = true
    
    fun setPosition(x: Int, y: Int) {
        this.x = x
        this.y = y
    }
    
    fun move(dx: Int, dy: Int) {
        x += dx
        y += dy
    }
    
    abstract fun paint(g: Graphics)
}

/**
 * Layer manager for organizing game layers
 */
class LayerManager {
    private val layers = mutableListOf<Layer>()
    
    fun append(layer: Layer) {
        layers.add(layer)
    }
    
    fun insert(layer: Layer, index: Int) {
        layers.add(index, layer)
    }
    
    fun remove(layer: Layer) {
        layers.remove(layer)
    }
    
    fun getSize(): Int = layers.size
    
    fun getLayerAt(index: Int): Layer = layers[index]
    
    fun paint(g: Graphics, x: Int, y: Int) {
        layers.filter { it.isVisible }.forEach { it.paint(g) }
    }
    
    fun setViewWindow(x: Int, y: Int, width: Int, height: Int) {
        // TODO: Implement view window
    }
}

/**
 * Tiled layer for background maps
 */
class TiledLayer(columns: Int, rows: Int, image: Image, tileWidth: Int, tileHeight: Int) : Layer() {
    
    private val cellMatrix = Array(rows) { IntArray(columns) { 0 } }
    private val animatedTiles = mutableMapOf<Int, Int>()
    
    init {
        width = columns * tileWidth
        height = rows * tileHeight
    }
    
    fun setCell(col: Int, row: Int, tileIndex: Int) {
        if (row in 0 until cellMatrix.size && col in 0 until cellMatrix[0].size) {
            cellMatrix[row][col] = tileIndex
        }
    }
    
    fun getCell(col: Int, row: Int): Int {
        return if (row in cellMatrix.indices && col in cellMatrix[0].indices) {
            cellMatrix[row][col]
        } else 0
    }
    
    fun fillCells(col: Int, row: Int, numCols: Int, numRows: Int, tileIndex: Int) {
        for (r in row until (row + numRows).coerceAtMost(cellMatrix.size)) {
            for (c in col until (col + numCols).coerceAtMost(cellMatrix[0].size)) {
                cellMatrix[r][c] = tileIndex
            }
        }
    }
    
    fun createAnimatedTile(staticTileIndex: Int): Int {
        val animatedIndex = -(animatedTiles.size + 1)
        animatedTiles[animatedIndex] = staticTileIndex
        return animatedIndex
    }
    
    fun setAnimatedTile(animatedTileIndex: Int, staticTileIndex: Int) {
        animatedTiles[animatedTileIndex] = staticTileIndex
    }
    
    fun getAnimatedTile(animatedTileIndex: Int): Int {
        return animatedTiles[animatedTileIndex] ?: 0
    }
    
    override fun paint(g: Graphics) {
        // TODO: Draw tiles
    }
}
