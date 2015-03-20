package tk.mirenamorrortu.kalkulatu;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;

import tk.mirenamorrortu.kalkulatu.Logik.CalcLogic;


public class MainActivity extends ActionBarActivity {

    private final String OPERANDO1_KEY = "op1";
    private final String OPERANDO2_KEY = "op2";
    private final String OPERADOR_KEY = "op";

    private final String PUNTO_BOL_KEY = "punto";
    private final String OPER_BOL_KEY = "operador";

    private String op1;
    private String op2;
    private String op;

    private CalcLogic kalkulat;

    private boolean punto = false;
    private boolean operador = false;

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
    private Button btn_minus;
    private Button btn_div;
    private Button btn_mul;
    private Button btn_point;
    private Button btn_equal;
    private TextView resul_text;


    private void restore_state(Bundle savedInstanceState){
        this.op1 = savedInstanceState.getString(OPERANDO1_KEY);
        this.op2 = savedInstanceState.getString(OPERANDO2_KEY);
        this.op = savedInstanceState.getString(OPERADOR_KEY);

        this.operador = savedInstanceState.getBoolean(OPER_BOL_KEY);
        this.punto = savedInstanceState.getBoolean(PUNTO_BOL_KEY);
    }

    private void save_state(Bundle savedInstanceState){
        savedInstanceState.putString(OPERANDO1_KEY, this.op1);
        savedInstanceState.putString(OPERANDO2_KEY, this.op2);
        savedInstanceState.putString(OPERADOR_KEY, this.op);

        savedInstanceState.putBoolean(OPER_BOL_KEY, this.operador);
        savedInstanceState.putBoolean(PUNTO_BOL_KEY, this.punto);
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
        btn_div = (Button) findViewById(R.id.btn_div);
        btn_minus = (Button) findViewById(R.id.btn_min);
        btn_mul = (Button) findViewById(R.id.btn_mul);
        btn_point = (Button) findViewById(R.id.btn_pnt);
        btn_equal = (Button) findViewById(R.id.btn_exc);
        resul_text = (TextView) findViewById(R.id.txtV_resul);
    }

    View.OnClickListener clicknumbtn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String new_num = ((Button)v).getText().toString();
            if (operador){
                op2 = op2 + new_num;
            }
            else{
                op1 =  op1 + new_num;
            }
            visualizar();
        }
    };

    View.OnClickListener clickopbtn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String oper = ((Button)v).getText().toString();
            Log.d("CHANGE", "Pulsado: " + oper);
            if (operador){
                //mandamos hacer la operación y ponemos el resultado como operando 1
                if (op.equals("+")){
                    kalkulat.add(op2);
                }
                else if (op.equals("-")){
                    kalkulat.subtract(op2);
                }
                else if (op.equals("/")){
                    kalkulat.divide(op2);
                }
                else if (op.equals("X")){
                    kalkulat.multiply(op2);}
                else{
                    Log.d("CHANGE", "Problemas en el paraiso, op: " + op);
                }
                op1 = kalkulat.getTotalString();
                op2 = "";
            }
            else{
                kalkulat.setTotal(Double.parseDouble(op1));
            }
            operador = true;
            op = oper;
            punto = false;
            visualizar();
        }
    };
    View.OnClickListener clickpointbtn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!punto){
                if (operador){
                    op2 = op2 + ".";
                }
                else{
                    op1 = op1 + ".";
                }
                punto = true;
            }
            //Si ya se ha pulsado el punto pasamos del tema!
            visualizar();
        }
    };
    View.OnClickListener clickeqbtn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("CHANGE", "Pulsado: " + ((Button)v).getText());
            if (operador){
                //hacemos la operación y ponemos el resultado como operando1
                if (op.equals("+")){
                    kalkulat.add(op2);
                }
                else if (op.equals("-")){
                    kalkulat.subtract(op2);
                }
                else if (op.equals("/")){
                    kalkulat.divide(op2);
                }
                else if (op.equals("X")){
                    kalkulat.multiply(op2);}
                else{
                    Log.d("CHANGE", "Problemas en el otro paraiso, op: " + op);
                }
                op1 = kalkulat.getTotalString();
            }
            else{
                //no habia más que un operador, lo dejamos así y esperamos operación.
                kalkulat.setTotal(Double.parseDouble(op1));
            }
            operador = false;
            op2 = "";
            punto = false;
            visualizar();
            op1 = "";
        }
    };

    private void visualizar (){
        String temp = "";

        if (operador){
            if (op2 == ""){
                temp = String.valueOf(Double.parseDouble(op1));
            }else{
                temp = String.valueOf(Double.parseDouble(op2));
            }
        }
        else{
            if (op1 == ""){
                temp = "0";
            }else{
                temp = String.valueOf(Double.parseDouble(op1));
            }
        }
        resul_text.setText(temp);
    }

    private void asignar_listener() {

        btn0.setOnClickListener(clicknumbtn);
        btn1.setOnClickListener(clicknumbtn);
        btn2.setOnClickListener(clicknumbtn);
        btn3.setOnClickListener(clicknumbtn);
        btn4.setOnClickListener(clicknumbtn);
        btn5.setOnClickListener(clicknumbtn);
        btn6.setOnClickListener(clicknumbtn);
        btn7.setOnClickListener(clicknumbtn);
        btn8.setOnClickListener(clicknumbtn);
        btn9.setOnClickListener(clicknumbtn);

        btn_plus.setOnClickListener(clickopbtn);
        btn_minus.setOnClickListener(clickopbtn);
        btn_div.setOnClickListener(clickopbtn);
        btn_mul.setOnClickListener(clickopbtn);

        btn_point.setOnClickListener(clickpointbtn);

        btn_equal.setOnClickListener(clickeqbtn);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        op1 = "";
        op2 = "";
        op = null;
        operador = false;
        punto = false;

        kalkulat = new CalcLogic();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        carga_botones();
        asignar_listener();
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
