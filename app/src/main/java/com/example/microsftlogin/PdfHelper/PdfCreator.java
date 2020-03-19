package com.example.microsftlogin.PdfHelper;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;

import com.example.microsftlogin.AboutUserDatabase.AboutUser;
import com.example.microsftlogin.UserDatabase.UserViewModel;
import com.example.microsftlogin.UserDatabaseRelation.UserWithAbout;
import com.example.microsftlogin.Utils.SharedPrefrenceUtil;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class PdfCreator extends AppCompatActivity {

  /*  private static final String TAG = "PdfCreatorActivity";
    private File pdfFile;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;
    private AboutUser aboutUser;
    private UserViewModel userViewModel;
    int user_id = SharedPrefrenceUtil.getInstance(this).getIntValue(SharedPrefrenceUtil.CURRENT_USER_ID);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        aboutUser = userViewModel.findUserWithAbout(user_id).get(0).getAboutUser();
    }

    public void onCreatePdfWrapper() throws FileNotFoundException, DocumentException {
        int hasWriteStoragePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS)) {
                    showMessageOKCancel("You Need To Allow Permission For Storage",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                               REQUEST_CODE_ASK_PERMISSIONS );
                                    }
                                }
                            });
                    return;
                }
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS );
            }
            return;
        } else {
            createPdf();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    try {
                        onCreatePdfWrapper();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Permission Denied
                    Toast.makeText(this, "WRITE_EXTERNAL Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }



    private void createPdf() throws FileNotFoundException,DocumentException {
       File docsFolder = new File(Environment.getExternalStorageDirectory() + "//Documents");
       if (!docsFolder.exists()) {
           docsFolder.mkdir();
           Log.i(TAG, "Created a new directory for PDF");
       }

       pdfFile = new File(docsFolder.getAbsolutePath(),"Resume.pdf");
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document();
        PdfWriter.getInstance(document,output);
        document.open();
        Paragraph name = new Paragraph(aboutUser.getName());
        float fntSize = 6.7f;
        name.setFont(FontFactory.getFont(FontFactory.COURIER, fntSize));
        document.add(name);
    }*/
}