package com.example.deviceconnector.conditioner.tabs.main;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;

import com.example.deviceconnector.R;
import com.example.deviceconnector.conditioner.ConditionarActivity;
import com.example.deviceconnector.conditioner.util.DesiredTempUtil;


public class SliderFragment extends Fragment {

    SeekBar seekBar;
    TextView textView;
    Switch acSwitch;
    TextView currentNumber;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.slider_tab_layout, container, false);


        seekBar = v.findViewById(R.id.seek_bar);
        textView = v.findViewById(R.id.temp_number);
        acSwitch = v.findViewById(R.id.toggle_ac);
        context = this.getContext();
        currentNumber = v.findViewById(R.id.current_number);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                DesiredTempUtil.desiredTemp = (progress + 60000) / 1000;
                textView.setText(Integer.toString(DesiredTempUtil.desiredTemp));

                if (progress < 500 || progress > 19500) {
                    seekBar.setThumb(ContextCompat.getDrawable(context, R.drawable.slider_empty_thumb));
                    //seekBar.setThumb(getResources().getDrawable(R.drawable.slider_empty_thumb));
                } else {
                    //seekBar.setThumb(getResources().getDrawable(R.drawable.slider_thumb));
                    seekBar.setThumb(ContextCompat.getDrawable(context, R.drawable.slider_thumb));
                }

                updateTextAndSlider();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        return v;
    }
    private void updateTextAndSlider(){
        textView.setText(Integer.toString(DesiredTempUtil.desiredTemp));
        // Change colors here
        ConditionarActivity.toolbar.setBackgroundColor(DesiredTempUtil.getColor());
        ConditionarActivity.toolbar.getNavigationIcon().setColorFilter(DesiredTempUtil.getColorFaded(), PorterDuff.Mode.SRC_IN);
        currentNumber.setTextColor(DesiredTempUtil.getColor());
        ConditionarActivity.tabLayout.setSelectedTabIndicatorColor(DesiredTempUtil.getColor());
        int[][] states = new int[][] {
                new int[] {-android.R.attr.state_checked},
                new int[] {android.R.attr.state_checked},
        };

        int[] trackColors = new int[] {
                //getResources().getColor(R.color.gray),
                DesiredTempUtil.getColor(),
                ContextCompat.getColor(context, R.color.gray),
        };

        int[] fadedColors = new int[] {
                DesiredTempUtil.getColorFaded(),
                //getResources().getColor(R.color.gray)
                ContextCompat.getColor(context, R.color.gray)
        };

        int[] switchColors1 = new int[] {
                ContextCompat.getColor(context, R.color.gray),
                DesiredTempUtil.getColor()
                //getResources().getColor(R.color.gray)
        };

        DrawableCompat.setTintList(DrawableCompat.wrap(acSwitch.getThumbDrawable()), new ColorStateList(states, switchColors1));
        DrawableCompat.setTintList(DrawableCompat.wrap(acSwitch.getTrackDrawable()), new ColorStateList(states, switchColors1));
        LayerDrawable d = (LayerDrawable) (ResourcesCompat.getDrawable(getResources(), R.drawable.slider_track, null));
        assert d != null;
        Drawable progressDrawable = d.getDrawable(1);
        DrawableCompat.setTintList(DrawableCompat.wrap(progressDrawable), new ColorStateList(states, trackColors));
        DrawableCompat.setTintList(DrawableCompat.wrap(d.getDrawable(0)), new ColorStateList(states, fadedColors));
    }
}
