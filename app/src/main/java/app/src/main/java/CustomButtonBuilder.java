package app.src.main.java;

import javax.swing.*;

import app.src.main.java.mainframe.CustomButton;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomButtonBuilder extends mainframe{
    private String text;
    private boolean isEnabled;
    private Color background;
    private Color foreground;
    private Dimension size;
    private Font font;
    private ActionListener actionListener;

    public CustomButtonBuilder(String text) {
        this.text = text;
        this.isEnabled = true; // Default to enabled
        this.background = new Color(204, 153, 0); // Default background color
        this.foreground = Color.BLACK; // Default foreground color
        this.size = new Dimension(180, 50); // Default button size
        this.font = new Font("SansSerif", Font.BOLD, 15); // Default font
        this.actionListener = null;
    }

    public CustomButtonBuilder setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
        return this;
    }

    public CustomButtonBuilder setBackground(Color background) {
        this.background = background;
        return this;
    }

    public CustomButtonBuilder setForeground(Color foreground) {
        this.foreground = foreground;
        return this;
    }

    public CustomButtonBuilder setSize(Dimension size) {
        this.size = size;
        return this;
    }

    public CustomButtonBuilder setFont(Font font) {
        this.font = font;
        return this;
    }

    public CustomButtonBuilder setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
        return this;
    }

    public CustomButton build() {
        CustomButton button = new CustomButton(text, isEnabled);
        button.setBackground(background);
        button.setForeground(foreground);
        button.setPreferredSize(size);
        button.setFont(font);
        if (actionListener != null) {
            button.addActionListener(actionListener);
        }
        return button;
    }
}

