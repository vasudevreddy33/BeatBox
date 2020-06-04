import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class MiniMusicPlayer{

    static JFrame frame = new JFrame();
    static DrawPanelGUIMUsic guiMP;    
    public static void main(String[] args) {
      MiniMusicPlayer miniP = new MiniMusicPlayer();
      miniP.go();
    }

    public void setUpGUI(){
        guiMP = new DrawPanelGUIMUsic();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(guiMP);
        frame.setBounds(50, 50, 500, 500);
        frame.setVisible(true);
    }

    public void go(){

        setUpGUI();
        try{
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();
    
            Sequence sequence = new Sequence(Sequence.PPQ, 4);
            Track track = sequence.createTrack();
    
        
            sequencer.addControllerEventListener(guiMP, new int[] {127});

            int r= 0;
            for(int i=0; i<60; i+=4){

                r= (int)((Math.random()*50) +1);
                track.add(makeEvent(144, 1, r, 100, i));
                track.add(makeEvent(176, 1, 127, 0, i));
                track.add(makeEvent(128, 1, r, 100, i+2));
            }
            sequencer.setSequence(sequence);
            sequencer.start();
            sequencer.setTempoInBPM(220);
    
          }
          catch(Exception ex){
              ex.printStackTrace();
          }
    }

    public static MidiEvent makeEvent(int comd, int chan, int one, int two, int tick){
        MidiEvent event = null;
        try{
            ShortMessage message = new ShortMessage();
            message.setMessage(comd, chan, one, two);
            event = new MidiEvent(message, tick);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return event;
    }

    public class DrawPanelGUIMUsic extends JPanel implements ControllerEventListener{
        boolean msg = false;

        public void controlChange(ShortMessage message){
            msg= true;
            repaint();
        }

        public void paintComponent(Graphics g){
           if(msg){
            Graphics2D gd = (Graphics2D) g;

            int x = (int) ((Math.random()*40)+10);
            int y = (int) ((Math.random()*40)+10);
            int ht = (int) ((Math.random()*120)+10);
            int wd = (int) ((Math.random()*120)+10);
        
            int red = (int)(Math.random()*255);
            int blue = (int)(Math.random()*255);
            int green = (int)(Math.random()*255);
            Color startColor = new Color(red, blue, green);
   
            red = (int)(Math.random()*255);
            blue = (int)(Math.random()*255);
            green = (int)(Math.random()*255);
            Color endColor = new Color(red, blue, green);
   
            GradientPaint gPaint = new GradientPaint(70, 70, startColor, 150, 150, endColor);
            gd.setPaint(gPaint);

            gd.fillRect(x, y, ht, wd);
            msg = false;
           }
        }
    }
}