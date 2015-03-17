package tk.mirenamorrortu.configchange;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivityA extends ActionBarActivity {

    private final String DATA = "data";
    private String op = "op";
    private Double result = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);

        Log.d("CHANGE", "Activity onCreate()");

        if (savedInstanceState != null){
            String data = savedInstanceState.getString(DATA);
            Log.d("CHANGE", "Activity onCreate() saved data: " + data);
        }

        Button btnOpenB = (Button) findViewById(R.id.btnOpenB);
        btnOpenB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CHANGE", "ActivityA Onclick(OpenB)");
                Intent open_b = new Intent(MainActivityA.this, MainActivityB.class);
                startActivity(open_b);
            }
        });

        Button btnClose = (Button) findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CHANGE", "ActivityA Onclick(Close)");

                finish();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d("CHANGE", "ActivityA onSaveInstanceState()");
        outState.putString(op, "datos");

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Log.d("CHANGE","ActivityA onRestoreInstanceState()");
        this.restore_state(savedInstanceState);
        Log.d("CHANGE","ActivityA onRestoreInstanceState()");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("CHANGE", "Activity onResume()");
    }

    private void restore_state(Bundle savedInstanceState){
        this.op = savedIntanceState.getString("op");
        this.result = savedIntanceState.getDouble("result");
    }

    private void save_state(Bundle savedInstanceState){
        savedIntanceState.setString("op", this.op);
        savedIntanceState.setDouble("result", this.result);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d("CHANGE", "Activity onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("CHANGE", "Activity onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("CHANGE", "Activity onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("CHANGE", "Activity onDestroy()");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
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
