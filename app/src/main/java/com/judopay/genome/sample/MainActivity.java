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

        Credentials credentials = new Credentials("xvTWcOyTn1ib5vvR", "73a577a8dff2459c57461598046bdce22fc1aa89dec75292899a6d33f3af273c");
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
                                            String identityScore = getString(R.string.identity_score, json.get("IdentityScore").getAsString());
                                            String lastSeen = getString(R.string.last_seen, json.get("LastSeen").getAsString());
                                            String createdAt = getString(R.string.created_at, json.get("CreatedAt").getAsString());

                                            identityScoreText.setText(identityScore);
                                            lastSeenText.setText(lastSeen);
                                            createdAtText.setText(createdAt);
                                        }
                                );
                    });
        }
    }
}
