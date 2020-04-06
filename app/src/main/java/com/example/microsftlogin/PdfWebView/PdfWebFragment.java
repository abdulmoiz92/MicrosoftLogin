package com.example.microsftlogin.PdfWebView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.os.Environment;
import android.print.PdfPrint;
import android.print.PrintAttributes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.example.microsftlogin.AboutUserDatabase.AboutUser;
import com.example.microsftlogin.R;
import com.example.microsftlogin.UserAchievementsDatabase.UserAchievement;
import com.example.microsftlogin.UserDatabase.UserViewModel;
import com.example.microsftlogin.UserEducationDatabase.UserEducation;
import com.example.microsftlogin.UserExperienceDatabase.UserExperience;
import com.example.microsftlogin.UserProjectsDatabase.UserProject;
import com.example.microsftlogin.UserSkillsDatabase.UserSkill;
import com.example.microsftlogin.Utils.SharedPrefrenceUtil;
import com.itextpdf.text.DocumentException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PdfWebFragment extends Fragment {
    WebView webView;
    private UserViewModel userViewModel;
    int user_id = SharedPrefrenceUtil.getInstance(getActivity()).getIntValue(SharedPrefrenceUtil.CURRENT_USER_ID);
    private AboutUser aboutUser;
    private List<UserExperience> userExperiences;
    private List<UserEducation> userEducations;
    private List<UserSkill> userSkills;
    private List<UserProject> userProjects;
    private List<UserAchievement> userAchievements;

    public PdfWebFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pdf_web, container, false);
        userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);
        aboutUser = null;
        if (userViewModel.findUserWithAbout(user_id).get(0).getAboutUserList().size() > 0) {
            aboutUser = userViewModel.findUserWithAbout(user_id).get(0).getAboutUser();
        }
        userExperiences = userViewModel.findUserWithExperiences(user_id).get(0).getUserExperiences();
        userEducations = userViewModel.findUserWithEducation(user_id).get(0).getUserEducations();
        userSkills = userViewModel.findUserWithSkill(user_id).get(0).getUserSkills();
        userProjects = userViewModel.findUserWithProject(user_id).get(0).getUserProjects();
        userAchievements = userViewModel.findUserWithAchievement(user_id).get(0).getUserAchievements();
        webView = view.findViewById(R.id.pdfweb);
        Button printButton = view.findViewById(R.id.print_Button);

        String htmlDocument = "<html><body>" +
                addStyles() +
                "<div class= container-fluid>" +
                "<div class= row>" +

                "<div class= col-lg-4>" +
                "<div class= sidebar>" +
                addAboutUser() +
                "<br/><br/>" +
                addUserSkills() +
                "<br/><br/>" +
                addUserAchievements() +
                "</div>" +
                "</div>" +

                "<div class= col-lg-8>" +
                addUserExperience() +
                "<br/><br/>" +
                addUserEducation() +
                "<br/><br/>" +
                addUserProjects() +
                "</div>" +

                "</div>" +
                "</div>" +
                "</body></html>";

        webView.loadDataWithBaseURL(null, htmlDocument, "text/html", "UTF-8", null);

        printButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            111);
                } else {
                    try {
                        createWebPrintJob(webView);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }
        });
        return view;
    }

    public String addAboutUser() {
        if (userViewModel.findUserWithAbout(user_id).get(0).getAboutUserList().size() > 0) {
            return "<h4>Contact</h4>" +
                    "<p>" + aboutUser.getName() + "</p>" +
                    "<p>" + aboutUser.getEmail() + "</p>" +
                    "<p>" + aboutUser.getPhone() + "</p>" +
                    "<p>" + aboutUser.getAddress() + "</p>" +
                    "<p>" + aboutUser.getEducationDegree() + "</p>" +
                    "<p>" + aboutUser.getDescription() + "</p>";
        } else {
            return "No User Information";
        }
    }

    public String addUserExperience() {
        if (userExperiences.size() > 0) {
            String experlist = "<div class=container-fluid> <h3>Work Experience</h3>";
            String endexperlist = "</div>";
            String list = "";

            for (int i = 0; i < userExperiences.size(); i++) {
                list = list + "<div class= row>" +
                        "<div class= col-lg-5>" +
                        "<h4>" + userExperiences.get(i).getJobTitle() + "</h4>" +
                        "<h4>" + userExperiences.get(i).getCompanyName() + "</h4>" +
                        "<p>( " + userExperiences.get(i).getWorkedFrom() + "-" + userExperiences.get(i).getWorkedTill() + " )</p>" +
                        "<p>" + userExperiences.get(i).getCityOrCountry() + "</p>" +
                        "</div>" +

                        "<div style= margin-top:25px; class= col-lg-6>" +
                        "<span style= font-size:13px; line-height:1.8em;>"+ userExperiences.get(i).getTasksPerformed() + "</p>" +
                        "</div>" +
                        "</div>";
            }

            return experlist + list + endexperlist;

        } else {
            return "No Work Experiences Yet";
        }
    }

    public String addUserEducation() {
        if (userEducations.size() > 0) {
            String educlist = "<div class= container-fluid> <h3>Education Information</h3> <div class= row>";
            String endeduclist = "</div> </div>";
            String list = "";

            for (int i = 0; i < userExperiences.size(); i++) {
                list = list  +
                        "<div class= col-lg-6>" +
                        "<h4>" + userEducations.get(i).getCourseName() + "</h4>" +
                        "<h5>" + userEducations.get(i).getSchoolOrWebsite() + "</h5>" +
                        "<p>" + userEducations.get(i).getStudiedFrom() + " - " + userEducations.get(i).getStudiedTill() + "</p>" +
                        "<p>" + userEducations.get(i).getCityOrCountry() + "</p>" +
                        "</div>";
            }

            return educlist + list + endeduclist;

        } else {
            return "No Education History Yet";
        }
    }

    public String addUserSkills() {
        String startrow = "<div> <h3>Skills</h3>";
        String list = "";
        String endrow = "</div>";
        if (userSkills.size() > 0) {
            for (int i = 0; i < userSkills.size(); i++) {
                list = list + "<p>" + userSkills.get(i).getSkillName() + "</p>";
            }
            return startrow + list + endrow;
        } else {
            return "No Skills Yet";
        }
    }

    public String addUserProjects() {
        if (userProjects.size() > 0) {
            String projectlist = "<div class= container-fluid> <h3>Projects Completed</h3> <div class= row>";
            String endprojectlist = "</div> </div>";
            String list = "";

            for (int i = 0; i < userProjects.size(); i++) {
                list = list + "<div class=col-lg-5>" +
                        "<h4>" + userProjects.get(i).getProjectName() + "</h4>" +
                        "<p>" + userProjects.get(i).getProjectTasks() + "</p>" +
                        "</div>" +
                        "<div class = col-lg-1></div>";
            }

            return projectlist + list + endprojectlist;

        } else {
            return "No Projects Yet";
        }
    }

    public String addUserAchievements() {
        if (userAchievements.size() > 0) {
            String achlist = "<div> <h3>Achievements</h3>";
            String endachlist = "</div>";
            String list = "";

            for (int i = 0; i < userAchievements.size(); i++) {
                list = list + "<div style=width:90%;>" +
                        "<h5 style=margin-bottom:-5px;>" + userAchievements.get(i).getAchievementName() + "</h5>" +
                        "<p style= font-size:12px;>" + userAchievements.get(i).getAchievementDescription() + "</p>" +
                        "</div>";
            }

            return achlist + list + endachlist;

        } else {
            return "No Achievements Yet";
        }
    }

    public String addStyles() {
        return "<style>" +
                "body {" +
                "width: 100%;" +
                "padding:0;" +
                "margin-left:-20px;" +
                "}" +
                ".container-fluid {" +
                "width: 100%;" +
                "padding-right: 15px;" +
                "padding-left: 15px;" +
                "margin-right: auto;" +
                "margin-left: auto;" +
                "}" +
                ".row {" +
                "display: -ms-flexbox;" +
                "display: flex;" +
                "-ms-flex-wrap: wrap;" +
                "flex-wrap: wrap;" +
                "}" +
                ".col-lg-1 {" +
                "-ms-flex: 0 0 8.333333%;" +
                "flex: 0 0 8.333333%;" +
                "max-width: 8.333333%;" +
                "}" +
                ".col-lg-3 {" +
                "-ms-flex: 0 0 25%;" +
                "flex: 0 0 25%;" +
                "max-width: 25%;" +
                "}" +
                ".col-lg-4 {" +
                "-ms-flex: 0 0 33.333333%;" +
                "flex: 0 0 33.333333%;" +
                "max-width: 33.333333%;" +
                "}" +
                ".col-lg-5 {" +
                "-ms-flex: 0 0 41.666667%;" +
                "flex: 0 0 41.666667%;" +
                "max-width: 41.666667%;" +
                "}" +
                ".col-lg-6 {" +
                "-ms-flex: 0 0 50%;" +
                "flex: 0 0 50%;" +
                "max-width: 50%;" +
                "  }" +
                ".col-lg-7 {" +
                "-ms-flex: 0 0 58.333333%;" +
                "flex: 0 0 58.333333%;" +
                "max-width: 58.333333%;" +
                "}" +
                ".col-lg-8 {" +
                "-ms-flex: 0 0 66.666667%;" +
                "flex: 0 0 66.666667%;" +
                "max-width: 66.666667%;" +
                "}" +
                ".sidebar {" +
                "background-color:#7E00FD;" +
                "color:#fff;" +
                "text-align: left !important;" +
                "padding:15px;" +
                "padding-left:10px;" +
                "height: 1000px !important;" +
                "}" +
                "</style>";
    }

    private void createWebPrintJob(WebView webView) throws Exception {
        try {
            String jobName = getString(R.string.app_name) + " Document";
            PrintAttributes attributes = new PrintAttributes.Builder()
                    .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
                    .setResolution(new PrintAttributes.Resolution("pdf", "pdf", 600, 600))
                    .setMinMargins(PrintAttributes.Margins.NO_MARGINS).build();
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/PDFTest/");
            PdfPrint pdfPrint = new PdfPrint(attributes);
            pdfPrint.print(webView.createPrintDocumentAdapter(jobName), path, "output_" + System.currentTimeMillis() + ".pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 111:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    try {
                        createWebPrintJob(webView);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    // Permission Denied
                    Toast.makeText(getActivity(), "WRITE_EXTERNAL Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
