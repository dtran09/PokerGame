
package poker ;

import java.util.ArrayList ;
import java.util.List ;

import poker.Card.Suit ;

/**
 * @author Eric Livezey
 * 
 * Represents the five card poker hand for a particular set of cards in Texas Hold
 * 'em
 */
public class PokerHand implements Comparable<PokerHand>
    {

    /**
     * The cards in the poker hand
     */
    public final List<Card> cards ;

    /**
     * The type of the poker hand
     */
    public final Type type ;

    private PokerHand( List<Card> desiredCards, Type desiredType )
        {
        this.cards = desiredCards ;
        this.type = desiredType ;

        }


    /**
     * Get the best poker hand of the specified hand
     *
     * @param hand
     *     the hand to check
     *
     * @return the {@code PokeHand} representing the best poker hand of the hand
     */
    public static PokerHand getBestHand( List<Card> hand )
        {
        List<Card> straightFlush = getStraightFlush( hand ) ;
        if ( straightFlush != null )
            {
            if ( straightFlush.get( 0 ).rank == 14 )
                {
                return new PokerHand( straightFlush, Type.ROYAL_FLUSH ) ;

                }

            return new PokerHand( straightFlush, Type.STRAIGHT_FLUSH ) ;

            }

        List<Card> mostOfAKind = getMostOfAKind( hand ) ;
        if ( mostOfAKind != null )
            {
            if ( mostOfAKind.size() == 4 )
                {
                return new PokerHand( mostOfAKind, Type.FOUR_OF_A_KIND ) ;

                }
            else if ( mostOfAKind.size() == 3 )
                {
                List<Card> cards = new ArrayList<>( hand ) ;
                cards.removeAll( mostOfAKind ) ;
                List<Card> pair = getMostOfAKind( cards ) ;
                if ( ( pair != null ) && ( pair.size() >= 2 ) )
                    {
                    while ( mostOfAKind.size() < 5 )
                        {
                        mostOfAKind.add( pair.remove( 0 ) ) ;

                        }

                    return new PokerHand( mostOfAKind, Type.FULL_HOUSE ) ;

                    }

                }

            }

        List<Card> flush = getFlush( hand ) ;
        if ( flush != null )
            {
            return new PokerHand( flush, Type.FLUSH ) ;

            }

        List<Card> straight = getStraight( hand ) ;
        if ( straight != null )
            {
            return new PokerHand( straight, Type.STRAIGHT ) ;

            }

        if ( mostOfAKind != null )
            {
            if ( mostOfAKind.size() == 3 )
                {
                return new PokerHand( mostOfAKind, Type.THREE_OF_A_KIND ) ;

                }
            else if ( mostOfAKind.size() == 2 )
                {
                List<Card> cards = new ArrayList<>( hand ) ;
                cards.removeAll( mostOfAKind ) ;
                List<Card> pair = getMostOfAKind( cards ) ;
                if ( pair != null )
                    {
                    mostOfAKind.addAll( pair ) ;
                    return new PokerHand( mostOfAKind, Type.TWO_PAIR ) ;

                    }

                return new PokerHand( mostOfAKind, Type.PAIR ) ;

                }

            }

        List<Card> high = new ArrayList<>() ;
        high.add( getHigh( hand ) ) ;
        return new PokerHand( high, Type.HIGH_CARD ) ;

        }


    /**
     * Get the high card in the hand
     *
     * @param hand
     *     the hand to check
     *
     * @return the high card of the hand
     */
    public static Card getHigh( List<Card> hand )
        {
        Card high = new Card( 2, hand.get( 0 ).suit ) ;
        for ( Card card : hand )
            {
            if ( ( card.rank == 1 ) || ( card.rank == 14 ) )
                {
                return new Card( 14, card.suit ) ;

                }

            if ( card.rank > high.rank )
                {
                high = card ;

                }

            }

        return high ;

        }


    /**
     * Gets the best straight flush contained in the hand
     *
     * @param hand
     *     the hand to check
     *
     * @return the best straight flush in the hand if one exists, otherwise
     *     {@code null}
     */
    private static List<Card> getStraightFlush( List<Card> hand )
        {
        List<Card> cards = new ArrayList<>( hand ) ;
        while ( cards.size() > 0 )
            {
            Card high = getHigh( cards ) ;
            cards.remove( high ) ;
            List<Card> straightFlush = new ArrayList<>() ;
            if ( high.rank < 5 )
                {
                continue ;

                }

            straightFlush.add( high ) ;
            for ( int i = high.rank - 1 ; i > ( high.rank - 5 ) ; i-- )
                {
                int index = hand.indexOf( new Card( i, high.suit ) ) ;
                if ( index != -1 )
                    {
                    straightFlush.add( hand.get( index ) ) ;
                    if ( straightFlush.size() == 5 )
                        {
                        return straightFlush ;

                        }

                    }
                else
                    {
                    break ;

                    }

                }

            if ( high.rank == 14 )
                {
                int indexOf5 = hand.indexOf( new Card( 5, high.suit ) ) ;
                if ( indexOf5 != -1 )
                    {
                    straightFlush.clear() ;
                    straightFlush.add( hand.get( indexOf5 ) ) ;
                    for ( int i = 5 ; i > 1 ; i-- )
                        {
                        int index = cards.indexOf( new Card( i, high.suit ) ) ;
                        if ( index != -1 )
                            {
                            straightFlush.add( hand.get( index ) ) ;

                            }
                        else
                            {
                            break ;

                            }

                        }

                    if ( straightFlush.size() == 4 )
                        {
                        straightFlush.add( high ) ;
                        return straightFlush ;

                        }

                    }

                }

            }

        return null ;

        }


    /**
     * Get the best flush contained in the hand
     *
     * @param hand
     *     the hand to check
     *
     * @return the best flush in the hand if one exists, otherwise {@code null}
     */
    private static List<Card> getFlush( List<Card> hand )
        {
        for ( Suit suit : Card.Suit.values() )
            {
            List<Card> cards = new ArrayList<>( hand ) ;
            List<Card> flush = new ArrayList<>() ;
            while ( cards.size() > 0 )
                {
                Card high = getHigh( cards ) ;
                cards.remove( high ) ;
                if ( high.suit == suit )
                    {
                    flush.add( high ) ;
                    if ( flush.size() == 5 )
                        {
                        return flush ;

                        }

                    }

                }

            }

        return null ;

        }


    /**
     * Get the best straight contained in the hand
     *
     * @param hand
     *     the hand to check
     *
     * @return the best straight in the hand if one exists, otherwise {@code null}
     */
    private static List<Card> getStraight( List<Card> hand )
        {
        List<Integer> ranks = new ArrayList<>() ;
        for ( Card card : hand )
            {
            ranks.add( card.rank ) ;

            }

        List<Card> cards = new ArrayList<>( hand ) ;
        while ( cards.size() > 0 )
            {
            Card high = getHigh( cards ) ;
            cards.remove( high ) ;
            List<Card> straight = new ArrayList<>() ;
            straight.add( high ) ;
            for ( int i = high.rank - 1 ; i > ( high.rank - 5 ) ; i-- )
                {
                int index = ranks.indexOf( i ) ;
                if ( index != -1 )
                    {
                    straight.add( hand.get( index ) ) ;
                    if ( straight.size() == 5 )
                        {
                        return straight ;

                        }

                    }
                else
                    {
                    break ;

                    }

                }

            if ( high.rank == 14 )
                {
                int indexOf5 = ranks.indexOf( 5 ) ;
                if ( indexOf5 != -1 )
                    {
                    straight.clear() ;
                    straight.add( hand.get( indexOf5 ) ) ;
                    for ( int i = 5 ; i > 1 ; i-- )
                        {
                        int index = ranks.indexOf( i ) ;
                        if ( index != -1 )
                            {
                            straight.add( hand.get( index ) ) ;

                            }
                        else
                            {
                            break ;

                            }

                        }

                    if ( straight.size() == 4 )
                        {
                        straight.add( high ) ;
                        return straight ;

                        }

                    }

                }

            }

        return null ;

        }


    /**
     * Gets an array of cards which contains most consecutive cards of the same rank,
     * if there are two sets of consecutive cards of the same rank with the same
     * length, the highest if those two ranks will be returned.
     *
     * @param hand
     *     the hand to check
     *
     * @return the array of cards with the highest rank most consecutive cards in the
     *     hand, or {@code null} if no consecutive cards of the same rank exist
     */
    private static List<Card> getMostOfAKind( List<Card> hand )
        {
        List<Card> cards = new ArrayList<>( hand ) ;
        List<Card> high = null ;
        while ( cards.size() > 0 )
            {
            Card kindCard = cards.remove( 0 ) ;
            if ( kindCard.rank == 1 )
                {
                kindCard = new Card( 14, kindCard.suit ) ;

                }

            List<Card> kind = new ArrayList<>() ;
            cards.remove( kindCard ) ;
            for ( Card card : hand )
                {
                if ( card.rank == 1 )
                    {
                    card = new Card( 14, card.suit ) ;

                    }

                if ( card.rank == kindCard.rank )
                    {
                    cards.remove( card ) ;
                    kind.add( card ) ;

                    }

                }

            if ( kind.size() > 1 )
                {
                if ( high == null )
                    {
                    high = kind ;

                    }
                else if ( ( kind.size() == high.size() ) &&
                          ( kind.get( 0 ).rank > high.get( 0 ).rank ) )
                    {
                    high = kind ;

                    }
                else if ( kind.size() > high.size() )
                    {
                    high = kind ;

                    }

                }

            }

        return high ;

        }


    @Override
    public int compareTo( PokerHand other )
        {
        if ( this == other )
            {
            return 0 ;

            }

        if ( other == null )
            {
            throw new RuntimeException( "Object to compare cannot be null" ) ;

            }

        if ( this.type.precedence != other.type.precedence )
            {
            return other.type.precedence - this.type.precedence ;

            }

        if ( this.type == Type.FULL_HOUSE )
            {
            return getHigh( getMostOfAKind( this.cards ) ).rank -
                   getHigh( getMostOfAKind( other.cards ) ).rank ;

            }

        return getHigh( this.cards ).rank - getHigh( other.cards ).rank ;

        }


    @Override
    public String toString()
        {
        return String.format( "%s %s", this.type, this.cards ) ;

        }

    /**
     * Represents the 10 possible poker hand types in texas hold em'
     */
    public enum Type
        {

        /**
         * A straight flush consisting of the a 10 through A straight
         */
        ROYAL_FLUSH ( 0 ),
        /**
         * A straight which is also a flush
         */
        STRAIGHT_FLUSH ( 1 ),
        /**
         * Four cards are the same rank
         */
        FOUR_OF_A_KIND ( 2 ),
        /**
         * Three of a kind and a pair
         */
        FULL_HOUSE ( 3 ),
        /**
         * Five cards of the same suit
         */
        FLUSH ( 4 ),
        /**
         * Five cards in ascending order
         */
        STRAIGHT ( 5 ),
        /**
         * Theee cards of the same rank
         */
        THREE_OF_A_KIND ( 6 ),
        /**
         * Two pairs
         */
        TWO_PAIR ( 7 ),
        /**
         * Two cards of the same rank
         */
        PAIR ( 8 ),
        /**
         * The highest card
         */
        HIGH_CARD ( 9 );

        /**
         * The precedence of the hand type lower precedence value == higher priority
         */
        public final int precedence ;

        private Type( int rank )
            {
            this.precedence = rank ;

            }

        }

    }
