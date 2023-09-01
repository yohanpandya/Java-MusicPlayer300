import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLOutput;

/**
 * Music player tester class
 */
public class MusicPlayerTester {
	/**
	 * Main method
	 *
	 * @param args list of input arguments if any
	 */
	public static void main(String[] args) {
		System.out.println(testAllMethods());
		
	}

	/**
	 * Runs all methods from the tester class
	 * 
	 * @return whether or not all the tests are passing
	 */
	public static boolean testAllMethods() {
		return testSongConstructor()&&testSongPlayback()&&testSongNode()&&testEnqueue()&&testDequeue()&&loadPlaylistTester();
	}

	/**
	 * Tests the song constructor method
	 * 
	 * @return whether it works as intended
	 */
	public static boolean testSongConstructor(){
		// Test with invalid file
		// Try creating a new invalid song
		try {
			Song s = new Song("song", "Artist", "1.mid");
			System.out.println("song constructor failed: No exception thrown");
			return false;
		}
		// Catch, check if Illegal argument exception is thrown
		catch (IllegalArgumentException e) {

		} catch (Exception e) {
			System.out.println("song constructor failed: Wrong exception thrown");
			return false;
		}
		// Test with valid file, also tests the toString() and getTitle() and
		// getArtist() accessor methods.
		Song s = new Song("song", "Artist", "audio" + File.separator + "1.mid");
		AudioUtility a = null;
		try {
			a = new AudioUtility("audio" + File.separator + "1.mid");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}
		int len = a.getClipLength();
		if (!s.getTitle().equals("song") || !s.getArtist().equals("Artist") || !s.toString().contains(s.getTitle())
				|| !s.toString().contains(s.getArtist()) || !s.toString().contains("0:" + len)) {
			System.out.println("song constructor failed: Something failed");
			return false;
		}
		return true;
	}

	/**
	 * Tests song playback
	 * 
	 * @return whether it works as intended
	 */
	public static boolean testSongPlayback()  {
		Song s = new Song("song", "Artist", "audio" + File.separator + "toto-africa.mid");
		s.play();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("testSongPlayback failed: thread.sleep didn't work");
			return false;
		}
		if (!s.isPlaying()) {
			System.out.println("Test song playback failed: song ain't playin");
			return false;
		}

		return true;
	}

	/**
	 * Tests the SongNode class
	 * 
	 * @return whether it works as intended
	 */
	public static boolean testSongNode() {
		Song s = new Song("song", "Artist", "audio" + File.separator + "1.mid");
		Song s2 = new Song("song", "Artist", "audio" + File.separator + "2.mid");
		Song s3 = new Song("song", "Artist", "audio" + File.separator + "3.mid");
		SongNode song3 = new SongNode(s3);
		// Test single node constructor and accessors
		SongNode song = new SongNode(s);

		if (!(song.getSong().equals(s) || song.getNext()!=null)) {
			return false;
		}
		// Test multi node constructor and accessors
		SongNode song2 = new SongNode(s2, song);
		if (!(song2.getSong().equals(s2)) || song2.getNext()!=(song)) {
			System.out.println("TestSongNode failed: case 1");
			return false;
		}
		// Test setNext with single node
		song.setNext(song3);
		if(song.getNext()==null) {
			System.out.println("SongNode is wrong case 1.1");
			return false;
		}
		if (song.getNext()!=song3) {
			System.out.println("TestSongNode failed: case 2 ");
			return false;
		}
		
		// Test setNext with node that already has a next reference
		song2.setNext(song3);
		if(song2.getNext()==null) {
			System.out.println("SongNode is wrong case 2.1");
		}
		if (song2.getNext()!=song3) {
			System.out.println("TestSongNode failed: case 3");
			return false;
		}
		return true;
	}

	/**
	 * tests enqueue method in playlist
	 * 
	 * @return if it works as intended
	 */
	public static boolean testEnqueue() {
		Song s = new Song("song", "Artist", "audio" + File.separator + "1.mid");
		Song s2 = new Song("song", "Artist", "audio" + File.separator + "2.mid");
		Playlist p = new Playlist();
		Playlist p2 = new Playlist();
		p2.enqueue(s);
		// Add song to an empty playlist
		p.enqueue(s);
		if (!(p.peek().equals(s)) || p.size() != 1 || p.isEmpty()) {
			System.out.println("testEnqueue failed: case 1");
			return false;
		}
		// Add song to a non-empty playlist
		p2.enqueue(s2);
		if (!(p2.peek().equals(s)) || p2.size() != 2) {
			System.out.println("testEnqueue failed: case 2");
			return false;
		}
		/*String str = "\"song\" (0:06) by Artist\n" + "\"song\" (0:06) by Artist\n" ;
		if(!p2.toString().equals(str)) {
			System.out.println("Enqueue failed");
			return false;
		}*/
		return true;
	}

	/**
	 * Tests the dequeue() method
	 * 
	 * @return if it works as intended
	 */
	public static boolean testDequeue() {
		Playlist p = new Playlist();
		Playlist p2 = new Playlist();
		Playlist p3 = new Playlist();
		Song s = new Song("song", "Artist", "audio" + File.separator + "1.mid");
		Song s2 = new Song("song", "Artist", "audio" + File.separator + "2.mid");
		p2.enqueue(s);
		p3.enqueue(s);
		p3.enqueue(s2);
		// Dequeue an empty playlist
		if ( !p.isEmpty()||p.dequeue() != null ) {
			System.out.println("testDequeue failed: case 1");
			return false;
		}
		// Dequeue a playlist with 1 element
		Song tmp = p2.dequeue();
		//p2.dequeue();
		if (!tmp.equals(s) || !p2.isEmpty()) {
			System.out.println("testDequeue failed: case 2");
			return false;
		}
		// Dequeue a playlist of more than 1 element
		tmp = p3.dequeue();
		//p3.dequeue();
		if (!tmp.equals(s) || p3.size() != 1 || !p3.peek().equals(s2)) {
			System.out.println("TestDequeue failed: case 3 ");
			return false;
		}
		return true;
	}
	public static boolean loadPlaylistTester() {
		File f = new File("PlaylistTester");
		MusicPlayer300 mp = new MusicPlayer300();
		try{
			mp.loadPlaylist(f);
		}catch(FileNotFoundException e) {
			System.out.println("loadPlaylist failed case 1: File was not found");
			return false;
		}
		String playlist = mp.printPlaylist();
		AudioUtility a = null;
		try {
			a = new AudioUtility("audio" + File.separator + "1.mid");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}
		int len = a.getClipLength();
        int minutes = len/60;
        int seconds = len%60;
		String expected = "\"test1\" (" + minutes + ":" + seconds + ") by hobbes\n"; 
		try {
			a = new AudioUtility("audio" + File.separator + "2.mid");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}
		 len = a.getClipLength();
         minutes = len/60;
         seconds = len%60;
		 expected += "\"test2\" (" + minutes + ":" + seconds + ") by hobbes\n"; 
		try {
			a = new AudioUtility("audio" + File.separator + "4.mid");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}
		len = a.getClipLength();
	    minutes = len/60;
	    seconds = len%60;
		expected += "\"test4\" (" + minutes + ":" + seconds + ") by yoko kanno\n";  
		try {
			a = new AudioUtility("audio" + File.separator + "3.mid");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}
		len = a.getClipLength();
	    minutes = len/60;
	    seconds = len%60;
		expected += "\"test3\" (" + minutes + ":" + seconds + ") by hobbes\n"; 	
		if(!expected.equals(playlist)) {
			System.out.println("EXPECTED:\n" + expected + "\n");
			
			System.out.println("ACTUAL:\n" + playlist);
			
			System.out.println("LoadPlaylist didn't load the playlist correctly");
			return false;
		}
		return true;
	}
}
