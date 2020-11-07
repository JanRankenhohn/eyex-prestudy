package com.github.janrankenhohn.eyexprestudy.java;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class LogErrorFindingAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        StartSessionAction sessionAction = (StartSessionAction) ActionManager.getInstance().getAction("PreStudy.Actions.StartSessionAction");

        long unixTime = System.currentTimeMillis() - sessionAction.startTime;
        ArrayList<String[]> log = new ArrayList<>();
        log.add(new String[]{"Participant " + sessionAction.participantId + " found error in sample " + sessionAction.trialOrder[sessionAction.sessionId] + " at " + unixTime});

        Utils.writeToCSV("found_error_log_" + sessionAction.participantId + "_" + sessionAction.trialOrder[sessionAction.sessionId] + ".txt", log);
    }
}
