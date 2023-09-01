/**
 * A FIFO linked queue of SongNodes, conforming to our QueueADT interface.
 */
public class Playlist extends Object implements QueueADT<Song>{
    // Data fields
    private SongNode first; // The current first song in the queue; the next one up to play (front of the queue)
    private SongNode last; // The current last song in the queue; the most-recently added one (back of the queue)
    private int numSongs; // The number of songs currently in the queue

    /**
     * Constructs a new, empty playlist queue
     */
    public Playlist(){}

    /**
     * Adds a new song to the end of the queue
     * @param element the song to add to the Playlist
     */
    public void enqueue(Song element) {
        SongNode s = new SongNode(element);
        // Add song to empty playlist
        if (numSongs == 0){
            this.first = s;
            this.last = s;
            numSongs++;
        }
        // Add song to non-empty playlist
        else{
            this.last.setNext(s);
            this.last = s;
            numSongs++;
        }
    }

    /**
     * Removes the song from the beginning of the queue
     * @return the song that was removed from the queue, or null if the queue is empty
     */
    public Song dequeue() {
        // Check if queue is empty
        if (numSongs == 0){
            return null;
        }
        // Queue has 1 element
        else if (numSongs == 1){
            Song tmp = peek();
            this.first = null;
            this.last = null;
            numSongs--;
            return tmp;
        }
        // Queue has more than one element
        else{
            Song tmp = first.getSong();
            this.first = first.getNext();
            numSongs--;
            return tmp;
        }
    }

    /**
     * Returns the song at the front of the queue without removing it
     * @return the song that is at the front of the queue, or null if the queue is empty
     */
    public Song peek() {return first.getSong();}

    /**
     * Returns true if and only if there are no songs in this queue
     * @return true if this queue is empty, false otherwise
     */
    public boolean isEmpty() {return numSongs == 0;}

    /**
     * Returns the number of songs in this queue
     * @return the number of songs in this queue
     */
    public int size() {return numSongs;}

    /**
     * Creates and returns a formatted string representation of this playlist,
     * with the string version of each song in the list on a separate line. For example:
     *   "He's A Pirate" (1:21) by Klaus Badelt
     *   "Africa" (4:16) by Toto
     *   "Waterloo" (2:45) by ABBA
     *   "All I Want For Christmas Is You" (4:10) by Mariah Carey
     *   "Sandstorm" (5:41) by Darude
     *   "Never Gonna Give You Up" (3:40) by Rick Astley
     *   Overrides toString in class Object
     * @return the string representation of this playlist
     */
    @Override
    public String toString(){

    	
      SongNode temp = first;
      String tmp = "";
      while(temp!=null) {
        tmp += temp.getSong().toString() + "\n";
        temp = temp.getNext();
      }
      return tmp;
    }
}
