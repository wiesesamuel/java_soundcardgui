package main.java.sound;


import javax.swing.*;
import java.io.IOException;

class Controller {

    private int[] percent;
    private Config conf;
    private boolean verbose = true;

    Controller(Config conf) {
        this.conf = conf;
        this.percent = conf.channel;
    }

    void enableVerbose() {
        this.verbose = true;
    }


    void adjust(String command, int id, int value) {

        // single member
        if (id > 0){
            this.percent[id] = addInRange(this.percent[id], value);

            // get values from left and right
            int[] values;
            if (id % 2 == 1) {
                values = new int[]{this.percent[id], this.percent[id + 1]};
            }
            else {
                values = new int[]{this.percent[id - 1], this.percent[id]};
            }

            // update both values
            setShellOutput(getString(command, values));
        }
        else {

            // master
            if (id == 0) {
                this.percent[id] = addInRange(this.percent[id], value);
                setShellOutput(getString(command, this.percent[id]));
            }

            // group master
            else {
                int target = -1 * id;
                int[] targets = new int[]{(target * 2) - 1, target * 2};
                this.percent[targets[0]] = addInRange(this.percent[targets[0]], value);
                this.percent[targets[1]] = addInRange(this.percent[targets[1]], value);
                setShellOutput(getString(command,
                                                new int[]{
                                                    this.percent[targets[0]],
                                                    this.percent[targets[1]]
                                                })
                );
            }
        }
    }

    private int addInRange(int a, int b) {
        if (a + b > 100) return 100;
        if (a + b < 0) return 0;
        return a + b;
    }

    void set(String command, int id, int value) {

        // check value
        if (value >= 0 && value <= 100) {

            // single group member
            if (id > 0){
                this.percent[id] = value;

                // get value from left or right
                int[] values;
                if (id % 2 == 1) {
                    values = new int[]{value, this.percent[id + 1]};
                }
                else {
                    values = new int[]{this.percent[id - 1], value};
                }

                // update both values
                setShellOutput(getString(command, values));
            }
            else {

                // master
                if (id == 0) {
                    this.percent[id] = value;
                    setShellOutput(getString(command, value));
                }

                // group master
                else {
                    int target = -1 * id;
                    int[] targets = new int[]{(target * 2) - 1, target * 2};
                    this.percent[targets[0]] = value;
                    this.percent[targets[1]] = value;
                    setShellOutput(getString(command, value));
                }
            }
        }
    }

    private String getString(String group, int[] values) {
        StringBuilder result = new StringBuilder();

        for (String i : conf.command) {
            result.append(i);
            result.append(" ");
        }

        result.append(group);
        result.append(" ");

        for (int value : values) {
            result.append(value);
            result.append("%,");
        }

        String command = result.substring(0, result.length() -1);
        if (this.verbose)
            System.out.println(command);

        return command;
    }

    private String getString(String group, int value) {
        StringBuilder result = new StringBuilder();

        for (String i : conf.command) {
            result.append(i);
            result.append(" ");
        }

        result.append(group);
        result.append(" ");
        result.append(value);
        result.append("%");

        String command = result.toString();
        if (this.verbose)
            System.out.println(command);

        return command;
    }

    /**
     * use string for shell
     * @param output command for shell
     */
    private static void setShellOutput(String output) {
        try {
            Process proc = Runtime.getRuntime().exec(output);
            proc.waitFor();
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                    "Couldn't start amixer: " + e.toString());
            System.err.println("Couldn't start amixer: " + e.toString());
        }
        catch (InterruptedException e) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                    "Interrupted starting amixer: " + e.toString());
            System.err.println("Interrupted starting amixer: " + e.toString());
        }
    }

    int getCurrentValue(int id) {
        // single channel
        if (id >= 0)
            return this.percent[id];
        // master channel, return highest value from both member
        else {
            int ID = -1 * id;
            if (this.percent[ID] > this.percent[ID + 1])
                return this.percent[ID];
            else
                return this.percent[ID + 1];
        }
    }

    void updateOutput(String commmand, int id) {
        adjust(commmand, id, 0);
    }
}
