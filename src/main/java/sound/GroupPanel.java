package main.java.sound;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;

class GroupPanel extends JPanel {
    private ArrayList<ChannelPanel> panels = new ArrayList<>();

    GroupPanel(String command, Config conf, Controller control){
        this.setLayout(new FlowLayout());

        // get ID
        int id = 0;
        for(int i = 0; i < conf.channelGroup.length; i++){
            if (conf.channelGroup[i].equals(command)) {
                id = i;
                break;
            }
        }

        int count = 0;
        for (int ID: conf.channelGroupMapId[id]){
            panels.add(new ChannelPanel(conf, control, command, conf.channelGroupMembers[id][count], ID));
            this.add(panels.get(count));
            count++;
        }

        // set border title
        TitledBorder border = new TitledBorder(command);
        border.setTitleJustification(TitledBorder.CENTER);
        border.setTitlePosition(TitledBorder.TOP);
        this.setBorder(border);
    }

    public void update() {
        for (ChannelPanel channelPanel: this.panels) {
            channelPanel.update();
        }
    }

}
