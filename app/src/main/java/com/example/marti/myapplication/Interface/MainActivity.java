package com.example.marti.myapplication.Interface;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;


import com.example.marti.myapplication.R;
import com.example.marti.myapplication.Model.ScheduledEcoSwitch;
import com.example.marti.myapplication.Model.ScheduledSwitch;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EcoViewAdapter eco_adapter;
    private ProgramViewAdapter prog_adapter;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new MainPageAdapter());

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);
        addSeparators(tabLayout);

        ImageView img = (ImageView) findViewById(R.id.easterEgg);
        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://danielme" +
                        ".com/2016/03/26/diseno-android-tutorial-pestanas-con-material-design")));
            }

        });
    }

    protected void addSeparators(TabLayout tabLayout){

        View root = tabLayout.getChildAt(0);
        if (root instanceof LinearLayout) {
            ((LinearLayout) root).setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(getResources().getColor(R.color.colorSeparator));
            drawable.setSize(2, 1);
            ((LinearLayout) root).setDividerPadding(10);
            ((LinearLayout) root).setDividerDrawable(drawable);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://danielme" +
                        ".com/2016/03/26/diseno-android-tutorial-pestanas-con-material-design")));
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public EcoViewAdapter getEcoAdapter(){
        return this.eco_adapter;
    }

    public ProgramViewAdapter getProgramAdapter(){
        return this.prog_adapter;
    }

    class MainPageAdapter extends PagerAdapter {

        private LinearLayout page1;
        private LinearLayout page2;
        private LinearLayout page3;
        private final int[] titles = {R.string.page1, R.string.page2, R.string.page3};

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getString(titles[position]);
        }

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {

            View page;
            switch (position) {
                case 0:
                    if (page1 == null) {
                        page1 = (LinearLayout) LayoutInflater.from(MainActivity.this).inflate(R.layout.page_one, collection, false);
                    }
                    page = page1;
                    break;
                case 1:
                    if (page2 == null) {
                        page2 = (LinearLayout) LayoutInflater.from(viewPager.getContext()).inflate(R.layout.page_two, collection, false);

                        ArrayList<ScheduledSwitch> switch_array = new ArrayList<>();
                        for(int i = 0; i < 50; i++){
                            switch_array.add(new ScheduledSwitch("adeu" + i, "00:00", "00:01"));
                        }

                        RecyclerView prog_view = (RecyclerView) page2.findViewById(R.id.scheduled_view);
                        prog_view.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        prog_adapter = new ProgramViewAdapter(viewPager.getContext(),switch_array);
                        prog_view.setAdapter(prog_adapter);

                    }

                    page = page2;
                    break;
                case 2:
                    if (page3 == null) {
                        page3 = (LinearLayout) LayoutInflater.from(viewPager.getContext()).inflate(R.layout.page_three, collection, false);

                        ArrayList<ScheduledEcoSwitch> eco_array = new ArrayList<>();
                        for(int i = 0; i < 50; i++){
                            eco_array.add(new ScheduledEcoSwitch("hola" + i, "00:00", "2"));
                        }

                        RecyclerView eco_view = (RecyclerView) page3.findViewById(R.id.eco_view);
                        eco_view.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        eco_adapter = new EcoViewAdapter(viewPager.getContext() , eco_array);
                        eco_view.setAdapter(eco_adapter);
                    }

                    page = page3;
                    break;
                default:
                    if (page1 == null) {
                        page1 = (LinearLayout) LayoutInflater.from(MainActivity.this).inflate(R.layout.page_one, collection, false);
                    }
                    page = page1;
                    break;
            }

            collection.addView(page, 0);

            return page;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }
    }
}
