package com.github.janrankenhohn.eyexprestudy.java;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

public class SwitchFileAction extends AnAction {

    public static Project project;

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {

        project = e.getProject();

        StartSessionAction sessionAction = (StartSessionAction) ActionManager.getInstance().getAction("PreStudy.Actions.StartSessionAction");
        int trial = sessionAction.trialOrder[sessionAction.sessionId];

        switch(trial){
            case 1:
                navigateToFile("src/main/Accumulate.txt");
                break;
            case 2:
                navigateToFile("src/main/Sum5.txt");
                break;
            case 3:
                navigateToFile("src/main/Average5.txt");
                break;
            case 4:
                navigateToFile("src/main/Prime.txt");
        }
        update(e);
    }

    private void navigateToFile(String path){
        VirtualFile virtualFile = project.getBaseDir().findFileByRelativePath(path);
        if (virtualFile != null) {
            OpenFileDescriptor openFileDescriptor =
                    new OpenFileDescriptor(project, virtualFile, 1, 1);
            openFileDescriptor.navigate(true);
        }
    }
}
