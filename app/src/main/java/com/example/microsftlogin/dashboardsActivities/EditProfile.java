package com.example.microsftlogin.dashboardsActivities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.microsftlogin.AboutUserDatabase.AboutUserViewModel;
import com.example.microsftlogin.Helpers.SharedPrefrenceHelper;
import com.example.microsftlogin.HomePage;
import com.example.microsftlogin.R;
import com.example.microsftlogin.StartUpActivities.MainActivity;
import com.example.microsftlogin.UserAchievementsDatabase.UserAchievementViewModel;
import com.example.microsftlogin.UserDatabase.UserViewModel;
import com.example.microsftlogin.UserEducationDatabase.UserEducationViewModel;
import com.example.microsftlogin.UserExperienceDatabase.UserExperience;
import com.example.microsftlogin.UserExperienceDatabase.UserExperienceViewModel;
import com.example.microsftlogin.UserProjectsDatabase.UserProjectViewModel;
import com.example.microsftlogin.UserSkillsDatabase.UserSkillViewModel;
import com.example.microsftlogin.Utils.SharedPrefrenceUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    TextInputLayout Name;
    TextInputLayout Email;
    TextInputLayout Password;
    Button Updateprofile;
    TextView passwordreset;
    ProgressBar changepasswordProgress;
    TextView deleteaccount;
    ProgressBar deleteaccountProgress;
    ImageView imageView;
    UserViewModel userViewModel;
    AboutUserViewModel aboutUserViewModel;
    UserEducationViewModel userEducationViewModel;
    UserExperienceViewModel userExperienceViewModel;
    UserSkillViewModel userSkillViewModel;
    UserProjectViewModel userProjectViewModel;
    UserAchievementViewModel userAchievementViewModel;
    Boolean connected = false;
    SharedPrefrenceHelper sph = new SharedPrefrenceHelper();
    static final int REQUEST_IMAGE_CAPTURE = 1;

    public EditProfile() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        LayoutInflater lf = getActivity().getLayoutInflater();
        View view = lf.inflate(R.layout.activity_edit_profile, container, false);
        //sph.mprefrences = getActivity().getSharedPreferences(sph.getSharedfile(),MODE_PRIVATE);

        userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel .class);
        aboutUserViewModel = ViewModelProviders.of(getActivity()).get(AboutUserViewModel .class);
        userEducationViewModel = ViewModelProviders.of(getActivity()).get(UserEducationViewModel .class);
        userExperienceViewModel = ViewModelProviders.of(getActivity()).get(UserExperienceViewModel .class);
        userSkillViewModel = ViewModelProviders.of(getActivity()).get(UserSkillViewModel .class);
        userProjectViewModel = ViewModelProviders.of(getActivity()).get(UserProjectViewModel .class);
        userAchievementViewModel = ViewModelProviders.of(getActivity()).get(UserAchievementViewModel .class);

        Name = view.findViewById(R.id.edituser_name);
        Email = view.findViewById(R.id.edituser_email);
        Password = view.findViewById(R.id.edituser_password);
        Updateprofile = view.findViewById(R.id.updateProfile);
        passwordreset = view.findViewById(R.id.edit_passwordreset);
        changepasswordProgress = view.findViewById(R.id.edit_changepassword_progress);
        deleteaccount = view.findViewById(R.id.edit_deleteaccount);
        deleteaccountProgress = view.findViewById(R.id.edit_deleteaccount_progress);
        imageView = view.findViewById(R.id.profileimage);


        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        final Matcher mat = pattern.matcher(Email.getEditText().getText().toString());

        passwordreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    //we are connected to a network
                    connected = true;
                } else {
                    connected = false;
                }

                if (connected == true) {
                    passwordreset.setVisibility(View.INVISIBLE);
                    changepasswordProgress.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.main_background),
                            android.graphics.PorterDuff.Mode.SRC_ATOP);
                    changepasswordProgress.setVisibility(View.VISIBLE);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            sendPasswordResetLink();
                            changepasswordProgress.setVisibility(View.INVISIBLE);
                            passwordreset.setVisibility(View.VISIBLE);
                        }
                    }, 3000);
                } else {
                    Toast.makeText(getActivity(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deleteaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    //we are connected to a network
                    connected = true;
                } else {
                    connected = false;
                }

                //Check Internet Connectivity
                if (connected == true) {
                    if (!Password.getEditText().getText().toString().equals("")) {
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Delete Account")
                                .setMessage("Do you really want to Delete Your Account?")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                    public void onClick(final DialogInterface dialog, int whichButton) {
                                        deleteaccount.setVisibility(View.INVISIBLE);
                                        deleteaccountProgress.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.main_background),
                                                PorterDuff.Mode.SRC_ATOP);
                                        deleteaccountProgress.setVisibility(View.VISIBLE);

                                        final FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();
                                        AuthCredential credential = EmailAuthProvider
                                                .getCredential(FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                                                        Password.getEditText().getText().toString());

                                        //ReAuthenticate And Delete
                                        FirebaseAuth.getInstance().getCurrentUser().reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    currentuser.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users")
                                                                    .child(currentuser.getUid());
                                                            ref.removeValue();
                                                            FirebaseAuth.getInstance().signOut();
                                                            userViewModel.deleteAllUser();
                                                            aboutUserViewModel.deleteAllAboutUser();
                                                            userExperienceViewModel.deleteAllUserExperience();
                                                            userEducationViewModel.deleteAllUserEducation();
                                                            userSkillViewModel.deleteAllUserSkill();
                                                            userProjectViewModel.deleteAllUserProjects();
                                                            userAchievementViewModel.deleteAllUserAchievements();
                                                            dialog.dismiss();
                                                            Intent intent = new Intent(getActivity(), MainActivity.class);
                                                            startActivity(intent);
                                                            getActivity().finish();
                                                            Toast.makeText(getActivity(), "User Deleted", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            deleteaccount.setVisibility(View.VISIBLE);
                                                            deleteaccountProgress.setVisibility(View.INVISIBLE);
                                                            Log.d("EditProfile", e.getMessage());
                                                            Toast.makeText(getActivity(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                } else {
                                                    deleteaccount.setVisibility(View.VISIBLE);
                                                    deleteaccountProgress.setVisibility(View.INVISIBLE);
                                                    Toast.makeText(getActivity(), "Please ReCheck Your Password", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                })
                                .setNegativeButton(android.R.string.no, null).show();
                    } else {
                        Password.getEditText().requestFocus();
                        Toast.makeText(getActivity(), "Password Needs To Be Filled", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Updateprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean NameCorrect = false;
                Boolean EmaiCorrect = false;
                Boolean PassworCorrect = true;
                if (!(Name.getEditText().getText().toString().equals(""))) {
                    NameCorrect = true;
                } if (mat.matches()) {
                    EmaiCorrect = true;
                }

                if (NameCorrect == true && EmaiCorrect == true && PassworCorrect == true) {

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

    private void sendPasswordResetLink() {
        FirebaseAuth.getInstance().sendPasswordResetEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Sent Password Reset Link to Email",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getActivity(), "No User Associated with that Email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
