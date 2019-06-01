package main.java.sound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


class Window extends JFrame {

    private ArrayList<GroupPanel> panels = new ArrayList<>();

    Window(Config conf, Controller control) {

        // set window settings
        this.setTitle("Sound Controller");
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        this.setLayout(new FlowLayout());

        // new panel for each channel group
        for (String command: conf.channelGroup) {
            GroupPanel groupPanel = new GroupPanel(command, conf, control);
            panels.add(groupPanel);
            this.add(groupPanel);
        }

        /*
        // add update button
        JButton update = new JButton("Update");
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                for (int i = 0; i < panels.size(); i++) {
                    panels.get(i).update();
                }
            }
        });
        this.add(update);
        */

        // show window
        this.setSize(450, 300);
        this.setVisible(true);
    }
}
