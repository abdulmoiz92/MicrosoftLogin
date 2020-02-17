package com.example.microsftlogin.dashboardsActivities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.microsftlogin.Helpers.SharedPrefrenceHelper;
import com.example.microsftlogin.HomePage;
import com.example.microsftlogin.R;
import com.example.microsftlogin.Utils.SharedPrefrenceUtil;

import java.io.File;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static com.example.microsftlogin.Utils.SharedPrefrenceUtil.USER_EMAIL;
import static com.example.microsftlogin.Utils.SharedPrefrenceUtil.USER_NAME;
import static com.example.microsftlogin.Utils.SharedPrefrenceUtil.USER_PASSWORD;

public class EditProfile extends Fragment {

    EditText Name;
    EditText Email;
    EditText Password;
    EditText ConfirmPassword;
    Button Updateprofile;
    ImageView imageView;
    SharedPrefrenceHelper sph = new SharedPrefrenceHelper();
    static final int REQUEST_IMAGE_CAPTURE = 1;

    public EditProfile() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LayoutInflater lf = getActivity().getLayoutInflater();
        View view = lf.inflate(R.layout.activity_edit_profile, container, false);
        //sph.mprefrences = getActivity().getSharedPreferences(sph.getSharedfile(),MODE_PRIVATE);

        Name = view.findViewById(R.id.editName);
        Email = view.findViewById(R.id.editEmail);
        Password = view.findViewById(R.id.editPassword);
        ConfirmPassword = view.findViewById(R.id.editPasswordConfirm);
        Updateprofile = view.findViewById(R.id.updateProfile);
        imageView = view.findViewById(R.id.profileimage);

        Name.setText(SharedPrefrenceUtil.getInstance(getActivity()).getStringValue(USER_NAME));
        Email.setText(SharedPrefrenceUtil.getInstance(getActivity()).getStringValue(USER_EMAIL));
        Password.setText(SharedPrefrenceUtil.getInstance(getActivity()).getStringValue(USER_PASSWORD));
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        final Matcher mat = pattern.matcher(Email.getText().toString());

        Updateprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean NameCorrect = false;
                Boolean EmaiCorrect = false;
                Boolean PassworCorrect = true;
                if (!(Name.getText().toString().equals(""))) {
                    NameCorrect = true;
                } if (mat.matches()) {
                    EmaiCorrect = true;
                }

                if (!(Password.getText().toString().equals(SharedPrefrenceUtil.getInstance(getActivity()).getStringValue(USER_PASSWORD))) ) {
                    if (!(Password.getText().toString().equals("")) &&
                            Password.getText().toString().equals(ConfirmPassword.getText().toString()) ) {
                            SharedPrefrenceUtil.getInstance(getActivity()).saveValue(USER_PASSWORD,Password.getText().toString());
                            PassworCorrect = true;
                    } else {
                        PassworCorrect = false;
                    }
                }

                if (NameCorrect == true && EmaiCorrect == true && PassworCorrect == true) {
                    SharedPrefrenceUtil.getInstance(getActivity()).saveValue(USER_NAME,Name.getText().toString());
                    SharedPrefrenceUtil.getInstance(getActivity()).saveValue(USER_EMAIL,Email.getText().toString());
                    Toast.makeText(getActivity(),"Information Updated Successfully",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(),"Error Please Check Your Information",Toast.LENGTH_LONG).show();
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });


        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (resultCode == RESULT_OK) {
         /*   Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) Objects.requireNonNull(extras).get("data");*/
            Uri selectedImage = data.getData();
            imageView.setImageURI(selectedImage);
        }
    }



  /*  public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    Bitmap photo = (Bitmap) Objects.requireNonNull(imageReturnedIntent.getExtras()).get("data");
                    imageViewExpense.setImageBitmap(photo);
                    buttonSelectImage.setVisibility(View.INVISIBLE);
                    imageViewExpense.setVisibility(View.VISIBLE);
                    btn_cancel.setVisibility(View.VISIBLE);


                    // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                    Uri tempUri = getImageUri(con, photo);

                    // CALL THIS METHOD TO GET THE ACTUAL PATH
                    File finalFile = new File(getRealPathFromURI(tempUri));


                }

                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    imageViewExpense.setImageURI(selectedImage);
                    buttonSelectImage.setVisibility(View.INVISIBLE);
                    imageViewExpense.setVisibility(View.VISIBLE);
                    btn_cancel.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
*/
    private void dispatchTakePictureIntent() {
     /*   Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }*/

        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, 0);


        /*Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePicture, 0);*///zero can be replaced with any action code
    }


}
