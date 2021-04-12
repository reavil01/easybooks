package com.easybooks.demo

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ClientManagerTests {
    private val db = MemDB
    private val manager = ClientManager()

    @BeforeEach
    fun setUp() {
        db.clientTable.clear()
    }

    @Test
    fun saveClientTest() {
        // given
        val client = Client("123-456-7890", "테스트")

        // when
        val result = manager.saveClient(client)

        // then
        assertTrue(result, "saveClient() fail")
        assertSame(client, db.clientTable[client.id])
    }

    @Test
    fun saveClientWhenIdDuplicationTest() {
        // given
        val client = Client("123-456-7890", "테스트")
        val client2 = Client("123-456-7890", "two")
        manager.saveClient(client)

        // when
        val result = manager.saveClient(client2)

        // then
        assertFalse(result)
    }

    @Test
    fun loadClientTest() {
        // given
        val client = Client("123-456-7890", "테스트")
        manager.saveClient(client)

        // when
        val result = manager.getClient(client.id)

        // then
        assertSame(client, result)
    }
}


