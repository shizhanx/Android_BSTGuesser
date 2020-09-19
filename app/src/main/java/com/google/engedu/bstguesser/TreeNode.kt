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

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class TreeNode(val value: Int) {
    private val height = 0
    var left: TreeNode? = null
    var right: TreeNode? = null
    private var showValue = false
    private var x = 0
    private var y = 0
    private var color = Color.rgb(150, 150, 250)
    fun insert(valueToInsert: Int) {
        if (valueToInsert > value) {
            if (right != null) right!!.insert(valueToInsert) else right = TreeNode(valueToInsert)
        } else {
            if (left != null) left!!.insert(valueToInsert) else left = TreeNode(valueToInsert)
        }
    }

    fun positionSelf(x0: Int, x1: Int, y: Int) {
        this.y = y
        x = (x0 + x1) / 2
        if (left != null) {
            left!!.positionSelf(x0, if (right == null) x1 - 2 * MARGIN else x, y + SIZE + MARGIN)
        }
        if (right != null) {
            right!!.positionSelf(if (left == null) x0 + 2 * MARGIN else x, x1, y + SIZE + MARGIN)
        }
    }

    fun draw(c: Canvas) {
        val linePaint = Paint()
        linePaint.style = Paint.Style.STROKE
        linePaint.strokeWidth = 3f
        linePaint.color = Color.GRAY
        if (left != null) c.drawLine(x.toFloat(), y + SIZE / 2.toFloat(), left!!.x.toFloat(), left!!.y + SIZE / 2.toFloat(), linePaint)
        if (right != null) c.drawLine(x.toFloat(), y + SIZE / 2.toFloat(), right!!.x.toFloat(), right!!.y + SIZE / 2.toFloat(), linePaint)
        val fillPaint = Paint()
        fillPaint.style = Paint.Style.FILL
        fillPaint.color = color
        c.drawRect(x - SIZE / 2.toFloat(), y.toFloat(), x + SIZE / 2.toFloat(), y + SIZE.toFloat(), fillPaint)
        val paint = Paint()
        paint.color = Color.BLACK
        paint.textSize = SIZE * 2 / 3.toFloat()
        paint.textAlign = Paint.Align.CENTER
        c.drawText(if (showValue) value.toString() else "?", x.toFloat(), y + SIZE * 3 / 4.toFloat(), paint)
        if (height > 0) {
            val heightPaint = Paint()
            heightPaint.color = Color.MAGENTA
            heightPaint.textSize = SIZE * 2 / 3.toFloat()
            heightPaint.textAlign = Paint.Align.LEFT
            c.drawText(height.toString(), x + SIZE / 2 + 10.toFloat(), y + SIZE * 3 / 4.toFloat(), heightPaint)
        }
        if (left != null) left!!.draw(c)
        if (right != null) right!!.draw(c)
    }

    fun click(clickX: Float, clickY: Float, target: Int): Int {
        var hit = -1
        if (Math.abs(x - clickX) <= SIZE / 2 && y <= clickY && clickY <= y + SIZE) {
            if (!showValue) {
                color = if (target != value) {
                    Color.RED
                } else {
                    Color.GREEN
                }
            }
            showValue = true
            hit = value
        }
        if (left != null && hit == -1) hit = left!!.click(clickX, clickY, target)
        if (right != null && hit == -1) hit = right!!.click(clickX, clickY, target)
        return hit
    }

    fun invalidate() {
        color = Color.CYAN
        showValue = true
    }

    companion object {
        private const val SIZE = 60
        private const val MARGIN = 20
    }
}