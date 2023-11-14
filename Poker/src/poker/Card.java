
package poker ;

import java.util.Objects ;

/**@author Matt Maloney
 * 
 * Represents a playing card
 */
public class Card
    {

    /**
     * The rank of the card
     */
    protected final int rank ;

    /**
     * The suit of the card
     */
    protected final Suit suit ;

    /**
     * Creates a new card with the specified rank and suit
     * 
     * @param desiredRank
     *     the rank (1-14)
     * @param desiredSuit
     *     the suit
     */
    public Card( int desiredRank, Suit desiredSuit )
        {
        if ( desiredRank < 1 || desiredRank > 14 )
            {
            throw new IllegalArgumentException( "Invalid Card Rank" ) ;

            }

        this.rank = desiredRank ;
        this.suit = desiredSuit ;

        }


    @Override
    public String toString()
        {
        return switch ( this.suit )
            {
            case HEART -> "♥" ;
            case DIAMOND -> "♦" ;
            case CLUB -> "♣" ;
            case SPADE -> "♠" ;

            } + switch ( this.rank )
                {
                case 1, 14 -> "A" ;
                case 11 -> "J" ;
                case 12 -> "Q" ;
                case 13 -> "K" ;
                default -> String.valueOf( this.rank ) ;

                } ;

        }


    @Override
    public int hashCode()
        {
        return Objects.hash( this.rank, this.suit ) ;

        }


    @Override
    public boolean equals( Object obj )
        {
        if ( this == obj )
            return true ;
        if ( obj == null )
            return false ;
        if ( getClass() != obj.getClass() )
            return false ;
        Card other = (Card) obj ;
        return ( this.rank == other.rank || this.rank == 1 && other.rank == 14 ||
                 this.rank == 14 && other.rank == 1 ) &&
               this.suit == other.suit ;

        }

    /**
     * Represents one of the four card suits
     */
    public enum Suit
        {

        /**
         * Heart suit (♥)
         */
        HEART,
        /**
         * Diamond suit (♦)
         */
        DIAMOND,
        /**
         * Club suit (♣)
         */
        CLUB,
        /**
         * Spade suit (♠)
         */
        SPADE;

        }

    }
