package main.java.sound;


import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

public class Main {

    public void writeXML(Object obj) {
        try {
            XMLEncoder e = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("settings.xml")));
            e.writeObject(obj);
            e.close();
        } catch (Exception e) {
        }
    }

    public Object readXML() {
        try {
            XMLDecoder d = new XMLDecoder(new BufferedInputStream(new FileInputStream("settings.xml")));
            Object result = d.readObject();
            d.close();
            return result;
        } catch (Exception e) {
        }
        return null;
    }

    public static void main(String[] args) {

        // load config
        Config conf = new Config();
        Controller control = new Controller(conf);

        // set custom parameters
        String prev = "";
        try {
            for (String arg : args) {

                // set paramter for previous command
                if (prev.length() > 0) {

                    // custom channel
                    switch (prev) {
                        case "c":
                            System.out.println("c not supported");
                            break;
                        //conf.channel = Integer.valueOf(arg);

                        // custom button values
                        case "ss": {
                            String[] valuesS = arg.split(",");
                            int[] valuesI = new int[valuesS.length];
                            for (int i = 0; i < valuesS.length; i++)
                                valuesI[i] = Integer.valueOf(valuesS[i]);
                            conf.buttonValues = valuesI;

                            // custom button values and add negative values
                            break;
                        }
                        case "s": {
                            String[] valuesS = arg.split(",");
                            int l = valuesS.length;
                            int[] valuesI = new int[l * 2];
                            for (int i = 0; i < l; i++)
                                valuesI[i] = Integer.valueOf(valuesS[i]);
                            int c = 0;
                            for (int i = l * 2; i > l; i--) {
                                valuesI[i - 1] = valuesI[c] * (-1);
                                c++;
                            }
                            conf.buttonValues = valuesI;

                            // custom start volume value
                            break;
                        }
                    }
                }
                prev = "";
                switch (arg) {
                    case "--help":
                    case "-h":
                        System.out.println(conf.helpPage);
                        break;
                    case "-v":
                        control.enableVerbose();
                        break;
                    case "--channel":
                    case "-c":
                        prev = "c";
                        break;
                    case "--steps":
                    case "-s":
                        prev = "s";
                        break;
                    case "--save-steps":
                    case "-ss":
                        prev = "ss";
                        break;
                    case "-nb":
                        conf.useButtons = false;
                        break;
                }

            }
        } catch (Exception e) {
            System.out.println("Wrong usage, for right usage look at the help page.");
            System.out.println(conf.helpPage);
        }

        // make window and set defaults
        new Window(conf, control);
        //control.update();
    }
}
