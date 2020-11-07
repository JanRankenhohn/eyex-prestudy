package com.github.janrankenhohn.eyexprestudy.java;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.IdeFrame;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.pom.Navigatable;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import ur.eyex.intellij.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StartSessionAction extends AnAction implements KeyListener {

    private boolean isEnabled = false;
    static int sessionId = 0;
    private boolean sessionRunning = false;
    private long fixationStartTime = 0;
    private ApiHost apiHost;
    public static long startTime;
    Project project;
    Editor editor;
    public static String participantId;
    public static int[] trialOrder;
    static ArrayList<String[]> dataLinesForCode;
    static ArrayList<String[]> dataLinesForUI;

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {

        if(!sessionRunning){
            project = e.getDataContext().getData(PlatformDataKeys.PROJECT);
            editor = e.getRequiredData(CommonDataKeys.EDITOR);

            ApiHost apiHost = new ApiHost(Constants.Apis.TOBIIPRO);

            // Start receiving Gaze Data
            apiHost.startSession(project, Constants.GazeDataTypes.FIXATIONS);

            // Handle Gaze Point Data Stream
            GazeDataHandler.addGazePointDataListener(new GazePointDataListener() {
                @Override
                public void gazePointDataReceived() {
                    System.out.println("GazePoint at X: " + GazeData.X_Median + " and Y: " + GazeData.Y_Median);
                }
            });

            // Handle Fixation Data Stream
            GazeDataHandler.addFixationDataListener(new FixationDataListener() {
                @Override
                public void fixationDataReceived() {
                    System.out.println("Fixation at X: " + FixationData.X_Median + " and Y: " + FixationData.Y_Median);
                }
            });

            // Handle UI Element Fixation Data Stream
            GazeDataHandler.addUIFixationListener(new UIFixationListener() {
                @Override
                public void uiElementFixated(Component c) {
                    Component fixatedUIElement = c;
                }
            });

            // Handle Code Element Fixation Data Stream
            GazeDataHandler.addCodeFixationListener(new CodeFixationListener() {
                @Override
                public void codeElementFixated(CodeElement e) {
                    CodeElement fixatedCodeElement = e;
                }
            });

            // save data for csv
            dataLinesForCode = new ArrayList<>();
            dataLinesForUI  = new ArrayList<>();
            dataLinesForCode.add(new String[]{"Participant", "Name", "Timestamp", "Duration"});
            dataLinesForUI.add(new String[]{"Participant", "Name", "Timestamp"});

            // add start timestamp
            startTime = System.currentTimeMillis();
            dataLinesForUI.add(new String[]{participantId, "FLAG_SESSIONSTART", Long.toString(0)});
            dataLinesForCode.add(new String[]{participantId, "FLAG_SESSIONSTART", Long.toString(0)});

            // only pre-study:
            /*if(trialOrder[sessionId] == 1){
                ArrayList<CodeElement> codeElements = new ArrayList<>();
                codeElements.add(new CodeElement(122,134, 1, "variable_isOn", editor));
                codeElements.add(new CodeElement(195,211, 1, "function_turnOff", editor));
                for(CodeElement ce : codeElements){

                    GazeDataHandler.addCodeElement(ce);
                }
            }*/

            // For study: Add Code Elements and editor for Gaze Check
            if(trialOrder[sessionId] == 1){
                ArrayList<CodeElement> codeElements = new ArrayList<>();
                codeElements.add(new CodeElement(129,142, 1, "1", editor));
                codeElements.add(new CodeElement(143,168, 1, "2", editor));
                codeElements.add(new CodeElement(170,194, 1, "4", editor));
                codeElements.add(new CodeElement(199,227, 1, "5", editor));
                codeElements.add(new CodeElement(236,247, 1, "6", editor));
                codeElements.add(new CodeElement(256,264, 1, "7", editor));
                codeElements.add(new CodeElement(274,280, 1, "9", editor));
                codeElements.add(new CodeElement(289,304, 1, "10", editor));
                codeElements.add(new CodeElement(317,331, 1, "11", editor));
                codeElements.add(new CodeElement(344,354, 1, "12", editor));
                codeElements.add(new CodeElement(363,364, 1, "13", editor));
                codeElements.add(new CodeElement(373,384, 1, "14", editor));
                codeElements.add(new CodeElement(389,390, 1, "15", editor));
                codeElements.add(new CodeElement(396,436, 1, "17", editor));
                codeElements.add(new CodeElement(445,460, 1, "18", editor));
                codeElements.add(new CodeElement(470,510, 1, "20", editor));
                codeElements.add(new CodeElement(519,563, 1, "21", editor));
                codeElements.add(new CodeElement(572,593, 1, "22", editor));
                codeElements.add(new CodeElement(602,626, 1, "23", editor));
                codeElements.add(new CodeElement(631,632, 1, "24", editor));
                codeElements.add(new CodeElement(633,634, 1, "25", editor));
                for(CodeElement ce : codeElements){

                    GazeDataHandler.addCodeElement(ce);
                }
            }
            if(trialOrder[sessionId] == 2){
                ArrayList<CodeElement> codeElements = new ArrayList<>();
                codeElements.add(new CodeElement(108,121, 1, "1", editor));
                codeElements.add(new CodeElement(123,148, 1, "3", editor));
                codeElements.add(new CodeElement(150,169, 1, "5", editor));
                codeElements.add(new CodeElement(174,214, 1, "6", editor));
                codeElements.add(new CodeElement(223,241, 1, "7", editor));
                codeElements.add(new CodeElement(251,257, 1, "9", editor));
                codeElements.add(new CodeElement(266,279, 1, "10", editor));
                codeElements.add(new CodeElement(292,332, 1, "11", editor));
                codeElements.add(new CodeElement(345,389, 1, "12", editor));
                codeElements.add(new CodeElement(402,420, 1, "13", editor));
                codeElements.add(new CodeElement(433,443, 1, "14", editor));
                codeElements.add(new CodeElement(452,453, 1, "15", editor));
                codeElements.add(new CodeElement(463,497, 1, "17", editor));
                codeElements.add(new CodeElement(502,503, 1, "18", editor));
                codeElements.add(new CodeElement(504,505, 1, "19", editor));
                for(CodeElement ce : codeElements){
                    GazeDataHandler.addCodeElement(ce);
                }
            }
            if(trialOrder[sessionId] == 3){
                ArrayList<CodeElement> codeElements = new ArrayList<>();
                codeElements.add(new CodeElement(81,94, 1, "1", editor));
                codeElements.add(new CodeElement(96,121, 1, "3", editor));
                codeElements.add(new CodeElement(123,146, 1, "5", editor));
                codeElements.add(new CodeElement(151,191, 1, "6", editor));
                codeElements.add(new CodeElement(200,218, 1, "7", editor));
                codeElements.add(new CodeElement(227,238, 1, "8", editor));
                codeElements.add(new CodeElement(248,256, 1, "10", editor));
                codeElements.add(new CodeElement(266,272, 1, "12", editor));
                codeElements.add(new CodeElement(281,294, 1, "13", editor));
                codeElements.add(new CodeElement(307,347, 1, "14", editor));
                codeElements.add(new CodeElement(360,404, 1, "15", editor));
                codeElements.add(new CodeElement(417,435, 1, "16", editor));
                codeElements.add(new CodeElement(448,458, 1, "17", editor));
                codeElements.add(new CodeElement(467,468, 1, "18", editor));
                codeElements.add(new CodeElement(478,492, 1, "20", editor));
                codeElements.add(new CodeElement(501,539, 1, "21", editor));
                codeElements.add(new CodeElement(544,545, 1, "22", editor));
                codeElements.add(new CodeElement(546,547, 1, "23", editor));
                for(CodeElement ce : codeElements){
                    GazeDataHandler.addCodeElement(ce);
                }
            }
            if(trialOrder[sessionId] == 4){
                ArrayList<CodeElement> codeElements = new ArrayList<>();
                codeElements.add(new CodeElement(101,113, 1, "1", editor));
                codeElements.add(new CodeElement(116,141, 1, "3", editor));
                codeElements.add(new CodeElement(143,163, 1, "5", editor));
                codeElements.add(new CodeElement(168,208, 1, "6", editor));
                codeElements.add(new CodeElement(217,241, 1, "7", editor));
                codeElements.add(new CodeElement(251,291, 1, "9", editor));
                codeElements.add(new CodeElement(300,342, 1, "10", editor));
                codeElements.add(new CodeElement(352,358, 1, "12", editor));
                codeElements.add(new CodeElement(367,382, 1, "13", editor));
                codeElements.add(new CodeElement(395,411, 1, "14", editor));
                codeElements.add(new CodeElement(428,440, 1, "15", editor));
                codeElements.add(new CodeElement(453,463, 1, "16", editor));
                codeElements.add(new CodeElement(472,473, 1, "17", editor));
                codeElements.add(new CodeElement(483,499, 1, "19", editor));
                codeElements.add(new CodeElement(512,595, 1, "20", editor));
                codeElements.add(new CodeElement(568,572, 1, "21", editor));
                codeElements.add(new CodeElement(585,636, 1, "22", editor));
                codeElements.add(new CodeElement(641,642, 1, "23", editor));
                codeElements.add(new CodeElement(643,644, 1, "24", editor));
                for(CodeElement ce : codeElements){
                    GazeDataHandler.addCodeElement(ce);
                }
            }

            // Handle Code Element Fixation Counts
            HashMap<Object, Integer> codeElements = new HashMap<>();
            GazeDataHandler.addCodeFixationCountListener(new CodeFixationCountListener() {
                @Override
                public void codeElementFixationCounted(CodeElement e, FixationDataEventType type, long timestamp) {
                    if(type.equals(FixationDataEventType.Begin)){
                        fixationStartTime = timestamp - startTime;
                        if(!codeElements.containsKey(e)){
                            codeElements.put(e, 1);
                        }
                        else{
                            codeElements.replace(e, codeElements.get(e) + 1);
                        }
                    }
                    else if(type.equals(FixationDataEventType.End)){
                        // compute fixation time
                        long fixationDuration = (timestamp - startTime) - fixationStartTime;
                        dataLinesForCode.add(new String[]{participantId, e.getId(), Long.toString(fixationStartTime), Long.toString(fixationDuration) });
                    }
                }
            });

            GazeDataHandler.addUIFixationCountListener(new UIFixationCountListener() {
                @Override
                public void uiElementFixationCounted(Component component, long timestamp) {
                    long unixTime = timestamp - startTime;
                    dataLinesForUI.add(new String[]{participantId, component.getClass().getSimpleName(), Long.toString(unixTime)});
                }
            });

            e.getPresentation().setText("Stop Session");

            sessionRunning = true;

            update(e);
        }
        else{
            // add end timestamp
            long unixTime = System.currentTimeMillis() - startTime;
            dataLinesForUI.add(new String[]{participantId, "FLAG_SESSIONEND", Long.toString(unixTime)});
            dataLinesForCode.add(new String[]{participantId, "FLAG_SESSIONEND", Long.toString(unixTime)});

            // write to csv
            Utils.writeToCSV("gazedata_ui_p" + participantId + "_t" + trialOrder[sessionId] + ".csv", dataLinesForUI);
            Utils.writeToCSV("gazedata_code_p" + participantId + "_t" + trialOrder[sessionId] + ".csv", dataLinesForCode);
            App.apiHost.stopSession();

            sessionId++;

            //apiHost.unloadApi();
            e.getPresentation().setText("Start Session");
            sessionRunning = false;

            update(e);
        }

    }

    public void setEnabled(boolean enabled){
        this.isEnabled = enabled;
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        super.update(e);
        Presentation presentation = e.getPresentation();
        if(isEnabled){
            presentation.setEnabled(true);
        }
        else{
            presentation.setEnabled(false);
        }
    }

    private void navigateToFile(String path){
        VirtualFile virtualFile = project.getBaseDir().findFileByRelativePath(path);
        if (virtualFile != null) {
            OpenFileDescriptor openFileDescriptor =
                    new OpenFileDescriptor(project, virtualFile, 1, 1);
            openFileDescriptor.navigate(true);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int d = 0;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
