import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLOutput;
import java.util.Scanner;

/**
 * A linked-queue based music player which plays Actual Music Files based on keyboard input in an interactive console method.
 * This music player can load playlists of music or add individual song files to the queue.
 */
public class MusicPlayer300 {
    // Data fields
    private Playlist playlist; // The current playlist of Songs
    private boolean filterPlay = false;
        // Whether the current playback mode should be filtered by artist; false by default
    private String filterArtist = null;
        // The artist to play if filterPlay is true; should be null otherwise

    /**
     * Creates a new MusicPlayer300 with an empty playlist
     */
    public MusicPlayer300() {
        this.playlist = new Playlist();
    }

    /**
     * Stops any song that is playing and clears out the playlist
     */
    public void clear() {
        playlist.peek().stop();
        playlist = null;
    }

    /**
     * Loads a playlist from a provided file, skipping any individual songs which cannot be loaded.
     * Note that filenames in the provided files do NOT include the audio directory,
     * and will need that added before they are loaded.
     * Print "Loading" and the song's title in quotes before you begin loading a song,
     * and an "X" if the load was unsuccessful for any reason.
     *
     * @param file the File object to load
     * @throws FileNotFoundException if the playlist file cannot be loaded
     */
    public void loadPlaylist(File file) throws FileNotFoundException {
    	Scanner fscan = new Scanner(file);

    	while(fscan.hasNextLine()) {
    		String s = fscan.nextLine();
    		String[] TAF = s.split(",");
    		System.out.println("Loading \"" + TAF[0] + "\"");
    		try {
    			loadOneSong(TAF[0], TAF[1], "audio" + File.separator + TAF[2]);
    		}
    		catch(IllegalArgumentException e) {
    			System.out.println("X");
    		}
    		
    	}

    }

    /**
     * Loads a single song to the end of the playlist given the title, artist, and filepath.
     * Filepaths for P08 must refer to files in the audio directory.
     *
     * @param title    the title of the song
     * @param artist   the artist of this song
     * @param filepath the full relative path to the song file, begins with the "audio" directory for P08
     * @throws IllegalArgumentException if the song file cannot be read
     */
    public void loadOneSong(String title, String artist, String filepath)
        throws IllegalArgumentException {
        Song s = new Song(title, artist,
            filepath); // Should throw proper exception based on Song constructor
        this.playlist.enqueue(s);
    }

    /**
     * Provides a string representation of all songs in the current playlist
     *
     * @return a string representation of all songs in the current playlist
     */
    public String printPlaylist() {
        return this.playlist.toString();
    }

    /**
     * Creates and returns the menu of options for the interactive console program.
     *
     * @return the formatted menu String
     */
    public String getMenu() {
        // String representation to be returned
        String tmp = "";
        // Add stuff to tmp
        tmp += "[A <filename>] to enqueue a new song file to the end of this playlist\n";
        tmp += "[F <filename>] to load a new playlist from the given file\n";
        tmp += "[L] to list all songs in the current playlist\n";
        tmp += "[P] to start playing ALL songs in the playlist from the beginning\n";
        tmp += "[P -t <Title>] to play all songs in the playlist starting from <Title>\n";
        tmp += "[P -a <Artist>] to start playing only the songs in the playlist by Artist\n";
        tmp += "[N] to play the next song\n";
        tmp += "[Q] to stop playing music and quit the program\n";
        return tmp;
    }

    /**
     * Stops playback of the current song (if one is playing)
     * and advances to the next song in the playlist.
     *
     * @throws IllegalStateException if the playlist is null or empty,
     *                               or becomes empty at any time during this method
     */
    public void playNextSong() throws IllegalStateException {
        // Check if playlist has a valid state
        if (playlist.isEmpty() || playlist == null || playlist.size() == 1) {
            throw new IllegalStateException("playlist is null or empty");
        }
        // Play next song if filter play isn't on
        if (filterPlay == false) {
            if (playlist.peek().isPlaying()) {
                playlist.peek().stop();
            }
            playlist.dequeue();
            playlist.peek().play();
        }
        // Play next song if filter play is on
        else {
            // Stop a song if it is currently playing and then dequeue
            if (playlist.peek().isPlaying()) {
                playlist.peek().stop();
            }
            playlist.dequeue();
            // If the song is by the filter artist play it
            if (playlist.peek().getArtist().equals(filterArtist) && playlist != null && playlist.size() > 0) {
                playlist.peek().play();
            }
            // If not then dequeue and run playNextSong recursively
            else {
                playlist.dequeue();
                if (playlist != null && playlist.size() > 0) {
                    playNextSong();
                }
            }
        }
    }

    /**
     * Interactive method to display the MusicPlayer300 menu and get keyboard input from the user.
     * See writeup for details.
     *
     * @param in scanner
     */
    public void runMusicPlayer300(Scanner in) {
        Scanner s = new Scanner(System.in); // create scanner
        // Print out text and scan user input
        System.out.println(getMenu());
        System.out.println("> ");
        String str = s.nextLine();
        // User input A
        if (str.substring(0,str.length()) == "A"){

        }
        // User input F
        if (str.substring(0,str.length()) == "F"){

        }
        // User input L
        if (str.substring(0,str.length()) == "L"){
            printPlaylist();
        }
        // User input P
        if (str.substring(0,str.length()) == "P"){
            for (int i = 0; i < playlist.size(); i++){

            }
        }
        // User input P -t
        if (str.substring(0,str.length()) == "P -t"){

        }
        // User input P -a
        if (str.substring(0,str.length()) == "P -a"){

        }
        // User input N
        if (str.substring(0,str.length()) == "N"){

        }
        // User input Q
        if (str.substring(0,str.length()) == "Q"){

        }
    }
}
