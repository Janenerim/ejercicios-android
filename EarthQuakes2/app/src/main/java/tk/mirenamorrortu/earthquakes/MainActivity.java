package tk.mirenamorrortu.earthquakes;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import tk.mirenamorrortu.earthquakes.Activities.SettingsActivity;
import tk.mirenamorrortu.earthquakes.Fragments.EarthQuakeListFragment;
import tk.mirenamorrortu.earthquakes.Fragments.MapListFragment;
import tk.mirenamorrortu.earthquakes.Managers.EarthQuakeAlarmManager;
import tk.mirenamorrortu.earthquakes.Services.DownloadEarthquakeService;
import tk.mirenamorrortu.earthquakes.task.DownloadEarQuakesTask;

public class MainActivity extends Activity {
    public static final int PREFS_ACTIVITY = 0;
    private String EARTHQUAKE_PREFS = "PREFERENCES";
    private ActionBar actionBar;
    private static final String SELECTED_TAB= "SELECTED_TAB";

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_TAB, actionBar.getSelectedNavigationIndex());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);
        actionBar.setSelectedNavigationItem(savedInstanceState.getInt(SELECTED_TAB));
    }

    public static class TabListener < T extends Fragment> implements android.app.ActionBar.TabListener {
        private Fragment fragment;
        private Activity activity;
        private Class<T> fragmentClass;
        private int fragmentContainer;

        public TabListener(Activity	activity, int fragmentContainer, Class<T> fragmentClass) {
            this.activity = activity;
            this.fragmentContainer = fragmentContainer;
            this.fragmentClass = fragmentClass;
        }

        // Called when a new tab has been selected
        public void onTabSelected(android.app.ActionBar.Tab tab, android.app.FragmentTransaction ft) {
            if (fragment == null) {
                String fragmentName = fragmentClass.getName();
                fragment = Fragment.instantiate(activity, fragmentName);
                ft.add(fragmentContainer, fragment, fragmentName);
            } else{
                ft.attach(fragment);
            }
        }

        // Called on the currently selected tab when a different tag is
        // selected.

        public void onTabUnselected(android.app.ActionBar.Tab tab, android.app.FragmentTransaction ft) {
            if	(fragment	!=	null){
                ft.detach(fragment);
            }
        }

        // Called when the selected tab is selected.
        public void onTabReselected(android.app.ActionBar.Tab tab, android.app.FragmentTransaction ft) {
            if (fragment !=	null){
                ft.attach(fragment);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabListener listFragment;
        TabListener mapfragment;

        // Set the Action Bar to use tabs for navigation
        actionBar = getActionBar();

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);


        ActionBar.Tab tabOne = actionBar.newTab();
        listFragment = new TabListener<EarthQuakeListFragment>(this, R.id.fragmentcontainer, EarthQuakeListFragment.class);
        tabOne.setText("EarthQuakes List")
                .setContentDescription("order by magnitude")
                .setTabListener(listFragment);
        actionBar.addTab(tabOne);

        ActionBar.Tab tabtwo = actionBar.newTab();
        mapfragment = new TabListener<MapListFragment>(this, R.id.fragmentcontainer, MapListFragment.class);
        tabtwo.setText("EarthQuakes Map")
                .setTabListener(mapfragment);
        actionBar.addTab(tabtwo);

        checkToSettedAlarm();

        //Comprobamos si hay que lanzar la alarma:
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean autorefresh = Boolean.parseBoolean(prefs.getString(this.getString(R.string.autorefresh), "false"));
        if (autorefresh){
            long freq = Long.parseLong(prefs.getString(this.getString(R.string.frequency), "1"));
            freq = freq * 60000;
            EarthQuakeAlarmManager.setAlarm(this, freq);
        }
    }

    private void checkToSettedAlarm() {
        String KEY = "LAUNCHED_BEFORE";
        SharedPreferences pref = getSharedPreferences(EARTHQUAKE_PREFS, Activity.MODE_PRIVATE);
        if(!pref.getBoolean(KEY, false)){
            long interval = getResources().getInteger(R.integer.default_interval) * 60 * 1000;
            EarthQuakeAlarmManager.setAlarm(this, interval);
            pref.edit().putBoolean(KEY, true).apply();
        }

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
            Intent preferences = new Intent(this, SettingsActivity.class);

            startActivityForResult(preferences, PREFS_ACTIVITY);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}
