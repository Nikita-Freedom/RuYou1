package com.example.ruyou1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ruyou1.API.APIHelper;
import com.example.ruyou1.API.APIService;
import com.example.ruyou1.Model.POST.ItemPostList;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class Description extends AppCompatActivity {
    private static final int CAMERA_PERM_CODE = 1;
    private static final int CAMERA_REQUEST_CODE = 1;
    public ImageView imageView;
    private TextView textTarget;
    private TextView textResponse;
    Button buttonPhoto;
    String mCurrentPhotoPath;
    boolean isImageFitToScreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        imageView = findViewById(R.id.img);
        textTarget =findViewById(R.id.text_target);
        textResponse = findViewById(R.id.text_response);
        textResponse.setTextColor(Color.WHITE);
        buttonPhoto = findViewById(R.id.btn);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isImageFitToScreen) {
                    isImageFitToScreen=false;
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(650, 650);
                    imageView.setLayoutParams(layoutParams);
                    imageView.setAdjustViewBounds(true);

                }else{
                    isImageFitToScreen=true;
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                    imageView.setAdjustViewBounds(true);
                    imageView.setScaleType(ImageView.ScaleType.FIT_END);
                }
            }
        });
        buttonPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AskCameraPermission();
            }
        });
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value1 = extras.getString("key1");
            textTarget.setText(value1);
            textTarget.setTextColor(Color.WHITE);
        }
    }

    private void openCamera() {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(camera.resolveActivity(getPackageManager()) != null) {

            File pictureFile = null;
                // Создание файла, куда должна идти фотография
            try {
                pictureFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(this,
                        "Photo file can't be created, please try again",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (pictureFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.ruyou1.fileprovider",
                        pictureFile);
                camera.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(camera, CAMERA_REQUEST_CODE);
            }
        }

    }

    private void AskCameraPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        }else{
            openCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERM_CODE) {
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String pictureFile = "RUYOU" + timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(pictureFile,  ".jpg", storageDir);
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
             File imgFile = new  File(mCurrentPhotoPath);
            if(imgFile.exists())            {
                 imageView.setImageURI(Uri.fromFile(imgFile));
            }
        }


            final AlertDialog dialogBuilder = new AlertDialog.Builder(this,R.style.CustomAlertDialog).create();
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog, null);
            File imgFile = new  File(mCurrentPhotoPath);
            //dialogView.setBackgroundResource(android.R.color.transparent);

            final EditText editText1 = (EditText) dialogView.findViewById(R.id.name_dialog);
            editText1.setTextColor(Color.WHITE);
            final EditText editText2 = (EditText) dialogView.findViewById(R.id.surname_dialog);
            editText2.setTextColor(Color.WHITE);
            final EditText editText3 = (EditText) dialogView.findViewById(R.id.patronymic_dialog);
            editText3.setTextColor(Color.WHITE);


            final TextView textview = (TextView) dialogView.findViewById(R.id.photo_dialog);
            textview.setTextColor(Color.WHITE);

            Button button_no = (Button) dialogView.findViewById(R.id.no_btn_dialog);
            Button button_repeat = (Button) dialogView.findViewById(R.id.repeat_btn_dialog);
            final Button button_yes = (Button) dialogView.findViewById(R.id.yes_btn_dialog);
        textview.setText(imgFile.getName());


        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isImageFitToScreen) {
                    isImageFitToScreen=false;
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(650, 650);
                    imageView.setLayoutParams(layoutParams);
                    imageView.setAdjustViewBounds(true);

                }else{
                    isImageFitToScreen=true;
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                    imageView.setAdjustViewBounds(true);
                    imageView.setScaleType(ImageView.ScaleType.FIT_END);
                }
            }
        });


            button_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Закрываем диалог
                    dialogBuilder.dismiss();
                    textview.clearComposingText();
                }
            });
            button_repeat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AskCameraPermission();
                    textview.clearComposingText();
                }
            });

            button_yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String title1 = editText1.getText().toString().trim();
                    String title2 = editText2.getText().toString().trim();
                    String title3 = editText3.getText().toString().trim();
                    //Проверка на пустые данные и отправка их на сервер
                    if(!TextUtils.isEmpty(title1) && !TextUtils.isEmpty(title2) && !TextUtils.isEmpty(title3)) {
                        SenLoadItems(title1, title2, title3);
                        Snackbar
                                .make(button_yes, "Данные отправлены на сервер", Snackbar.LENGTH_SHORT)
                                .show();
                    }
                }
            });
            dialogBuilder.setView(dialogView);
            dialogBuilder.show();
        }
        public void SenLoadItems(final String name, final String surname, final String patronymic){
            final ItemPostList contacts = new ItemPostList();
            contacts.setId(2);
            contacts.setImage(mCurrentPhotoPath);
            //contacts.setContact("Nikita", "Ulyanov", "Borisovich");
            APIHelper.getInstance().sendItems("send_data", 2, contacts,
            new APIHelper.APIHelperCallback<ResponseBody>() {
                @Override
                public void onResponse(ResponseBody response) {
                    showResponse(response.toString());
                    contacts.setContact(name, surname, patronymic);
                }

                @Override
                public void onError() {

                }
            });


        }
    public void showResponse(String response) {

        if(textResponse.getVisibility() == View.GONE) {
            textResponse.setVisibility(View.VISIBLE);
        }
        textResponse.setText(response.toString());
    }


}
