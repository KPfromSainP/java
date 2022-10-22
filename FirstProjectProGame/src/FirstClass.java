import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class FirstClass extends JComponent implements Serializable {
    JFrame frame;
    NewSlowCircle newSlowCircle;    //PANEL, WHERE APPEARS YOUR CIRcLE
    JButton button2Move;                //BUTTON FOR MOVE (FIRST CLICK - ACTIVE, SECOND CLICK - PASSIVE)
    JButton button3Add;
    JButton button1Color;
    JButton button4Fire;
    JButton button5SpeedBullet;
    JButton button6Cost;
    JButton button7AlwaysChange;
    JLabel label1Score;
    Synthesizer synth;
    MidiChannel[] channels;

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////AddNewCircle [] circles = new AddNewCircle[5];////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    boolean isWeaponAdded = false;
    boolean isAlwaysChange = false;
    boolean isTrue = true; //FOR ALWAYS CHANGE

    int s = 0;
    int cost = 1;

    int yFire = 20;

    int speedBullet = 2;

    int x = 70;             //FIRST POSITION by X
    int y = 70;             //FIRST POSITION by Y

    int goToX = 3;          //CIRCLE'S STEP BY X ----- --
    int goToY = 3;          //CIRCLE'S STEP BY Y ----- |
    public static void main(String[]arg){
        FirstClass home = new FirstClass();
        try
        {
            FileInputStream fileStream = new FileInputStream("foo.ser");
            ObjectInputStream os = new ObjectInputStream(fileStream);

            Seriali ser = (Seriali) os.readObject();

            home.isWeaponAdded = ser.getWeap();
            home.isAlwaysChange = ser.getAlwa();
            home.isTrue = ser.getIsTr();
            home.s = ser.getS();
            home.cost = ser.getCos();
            home.speedBullet = ser.getSpeed();

        }
        catch (Exception ex){ex.printStackTrace();}
        home.go();
    }
    public void go(){
        try{
            synth = MidiSystem.getSynthesizer();
            synth.open();
            channels = synth.getChannels();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
        frame = new JFrame("fuck");
        JPanel panel = new JPanel();                            //PANEL WITH BUTTONS
        Font font = new Font("Verdana", Font.PLAIN, 12);
        newSlowCircle = new NewSlowCircle();
        button1Color = new JButton("CHANGE COLORS");     //THIS BUTTON CHANGE CIRCLE'S COLOR
        button2Move = new JButton("M0VE");                      //YOUR CIRCLE MOVE
        button3Add = new JButton("ADD WEAPON");               //ADD WEAPON
        button4Fire = new JButton("FIRE");
        button5SpeedBullet = new JButton("Speed+(25)");
        button6Cost = new JButton("Cost+(25)");
        button7AlwaysChange = new JButton("AlwaysChange(15)");
        JButton button8Save = new JButton("Save");
        JButton button9DisSave = new JButton("Delete Save");
        button5SpeedBullet.setEnabled(false);
        button7AlwaysChange.setEnabled(false);
        button6Cost.setEnabled(false);
        label1Score= new JLabel(String.valueOf(s));
        label1Score.setFont(font);
        label1Score.setForeground(Color.WHITE);
        button4Fire.setEnabled(false);
        button3Add.setEnabled(false);
        button8Save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    FileOutputStream fs = new FileOutputStream("foo.ser");
                    ObjectOutputStream os = new ObjectOutputStream(fs);
                    os.writeObject(new Seriali(isWeaponAdded,isAlwaysChange,isTrue,s,cost,speedBullet));
                    os.close();
                }
                catch (Exception ex){ex.printStackTrace();}
            }
        });
        button9DisSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    FileOutputStream fs = new FileOutputStream("foo.ser");
                    ObjectOutputStream os = new ObjectOutputStream(fs);
                    os.writeObject(new Seriali(false,false,true,0,1,2));
                    os.close();
                    isAlwaysChange = false;
                    isTrue = true;
                    s = 0;
                    cost = 1;
                    speedBullet = 2;
                }
                catch (Exception ex){ex.printStackTrace();}
            }
        });
        button2Move.addActionListener(this::actionPerformedMove);
        button5SpeedBullet.addActionListener(this::actionPerformedSpeedBullet);
        button3Add.addActionListener(this::actionPerformedAdd);
        button1Color.addActionListener(this::actionPerformedColor);
        button4Fire.addActionListener(this::actionPerformedFire);
        button6Cost.addActionListener(this::actionPerformedCost);
        button7AlwaysChange.addActionListener(this::actionPerformedAlwaysChange);
        frame.setResizable(false);
        frame.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 450,Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 350);
        panel.setBackground(Color.PINK);                           //PINK COLOR FOR PANEL WITH BUTTONS
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(button1Color, BorderLayout.EAST);
        panel.add(button2Move, BorderLayout.CENTER);
        panel.add(button3Add, BorderLayout.CENTER);
        panel.add(button4Fire, BorderLayout.CENTER);
        panel.add(button5SpeedBullet, BorderLayout.EAST);
        panel.add(button6Cost, BorderLayout.CENTER);
        panel.add(button7AlwaysChange, BorderLayout.CENTER);
        panel.add(button8Save, BorderLayout.CENTER);
        panel.add(button9DisSave, BorderLayout.CENTER);
        newSlowCircle.add(label1Score, BorderLayout.EAST);
        //newSlowCircle.setLayout(new BorderLayout());
        frame.getContentPane().add(BorderLayout.EAST, panel);           //ADD PINK PANEL WITH SUBJECT TO POSITION
        frame.getContentPane().add(BorderLayout.CENTER, newSlowCircle); //ADD CIRCLE PANEL WITH SUBJECT TO POSITION
        frame.setSize(900,700);
        //frame.setSize(Toolkit.getDefaultToolkit ().getScreenSize());    //WINDOW SIZE = ALL MONITOR
        frame.setVisible(true);
        iRED = 0;iBLUE = 0;iGREEN = 0;
        int red = (int) (Math.random() * 250);
        int green = (int) (Math.random() * 250);
        int blue = (int) (Math.random() * 250);
        int i = 0;
        x  = newSlowCircle.getWidth() / 2 - 50;
        y  = newSlowCircle.getHeight() / 2 - 50;
        while (i < red || i<blue || i<green) {
            i = i + 1;
            if (i < blue) iBLUE++;
            if (i < green) iGREEN++;
            if (i < red) iRED++;
            newSlowCircle.repaint();
            try{
                Thread.sleep(10);
            }
            catch(Exception ex){ex.printStackTrace();}
        }
    }
    int iRED = 0;
    int iBLUE = 0;
    int iGREEN = 0;

    boolean alColor=true;

    public void actionPerformedMove(ActionEvent event){ button3Add.setEnabled(true);MOVE(); }
    public void actionPerformedAlwaysChange(ActionEvent event){ s -= 15;isAlwaysChange = true;isTrue = false;button7AlwaysChange.setEnabled(false);label1Score.setText(String.valueOf(s)); }
    public void actionPerformedAdd(ActionEvent event){ isWeaponAdded = true; button3Add.setEnabled(false); button4Fire.setEnabled(true);}
    public void actionPerformedColor(ActionEvent event){ COLOR();if (alColor&&isAlwaysChange){button1Color.setEnabled(false);}}
    public void actionPerformedFire(ActionEvent event){ button4Fire.setEnabled(false);FIRE(); }
    public void actionPerformedSpeedBullet(ActionEvent event){ speedBullet += 2;s-=25; if(s<25){button5SpeedBullet.setEnabled(false);button6Cost.setEnabled(false);} if(s<15)button7AlwaysChange.setEnabled(false);if (speedBullet >= 6) button5SpeedBullet.setEnabled(false); label1Score.setText(String.valueOf(s));}
    public void actionPerformedCost(ActionEvent event){ cost++;s-=25; if(s<25){button6Cost.setEnabled(false);button5SpeedBullet.setEnabled(false);}if(s<15)button7AlwaysChange.setEnabled(false); if(cost >=4){button6Cost.setEnabled(false);}label1Score.setText(String.valueOf(s));}

    public class NewSlowCircle extends JPanel{
        @Override
        public void paintComponent(Graphics g){
            if (newSlowCircle.getWidth()-100 <= x && goToX > 0) goToX = -goToX;

            if (x <= 0 &&  goToX < 0) goToX = -goToX;

            if (newSlowCircle.getHeight()-100 <= y && goToY >0) goToY = -goToY;

            if (y <= 45 && goToY < 0) goToY = -goToY;
            g.fillRect(0,45,this.getWidth(),this.getHeight()-45);
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0,0,this.getWidth(),45);
            Color col2 = new Color(iRED, iGREEN, iBLUE);
            g.setColor(col2);
            g.fillOval(x,y,100,100);
            if(isWeaponAdded){
int xBullet = newSlowCircle.getWidth()/2-10;
                g.setColor(Color.ORANGE);
                g.fillRect(newSlowCircle.getWidth() / 2 - 10,yFire,20,35);
                if (y<=yFire && y+100>=yFire+35 && x <= xBullet&& x+ 100>=xBullet+20) {

                    try {
                        channels[0].programChange(10);
                        channels[0].noteOn(72, 56);
                        channels[0].noteOff(72);
                    }  catch (Exception e) {
                        e.printStackTrace();
                    }

                    yFire = 20; s+=cost; label1Score.setText(String.valueOf(s));if(s >=25&&speedBullet!=6)
                    {button5SpeedBullet.setEnabled(true);}
                    if(s >=25&&cost!=4||cost>4)
                    {button6Cost.setEnabled(true);}
                    if(s>=15&&isTrue)button7AlwaysChange.setEnabled(true);}
            }
        }
    }
    private  void FIRE()
    {
        Thread s1 = new Thread(new Runnable() {
            @Override
            public void run() {
                yFire += 1;
                while(yFire != 20){
                    yFire += speedBullet;
                    if (yFire > newSlowCircle.getHeight()) yFire = 20;
                    newSlowCircle.repaint();
                    try{Thread.sleep(7);}catch (Exception ignored){}
                }
                button4Fire.setEnabled(true);
            }
        });
        s1.start();
    }
    private void COLOR(){
        Thread s1 = new Thread(new Runnable() {
            @Override
            public void run() {
                if(!isAlwaysChange) {
                    int red = (int) (Math.random() * 250);
                    int green = (int) (Math.random() * 250);
                    int blue = (int) (Math.random() * 250);
                    while (iRED != red && iBLUE != blue && iGREEN != green) {
                        if (iRED < red) iRED++;
                        if (iBLUE < blue) iBLUE++;
                        if (iGREEN < green) iGREEN++;
                        if (iRED > red) iRED--;
                        if (iBLUE > blue) iBLUE--;
                        if (iGREEN > green) iGREEN--;
                        newSlowCircle.repaint();
                        try {
                            Thread.sleep(10);
                        } catch (Exception ignored) {
                        }
                    }
                }
                                if(isAlwaysChange) {
                    int red = (int) (Math.random() * 250);
                    int green = (int) (Math.random() * 250);
                    int blue = (int) (Math.random() * 250);
                    while (true) {
                        if (iRED < red) iRED++;
                        if (iBLUE < blue) iBLUE++;
                        if (iGREEN < green) iGREEN++;
                        if (iRED > red) iRED--;
                        if (iBLUE > blue) iBLUE--;
                        if (iGREEN > green) iGREEN--;
                        newSlowCircle.repaint();
                        if(iRED == red && iBLUE == blue && iGREEN == green){
                            red = (int) (Math.random() * 250);
                            green = (int) (Math.random() * 250);
                            blue = (int) (Math.random() * 250);
                        }
                        try {
                            Thread.sleep(10);
                        } catch (Exception ex){ex.printStackTrace();}
                    }
                }
            }
        });
        s1.start();
    }
    private void MOVE(){
        button2Move.setEnabled(false);
        Thread s1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    x += goToX;
                    y += goToY;
                    newSlowCircle.repaint();
                    try{Thread.sleep(6);}catch (Exception ex){ex.printStackTrace();}
                }
            }
        });
        s1.start();
    }
}