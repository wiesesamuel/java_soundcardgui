package main.java.sound;

import javafx.scene.control.Slider;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

class ChannelPanel extends JPanel {
    private Controller controller;
    private String command;
    private int id;
    private JSlider slider;

    ChannelPanel(Config conf, Controller control, String command, String name, int id) {
        this.controller = control;
        this.command = command;
        this.id = id;

        // set Panel Size
        int sizeY = 1;
        if (conf.useButtons) sizeY++;
        this.setLayout(new GridLayout(0, sizeY));

        // create slide
        JSlider slide = new JSlider(0,100,25);
        slide.setOrientation(JSlider.VERTICAL);
        slide.setPaintLabels(true);
        slide.setSnapToTicks(false);
        slide.setMajorTickSpacing(20);
        slide.setMinorTickSpacing(5);
        slide.setPaintTicks(true);

        this.slider = slide;


        // set slide action
        slide.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                control.set(command, id, slide.getValue());
            }
        });

        if (conf.useButtons) {
            ArrayList<JButton> buttons = new ArrayList<>();
            JPanel buttonGroup = new JPanel();
            buttonGroup.setLayout(new GridLayout(0, 1));

            // create buttons
            for (int step : conf.buttonValues) {
                if (step > 0)
                    buttons.add(new JButton("+" + String.valueOf(step)));
                else
                    buttons.add(new JButton(String.valueOf(step)));
            }

            // set button action
            int i = 0;
            for (JButton button : buttons) {
                final int finalV = conf.buttonValues[i];
                button.addActionListener(e -> {
                    control.adjust(command, id, finalV);

                    // update slide
                    int a = slide.getValue();
                    if (a + finalV > 100) a = 100;
                    else if (a + finalV < 0) a = 0;
                    else a += finalV;
                    slide.setValue(a);
                });
                buttonGroup.add(button);
                i++;
            }
            // add content to panel
            this.add(buttonGroup);
        }

        // set border title
        TitledBorder border = new TitledBorder(name);
        border.setTitleJustification(TitledBorder.CENTER);
        border.setTitlePosition(TitledBorder.TOP);
        this.setBorder(border);

        // update slide value and output
        slide.setValue(control.getCurrentValue(id));
        control.updateOutput(command, id);

        this.add(slide);
    }

    public void update() {
        this.controller.set(command, id, slider.getValue());
    }

    private int addInRange(int a, int b) {
        if (a + b > 100) return 100;
        if (a + b < 0) return 0;
        return a + b;
    }
}
