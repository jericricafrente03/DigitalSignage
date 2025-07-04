package com.jeric.bitteldigitalsignage.http

import android.util.Log
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.net.InetSocketAddress
import java.util.Scanner
import java.util.concurrent.Executors


object HTTPConnection {

    private const val TAG = "HTTPConnection"

    private val _dataFlow = MutableSharedFlow<JSONObject>()
    val dataFlow = _dataFlow.asSharedFlow()

    private var mHttpServer: HttpServer? = null

    private fun streamToString(inputStream: InputStream): String =
        Scanner(inputStream).useDelimiter("\\A").takeIf { it.hasNext() }?.next() ?: ""

    private fun sendResponse(httpExchange: HttpExchange, responseText: String) {
        httpExchange.sendResponseHeaders(200, responseText.length.toLong())
        httpExchange.responseBody.use { os ->
            os.write(responseText.toByteArray())
        }
    }

    fun startServer(port: Int) {
        try {
            mHttpServer = HttpServer.create(InetSocketAddress(port), 0).apply {
                executor = Executors.newCachedThreadPool()
                createContext("/", rootHandler)
                createContext("/index", rootHandler)
                start()
            }
            Log.d(TAG, "Server started on port: $port")
        } catch (e: IOException) {
            Log.e(TAG, "Error starting server: ${e.localizedMessage}", e)
        }
    }

    fun stopServer() {
        mHttpServer?.stop(0)
        Log.d(TAG, "Server stopped")
    }

    private val rootHandler = HttpHandler { exchange ->
        runBlocking {
            when (exchange.requestMethod) {
                "GET" -> sendResponse(exchange, "Mesh Server")
                "POST" -> {
                    val requestBody = streamToString(exchange.requestBody)
                    val jsonBody = JSONObject(requestBody)
                    CoroutineScope(Dispatchers.IO).launch {
                        _dataFlow.emit(jsonBody)
                    }
                    Log.d("meme","_dataFlow -> $jsonBody")
                    sendResponse(exchange, jsonBody.toString())
                }
                else -> sendResponse(exchange, "Unsupported Method")
            }
        }
    }
}