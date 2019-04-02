package com.devdungeon.android.portscanner

import android.widget.EditText
import java.net.Socket

class PortScan(private val results: EditText, private val host: String, private val portNumber: Int) : Thread() {

        override fun run() {
            try {
                val sock = Socket(host, portNumber)
                if (sock.isConnected) {
                    results.post {
                        val newMessage = "${results.text}" + "\n" +
                                "$portNumber is open."
                        results.setText(newMessage)
                    }
                    sock.close()
                } else {
                    results.post {
                        val newMessage = "${results.text}" + "\n" +
                                "$portNumber is not connected."
                        results.setText(newMessage)
                    }
                }

            } catch (e: Exception) {
                results.post {
                    val newMessage = "${results.text}" + "\n" +
                            "$portNumber is N/A."
                    results.setText(newMessage)
                }
            }
        }
    }
