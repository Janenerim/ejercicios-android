package tk.mirenamorrortu.todolistfragments;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.TextView;

import tk.mirenamorrortu.todolistfragments.Fragments.ToDoListFragment;
import tk.mirenamorrortu.todolistfragments.Model.ToDo;


public class DetailActivity extends ActionBarActivity {

    private ToDo todo;
    private TextView lblTask;
    private TextView lblDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        lblDate = (TextView) findViewById(R.id.lbl_Date);
        lblTask = (TextView) findViewById(R.id.lbl_Task);

        Intent detailIntent = getIntent();
        todo = detailIntent.getParcelableExtra(ToDoListFragment.TODO_ITEM);

        populateView();
    }

    private void populateView(){
        lblTask.setText(todo.getTask());
        lblDate.setText(todo.getCreatedFormated());
    }


}
