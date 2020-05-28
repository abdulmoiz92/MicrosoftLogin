package com.example.microsftlogin.TabsFragment;


import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.text.LineBreaker;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavGraphNavigator;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.microsftlogin.AboutUserDatabase.AboutUser;
import com.example.microsftlogin.HomePage;
import com.example.microsftlogin.PdfHelper.PdfCreator;
import com.example.microsftlogin.TodoDatabase.Todo;
import com.example.microsftlogin.Adapter.TodoRecyclerAdapter;
import com.example.microsftlogin.Helpers.SharedPrefrenceHelper;
import com.example.microsftlogin.R;
import com.example.microsftlogin.TodoDatabase.TodoViewModel;
import com.example.microsftlogin.UserDatabase.UserViewModel;
import com.example.microsftlogin.UserExperienceDatabase.UserExperience;
import com.example.microsftlogin.Utils.SharedPrefrenceUtil;
import com.example.microsftlogin.dashboardsActivities.EditTodoFragmentDirections;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Dashboard extends Fragment implements View.OnClickListener {

    SharedPrefrenceHelper sph = new SharedPrefrenceHelper();
   // List<Todo> todos = new ArrayList<>();
   // List<Todo> todosFromPref = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private TodoRecyclerAdapter mAdapter;
    private TodoViewModel todoViewModel;
    private NavController navController;
    private MaterialCardView aboutuserCard;
    private MaterialCardView userexperienceCard;
    private MaterialCardView userEducationCard;
    private MaterialCardView userSkillsCard;
    private MaterialCardView userProjectsCard;
    private MaterialCardView userAcheivementsCard;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;


    public Dashboard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        aboutuserCard = view.findViewById(R.id.aboutuser);
        userexperienceCard = view.findViewById(R.id.userexperience);
        userEducationCard = view.findViewById(R.id.usereducation);
        userSkillsCard = view.findViewById(R.id.userskills);
        userProjectsCard = view.findViewById(R.id.userprojects);
        userAcheivementsCard = view.findViewById(R.id.userachievements);
        navController = Navigation.findNavController(getActivity(), R.id.fragment);
        TextView makeYourCv = view.findViewById(R.id.makeyourcv);
       /* sph.tprefrences = getActivity().getSharedPreferences(sph.getTodoSharedfile(),MODE_PRIVATE);
        if(null != sph.getSpArray(SharedPrefrenceHelper.getTodo_Key(), todos))
            todosFromPref = sph.getSpArray(SharedPrefrenceHelper.getTodo_Key(), todos); */
/*        todoViewModel = ViewModelProviders.of(getActivity()).get(TodoViewModel.class);
        mAdapter = new TodoRecyclerAdapter(getActivity(),todoViewModel,2);
        todoViewModel.getAllTodos().observe(getActivity(), new Observer<List<Todo>>() {
            @Override
            public void onChanged(List<Todo> todos) {
                // Update the cached copy of the words in the adapter.
                mAdapter.setTodos(todos);
            }
        });

        mRecyclerView = view.findViewById(R.id.slidertodolist);

       /* if(null != todosFromPref && todosFromPref.size() > 0){
            //todoAdapter = new TodoAdapter(getActivity(), todosFromPref);
            mAdapter = new TodoRecyclerAdapter(getActivity(),todosFromPref,2);

        } else {
            //todoAdapter = new TodoAdapter(getActivity(), todos);
            mAdapter = new TodoRecyclerAdapter(getActivity(), todos,2);
        } */

       /* mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(linearLayoutManager); */


       aboutuserCard.setOnClickListener(this);
       userexperienceCard.setOnClickListener(this);
       userEducationCard.setOnClickListener(this);
       userSkillsCard.setOnClickListener(this);
       userProjectsCard.setOnClickListener(this);
       userAcheivementsCard.setOnClickListener(this);
       makeYourCv.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.aboutuser:
                navController.navigate(R.id.aboutUserFragment);
            break;

            case R.id.userexperience:
               navController.navigate(R.id.userExperiences);
            break;

            case R.id.usereducation:
               navController.navigate(R.id.userEducation);
            break;

            case R.id.userskills:
                navController.navigate(R.id.userSkills);
            break;

            case R.id.userprojects:
                navController.navigate(R.id.userPersonalProjects);
            break;

            case R.id.userachievements:
                navController.navigate(R.id.userAchievements);
            break;

            case R.id.makeyourcv:

                navController.navigate(R.id.pdfWebFragment);

               /* int hasWriteStoragePermission = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
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
                    try {
                        createPdf();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                } */

            break;
        }
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /* private void createPdf() throws FileNotFoundException,DocumentException {
        int user_id = SharedPrefrenceUtil.getInstance(getActivity()).getIntValue(SharedPrefrenceUtil.CURRENT_USER_ID);
        String user_id2 = FirebaseAuth.getInstance().getCurrentUser().getUid();
        UserViewModel userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);
        AboutUser aboutUser = null;

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i(TAG, "Created a new directory for PDF");
        }

        File pdfFile = new File(docsFolder.getAbsolutePath(), "Resume.pdf");
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document();
        PdfWriter.getInstance(document, output);

        document.open();
        if (userViewModel.findUserWithAbout(user_id2).get(0).getAboutUserList().size() > 0) {
            aboutUser = userViewModel.findUserWithAbout(user_id2).get(0).getAboutUser();
            document.add(new Paragraph("Name: " + aboutUser.getName() + "\n"));
            document.add(new Paragraph("Email: " + aboutUser.getEmail()));
            document.add(new Paragraph("Phone: " + aboutUser.getPhone()));
            document.add(new Paragraph("Address: " + aboutUser.getAddress()));
            document.add(new Paragraph("Education Degree : " + aboutUser.getEducationDegree()));
            document.add(new Paragraph("Description: " + aboutUser.getDescription()));
            document.add(Chunk.NEWLINE);
            document.add(new LineSeparator(0.5f, 40, null, 0, -5));
            Toast.makeText(getActivity(),"File Created",Toast.LENGTH_LONG).show();
        }

        if (userViewModel.findUserWithExperiences(user_id).get(0).getUserExperiences().size() > 0) {
            List<UserExperience> userExperienceList = userViewModel.findUserWithExperiences(user_id).get(0).getUserExperiences();
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("Work Experiences"));
            document.add(new LineSeparator(0.5f,20,null,0,-5));
            document.add(Chunk.NEWLINE);
            for (int i = 0; i < userViewModel.findUserWithExperiences(user_id).get(0).getUserExperiences().size();
                 i++) {
                document.add(new Paragraph(userExperienceList.get(i).getJobTitle()));
                document.add(new Paragraph(userExperienceList.get(i).getCompanyName()));
                document.add(new Paragraph(userExperienceList.get(i).getWorkedFrom() + " - "+
                        userExperienceList.get(i).getWorkedTill()));
                document.add(new Paragraph(userExperienceList.get(i).getCityOrCountry()));
                document.add(new Paragraph(userExperienceList.get(i).getTasksPerformed()));
                document.add(new LineSeparator(0.5f,20,null,0,-5));
                document.add(Chunk.NEWLINE);
            }
        }
        document.close(); */
}
