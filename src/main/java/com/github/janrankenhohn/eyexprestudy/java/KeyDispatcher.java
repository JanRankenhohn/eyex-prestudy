package com.github.janrankenhohn.eyexprestudy.java;

import java.awt.*;
import java.awt.event.KeyEvent;

//Custom dispatcher
class KeyDispatcher implements KeyEventDispatcher {
    public boolean dispatchKeyEvent(KeyEvent e) {
        if(e.getKeyCode() == 118) {
            long unixTime = System.currentTimeMillis() - StartSessionAction.startTime;
            StartSessionAction.dataLinesForCode.add(new String[]{StartSessionAction.participantId, "FLAG_ENDTASK", Long.toString(unixTime)});
            StartSessionAction.dataLinesForUI.add(new String[]{StartSessionAction.participantId, "FLAG_ENDTASK", Long.toString(unixTime)});
        }

        //Allow the event to be redispatched
        return false;
    }
}