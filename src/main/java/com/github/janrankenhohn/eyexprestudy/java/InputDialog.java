package com.github.janrankenhohn.eyexprestudy.java;

import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class InputDialog extends DialogWrapper {

    public JTextField textField;
    String labelText;

    public InputDialog(String labelText) {
        super(true); // use current window as parent
        init();
        setTitle(labelText);
        this.labelText = labelText;
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel dialogPanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(this.labelText);
        textField = new JTextField();
        dialogPanel.add(label, BorderLayout.NORTH);
        dialogPanel.add(textField, BorderLayout.CENTER);

        return dialogPanel;
    }
}