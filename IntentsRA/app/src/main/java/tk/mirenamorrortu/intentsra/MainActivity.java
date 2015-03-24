package tk.mirenamorrortu.intentsra;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v4.media.session.MediaSessionCompatApi14;
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


public class MainActivity extends ActionBarActivity {

    public static final String MESSAGE = "MSG";

    private static final int REQUEST_TARGET = 1;
    private static final int REQUEST_PHOTO = 2;

    Button btn_send;
    Button btn_photo;
    TextView txt_send;
    TextView txt_received;
    ImageView img_photo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        obtainsViews();
        addEventListeners();
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

    private void obtainsViews(){
        btn_photo = (Button) findViewById(R.id.btn_photo);
        btn_send = (Button) findViewById(R.id.btn_send);
        txt_send = (TextView) findViewById(R.id.txtV_send);
        txt_received = (TextView) findViewById(R.id.txtV_received);
        img_photo = (ImageView) findViewById(R.id.imgV_photo);
    }

    private void addEventListeners(){
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = txt_send.getText().toString();

                if (message.length() > 0){
                    Intent sendRequest =  new Intent (MainActivity.this, TargetActivity.class);
                    sendRequest.putExtra(MESSAGE, txt_send.getText().toString());

                    startActivityForResult(sendRequest, REQUEST_TARGET );
                }else{
                    Toast toast = Toast.makeText(MainActivity.this, getResources().getString(R.string.no_text),Toast.LENGTH_SHORT );
                    toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0,0 );
                    toast.show();
                }

            }
        });
        btn_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //por si no hay ninguna actividad que pueda ejecutarlo
                if (takePictureIntent.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(takePictureIntent, REQUEST_PHOTO);
                }else{
                    Toast toast = Toast.makeText(MainActivity.this, getResources().getString(R.string.no_camera), Toast.LENGTH_SHORT );
                    toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0,0 );
                    toast.show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case REQUEST_TARGET:
                    String message = data.getStringExtra(MESSAGE);
                    txt_received.setText(message);
                    break;
                case REQUEST_PHOTO:
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    img_photo.setImageBitmap(imageBitmap);

                    break;
                default:
                    break;
            }
        }

    }
}
