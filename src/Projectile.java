/**
 * @author Aditya Bankoti
 * @date 9/24/2023
 * @description a program that creates a GUI to display the projectile path based off of adjustable parameters using sliders.
 * 9/25/2023: removed redundant pair class
 */

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Hashtable;

public class Projectile {
    // store velocity, angle, steps, secondsPerStep, and gravity as double holders
    // so later on other classes/funcs can change their values
    DoubleHolder velocity, angle, steps, secondsPerStep, gravity;
    // store the drawing panel
    DrawingPanel dp;
    // the jpanel for the gui
    JPanel panel;
    // width and height of the window
    int WIDTH, HEIGHT;

    // projectile class
    public Projectile(int width, int height) {
        // set width and height
        WIDTH = width;
        HEIGHT = height;

        // update initial values
        velocity = new DoubleHolder(30.0);
        angle = new DoubleHolder(50.0);
        steps = new DoubleHolder(10.0);
        secondsPerStep = new DoubleHolder(1.0);
        gravity = new DoubleHolder(-9.8);

        // setup drawing panel
        dp = new DrawingPanel(WIDTH, HEIGHT);

        // setup a jpanel for the gui
        panel = new JPanel();

        // setup initial dots
        drawDots();

        // create the gui
        createGUI();
    }

    // draws the dots with respective positions given initial values
    public void drawDots() {
        // initial velocities on different axis
        double xiVelocity = Math.cos(Math.toRadians(angle.getValue())) * velocity.getValue();
        double yiVelocuty = Math.sin(Math.toRadians(angle.getValue())) * velocity.getValue();

        // get the graphics of the display panel
        Graphics g = dp.getGraphics();

        // clear the window
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // store the spots of the dots in an array
        ArrayList<Point> spots = new ArrayList<Point>();

        // iterate through all the steps
        for (int i = 0; i < steps.getValue(); i++) {
            // get distance of each respective axis
            double x = getDistance(xiVelocity, 0, i * secondsPerStep.getValue());
            double y = getDistance(yiVelocuty, gravity.getValue(), i * secondsPerStep.getValue());

            // set color of dots to black
            g.setColor(Color.BLACK);
            // set the dot
            g.fillOval((int) x, HEIGHT - (int) y, 5, 5);
            // add position to dot into the array
            spots.add(new Point((int) x, HEIGHT - (int) y));
        }

        // iterate through the dot positions
        for (int i = 0; i < spots.size() - 1; i++) {
            // get the pos of current and next dot
            Point at = spots.get(i);
            Point next = spots.get(i + 1);
            // draw a line through current and next dot
            g.drawLine((int) at.getX(), (int) at.getY(), (int) next.getX(), (int) next.getY());
        }
    }

    // create the GUI for the sliders
    public void createGUI() {
        // create a new frame
        JFrame f = new JFrame();

        // create sliders for each changeable parameter
        createSlider(this, velocity, "Velocity", 0, 100);
        createSlider(this, angle, "Angle", 0, 90);
        createSlider(this, steps, "Steps", 0, 100);
        createSlider(this, gravity, "Gravity", -25, 25);

        // add panel
        f.add(panel);
        // pack everything
        f.pack();
        // show the frame
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    public static void updateValue(Double v1, double v2) {
        v1 = v2;
    }

    // creates a slider
    public static JSlider createSlider(Projectile c, DoubleHolder toChange, String title, int min, int max) {
        // get the panel from projectile class
        JPanel panel = c.panel;
        // create a slider
        JSlider slider = new JSlider(JSlider.HORIZONTAL, min, max, (int) toChange.getValue());
        // create a textfield
        // JLabel textField = new JLabel();

        // create a listener for when the slider changes values
        slider.addChangeListener(e -> {
            // get the slider
            JSlider source = (JSlider) e.getSource();
            // set the text of the slider to the new value
            // textField.setText("" + source.getValue());
            // change the value of the doubleholder to the new value`
            toChange.setValue(source.getValue());
//            toChange = source.getValue();
//            toChange = source.getValue();
            // re-draw the dots
            c.drawDots();
        });

        // create a titledborder
        TitledBorder bf = BorderFactory.createTitledBorder(title);
        // set the border for label and slider
        slider.setBorder(bf);
        // textField.setBorder(bf);

        // create hashtable for labelTable to set min and max values
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(new Integer(min), new JLabel(String.valueOf(min)));
        labelTable.put(new Integer(max), new JLabel(String.valueOf(max)));

        // set the label of the slider to the slider label
        slider.setLabelTable(labelTable);

        // set the major and minor ticker spacing
        // this just changes the interval in which the different ticks show
        slider.setMajorTickSpacing(max / 10);
        slider.setMinorTickSpacing(max / 5);

        // display the ticks
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        // add the slider and the label to the panel
        panel.add(slider);
        // panel.add(textField);
        return slider;
    }

    // gets the distance using physics equations
    public static double getDistance(double velocity, double acceleration, double time) {
        // y = v * t + 1/2 * a * t^2
        return velocity * time + (0.5) * acceleration * Math.pow(time, 2);
    }

    public static void main(String[] args) {
        int WIDTH = 420, HEIGHT = 220;
        new Projectile(WIDTH, HEIGHT);
    }
}
