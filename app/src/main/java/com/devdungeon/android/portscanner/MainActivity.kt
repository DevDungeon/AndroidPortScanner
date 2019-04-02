package com.devdungeon.android.portscanner

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor


class MainActivity : AppCompatActivity() {

    private val numThreads: Int = 16

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                this.scrollView2.portScanResults.setText("")

                val executor = Executors.newFixedThreadPool(10)

                val commonlyUsedPorts = intArrayOf(22, 80, 443, 3306, 21, 25, 53, 8080, 8988, 9999)
                for (port in commonlyUsedPorts) {
                    val worker = PortScan(this.scrollView2.portScanResults, port)
                    executor.execute(worker)
                }

                executor.shutdown()
                while (!executor.isTerminated()) {
                    // Threads are still running
                }



                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        this.scrollView2.portScanResults.setText("Hello!\nNo results yet.\nStart a scan.")
    }


}
