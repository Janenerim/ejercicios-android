package tk.mirenamorrortu.todolister;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private ArrayList<String> todos;

    private final String TODOs_KEY = "todos";

    private Button btnAdd;
    private TextView todoText;
    private ListView todoList;

    private ArrayAdapter<String> aa;

    private void restore_state(Bundle savedInstanceState){
        this.todos.addAll(savedInstanceState.getStringArrayList(TODOs_KEY));
    }

    private void save_state(Bundle savedInstanceState){
        savedInstanceState.putStringArrayList(TODOs_KEY, this.todos);
        //aa.notifyDataSetChanged();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d("CHANGE", "Save_state()");
        save_state(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d("CHANGE", "restore_state()");
        restore_state(savedInstanceState);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todos = new ArrayList <String> ();

        //todos = new ArrayList <String> ();
        aa = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, todos);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        todoText = (TextView) findViewById(R.id.todoText);
        todoList = (ListView) findViewById(R.id.todoList);

        todoList.setAdapter(aa);

        this.addEventListeners();
    }

    private void addEventListeners() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todo = todoText.getText().toString();
                todoText.setText("");
                todos.add(0, todo); //para que así aparezca al inicio de la lista, mueve al resto

                aa.notifyDataSetChanged();//avisar al adapter de que hay nuevos datos y hay que repintar
            }
        });
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
