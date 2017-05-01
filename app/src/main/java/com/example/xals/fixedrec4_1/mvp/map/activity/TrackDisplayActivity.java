package com.example.xals.fixedrec4_1.mvp.map.activity;

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

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.xals.fixedrec4_1.R;
import com.example.xals.fixedrec4_1.business.model.Fix4SuccessResultModel;
import com.example.xals.fixedrec4_1.repository.dto.TrackDTO;
import com.example.xals.fixedrec4_1.mvp.base.BaseActivity;
import com.example.xals.fixedrec4_1.mvp.main.MainActivity;
import com.example.xals.fixedrec4_1.mvp.map.adapter.TrackViewPageAdapter;
import com.example.xals.fixedrec4_1.mvp.map.fragment.GoogleMapFragment;
import com.example.xals.fixedrec4_1.mvp.map.activity.presenter.TrackViewPresenter;
import com.example.xals.fixedrec4_1.mvp.map.activity.presenter.TrackDisplayViewState;
import com.example.xals.fixedrec4_1.service.LocationService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lombok.Getter;


public class TrackDisplayActivity extends BaseActivity implements TrackDisplayViewState {

    public static final String START_NEW_TRACK_EXTRA = "start_new_track_extra";
    public static final String TRACK_UUID_EXTRA = "trackUUID_extra";

    @InjectPresenter
    TrackViewPresenter presenter;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.sliding_tabs)
    TabLayout tabLayout;

    @BindView(R.id.stop_recording)
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
            TrackDisplayActivity.this.service = ((LocationService.MyBinder) service).getService();
            if (startNewTrack) {
                TrackDisplayActivity.this.service.registerLocationUpdates();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            TrackDisplayActivity.this.service = null;
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
        presenter.getTrackForDisplay(trackUUID);
    }

    @Override
    public void onTrackForDisplayLoaded(TrackDTO track, int visibility) {
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
            presenter.closeCurrentTrack(trackUUID);
            stopService(serviceIntent);
        }
    }

    private List<Fragment> createTrackViewFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(GoogleMapFragment.getInstance());
        return fragments;
    }

    @Override
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

    public static Intent intent(Context context, Boolean startNewTrack, String trackUUID) {
        Intent intent = new Intent(context, TrackDisplayActivity.class);
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