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

class BinarySearchTree {
    private var root: TreeNode? = null
    fun insert(value: Int) {
        if (root == null) {
            root = TreeNode(value)
        } else {
            root!!.insert(value)
            reBalance(root!!)
        }
    }

    private fun reBalance(node: TreeNode): TreeNode {
        if (node.left != null) node.left = reBalance(node.left!!)
        if (node.right != null) node.right = reBalance(node.right!!)
        return when (node.balanceFactor) {
            -2 -> {
                if (node.right!!.balanceFactor == 1) {
                    node.right = rightShift(node.right!!)
                }
                leftShift(node)
            }
            2 -> {
                if (node.left!!.balanceFactor == -1) {
                    node.left = leftShift(node.left!!)
                }
                rightShift(node)
            }
            else -> node
        }
    }

    private fun leftShift(node: TreeNode): TreeNode {
        val exRight = node.right!!
        val exRightLeft = exRight.left
        exRight.left = node
        node.right = exRightLeft
        node.updateHeight()
        exRight.updateHeight()
        return exRight
    }

    private fun rightShift(node: TreeNode): TreeNode {
        val exLeft = node.left!!
        val exLeftRight = exLeft.right
        exLeft.right = node
        node.left = exLeftRight
        node.updateHeight()
        exLeft.updateHeight()
        return exLeft
    }

    fun positionNodes(width: Int) {
        if (root != null) root!!.positionSelf(0, width, 0)
    }

    fun draw(c: Canvas?) {
        if (c != null) {
            root!!.draw(c)
        }
    }

    fun click(x: Float, y: Float, target: Int): Int {
        return root!!.click(x, y, target)
    }

    private fun search(value: Int): TreeNode? {
        var cur = root
        while (cur != null && cur.value != value) {
            cur = if (value < cur.value) cur.left else cur.right
        }
        return cur
    }

    fun invalidateNode(targetValue: Int) {
        val target = search(targetValue)
        target!!.invalidate()
    }
}