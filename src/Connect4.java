//Authors: Max Chu
//Connect 4 Game  

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class Connect4 extends JFrame
{

    private int size;                             // Board size
    private int player=0;                    // First player 1 will play the game
    private int numPlayer;                   // Player number
    private static int emptySlotNumber=0;        // Number of empty Slots
    private static int difficulty = 0; //Difficulty

    // GUI Requirements
    private final JFrame frame;                   // Frame
    private final JPanel panel;                   // Panel
    private final JButton[][] buttons;            // Buttons
    private Slot gameBoard[][];                   // Game Board
    private final GridLayout grid;                // GridLayout

    // Button icons
    ImageIcon empty   = new ImageIcon(getClass().getResource("/resources/empty.png"));
    ImageIcon player1 = new ImageIcon(getClass().getResource("/resources/r.png"));
    ImageIcon player2 = new ImageIcon(getClass().getResource("/resources/y.png"));


    public static void main(String[] args)
    {
        Connect4 game = new Connect4();
    }

    public Connect4 ()
    {
        frame = new JFrame("Connect 4");
        panel = new JPanel();

        prompt();  // Get the game parameters
        createBoard();         // Create 2D dynamic Slot array

        buttons = new JButton[getBoardSize()][getBoardSize()];    // Create button array
        grid = new GridLayout(getBoardSize(),getBoardSize());     // Create GridLayout
        panel.setLayout(grid);

        // Initialization board
        initialBoard();

        frame.setContentPane(panel);
        frame.pack();                 // Automatic sizing of the window based on the added swing components
        //frame.setResizable(false);  // No resize to game board
        frame.setLocationRelativeTo(null); // Game board will be center of the screen
        frame.setVisible(true);            // Show frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the frame
    }



    //Set Board Size
    public void setBoardSize(int newSize)
    {
        size = newSize;
    }
    
    public int getBoardSize()
    {
        return size;
    }

    //Number of Empty Slots
    public static int numberOfEmptySlots()
    {
        return emptySlotNumber;
    }
    
    
    //Prompts-----------------------------------------------------------------------------------------------------------
    public void prompt(){
        String playerNumber = JOptionPane.showInputDialog("Welcome to Connect 4, please choose a gamemode (1 - Singleplayer)(2 - Multiplayer)");
        numPlayer = Integer.parseInt(playerNumber);

        //Check input
        if ((numPlayer > 2)||(numPlayer < 1)){
            JFrame frameInputError = new JFrame();
            JOptionPane.showMessageDialog(frameInputError, "Input must be 1 or 2", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        if (numPlayer == 1){
            String userDifficulty = JOptionPane.showInputDialog("Please choose a difficulty (1- Easy) (2- Hard)");
            difficulty = Integer.parseInt(userDifficulty);
            if ((difficulty > 2)||(difficulty < 1)) {
                JFrame frameInputError = new JFrame();
                JOptionPane.showMessageDialog(frameInputError, "Input must be 1 or 2", "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }
        int sizeOfBoard = 7;
        setBoardSize(sizeOfBoard);  // Set to dynamic board size
    }

    //Create 2D Array---------------------------------------------------------------------------------------------------
    public void createBoard()
    {
        // Create dynamic Slot array to game board
        gameBoard = new Slot[getBoardSize()][getBoardSize()];
        for (int i = 0; i <getBoardSize(); i++)
        {
            for (int j = 0; j <getBoardSize(); j++)
            {
                gameBoard [i][j]=new Slot();
            }
        }
    }
    
    //Add Buttons to game board-----------------------------------------------------------------------------------------
    public void addButtonsToBoard() {
        for(int i=0; i<getBoardSize(); ++i) {
            for(int j=0; j<getBoardSize(); ++j) {
                buttons[i][j] = new JButton(empty); // Empty button

                if(numPlayer==1) {  // Computer vs Player button listener
                    buttons[i][j].addActionListener(new listenButtonOnePlayer());
                }

                if(numPlayer==2) {  // Two players button listener
                    buttons[i][j].addActionListener(new listenButtonTwoPlayers());
                }
                panel.add(buttons[i][j]);   // Add buttons to panel
            }
        }
    }

    //Setup the initial board-------------------------------------------------------------------------------------------
    public void initialBoard()
    {
        for (int i=getBoardSize()-2; i>= 0; --i)
        {
            for (int j = getBoardSize()-1; j>=0; --j)
            {
                gameBoard[i][j].setSlotState(-99);
            }
        }
        addButtonsToBoard(); // Add buttons and listener
    }



    /**
     * Game winning state
     * If the four Slot is same, user 1 will win the game
     * @param winner integer If the player 1 is equal to 1, otherwise 2
     */
    public void winnerPlayer(int winner)
    {
        for(int i=0; i<getBoardSize(); ++i)
        {
            for(int j=0; j<getBoardSize(); ++j)
            {
                if(gameBoard[i][j].getSlotState() == winner)
                {
                    // CHECK UP TO DOWN POSITIONS
                    if(i+3<getBoardSize())
                    {
                        if(gameBoard[i+1][j].getSlotState() == winner && gameBoard[i+2][j].getSlotState() == winner && gameBoard[i+3][j].getSlotState() == winner)
                        {
                            if(winner==1)
                                showResult(1);
                            else
                                showResult(2);
                        }
                    }
                    // CHECK LEFT TO RIGHT POSITION
                    if(j + 3 <getBoardSize())
                    {
                        if(gameBoard[i][j+1].getSlotState() == winner && gameBoard[i][j+2].getSlotState() == winner && gameBoard[i][j+3].getSlotState() == winner)
                        {
                            if(winner==1)
                                showResult(1);
                            else
                                showResult(2);
                        }
                    }

                    // CHECK DIAGONAL LEFT TO RIGHT POSITION
                    if(i  < getBoardSize()- 3 && j<getBoardSize()-3)
                    {
                        if(gameBoard[i+1][j+1].getSlotState() == winner && gameBoard[i+2][j+2].getSlotState() == winner && gameBoard[i+3][j+3].getSlotState() == winner)
                        {
                            if(winner==1)
                                showResult(1);
                            else
                                showResult(2);
                        }
                    }

                    // CHECK DIAGONAL RIGHT TO LEFT POSITION
                    if(i  < getBoardSize()- 3 && j - 3 >= 0 )
                    {
                        if(gameBoard[i+1][j-1].getSlotState() == winner && gameBoard[i+2][j-2].getSlotState() == winner && gameBoard[i+3][j-3].getSlotState() == winner)
                        {
                            if(winner==1)
                                showResult(1);
                            else
                                showResult(2);
                        }
                    }
                }
            }
        }
    } // End winnerPlayer function


    /**
     * Show winner player on the new frame
     * @param winnerPlayer integer if the parameter is equal to 1,player 1 is winner.Otherwise, player 2
     */
    public void showResult(int winnerPlayer)
    {
        JFrame frameShowResult = new JFrame();
        if(winnerPlayer==1)
        {
            JOptionPane.showMessageDialog(frameShowResult,
                    "\nWinner : Player 1\n\nThe new game will start.\n\n",
                    "End Game",
                    JOptionPane.INFORMATION_MESSAGE);
            startAgain();
        }
        else
        {
            JOptionPane.showMessageDialog(frameShowResult,
                    "\nWinner : Player 2\n\nThe new game will start.\n\n",
                    "End Game",
                    JOptionPane.INFORMATION_MESSAGE);
            startAgain();
        }
    }

    /**
     * After the game ends, start from the beginning again
     */
    public void startAgain()
    {

        for(int i=0; i<getBoardSize(); ++i)
        {
            for(int j=0; j<getBoardSize(); ++j)
            {
                gameBoard[i][j].setSlotState(-99);  // Initial Value
                buttons[i][j].setIcon(empty);       // Put the empty Slot icon
            }
        }

        frame.setVisible(false);                            // Unvisible previous game board
        Connect4 newGame = new Connect4();           // New Game Object
    }

    /**
     *
     * Action listener to game button
     * Computer vs Player 1
     */
    private class listenButtonOnePlayer implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            try {
                for(int i=getBoardSize()-1; i>=0; --i)  // Check the buttons up to down position
                {
                    for(int j=0; j<=getBoardSize()-1; ++j)
                    {
                        // Get the button component that was clicked
                        if(buttons[i][j]== e.getSource())
                        {

                            if(0 == player%2)   // Player 1 operations
                            {
                                for(int k=0; k<=getBoardSize(); ++i)
                                {
                                    // Player 1 Operations
                                    // Fill the board from down to up
                                    if(gameBoard[i-k][j].getSlotState() == 0)
                                    {
                                        buttons[i-k][j].setIcon(player1);           // Change button icon
                                        gameBoard[i-k][j].setAllPosition('X', i);   // Set Slot parameters
                                        gameBoard[i-k][j].setSlotState(1);          // Set Slot state
                                        ++emptySlotNumber;                         // Increase empty Slot number
                                        winnerPlayer(1);                            // Check game winning state
                                        break;
                                    }
                                }

                                setUpperSlotToEmpty(i,j); // Set the upper Slots to empty Slot to listen button
                                System.out.println("... Player 1 played ... ");
                                ++player;  // Change player order from player 1 to computer
                                break;
                            }

                            // Computer Operations
                            // Basic idea is filling the Slots left to right

                            if(1 == player%2)
                            {
                                moveComputer(i);
                                System.out.println("... Computer played ... ");
                                ++player; // Change player order from computer to player 1
                                break;
                            }
                            else
                            {
                                warningMessage();
                            }
                        } // END EVENT SOURCE
                    } // END SECOND FOR LOOP
                } // END FIRST FOR LOOP


            } // END TRY
            catch(Exception ex)
            {
                warningMessage();
            }
        } // END ACTION PERFORMED

    } // END listenButtonOnePlayer CLASS


    public void warningMessage()
    {
        JFrame frameWarning = new JFrame();
        JOptionPane.showMessageDialog(frameWarning,
                "Invalid Movement !!\nThe Slot is not empty.", "Warning",
                JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Set the upper Slots to empty Slot to listen button
     * @param rowPos integer row position to board
     * @param columnPos integer column position to board
     */
    public void setUpperSlotToEmpty(int rowPos, int columnPos)
    {
        try
        {
            gameBoard[rowPos-1][columnPos].setSlotState(0);
        }
        catch (Exception ex)
        { }
    }

    /**
     * Computer's logic fills Slots from left to right
     * @param rowPosition Slot row position
     */
    public void moveComputer(int rowPosition)
    {
        int l,m;
        boolean flag=false;

        for(l=getBoardSize()-1; (l>=0)&& !flag; --l)
        {
            for(m=0; (m<getBoardSize()) && !flag; ++m)
            {
                if(gameBoard[l][m].getSlotState() == 0)
                {
                    buttons[l][m].setIcon(player2);         // Set new button icon
                    gameBoard[l][m].setAllPosition('O', rowPosition); // Set Slot parameters
                    gameBoard[l][m].setSlotState(2);        // Set Slot state
                    ++emptySlotNumber;
                    winnerPlayer(2); // Check the computer winning state
                    flag = true;
                    setUpperSlotToEmpty(l,m);
                }
            }
        }
    }

    /**
     * Action listener to game button
     * Player 1 vs Player 2
     */
    private class listenButtonTwoPlayers implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            try {
                int eventFlag = 0;
                int flagplayer=0;

                for(int i=getBoardSize()-1; i>=0; --i)
                {
                    for(int j=0; j<=getBoardSize()-1; ++j)
                    {
                        if(eventFlag==0 && buttons[i][j]== e.getSource()) // Get the button component that was clicked
                        {
                            if(flagplayer==0 && player%2==0)
                            {
                                // Player 1 Operations
                                // Fill the board from down to up
                                for(int k=0; k<=getBoardSize(); ++i)
                                {
                                    if(gameBoard[i-k][j].getSlotState()==0 && player%2==0)
                                    {
                                        buttons[i-k][j].setIcon(player1);          // Set new icon to player 1
                                        gameBoard[i-k][j].setAllPosition('X', i);  // Set Slot parameters
                                        gameBoard[i-k][j].setSlotState(1);
                                        ++emptySlotNumber;  // Increase empty Slot number
                                        winnerPlayer(1);     // Check player 1 winning state
                                        flagplayer=1;
                                        eventFlag=1;
                                        break;
                                    }
                                }

                                setUpperSlotToEmpty(i,j);   // Set upper Slot to empty
                                System.out.println("... Player 1 played ... ");
                                ++player; // Change order from player 1 to player 2
                                break;
                            }

                            // Player 2 operations
                            if(flagplayer==0 && player%2==1)
                            {
                                for(int k=0; k<=getBoardSize(); ++i)
                                {
                                    if(gameBoard[i-k][j].getSlotState()==0 && player%2==1)
                                    {
                                        buttons[i-k][j].setIcon(player2);            // Set new icon to player 2
                                        gameBoard[i-k][j].setAllPosition('O', i);    // Set Slot parameters
                                        gameBoard[i-k][j].setSlotState(2);           // Set Slot state
                                        ++emptySlotNumber;
                                        winnerPlayer(2);
                                        flagplayer=1;
                                        eventFlag=1;
                                        break;
                                    }
                                }
                                setUpperSlotToEmpty(i,j);
                                System.out.println("... Player 2 played ... ");
                                ++player;
                                break;
                            }
                        } // END EVENT SOURCE
                    } // END SECOND FOR LOOP
                } // END FIRST FOR LOOP
            }catch(Exception ex)
            {
                warningMessage();
            }

        } // END ACTIONPERFORMED
    } // END listenButtonTwoPlayers CLASS
} // END Connect4 CLASS
