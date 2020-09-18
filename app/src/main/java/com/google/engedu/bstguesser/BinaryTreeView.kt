/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.engedu.bstguesser

import android.content.Context
import android.graphics.Canvas
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import java.util.*

class BinaryTreeView(context: Context?, private val textView: TextView) : View(context) {
    private var tree: BinarySearchTree? = null
    private var searchSequence: ArrayList<Int>? = null
    private var searchPosition = 0
    fun initialize() {
        tree = BinarySearchTree()
        for (value in generateRandomSequence(TREE_SIZE)) {
            tree!!.insert(value)
        }
        tree!!.positionNodes(this.width)
        searchSequence = generateRandomSequence(TREE_SIZE)
        searchPosition = 0
        updateMessage()
        invalidate()
    }

    private fun generateRandomSequence(size: Int): ArrayList<Int> {
        val numbers = ArrayList<Int>(size)
        for (i in 0 until size) {
            numbers.add(i + 1)
        }
        numbers.shuffle()
        return numbers
    }

    public override fun onDraw(canvas: Canvas) {
        if (tree != null) {
            tree!!.draw(canvas)
        }
    }

    private fun updateMessage() {
        if (searchPosition < searchSequence!!.size) textView.text = "Looking for node " + searchSequence!![searchPosition] else textView.text = "Done!"
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (tree != null && searchPosition < searchSequence!!.size) {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    val targetValue = searchSequence!![searchPosition]
                    val hitValue = tree!!.click(event.x, event.y, targetValue)
                    if (hitValue != -1) {
                        invalidate()
                        if (hitValue != targetValue) {
                            tree!!.invalidateNode(targetValue)
                        }
                        searchPosition++
                        updateMessage()
                        return true
                    }
                }
            }
        }
        return super.onTouchEvent(event)
    }

    companion object {
        const val TREE_SIZE = 20
    }
}