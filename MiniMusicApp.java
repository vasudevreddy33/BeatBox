import javax.sound.midi.*;

public class MiniMusicApp {

    public static void main(String[] args) {
        MiniMusicApp mini = new MiniMusicApp();
        if(args.length<2){
            System.out.println("Plese make sure to enter both Instrumnet and note");
        }
        else{
            int instrument = Integer.parseInt(args[0]);
            int note = Integer.parseInt(args[0]);
            mini.play(instrument, note);
        }
        
    }

    public void play(int instrument, int note) {
        try {
           Sequencer player = MidiSystem.getSequencer();
           player.open();
           
           Sequence seq = new Sequence(Sequence.PPQ, 4);

           Track track = seq.createTrack();

           ShortMessage first = new ShortMessage();
           first.setMessage(192, 1, instrument, 0);
           MidiEvent changeInstrument = new MidiEvent(first, 1);
           track.add(changeInstrument);

           ShortMessage a = new ShortMessage();
           a.setMessage(144, 1, note, 100);
           MidiEvent noteOn = new MidiEvent(a, 1);
           track.add(noteOn);

           ShortMessage b = new ShortMessage();
           b.setMessage(128, 1, note, 100);
           MidiEvent noteOff = new MidiEvent(b, 16);
           track.add(noteOff);

           player.setSequence(seq);

           player.start();
        } catch (Exception ex) {
           ex.printStackTrace();
        }
    }
}