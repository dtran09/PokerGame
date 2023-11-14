
package poker ;

import java.util.Collections ;

import poker.Card.Suit ;

/**@author Matt Maloney
 * 
 * Represents a deck of cards
 */
public class Deck extends Pile
    {

    /**
     * Creates a new {@code deck} which is populated by {@link #populate()}
     */
    public Deck()
        {
        super() ;
        populate() ;

        }


    /**
     * Populates the deck by adding 52 cards, one of each rank for each suit, in
     * order of suit then ascending rank
     */
    public void populate()
        {
        for ( Suit suit : Suit.values() )
            {
            for ( int i = 1 ; i < 14 ; i++ )
                {
                this.cards.add( new Card( i, suit ) ) ;

                }

            }

        }


    /**
     * Shuffles the deck according to {@link Collections#shuffle(java.util.List)}
     */
    public void shuffle()
        {
        Collections.shuffle( this.cards ) ;

        }


    /**
     * Returns the a card from the "top" of the deck, that being the last entry and
     * removes it
     *
     * @return the last entry in the deck
     */
    public Card draw()
        {
        return this.cards.remove( this.cards.size() - 1 ) ;

        }

    }
