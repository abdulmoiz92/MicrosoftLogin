package com.example.microsftlogin.PdfWebView;

import android.os.Bundle;

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

import com.example.microsftlogin.AboutUserDatabase.AboutUser;
import com.example.microsftlogin.R;
import com.example.microsftlogin.UserAchievementsDatabase.UserAchievement;
import com.example.microsftlogin.UserDatabase.UserViewModel;
import com.example.microsftlogin.UserEducationDatabase.UserEducation;
import com.example.microsftlogin.UserExperienceDatabase.UserExperience;
import com.example.microsftlogin.UserProjectsDatabase.UserProject;
import com.example.microsftlogin.UserSkillsDatabase.UserSkill;
import com.example.microsftlogin.Utils.SharedPrefrenceUtil;

import java.io.File;
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
                "<style>" +
                "h1.heading {" +
                "text-align:center;" +
                "}" +
                "div.experiences{" +
                "width:70%;" +
                "}" +
                "hr {" +
                "color:#000;" +
                "}" +
                "ul {" +
                "list-style-type:none;" +
                "margin:0;" +
                "padding:0;" +
                "}" +
                "div.row {" +
                "display:flex;" +
                "width: 90%;" +
                "flex-wrap: wrap;" +
                "}" +
                "div.column {" +
                "flex: 30%;" +
                "}" +
                "h3.skill {" +
                "background:#7E00FD;" +
                "text-align:center;" +
                "border: none;" +
                "border-radius: 20px;" +
                "text-decoration: none;" +
                "padding: 10px;" +
                "margin-left: 10px;" +
                "margin-right:10px;" +
                "color:#fff;" +
                "}" +
                "</style>" +

                "<h1 class=heading>Your CV</h1>" +
                "</br>" +
                addAboutUser() + "</br> <hr> </br>" +

                "<div class=experiences>" +
                "<h3> Work Experiences </h3> <hr> </br>" +
                addUserExperience() +
                "</div>" + "</br> </br>" +

                "<div class=experiences>" +
                "<h3> Education History </h3> <hr> </br>" +
                addUserEducation() +
                "</div>" + "</br> </br>" +

                "<div>" +
                "<h3> My Skills </h3> <hr> </br>" +
                addUserSkills() +
                "</div>" + "<br/> <br/>" +

                "<div class=experiences>" +
                "<h3> Projects Completed </h3> <hr> </br>" +
                addUserProjects() +
                "</div>" + "</br> </br>" +

                "<div class=experiences>" +
                "<h3> My Achievements </h3> <hr> </br>" +
                addUserAchievements() +
                "</div>" + "</br> </br>" +

                "</body></html>";

        webView.loadDataWithBaseURL(null, htmlDocument, "text/html", "UTF-8", null);

        printButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createWebPrintJob(webView);

            }
        });
        return view;
    }

    public String addAboutUser() {
        if (userViewModel.findUserWithAbout(user_id).get(0).getAboutUserList().size() > 0) {
            return "<h2>" + aboutUser.getName() + "</h2>" +
                    "<h4>" + aboutUser.getEmail() + "</h4>" +
                    "<h4>" + aboutUser.getPhone() + "</h4>" +
                    "<h4>" + aboutUser.getAddress() + "</h4>" +
                    "<h4>" + aboutUser.getEducationDegree() + "</h4>" +
                    "<p>" + aboutUser.getDescription() + "</h4>";
        } else {
            return "No User Information";
        }
    }

    public String addUserExperience() {
        if (userExperiences.size() > 0) {
            String experlist = "<ul>";
            String endexperlist = "</ul>";
            String list = "";

            for (int i = 0; i < userExperiences.size(); i++) {
                list = list + "<li>" + "<h4>" + userExperiences.get(i).getJobTitle() + "</h4>" +
                        "<h4>" + userExperiences.get(i).getCompanyName() + "</h4>" +
                        "<span>" + userExperiences.get(i).getWorkedFrom() + " - " + userExperiences.get(i).getWorkedTill() +
                        "</span>" +
                        "<p>" + userExperiences.get(i).getCityOrCountry() + "</p>" +
                        "<p>" + userExperiences.get(i).getTasksPerformed() + "</p> <hr>";
            }

            return experlist + list + endexperlist;

        } else {
            return "No Work Experiences Yet";
        }
    }

    public String addUserEducation() {
        if (userEducations.size() > 0) {
            String educlist = "<ul>";
            String endeduclist = "</ul>";
            String list = "";

            for (int i = 0; i < userExperiences.size(); i++) {
                list = list + "<li>" + "<h4>" + userEducations.get(i).getCourseName() + "</h4>" +
                        "<h4>" + userEducations.get(i).getSchoolOrWebsite() + "</h4>" +
                        "<span>" + userEducations.get(i).getStudiedFrom() + " - " + userEducations.get(i).getStudiedTill() +
                        "</span>" +
                        "<p>" + userEducations.get(i).getCityOrCountry() + "</p>" +
                        "<p>" + userEducations.get(i).getSubcoursesOrTasks() + "</p> <hr>";
            }

            return educlist + list + endeduclist;

        } else {
            return "No Education History Yet";
        }
    }

    public String addUserSkills() {
        String startrow = "<div class=row>";
        String list = "";
        String endrow = "</div>";
        if (userSkills.size() > 0) {
            for (int i = 0; i < userSkills.size(); i++) {
                list = list + "<div class=column>" + "<h3 class=skill>" + userSkills.get(i).getSkillName() + "</h3>" + "</div>";
            }
            return startrow + list + endrow;
        } else {
            return "No Skills Yet";
        }
    }

    public String addUserProjects() {
        if (userProjects.size() > 0) {
            String projectlist = "<ul>";
            String endprojectlist = "</ul>";
            String list = "";

            for (int i = 0; i < userExperiences.size(); i++) {
                list = list + "<li>" + "<h4>" + userProjects.get(i).getProjectName() + "</h4>" +
                        "<p>" + userProjects.get(i).getProjectTasks() + "</p> <hr>";
            }

            return projectlist + list + endprojectlist;

        } else {
            return "No Projects Yet";
        }
    }

    public String addUserAchievements() {
        if (userAchievements.size() > 0) {
            String achlist = "<ul>";
            String endachlist = "</ul>";
            String list = "";

            for (int i = 0; i < userAchievements.size(); i++) {
                list = list + "<li>" + "<h4>" + userAchievements.get(i).getAchievementName() + "</h4>" +
                        "<p>" + userAchievements.get(i).getAchievementDescription() + "</p> <hr>";
            }

            return achlist + list + endachlist;

        } else {
            return "No Achievements Yet";
        }
    }


    private void createWebPrintJob(WebView webView) {
        String jobName = getString(R.string.app_name) + " Document";
        PrintAttributes attributes = new PrintAttributes.Builder()
                .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
                .setResolution(new PrintAttributes.Resolution("pdf", "pdf", 600, 600))
                .setMinMargins(PrintAttributes.Margins.NO_MARGINS).build();
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/PDFTest/");
        PdfPrint pdfPrint = new PdfPrint(getActivity(),attributes);
        pdfPrint.print(webView.createPrintDocumentAdapter(jobName), path, "output_" + System.currentTimeMillis() + ".pdf");
    }
}
