package com.example.deviceconnector.conditioner.adapters;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import java.util.ArrayList;

public class MainTabPagerAdapter extends FragmentStateAdapter {

    private ArrayList<Fragment> fragments = new ArrayList<>();

    public MainTabPagerAdapter(@NonNull FragmentActivity fragmentActivity ){
        super(fragmentActivity);
    }
    @NonNull @Override public Fragment createFragment(int position){
        return fragments.get(position);
    }

    public void addFragment(Fragment fragment) {
        fragments.add(fragment);
    }

    public Fragment getFragment(int position){
        return fragments.get(position);
    }



    @Override public int getItemCount(){
        return fragments.size();
    }
}
