package tk.mirenamorrortu.kalkulatu;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    private final String OPERANDO1_KEY = "op1";
    private final String OPERANDO2_KEY = "op2";
    private final String OPERADOR_KEY = "op";

    private String op1;
    private String op2;
    private String op;

    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;
    private Button btn0;
    private Button btn_plus;
    private Button btn1_minus;
    private Button btn1_div;
    private Button btn1_mul;
    private Button btn1_point;


    private void restore_state(Bundle savedInstanceState){
        this.op1 = savedInstanceState.getString(OPERANDO1_KEY);
        this.op2 = savedInstanceState.getString(OPERANDO2_KEY);
        this.op = savedInstanceState.getString(OPERADOR_KEY);
    }

    private void save_state(Bundle savedInstanceState){
        savedInstanceState.putString(OPERANDO1_KEY, this.op1);
        savedInstanceState.putString(OPERANDO2_KEY, this.op2);
        savedInstanceState.putString(OPERADOR_KEY, this.op);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        save_state(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        restore_state(savedInstanceState);
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void carga_botones(){
        btn0 = (Button) findViewById(R.id.btn_0);
        btn1 = (Button) findViewById(R.id.btn_1);
        btn2 = (Button) findViewById(R.id.btn_2);
        btn3 = (Button) findViewById(R.id.btn_3);
        btn4 = (Button) findViewById(R.id.btn_4);
        btn5 = (Button) findViewById(R.id.btn_5);
        btn6 = (Button) findViewById(R.id.btn_6);
        btn7 = (Button) findViewById(R.id.btn_7);
        btn8 = (Button) findViewById(R.id.btn_8);
        btn9 = (Button) findViewById(R.id.btn_9);
        btn_plus = (Button) findViewById(R.id.btn_pls);
        btn1_div = (Button) findViewById(R.id.btn_div);
        btn1_minus = (Button) findViewById(R.id.btn_min);
        btn1_mul = (Button) findViewById(R.id.btn_mul);
        btn1_point = (Button) findViewById(R.id.btn_pnt);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        carga_botones();

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
}
