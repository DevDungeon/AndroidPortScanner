package com.devdungeon.android.portscanner

import android.widget.EditText
import java.net.Socket

class PortScan(val results: EditText, val portNumber: Int) : Thread() {

        override fun run() {
            try {
                val sock = Socket("www.devdungeon.com", portNumber)
                if (sock.isConnected) {
                    results.post(Runnable() {
                        val newMessage = "${results.text}" + "\n" +
                                "$portNumber is open."
                        results.setText(newMessage)
                    })
                    sock.close()
                } else {
//                    results.post(Runnable() {
//                        val newMessage = "${results.text}" + "\n" +
//                                "$portNumber is not connected."
//                        results.setText(newMessage)
//                    })
                }

            } catch (e: Exception) {
//                results.post(Runnable() {
//                    val newMessage = "${results.text}" + "\n" +
//                            "$portNumber is N/A."
//                    results.setText(newMessage)
//                })
            }
        }
    }
