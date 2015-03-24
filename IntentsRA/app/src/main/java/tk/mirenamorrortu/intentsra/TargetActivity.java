package tk.mirenamorrortu.intentsra;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class TargetActivity extends ActionBarActivity {

    Button btn_send;
    Button btn_close;
    TextView txt_send;
    TextView txt_received;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);
        obtainsViews();
        addEventListeners();
        processIntent();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_target, menu);
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


    private void obtainsViews(){
       btn_close = (Button) findViewById(R.id.btn_close);
       btn_send = (Button) findViewById(R.id.btn_send);
       txt_send = (TextView) findViewById(R.id.txt_send);
       txt_received = (TextView) findViewById(R.id.txt_received);
    }

    private void addEventListeners(){
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = txt_send.getText().toString();
                if (message.length() > 0){
                    Intent data = new Intent();
                    data.putExtra(MainActivity.MESSAGE, txt_send.getText().toString());
                    setResult(RESULT_OK);
                    finish();
                }else{
                    Toast toast = Toast.makeText(TargetActivity.this, getResources().getString(R.string.no_text), Toast.LENGTH_SHORT );
                    toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0,0 );
                    toast.show();
                }
            }
        });
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    private void processIntent (){
        Intent data = getIntent();

        String message = data.getStringExtra(MainActivity.MESSAGE);
        txt_received.setText(message);
    }
}
