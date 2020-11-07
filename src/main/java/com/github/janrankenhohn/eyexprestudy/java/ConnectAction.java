package com.github.janrankenhohn.eyexprestudy.java;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;
import ur.eyex.intellij.ApiHost;
import ur.eyex.intellij.Constants;

public class ConnectAction extends AnAction{

    private boolean isConnected = false;

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        if(!isConnected){

            App.apiHost = new ApiHost(Constants.Apis.TOBIIPRO);

            AnAction sessionAction = ActionManager.getInstance().getAction("PreStudy.Actions.StartSessionAction");
            sessionAction = (StartSessionAction)sessionAction;

            ((StartSessionAction) sessionAction).setEnabled(true);

            isConnected = true;

            Notifier notifier = new Notifier();
            notifier.notify("Successfully connected to the EyeTracker API.");

            e.getPresentation().setText("Disconnect");
        }
        else{
            App.apiHost.unloadApi();

            isConnected = false;

            Notifier notifier = new Notifier();
            notifier.notify("Disconnected from the EyeTracker API.");

            e.getPresentation().setText("Connect");
        }
    }
}
