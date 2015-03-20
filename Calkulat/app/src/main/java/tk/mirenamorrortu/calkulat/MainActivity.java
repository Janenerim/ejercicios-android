package tk.mirenamorrortu.calkulat;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.util.ArrayList;

import tk.mirenamorrortu.calkulat.Listeners.NumberOnClickListener;
import tk.mirenamorrortu.calkulat.Listeners.OperationOnClickListener;


public class MainActivity extends ActionBarActivity {

    private final String CALC = "CALC";

    private ArrayList<Button> numberButtons ;
    private ArrayList<Button>operationButtons ;

    private void carga_botones(){



        //for (int i =0;i <  numbers.length(); i++){
         //   id = "btn" +
       // }
    }
    private void addEventListener() {
        //Add Listeners to number buttons
        for (int i=0; i < numberButtons.size(); i++){
            numberButtons.get(i).setOnClickListener(new NumberOnClickListener());
        }

        //
        for (int i=0; i < operationButtons.size(); i++){
            operationButtons.get(i).setOnClickListener(new OperationOnClickListener());
        }
    }



    private void restore_state(Bundle savedInstanceState){
    }

    private void save_state(Bundle savedInstanceState){
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        addEventListener();
    }

    public void setNumber (String num){

    }
    public void setOperation (String num){

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
