package com.demo.firebase.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

public class MainActivity extends AppCompatActivity {

    private FirebaseRemoteConfig firebaseRemoteConfig;
    private Bundle bundle;
    View buttonRemoteConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkForDynamicDeepLink();
        buttonRemoteConfig = findViewById(R.id.button_remote_config);
        View buttonDynamicLinks = findViewById(R.id.button_dynamic_link);
        View buttonCrashlytics = findViewById(R.id.button_crashlytics);
        View buttonRoboTesting = findViewById(R.id.button_robo_testing);
        bundle = new Bundle();
        buttonRemoteConfig.setVisibility(View.INVISIBLE);

        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        firebaseRemoteConfig.setConfigSettings(configSettings);
        firebaseRemoteConfig.setDefaults(R.xml.rconf_defaults);
        fetchRemoteConfig();

        buttonRoboTesting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RoboTestingActivity.class);
                startActivity(intent);
            }
        });

        buttonCrashlytics.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CrashlyticsActivity.class);
                startActivity(intent);
            }
        });

        buttonDynamicLinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DynamicLinkActivity.class);
                startActivity(intent);
            }
        });
    }


    // Dynamic Link
    private void checkForDynamicDeepLink() {
        FirebaseDynamicLinks.getInstance()
                            .getDynamicLink(getIntent())
                            .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                                @Override
                                public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                                    Uri deepLink;
                                    if (pendingDynamicLinkData != null) {
                                        deepLink = pendingDynamicLinkData.getLink();
                                        if(deepLink.toString().contains("discount")) {
                                            Intent intent = new Intent(MainActivity.this, DynamicLinkActivity.class);
                                            startActivity(intent);
                                        }
                                    }
                                }
                            })
                            .addOnFailureListener(this, new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                }
                            });

    }

    // Remote Config
    private void fetchRemoteConfig() {
        long cacheExpiration = 3600; //one hour
        if (firebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
            cacheExpiration = 0;
        }
        firebaseRemoteConfig.fetch(cacheExpiration)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            firebaseRemoteConfig.activateFetched();
                        } else {
                            Toast.makeText(MainActivity.this, "Fetch Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                        enableRemoteConfigButton();
                    }
                });
    }

    private void enableRemoteConfigButton() {
        buttonRemoteConfig.setVisibility(View.VISIBLE);
        String color = firebaseRemoteConfig.getString("buttonColor");
        if ("red".equalsIgnoreCase(color)) {
            buttonRemoteConfig.setBackgroundColor(getResources().getColor(R.color.red));
        } else if ("blue".equalsIgnoreCase(color)) {
            buttonRemoteConfig.setBackgroundColor(getResources().getColor(R.color.blue));
        } else if ("green".equalsIgnoreCase(color)) {
            buttonRemoteConfig.setBackgroundColor(getResources().getColor(R.color.green));
        } else if ("yellow".equalsIgnoreCase(color)) {
            buttonRemoteConfig.setBackgroundColor(getResources().getColor(R.color.yellow));
        }
        buttonRemoteConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RemoteConfigActivity.class);
                intent.putExtra("text", firebaseRemoteConfig.getString("tvSeriesText"));
                intent.putExtra("image", firebaseRemoteConfig.getBoolean("watchingNow"));
                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
