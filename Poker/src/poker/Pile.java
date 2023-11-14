
package poker ;

import java.util.ArrayList ;
import java.util.List ;

/**@author Denley Tran
 * 
 * Represents a pile of cards
 */
public class Pile
    {

    /**
     * Represents the cards in the pile
     */
    protected final List<Card> cards ;

    /**
     * Instantiates a new pile with no cards
     */
    public Pile()
        {
        this.cards = new ArrayList<>() ;

        }


    @Override
    public String toString()
        {
        return this.cards.toString() ;

        }

    }
