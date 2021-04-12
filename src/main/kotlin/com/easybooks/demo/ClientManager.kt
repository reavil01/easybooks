package com.easybooks.demo

class ClientManager {
    private val db = MemDB

    fun saveClient(client: Client):Boolean {
        if (db.clientTable[client.id] == null) {
            db.clientTable[client.id] = client
            return true
        }
        return false
    }

    fun getClient(id: String):Client? {
        return db.clientTable[id]
    }
}