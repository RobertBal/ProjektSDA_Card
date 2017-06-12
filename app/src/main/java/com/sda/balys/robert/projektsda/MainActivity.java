package com.sda.balys.robert.projektsda;

import android.media.AudioFormat;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.sda.balys.robert.projektsda.adapters.MyPageAdapter;
import com.sda.balys.robert.projektsda.fragments.BatteryFragment;
import com.sda.balys.robert.projektsda.fragments.CardsGameFragment;
import com.sda.balys.robert.projektsda.fragments.FlashlightFragment;
import com.sda.balys.robert.projektsda.fragments.LocalFragment;
import com.sda.balys.robert.projektsda.fragments.StopwatchFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import layout.AudioFragment;

public class MainActivity extends AppCompatActivity {
    private static final int TAB_BATTERY_POSITION = 0;
    private static final int TAB_FLASHLIGHT_POSITION = 1;
    private static final int TAB_STOPWATCH_POSITION = 2;
    private static final int TAB_LOCAL_POSITION=3;
    private static final int TAB_AUDIO_POSITION=4;
    private static final int TAB_CARD_POSITION=5;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    private BatteryFragment batteryFragment;
    private FlashlightFragment flashlightFragment;
    private StopwatchFragment stopwatchFragment;
    private LocalFragment loclaFragment;
    private AudioFragment audioFragment;
    private CardsGameFragment cardFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        addFragments();
        pageChangeListener();
    }



    private void pageChangeListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
    private void addFragments() {
        List<Fragment> fragments = new ArrayList<>();
        batteryFragment = new BatteryFragment();
        flashlightFragment = new FlashlightFragment();
        stopwatchFragment = new StopwatchFragment();
        loclaFragment = new LocalFragment();
        audioFragment = new AudioFragment();
        cardFragment = new CardsGameFragment();
        fragments.add(batteryFragment);
        fragments.add(flashlightFragment);
        fragments.add(stopwatchFragment);
        fragments.add(loclaFragment);
        fragments.add(audioFragment);
        fragments.add(cardFragment);
        MyPageAdapter adapter = new MyPageAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(TAB_BATTERY_POSITION)
                .setIcon(getDrawable(R.drawable.ic_battery_unknown_black_24dp));
        tabLayout.getTabAt(TAB_FLASHLIGHT_POSITION)
                .setIcon(getDrawable(R.drawable.ic_flash_on_black_24dp));
        tabLayout.getTabAt(TAB_STOPWATCH_POSITION)
                .setIcon(getDrawable(R.drawable.ic_watch_black_24dp));
        tabLayout.getTabAt(TAB_LOCAL_POSITION)
                .setIcon(getDrawable(R.drawable.ic_locla_black_24dp));
        tabLayout.getTabAt(TAB_AUDIO_POSITION)
                .setIcon(getDrawable(R.drawable.ic_audio_24dp));
        tabLayout.getTabAt(TAB_CARD_POSITION)
                .setIcon(getDrawable(R.drawable.ic_hearts));


    }
}
