
package poker ;

import java.util.ArrayList ;
import java.util.Iterator ;
import java.util.List ;
import java.util.Scanner ;

/**
 * @author Eric Livezey, Denley Tran, and Matt Maloney
 * 
 * The main class of the Poker game. Contains all of the logic for the actual game.
 */
public class Poker
    {

    /**
     * Main method
     *
     * @param args
     *     command-line arguments (ignored)
     */
    @SuppressWarnings( "null" )
    public static void main( String[] args )
        {
        try ( Scanner in = new Scanner( System.in ) )
            {
            System.out.print( "Number of players: " ) ;
            int numPlayers = in.nextInt() ;
            ArrayList<Player> players = new ArrayList<>( numPlayers ) ;

            for ( int i = 0 ; i < numPlayers ; i++ )
                {
                players.add( new Player( 100, "Player #" + ( i + 1 ) ) ) ;

                }

            boolean keepGoing = true ;

            while ( keepGoing )
                {
                Deck deck = new Deck() ;
                int pot = 0 ;
                int folded = 0 ;
                List<Card> cards = new ArrayList<>() ;
                for ( int round = 0 ; round < 5 ; round++ )
                    {
                    System.out.println( "ROUND #" + ( round + 1 ) ) ;
                    switch ( round )
                        {
                        case 0:
                            deck.shuffle() ;
                            for ( Player player : players )
                                {
                                player.folded = false ;
                                player.hand.clear() ;
                                player.hand.add( deck.draw() ) ;
                                player.hand.add( deck.draw() ) ;

                                }
                            break ;

                        case 1:
                            cards.add( deck.draw() ) ;
                            cards.add( deck.draw() ) ;
                            break ;

                        case 2, 3, 4:
                            cards.add( deck.draw() ) ;
                            break ;

                        default:
                            break ;

                        }

                    int lastBet = 5 ;
                    Iterator<Player> it = players.iterator() ;
                    boolean matching = false ;
                    while ( it.hasNext() )
                        {
                        Player player = it.next() ;
                        if ( matching && ( player.lastBet >= lastBet ) )
                            {
                            break ;

                            }

                        if ( player.folded || ( player.credits == 0 ) ||
                             ( folded == ( players.size() - 1 ) ) ||
                             ( matching && ( player.lastBet >= lastBet ) ) )
                            {
                            continue ;

                            }

                        for ( ;; )
                            {
                            System.out.printf( "%s%nBet: %s%nPot: %s" + ( cards.isEmpty()
                                ? ""
                                    : "%nTable: " + cards ) +
                                               "%nCredits: %s%nCards: %s%nWhat do you want to do? (raise/call/fold): ",
                                               player.name,
                                               lastBet,
                                               pot,
                                               player.credits,
                                               player.hand ) ;
                            String result = in.next().strip() ;
                            if ( "raise".equals( result ) || "r".equals( result ) )
                                {
                                int raise = in.nextInt() ;
                                if ( ( ( raise <= player.credits ) ||
                                       ( matching &&
                                         ( ( raise - player.lastBet ) <= player.credits ) ) ) &&
                                     ( raise > lastBet ) )
                                    {
                                    lastBet = raise ;
                                    if ( matching )
                                        {
                                        raise = raise - player.lastBet ;

                                        }

                                    player.credits -= raise ;
                                    pot += raise ;
                                    player.lastBet = lastBet ;
                                    break ;

                                    }

                                }

                            if ( "call".equals( result ) || "c".equals( result ) )
                                {
                                if ( matching &&
                                     ( player.credits >= ( lastBet - player.lastBet ) ) )
                                    {
                                    player.credits -= lastBet - player.lastBet ;
                                    pot += lastBet - player.lastBet ;

                                    }
                                else if ( ( lastBet <= player.credits ) && !matching )
                                    {
                                    player.credits -= lastBet ;
                                    pot += lastBet ;

                                    }
                                else
                                    {
                                    pot += player.credits ;
                                    player.credits = 0 ;

                                    }

                                player.lastBet = lastBet ;
                                break ;

                                }

                            if ( "fold".equals( result ) || "f".equals( result ) )
                                {
                                player.folded = true ;
                                folded++ ;
                                break ;

                                }

                            System.out.println( "\nInvalid input\n" ) ;

                            }

                        for ( int j = 0 ; j < 50 ; j++ )
                            {
                            if ( it.hasNext() && ( j == 25 ) )
                                {
                                System.out.printf( "enter anything to continue" ) ;
                                in.next() ;

                                }

                            System.out.println() ;

                            }

                        if ( !it.hasNext() )
                            {
                            it = players.iterator() ;
                            matching = true ;

                            }

                        }

                    // End of game
                    if ( round == 4 )
                        {
                        System.out.println( "Table: " + cards ) ;
                        ArrayList<Player> winners = new ArrayList<>() ;
                        PokerHand bestHand = null ;
                        for ( Player player : players )
                            {
                            System.out.println( player.name + " Cards: " + player.hand ) ;
                            if ( player.folded )
                                {
                                continue ;

                                }

                            player.hand.addAll( cards ) ;
                            PokerHand pokerHand = PokerHand.getBestHand( player.hand ) ;
                            if ( bestHand == null )
                                {
                                bestHand = pokerHand ;
                                winners.add( player ) ;
                                continue ;

                                }

                            int compared = bestHand.compareTo( pokerHand ) ;
                            if ( compared < 0 )
                                {
                                winners.clear() ;
                                winners.add( player ) ;
                                bestHand = pokerHand ;

                                }
                            else if ( compared == 0 )
                                {
                                winners.add( player ) ;

                                }

                            }

                        if ( winners.size() > 1 )
                            {
                            System.out.println( "Winners:" ) ;
                            int winnings = pot / winners.size() ;
                            for ( Player winner : winners )
                                {
                                if ( !it.hasNext() )
                                    {
                                    winnings = pot ;

                                    }

                                System.out.println( winner.name ) ;
                                pot -= winnings ;
                                winner.credits += winnings ;

                                }

                            System.out.println( "Hand: " + bestHand.type ) ;

                            }
                        else
                            {
                            System.out.println( winners.get( 0 ).name + " wins!" ) ;
                            System.out.println( "Hand: " + bestHand ) ;
                            winners.get( 0 ).credits += pot ;
                            pot = 0 ;

                            }

                        }

                    }

                for ( int i = 0 ; i < players.size() ; i++ )
                    {
                    Player player = players.get( i ) ;
                    if ( player.credits == 0 )
                        {
                        System.out.printf( player.name + " is out of credits%n" ) ;
                        players.remove( i ) ;

                        }

                    }

                if ( players.size() <= 1 )
                    {
                    System.out.println( "Not enough players to continue" ) ;
                    break ;

                    }

                for ( ;; )
                    {
                    System.out.print( "Do you want to continue? (y/n): " ) ;
                    String result = in.next().strip() ;
                    if ( "y".equals( result ) )
                        {
                        break ;

                        }

                    if ( "n".equals( result ) )
                        {
                        keepGoing = false ;
                        break ;

                        }

                    System.out.println( "Invalid input" ) ;

                    }

                System.out.println() ;

                }

            }

        }

    }
