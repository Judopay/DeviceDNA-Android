package com.judopay.genome.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.judopay.DeviceDna;
import com.judopay.devicedna.Credentials;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Credentials credentials = new Credentials("<TOKEN>", "<SECRET>");
        final DeviceDna deviceDna = new DeviceDna(this, credentials);

        TextView identityScoreText = (TextView) findViewById(R.id.identity_score_text);
        TextView lastSeenText = (TextView) findViewById(R.id.last_seen_text);
        TextView createdAtText = (TextView) findViewById(R.id.created_at_text);

        if (savedInstanceState == null) {
            deviceDna.identifyDevice()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(deviceId -> {
                        deviceDna.getDeviceProfile(deviceId)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io())
                                .subscribe(json -> {
                                            identityScoreText.setText(json.get("IdentityScore").getAsString());
                                            lastSeenText.setText(json.get("LastSeen").getAsString());
                                            createdAtText.setText(json.get("CreatedAt").getAsString());
                                        }
                                );
                    });
        }
    }
}
