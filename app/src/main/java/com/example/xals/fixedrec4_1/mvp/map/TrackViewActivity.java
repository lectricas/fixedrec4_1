package com.example.xals.fixedrec4_1.mvp.map;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.Button;

import com.example.xals.fixedrec4_1.R;
import com.example.xals.fixedrec4_1.business.dto.TrackDTO;
import com.example.xals.fixedrec4_1.mvp.base.BaseActivity;
import com.example.xals.fixedrec4_1.mvp.main.MainActivity;
import com.example.xals.fixedrec4_1.mvp.map.adapter.TrackViewPageAdapter;
import com.example.xals.fixedrec4_1.mvp.map.fragment.GoogleMapFragment;
import com.example.xals.fixedrec4_1.service.LocationService;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lombok.Getter;
import nucleus.factory.RequiresPresenter;


@RequiresPresenter(TrackViewPresenter.class)
public class TrackViewActivity extends BaseActivity<TrackViewPresenter> {

    public static final String START_NEW_TRACK_EXTRA = "start_new_track_extra";
    public static final String TRACK_UUID_EXTRA = "trackUUID_extra";
    @Bind(R.id.viewpager)
    ViewPager viewPager;

    @Bind(R.id.sliding_tabs)
    TabLayout tabLayout;

    @Bind(R.id.stop_recording)
    Button stopRecordingButton;

    LocationService service = null;
    Intent serviceIntent;

    Boolean startNewTrack;
    String trackUUID;

    @Getter
    TrackDTO trackDTO;

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            TrackViewActivity.this.service = ((LocationService.MyBinder) service).getService();
            if (startNewTrack) {
                TrackViewActivity.this.service.registerLocationUpdates();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            TrackViewActivity.this.service = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        startNewTrack = getIntent().getBooleanExtra(START_NEW_TRACK_EXTRA, false);
        trackUUID = getIntent().getStringExtra(TRACK_UUID_EXTRA);

        ButterKnife.bind(this);
        serviceIntent = new Intent(this, LocationService.class);
        startService(serviceIntent);
        bindService(serviceIntent, serviceConnection, BIND_AUTO_CREATE);
        getPresenter().checkIfTrackRunningToHideButton(trackUUID);
    }

    public void onCurrentTrackRunningChecked(TrackDTO track, int visibility) {
        this.trackDTO = track;
        stopRecordingButton.setVisibility(visibility);

        //initializing fragments here
        viewPager.setAdapter(new TrackViewPageAdapter(getSupportFragmentManager(), createTrackViewFragments()));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }

    @OnClick(R.id.stop_recording)
    public void onClick() {
        if (service != null) {
            service.unregisterLocationUpdates();
            getPresenter().closeCurrentTrack();
            stopService(serviceIntent);
        }
    }

    private List<Fragment> createTrackViewFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(GoogleMapFragment.getInstance());
        return fragments;
    }

    public void onTrackClosed(TrackDTO closedTrack) {
        setResult(RESULT_OK);
        finishActivity();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishActivity();
    }

    private void finishActivity() {
        if (isTaskRoot()) {
            Intent intent = new Intent(this, MainActivity.class);
            finish();
            startActivity(intent);
        } else {
            finish();
        }
    }

    public static Intent getIntentInstance(Context context, Boolean startNewTrack, String trackUUID) {
        Intent intent = new Intent(context, TrackViewActivity.class);
        intent.putExtra(START_NEW_TRACK_EXTRA, startNewTrack);
        intent.putExtra(TRACK_UUID_EXTRA, trackUUID);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return intent;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }
}