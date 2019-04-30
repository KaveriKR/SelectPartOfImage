package project.android.kaverikkr.selectpartofimage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;



public class MainActivity extends AppCompatActivity {

    Button imageSelect;
    Button save;
    ImageView im;
    private DrawableImageView customImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageSelect = findViewById(R.id.click);
  //      customImageView
        customImageView = findViewById(R.id.i1);
        im = findViewById(R.id.im);
        imageSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
pickImage();
            }
        });

        save  =findViewById(R.id.sav);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Bitmap b = customImageView.saveBitmap();
               im.setImageBitmap(b);
            }
        });
    }


    private void pickImage(){

        Intent  intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        }
        startActivityForResult(Intent.createChooser(intent,"Select Image"),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if (resultCode == RESULT_OK)
        {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                customImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }
}
