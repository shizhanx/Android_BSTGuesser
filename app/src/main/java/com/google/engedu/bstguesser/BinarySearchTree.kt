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
            return
        } else {
            root!!.insert(value)
        }
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
        /**
         *
         * YOUR CODE GOES HERE
         *
         */
        return root
    }

    fun invalidateNode(targetValue: Int) {
        val target = search(targetValue)
        target!!.invalidate()
    }
}