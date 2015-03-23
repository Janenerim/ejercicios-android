package tk.mirenamorrortu.todolistfragments;

import android.app.Fragment;
import android.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import tk.mirenamorrortu.todolistfragments.Fragments.InputFragment;
import tk.mirenamorrortu.todolistfragments.Model.ToDo;


public class MainActivity extends ActionBarActivity implements InputFragment.TODOItemListener{

    private final String TODO = "TODO";

    @Override
    public void addTodo (ToDo todo){

        Log.d("TODO", todo.getTask() + " " +todo.getCreated());
        InputFragment.TODOItemListener listFragment;
        try {
            listFragment = (InputFragment.TODOItemListener) getFragmentManager().findFragmentById(R.id.listFragment);

        }catch(ClassCastException ex){
            throw new ClassCastException(this.toString() + "must implements a InputFragment.TODOItemListener");
        }
       listFragment.addTodo(todo);
    }



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
}
