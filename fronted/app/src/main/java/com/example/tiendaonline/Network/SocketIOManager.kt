package com.example.tiendaonline.Network

import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

object SocketIOManager {
    lateinit var mSocket: Socket
    @Synchronized
    fun setSocket() {
        try {
// This will allow your Android Emulator and physical device at your home to connect to the server
            val i = IO.Options()
            i.path = "/socket/v1" //Es el que se está usando en strapi
            mSocket = IO.socket("http://10.0.2.2:1337",i)
        } catch (e: URISyntaxException) {

        }
    }
    @Synchronized
    fun getSocket(): Socket = mSocket
    @Synchronized
    fun establishConnection() = mSocket.connect()
    @Synchronized
    fun closeConnection() = mSocket.disconnect()
}