package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {

    Clip clip;
    URL soundURL[] = new URL[30];

    public Sound()
    {
        soundURL[0] = getClass().getClassLoader().getResource("sound/map1.wav");
        soundURL[1] = getClass().getClassLoader().getResource("sound/coin.wav");
        soundURL[2] = getClass().getClassLoader().getResource("sound/unlock.wav");
        soundURL[3] = getClass().getClassLoader().getResource("titlescreen/menu.wav");
        soundURL[4] = getClass().getClassLoader().getResource("titlescreen/mouse_ylover.wav");
        soundURL[5] = getClass().getClassLoader().getResource("titlescreen/mouse_ylselect.wav");
        soundURL[6] = getClass().getClassLoader().getResource("sound/death/death1.wav");
        soundURL[7] = getClass().getClassLoader().getResource("sound/death/death2.wav");
        soundURL[8] = getClass().getClassLoader().getResource("sound/death/death3.wav");
        soundURL[9] = getClass().getClassLoader().getResource("sound/death/death4.wav");
        soundURL[10] = getClass().getClassLoader().getResource("sound/guns/hitmarker.wav");
        soundURL[11] = getClass().getClassLoader().getResource("sound/guns/mosin_nagant.wav");
        soundURL[12] = getClass().getClassLoader().getResource("sound/guns/reload.wav");
        soundURL[13] = getClass().getClassLoader().getResource("sound/guns/ammo.wav");
        soundURL[14] = getClass().getClassLoader().getResource("sound/game_over.wav");
        soundURL[15] = getClass().getClassLoader().getResource("sound/victory1.wav");

    }

    public void setFile(int i)
    {

        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        }catch(Exception e)
        {
        }
    }
    public void play()
    {
        clip.start();
    }
    public void loop()
    {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop()
    {
        clip.stop();
    }

}
