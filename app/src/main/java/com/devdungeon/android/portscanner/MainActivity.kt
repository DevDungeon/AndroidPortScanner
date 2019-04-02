package com.devdungeon.android.portscanner

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import java.util.concurrent.Executors


class MainActivity : AppCompatActivity() {

    private val numThreads: Int = 16
    private val host: String = "www.devdungeon.com"

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_startscan -> {
                this.scrollView2.portScanResults.setText("Scan results for:\n$host\n============")

                val executor = Executors.newFixedThreadPool(numThreads)

                val commonlyUsedPorts = intArrayOf(22, 80, 443, 3306, 21, 25, 53, 8080, 8988, 9999)
                for (port in commonlyUsedPorts) {
                    //val worker = Thread(PortScan(this.scrollView2.portScanResults, host, port))
                    val worker = PortScan(this.scrollView2.portScanResults, host, port)
                    executor.execute(worker)
                }

                executor.shutdown()
//                while (!executor.isTerminated()) { // This will lock up UI thread
//                    // Threads are still running
//                }

                return@OnNavigationItemSelectedListener false
            }
            R.id.navigation_settings -> {
                // Replace screen fragment with settings
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
