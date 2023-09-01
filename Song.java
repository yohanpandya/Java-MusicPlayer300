import java.io.IOException;

/**
 * A representation of a single Song. Interfaces with the provided AudioUtility class,
 * which uses the javax.sound.sampled package to play audio to your computer's audio output device
 */
public class Song {
    // Data fields
    private String artist; // The artist of this song
    private AudioUtility audioClip; // This song's AudioUtility interface to javax.sound.sampled
    private int duration; // The duration of this song in number of seconds
    private String title; // The title of this song

    /**
     * Initializes all instance data fields according to the provided values
     * @param title the title of the song, set to empty string if null
     * @param artist the artist of this song, set to empty string if null
     * @param filepath the full relative path to the song file, begins with the "audio" directory for P08
     * @throws IllegalArgumentException if the song file cannot be read
     */
    public Song(String title,String artist,String filepath)
        throws IllegalArgumentException{
        // Set song title
        if (title == null){
            this.title = "";
        }
        else{
            this.title = title;
        }
        // Set artist
        if (artist == null){
            this.artist = "";
        }
        else {
            this.artist = artist;
        }
        // Check if file path is valid
        try {
            this.audioClip = new AudioUtility(filepath);
        }
        catch (Exception e){
            throw new IllegalArgumentException("Invalid file path");
        }
        //this.audioClip = new AudioUtility(filepath);
        // TODO: fix the audioutility part of the constructor
    }

    /**
     * Tests whether this song is currently playing using the AudioUtility
     * @return true if the song is playing, false otherwise
     */
    public boolean isPlaying(){return audioClip.isRunning();}

    /**
     * Accessor method for the song's title
     * @return the title of this song
     */
    public String getTitle(){return this.title;}

    /**
     * Accessor method for the song's artist
     * @return the artist of this song
     */
    public String getArtist(){return this.artist;}

    /**
     * Uses the AudioUtility to start playback of this song, reopening the clip for playback if necessary
     */
    public void play(){audioClip.startClip();
        System.out.println("Playing " + toString());}

    /**
     * Uses the AudioUtility to stop playback of this song
     */
    public void stop(){audioClip.stopClip();}

    /**
     * Creates and returns a string representation of this Song, for example:
     *     "Africa" (4:16) by Toto
     * The title should be in quotes, the duration should be split out into minutes and seconds
     * (recall it is stored as seconds only!), and the artist should be preceded by the word "by".
     * It is intended for this assignment to leave single-digit seconds represented as 0:6,
     * for example, but if you would like to represent them as 0:06, this is also allowed.
     * Overrides toString in class Object
     * @return a formatted string representation of this Song
     */
    @Override
    public String toString(){
        String tmp = ""; // Variable to store string representation
        // Add song title
        tmp += "\"" + getTitle() + "\" ";
        // Add song duration
        int len = audioClip.getClipLength();
        int minutes = len/60;
        int seconds = len%60;
        tmp += "(" + minutes + ":" + seconds + ") ";
        
        // Add artist name
        tmp += "by " + getArtist();
        return tmp;
    }
}
