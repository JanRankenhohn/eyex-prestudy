package com.github.janrankenhohn.eyexprestudy.java;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SetParticipantAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        InputDialog inputDialog = new InputDialog("Participant ID");
        inputDialog.show();

        StartSessionAction sessionAction = (StartSessionAction)ActionManager.getInstance().getAction("PreStudy.Actions.StartSessionAction");
        sessionAction.participantId = inputDialog.textField.getText();

        ArrayList<int[]> trialOrders = new ArrayList<>();
        trialOrders.add(new int[]{1,2,3,4});
        trialOrders.add(new int[]{1,2,4,3});
        trialOrders.add(new int[]{1,3,2,4});
        trialOrders.add(new int[]{1,3,4,2});
        trialOrders.add(new int[]{2,1,3,4});
        trialOrders.add(new int[]{2,1,4,3});
        trialOrders.add(new int[]{2,3,1,4});
        trialOrders.add(new int[]{2,3,4,1});
        trialOrders.add(new int[]{3,1,2,4});
        trialOrders.add(new int[]{3,2,1,4});
        trialOrders.add(new int[]{3,4,1,2});
        trialOrders.add(new int[]{4,1,2,3});
        trialOrders.add(new int[]{4,1,3,2});
        trialOrders.add(new int[]{4,2,3,1});
        trialOrders.add(new int[]{4,2,1,3});

        sessionAction.trialOrder = trialOrders.get(Integer.parseInt(inputDialog.textField.getText())-1);
    }
}
