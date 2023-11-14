
package poker ;

import java.util.ArrayList ;
import java.util.List ;

/**@author Eric Livezey
 * 
 * Represents a poker player
 */
public class Player
    {

    /**
     * The player's current credits
     */
    protected int credits ;

    /**
     * The player's name
     */
    protected String name ;

    /**
     * The players hand of cards
     */
    protected List<Card> hand ;

    /**
     * The last bet the player made
     */
    protected int lastBet ;

    /**
     * Whether the player has folded
     */
    protected boolean folded ;

    /**
     * Creates a new player the specified starting credits and name
     *
     * @param desiredCredits
     *     The starting credits
     * @param desiredName
     *     The name of the player
     */
    public Player( int desiredCredits, String desiredName )
        {
        this.hand = new ArrayList<>() ;
        this.lastBet = 0 ;
        this.credits = desiredCredits ;
        this.folded = false ;
        this.name = desiredName ;

        }

    }
