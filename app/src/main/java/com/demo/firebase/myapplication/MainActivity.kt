package com.demo.firebase.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings


class MainActivity : AppCompatActivity() {

    private var firebaseRemoteConfig: FirebaseRemoteConfig? = null
    lateinit var buttonRemoteConfig: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkForDynamicDeepLink()
        buttonRemoteConfig = findViewById(R.id.button_remote_config)
        val buttonDynamicLinks = findViewById<View>(R.id.button_dynamic_link)
        val buttonCrashlytics = findViewById<View>(R.id.button_crashlytics)
        val buttonRoboTesting = findViewById<View>(R.id.button_robo_testing)
        buttonRemoteConfig.visibility = View.INVISIBLE

        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        val configSettings = FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build()
        firebaseRemoteConfig?.setConfigSettings(configSettings)
        firebaseRemoteConfig?.setDefaults(R.xml.rconf_defaults)
        fetchRemoteConfig()

        buttonRoboTesting.setOnClickListener {
            val intent = Intent(this@MainActivity, RoboTestingActivity::class.java)
            startActivity(intent)
        }

        buttonCrashlytics.setOnClickListener {
            val intent = Intent(this@MainActivity, CrashlyticsActivity::class.java)
            startActivity(intent)
        }

        buttonDynamicLinks.setOnClickListener {
            val intent = Intent(this@MainActivity, DynamicLinkActivity::class.java)
            startActivity(intent)
        }
    }


    // Dynamic Link
    private fun checkForDynamicDeepLink() {
        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(intent)
                .addOnSuccessListener(this) { pendingDynamicLinkData ->
                    val deepLink: Uri
                    if (pendingDynamicLinkData != null) {
                        deepLink = pendingDynamicLinkData.link
                        if (deepLink.toString().contains("discount")) {
                            val intent = Intent(this@MainActivity, DynamicLinkActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }
                .addOnFailureListener(this) { }

    }

    // Remote Config
    private fun fetchRemoteConfig() {
        var cacheExpiration: Long = 3600 //one hour
        if (firebaseRemoteConfig!!.info.configSettings.isDeveloperModeEnabled) {
            cacheExpiration = 0
        }
        firebaseRemoteConfig?.fetch(cacheExpiration)?.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        firebaseRemoteConfig?.activateFetched()
                    } else {
                        Toast.makeText(this@MainActivity, "Fetch Failed",
                                Toast.LENGTH_SHORT).show()
                    }
                    enableRemoteConfigButton()
                }
    }

    private fun enableRemoteConfigButton() {
        buttonRemoteConfig.visibility = View.VISIBLE
        val color = firebaseRemoteConfig!!.getString("buttonColor")
        if ("red".equals(color, ignoreCase = true)) {
            buttonRemoteConfig.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
        } else if ("blue".equals(color, ignoreCase = true)) {
            buttonRemoteConfig.setBackgroundColor(ContextCompat.getColor(this, R.color.blue))
        } else if ("green".equals(color, ignoreCase = true)) {
            buttonRemoteConfig.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
        } else if ("yellow".equals(color, ignoreCase = true)) {
            buttonRemoteConfig.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow))
        }
        buttonRemoteConfig.setOnClickListener {
            val intent = Intent(this, RemoteConfigActivity::class.java)
            intent.putExtra("text", firebaseRemoteConfig?.getString("tvSeriesText"))
            intent.putExtra("image", firebaseRemoteConfig?.getBoolean("watchingNow"))
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }
}
