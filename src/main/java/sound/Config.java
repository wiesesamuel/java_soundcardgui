package main.java.sound;

public class Config {

    // fields
    String helpPage;
    String[] command;
    int[] buttonValues;
    int[] channel;
    String[] channelGroup;
    String[][] channelGroupMembers;
    int[][] channelGroupMapId;
    boolean useButtons;

    Config() {
        this.helpPage = "parameter usage:\n" +
                "-h            --help       show this message\n" +
                "-v                         enable verbose\n" +
                "-nb                        disable buttons\n" +
                "\n" +
                "-s [v0, v1, ...]           set button values, adds negativ values\n" +
                "-ss [v0, v1, ...]          set only the defined button values\n";
        this.command = new String[]{"amixer", "-c 2", "set"};
        //this.command = new String[]{"/bin/bash", "-c", "amixer", "-q", "-D", "pulse", "sset", "Master"};

        // for each Group a new panelGroup will be created
        // depending from the GroupMapId GroupMember parameters are needed
        this.channelGroup = new String[]{"Master", "Front", "Surround", "LFE"};//, "Center"};
        this.channelGroupMembers = new String[][]{
                {"Master"},
                {"Master"},//, "Left", "Right"},
                {"Master"},//, "Left", "Right"},
                {"Master"},
                {"Master"},
        };
        // for every group master channel (negative value) its needed to add 2 channels below
        this.channelGroupMapId = new int[][]{{0}, {-1}, {-2}, {-3}, {-4}};
        //                        0  1  2  3   4   5  6 7  8
        this.channel = new int[] {75,80,80,65,65,90,0,90,0};

        this.buttonValues = new int[] {5, 2, -2, -5};
        this.useButtons = false;

    }
}
