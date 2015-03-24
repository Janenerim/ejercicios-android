package tk.mirenamorrortu.intents;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import tk.mirenamorrortu.intents.Fragments.send_fragment;


public class MainActivity extends ActionBarActivity implements send_fragment.SendDataListener{

    public static final String IMG_KEY = "IMAGEN";
    public static final String SEND_KEY = "SEND";
    public static final String RECEIVED_KEY = "RECEIVED";

    public static final int	ASK_CAMERA	=	1;
    public static final int	SHOW_PHOTO	=	2;
    public static final int ERR_PHOTO = 3;

    public static final int ASK_SEND = 1;
    public static final int CLOSE_SEND = 2;
    public static final int ERR_SEND = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setData(String data) {

    }
}
