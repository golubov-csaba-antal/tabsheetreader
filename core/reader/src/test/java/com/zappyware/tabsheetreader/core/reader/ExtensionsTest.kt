package com.zappyware.tabsheetreader.core.reader

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ExtensionsTest {

    @Test
    fun `Test converting number to repeat alternative`() {
        assertNull(0.toRepeatAlternatives())
        assertEquals("1", 1.toRepeatAlternatives())
        assertEquals("1-2", 3.toRepeatAlternatives())
        assertEquals("1-3", 7.toRepeatAlternatives())
        assertEquals("1-3, 7", 71.toRepeatAlternatives())
        assertEquals("1, 3-5, 8", 157.toRepeatAlternatives())
    }
}