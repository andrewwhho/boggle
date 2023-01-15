import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Author: Armin Owrak
 * Date: 06/03/2021
 * This is the test GUI for Boggle
 */
public class BoggleGame extends JFrame implements ActionListener {
    //Creating the values of the different dies
    static char[][] dice = {{'A', 'A', 'A', 'F', 'R', 'S'}, {'A', 'A', 'E', 'E', 'E', 'E'}, {'A', 'A', 'F', 'I', 'R', 'S'}, {'A', 'D', 'E', 'N', 'N', 'N'}, {'A', 'E', 'E', 'E', 'E', 'M'},
            {'A', 'E', 'E', 'G', 'M', 'U'}, {'A', 'E', 'G', 'M', 'N', 'N'}, {'A', 'F', 'I', 'R', 'S', 'Y'}, {'B', 'J', 'K', 'Q', 'X', 'Z'}, {'C', 'C', 'N', 'S', 'T', 'W'},
            {'C', 'E', 'I', 'I', 'L', 'T'}, {'C', 'E', 'I', 'L', 'P', 'T'}, {'C', 'E', 'I', 'P', 'S', 'T'}, {'D', 'D', 'L', 'N', 'O', 'R'}, {'D', 'H', 'H', 'L', 'O', 'R'},
            {'D', 'H', 'H', 'N', 'O', 'T'}, {'D', 'H', 'L', 'N', 'O', 'R'}, {'E', 'I', 'I', 'I', 'T', 'T'}, {'E', 'M', 'O', 'T', 'T', 'T'}, {'E', 'N', 'S', 'S', 'S', 'U'},
            {'F', 'I', 'P', 'R', 'S', 'Y'}, {'G', 'O', 'R', 'R', 'V', 'W'}, {'H', 'I', 'P', 'R', 'R', 'Y'}, {'N', 'O', 'O', 'T', 'U', 'W'}, {'O', 'O', 'O', 'T', 'T', 'U'}};


    static ArrayList<String> dictionaryWords = new ArrayList<>(); //array list for dictionary

    //Creating a variable to determine if the user chose single player or multiplayer mode
    boolean singlePlayerMode;

    static int player1Score; // Create integer for player 1s score
    static int player2Score; // Create integer for player 2s score

    static boolean player1Turn; // Create boolean for player 1s turn

    static JLabel playerTurn; // Creating JLabel to see whose turn it is
    //Creating a variable to store the current player
    static String currentPlayer = "Current Player: ";

    Timer timer; // Creating the timer object
    int seconds; // Creating seconds variable

    //Creating a variable to store the rounds
    static int rounds = 1;

    //Creating the exitPrompt method
    public static void exitPrompt(){
        //Asking the user if they wish to exit the program
        if (JOptionPane.showConfirmDialog(null, "Are you sure you want to exit the program?",
                "Quit?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
            //If the user chooses "Yes," closing the program
            System.exit(0);
        }
    }

    //Creating the title of the main window
    String originalTitle = "Boggle Game";

    //Creating JPanel for the start pane
    JPanel startPane;
    //Creating button for single player
    JButton singlePlayerOption;
    //Creating a button for multiplayer option
    JButton multiplayerOption;
    //Creating the title for the start pane
    JLabel startMenuText;

    //Creating the main JPanel container
    JPanel mainContainer;

    //Creating the left JPanel container
    JPanel rightSideContainer;
    //Creating an exit button
    JButton quitButton;
    //Creating the exit button
    JButton restartButton;
    //Creating the resume button
    JButton resumeButton;
    //Creating the menu button
    JButton menuButton;
    //Creating the "Shake-up the board" button
    JButton shakeUpBoard;
    //Creating the "Pass" button
    JButton passRound;
    //Creating the information button
    JButton gameInfo;
    //Creating a JLabel for the timer
    JLabel timerLabel;
    //Creating a JLabel for the leaderboard (player 1)
    static JLabel leaderboardPlayer1;
    //Creating a JLabel for the leaderboard (player 2)
    static JLabel leaderboardPlayer2;

    //Creating JLabel to display the current gamemode
    static JLabel currentGameMode;

    //Creating a JLabel to display the current round number
    static JLabel roundNumber;

    //Creating a JPanel to contain the game grid
    JPanel gridContainer;
    //Creating an array to store the buttons
    public static JButton[][] gridButtons;

    //Creating the menuFrame
    JFrame menuFrame;

    //Creating the GUI constructor
    public BoggleGame() {
        //Setting the title of the window
        setTitle(originalTitle);
        //Setting the size of the window
        setSize(1800, 1000);
        //Setting the default close operation
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        //Checking to see if the close button was pressed on the window (on the title bar)
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                //If the close button was pressed, the "exitPrompt" method is called
                exitPrompt();
            }
        });

        //////////CODE BLOCK FOR THE MAIN GAME BOARD\\\\\\\\\\
        //Creating the main JPanel container
        mainContainer = new JPanel();

        //Creating a JPanel to contain the game grid
        gridContainer = new JPanel();
        //Creating a GridLayout for the buttons
        GridLayout gridLayout = new GridLayout(5, 5);
        //Creating an array to store the buttons
        gridButtons = new JButton[5][5];

        //Creating a counted loop to index the 2D button array (rows)
        for (int r = 0; r < gridButtons.length; r++){
            //Creating a counted loop to index the number of columns
            for (int c = 0; c < gridButtons[r].length; c++){
                //Creating the buttons at the current indexes
                gridButtons[r][c] = new JButton();
                //Setting the foreground of the button
                gridButtons[r][c].setForeground(Color.BLUE);
                //Setting the name of each button to its location in the 2D array
                gridButtons[r][c].setName(r + "\s" + c);

                //Adding action listeners to each button
                gridButtons[r][c].addActionListener(this);

                //Setting the gridButton font
                gridButtons[r][c].setFont(new Font("DIN", Font.BOLD, 22));

                //Adding each button to the JPanel
                gridContainer.add(gridButtons[r][c]);
            }
        }

        //Applying grid layout to the gridContainer
        gridContainer.setLayout(gridLayout);

        //Initializing the restart button
        restartButton = new JButton("Restart");
        //Setting the font of the restart button
        restartButton.setFont(new Font("DIN", Font.BOLD, 14));
        //Adding an action listener to the restart button
        restartButton.addActionListener(this);
        //Initializing the exit button
        quitButton = new JButton("Quit");
        //Setting the font of the exit button
        quitButton.setFont(new Font("DIN", Font.BOLD, 14));
        //Adding an action listener to the exit button
        quitButton.addActionListener(this);
        //Initializing the resume button
        resumeButton = new JButton("Resume");
        //Setting the font of the resume button
        resumeButton.setFont(new Font("DIN", Font.BOLD, 14));
        //Adding an action listener to the resume button
        resumeButton.addActionListener(this);
        //Initializing the menu button
        menuButton = new JButton("Menu");
        //Setting the font of the menu button
        menuButton.setFont(new Font("DIN", Font.BOLD, 14));
        //Adding an action listener to the menu button
        menuButton.addActionListener(this);
        //Initializing the "passRound" button
        passRound = new JButton("Pass");
        //Setting the font of the passRound button
        passRound.setFont(new Font("DIN", Font.BOLD, 14));
        //Adding an action listener to the passRound button
        passRound.addActionListener(this);
        //Initializing the shakeUpBoard button
        shakeUpBoard = new JButton("Shake-Up the Board");
        //Setting the shakeUpBoard button's text
        shakeUpBoard.setFont(new Font("DIN", Font.BOLD, 14));
        //Adding an action listener to the shakeUpBoard button
        shakeUpBoard.addActionListener(this);
        //Making the shakeUpBoard button not clickable
        shakeUpBoard.setEnabled(false);

        //Creating a header for the timer
        JLabel timerHeader = new JLabel("Time Remaining:");
        //Setting the header font
        timerHeader.setFont(new Font("DIN", Font.BOLD, 25));
        //Initializing the timer JLabel
        timerLabel = new JLabel("00s");
        //Setting the timerLabel font
        timerLabel.setFont(new Font("DIN", Font.PLAIN, 18));
        //Creating a header for the leaderboard
        JLabel leaderboardHeader = new JLabel("Leaderboard:");
        //Setting the header font
        leaderboardHeader.setFont(new Font("DIN", Font.BOLD, 25));
        //Initializing the leaderboardPlayer1 JLabel
        leaderboardPlayer1 = new JLabel("1. Player 1 Score: 0");
        //Setting the leaderboardPlayer1 Font
        leaderboardPlayer1.setFont(new Font("DIN", Font.PLAIN, 18));
        //Initializing the leaderboardPlayer2 JLabel
        leaderboardPlayer2 = new JLabel("2. Player 2 Score: 0");
        //Setting the leaderboardPlayer2 Font
        leaderboardPlayer2.setFont(new Font("DIN", Font.PLAIN, 18));

        //Making the button container for the rightSideContainer
        JPanel rightButtonContainer = new JPanel();
        //Adding the buttons to the rightButtonContainer
        rightButtonContainer.add(menuButton);
        rightButtonContainer.add(passRound);
        rightButtonContainer.add(shakeUpBoard);
        //Applying BoxLayout to the panel
        rightButtonContainer.setLayout(new BoxLayout(rightButtonContainer, BoxLayout.Y_AXIS));

        //Creating a JPanel for the timer elements
        JPanel timerElements = new JPanel();
        //Adding the timerHeader and timerLabel
        timerElements.add(timerHeader);
        timerElements.add(timerLabel);
        //Applying GridLayout
        timerElements.setLayout(new FlowLayout());

        //Creating a JPanel for the leaderboard elements
        JPanel leaderboardElements = new JPanel();
        //Adding the two leaderboards and the header to the JPanel
        leaderboardElements.add(leaderboardHeader);
        leaderboardElements.add(leaderboardPlayer1);
        leaderboardElements.add(leaderboardPlayer2);
        //Applying BoxLayout
        leaderboardElements.setLayout(new BoxLayout(leaderboardElements, BoxLayout.Y_AXIS));

        //Creating the game mode header
        JLabel gameModeHeader = new JLabel("Gamemode:");
        //Setting the header font
        gameModeHeader.setFont(new Font("DIN", Font.BOLD, 25));
        //Initializing the currentGameMode JLabel
        currentGameMode = new JLabel();
        //Setting the currentGameMode's font
        currentGameMode.setFont(new Font("DIN", Font.PLAIN, 18));

        //Creating a JPanel for the current gamemode information
        JPanel gamemodeContainer = new JPanel();
        //Adding the currentGameMod and gameModeHeader to the JPanel
        gamemodeContainer.add(gameModeHeader);
        gamemodeContainer.add(currentGameMode);
        //Applying BoxLayout to the JPanel
        gamemodeContainer.setLayout(new BoxLayout(gamemodeContainer, BoxLayout.Y_AXIS));

        //Creating the playerContainer
        JPanel playerContainer = new JPanel();

        //Creating the playerTurn header
        JLabel playerTurnHeader = new JLabel(currentPlayer);
        playerTurnHeader.setFont(new Font("DIN", Font.BOLD, 25));

        //Initializing the playerTurn JLabel
        playerTurn = new JLabel();
        //Setting the font of the playerTurn JLabel
        playerTurn.setFont(new Font("DIN", Font.PLAIN, 18));
        //If it's player 1s turn
        if (player1Turn){
            //Setting the text to "Player 1"
            playerTurn.setText("Player 1");
        }
        //If it's player 2s turn
        else {
            //Setting the text to "Player 2"
            playerTurn.setText("Player 2");
        }

        //Adding the playerTurnHeader and the playerTurn JLabels to the playerContainer
        playerContainer.add(playerTurnHeader);
        playerContainer.add(playerTurn);
        //Applying BoxLayout to the playerContainer
        playerContainer.setLayout(new BoxLayout(playerContainer, BoxLayout.Y_AXIS));

        //Creating a header for the rounds
        JLabel roundHeader = new JLabel("Round:");
        //Setting the font of the roundHeader
        roundHeader.setFont(new Font("DIN", Font.BOLD, 25));
        //Initializing the roundNumber JLabel
        roundNumber = new JLabel(String.valueOf(rounds));
        //Setting the font of the roundNumber
        roundNumber.setFont(new Font("DIN", Font.PLAIN, 18));

        //Creating a JPanel to contain the roundHeader and the roundNumber
        JPanel roundContainer = new JPanel();
        //Adding the JLabels to the panel
        roundContainer.add(roundHeader);
        roundContainer.add(roundNumber);
        //Applying BoxLayout to the roundContainer
        roundContainer.setLayout(new BoxLayout(roundContainer, BoxLayout.Y_AXIS));

        //Creating a JPanel for the top half of the rightSideContainer
        JPanel rightTopContainer = new JPanel();
        //Adding the timerElements JPanel, leaderboardElements JPanel, playerContainer JPanel,
        //gamemodeContainer JPanel, and the roundContainer JPanel to the rightTopContainer
        rightTopContainer.add(timerElements);
        rightTopContainer.add(leaderboardElements);
        rightTopContainer.add(playerContainer);
        rightTopContainer.add(gamemodeContainer);
        rightTopContainer.add(roundContainer);
        //Applying GridLayout to the rightTopContainer
        rightTopContainer.setLayout(new GridLayout(5, 1));

        //Initializing the rightSideContainer
        rightSideContainer = new JPanel();
        //Applying BorderLayout to the rightSideContainer
        rightSideContainer.setLayout(new BorderLayout());
        //Adding the rightTopContainer and rightButtonContainer to the rightSideContainer
        rightSideContainer.add(rightTopContainer);
        rightSideContainer.add(BorderLayout.SOUTH, rightButtonContainer);

        //Applying BorderLayout to the mainContainer
        mainContainer.setLayout(new BorderLayout());
        //Adding the gridContainer to the mainContainer
        mainContainer.add(gridContainer);
        //Adding the rightSideContainer to the right side of the mainContainer
        mainContainer.add(BorderLayout.EAST, rightSideContainer);

        //Creating a dimension for the start menu
        Dimension startMenuDimensions = new Dimension(500, 500);
        //////////START MENU TEXT\\\\\\\\\\
        //Creating the text for the start menu
        startMenuText = new JLabel("Select Your Gamemode:");
        //Centering the text (Horizontally)
        startMenuText.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        //Setting the startMenuText font
        startMenuText.setFont(new Font("DIN", Font.BOLD, 75));
        //Setting the startMenuText color to white
        startMenuText.setForeground(Color.WHITE);

        //////////SINGLE PLAYER BUTTON\\\\\\\\\\
        //Creating the single player JButton
        singlePlayerOption = new JButton("Single Player Mode");
        //Setting the font on the single player button
        singlePlayerOption.setFont(new Font("DIN", Font.BOLD, 35));
        //Setting the background color for the single player button
        singlePlayerOption.setBackground(new Color(85, 84, 84));
        //Setting the font color for the single player button
        singlePlayerOption.setForeground(Color.WHITE);
        //Setting the single player button border
        singlePlayerOption.setBorder(new LineBorder(Color.BLACK, 5, true));
        //Adding an action listener to the single player button
        singlePlayerOption.addActionListener(this);
        //Setting the preferred size of the single player button
        singlePlayerOption.setPreferredSize(startMenuDimensions);

        //////////MULTIPLAYER BUTTON\\\\\\\\\\
        //Creating the multiplayer JButton
        multiplayerOption = new JButton("Multiplayer Mode");
        //Setting the font on the multiplayer button
        multiplayerOption.setFont(new Font("DIN", Font.BOLD, 35));
        //Setting the background color for the multiplayer button
        multiplayerOption.setBackground(new Color(85, 84, 84));
        //Setting the font color for the multiplayer button
        multiplayerOption.setForeground(Color.WHITE);
        //Setting the multiplayer button border
        multiplayerOption.setBorder(new LineBorder(Color.BLACK, 5, true));
        //Adding an action listener to the multiplayer button
        multiplayerOption.addActionListener(this);
        //Setting the preferred size of the multiplayer button
        multiplayerOption.setPreferredSize(startMenuDimensions);

        //Creating the JPanel to store the buttons
        JPanel buttonPane = new JPanel();
        //Adding the buttons to the buttonPane
        buttonPane.add(singlePlayerOption);
        buttonPane.add(multiplayerOption);
        //Applying a FlowLayout to the buttonPane
        buttonPane.setLayout(new FlowLayout());
        //Setting the background of the button panel to dark gray
        buttonPane.setBackground(Color.DARK_GRAY);

        //Creating a JPanel for the text pane
        JPanel topPanel = new JPanel();
        //Applying BorderLayout to the top panel
        topPanel.setLayout(new BorderLayout());
        //Adding the startMenuText to the topPanel
        topPanel.add(BorderLayout.SOUTH, startMenuText);
        //Setting the background of the top panel to dark gray
        topPanel.setBackground(Color.DARK_GRAY);

        //Creating the startPane
        startPane = new JPanel();
        //Applying the GridLayout to the startPane
        startPane.setLayout(new BorderLayout());
        //Adding the topPanel to the startPane
        startPane.add(topPanel);
        //Adding the buttonPane to the startPane
        startPane.add(BorderLayout.SOUTH, buttonPane);

        //Adding the startPane to the main constructor
        add(startPane);

        //Calling the timer method
        timer();

        //Maximizing the window
        setExtendedState(MAXIMIZED_BOTH);
        //Making the window visible
        setVisible(true);
    }

    //Creating a boolean canContinue
    static boolean canContinue = false;

    //Creating the ActionListener
    public void actionPerformed(ActionEvent e){
        //Creating a variable to store the action performed
        String actionPerformed = e.getActionCommand();

        //Creating a boolean condition to break the outer loop
        boolean breakLoop = false;
        //Creating a counted loop to traverse the rows of the gridButtons
        for (int r = 0; r < gridButtons.length; r++){
            //If the code is told to break the outer loop
            if (breakLoop){
                //Breaking the loop
                break;
            }
            //Creating a counted loop to traverse the columns of the gridButtons
            for (int c = 0; c < gridButtons[r].length; c++){
                //If the button at the current index was pressed
                if (gridButtons[r][c].getModel().isArmed()){
                    System.out.println("Row: " + r
                    + "\nColumn: " + c);

                    //Telling the outer loop to break
                    breakLoop = true;

                    //Breaking the inner loop
                    break;
                }
            }
        }

        switch (actionPerformed) {
            //If the user chooses single player or multiplayer
            case "Single Player Mode", "Multiplayer Mode" -> {
                //If the program can continue
                if (canContinue){
                    //Try to pause code execution for 200ms
                    try {
                        //Pausing code execution for 200ms
                        Thread.sleep(200);
                    } catch (InterruptedException interruptedException) { //If an exception is caught
                        //Do nothing
                    }

                    //Hiding the startPane
                    startPane.hide();

                    //If the user chose single player
                    if (actionPerformed.equals("Single Player Mode")) {
                        //Setting the variable "singlePlayerMode" to true
                        singlePlayerMode = true;

                        //Setting the currentGameMode text to "Single Player"
                        currentGameMode.setText("Single Player");
                    }
                    //If the user chose multiplayer mode
                    else {
                        //Setting singlePlayerMode to false
                        singlePlayerMode = false;

                        //Setting the currentGameMode text to "Multiplayer"
                        currentGameMode.setText("Multiplayer");
                    }

                    //Adding the mainContainer to the GUI constructor
                    add(mainContainer);

                    //Starting the timer
                    timer.start();
                }
                //If the program cannot continue
                else {
                    //Displaying error message to the user
                    JOptionPane.showMessageDialog(null, "Please select either \"Heads\" or \"Tails\" before continuing!",
                            "Please Select From The Heads or Tails Menu", JOptionPane.ERROR_MESSAGE);
                }
            }

            //If the user presses the quit button
            case "Quit" ->
                    //Running the "exitPrompt" method
                    exitPrompt();

            //If the user presses the menu button
            case "Menu" -> {
                //////////PAUSE TIMER AND THE MAIN CODE HERE\\\\\\\\\\
                //Pausing the timer
                timer.stop();


                //////////CODE BLOCK FOR PAUSE MENU\\\\\\\\\\
                //Changing the title
                setTitle(originalTitle + " - PAUSED");

                //Initializing the menuFrame
                menuFrame = new JFrame("Pause Menu");
                //Setting the frame's default close operation
                menuFrame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                //Setting the size of the frame
                menuFrame.setSize(250, 450);
                //Making the frame not resizable
                menuFrame.setResizable(false);

                ///Creating a JPanel to store the buttons
                JPanel mainButtonContainer = new JPanel();

                //Initializing the gameInfo button
                gameInfo = new JButton("Help");
                //Setting the gameInfo button font
                gameInfo.setFont(new Font("DIN", Font.BOLD, 14));
                //Adding an action listener to the gameInfo button
                gameInfo.addActionListener(this);

                //Adding the buttons to the mainButtonContainer
                mainButtonContainer.add(resumeButton);
                mainButtonContainer.add(gameInfo);
                mainButtonContainer.add(restartButton);
                mainButtonContainer.add(quitButton);
                //Applying flow layout to the mainButtonContainer
                mainButtonContainer.setLayout(new GridLayout(4, 1));

                //Creating a dimension for the center of the screen
                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                //Centering the menu GUI on the center of the screen
                menuFrame.setLocation(dim.width / 2 - menuFrame.getSize().width / 2, dim.height / 2 - menuFrame.getSize().height / 2);

                //Adding the mainButtonContainer to the menu frame
                menuFrame.add(mainButtonContainer);
                //Making the frame visible
                menuFrame.setVisible(true);
                //Requesting focus on the menu frame
                menuFrame.requestFocus();
            }
            //If the user presses the "Resume" button in the menu
            case "Resume" -> {
                //Changing the title back to the original
                setTitle(originalTitle);

                //Closing the menuFrame
                menuFrame.dispose();

                //////////RESUME TIMER & REST OF CODE HERE\\\\\\\\\\
                //Restarting the timer
                timer.start();
            }

            //If the user presses the "Restart" button
            case "Restart" -> {
                //Asking the user if they are sure they want to restart
                if (JOptionPane.showConfirmDialog(null, "Are you sure you want to restart? " +
                                "All current progress will be lost!", "Restart?",
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION){

                    //Changing the title back to the original
                    setTitle(originalTitle);

                    //Closing the menu
                    menuFrame.dispose();

                    //Clearing the variables
                    timerLabel.setText("00s");

                    ///////////CLEAR OTHER VARIABLES HERE\\\\\\\\\\
                    //Restarting the timer
                    timer.restart();
                    timer.stop();

                    //Generating the random letters
                    diceRandomizer(dice);

                    //Setting seconds to 0
                    seconds = 0;

                    //Telling the program it cannot continue
                    canContinue = false;

                    //Resetting the number of rounds
                    rounds = 1;
                    //Updating the text on the GUI
                    roundNumber.setText(String.valueOf(rounds));
                    
                    //Hiding the mainContainer panel
                    remove(mainContainer);

                    //Calling the headsOrTailsFlip method
                    headsOrTailsFlip();

                    //Showing the startPane
                    startPane.show();
                }
            }

            //If the user presses the "Help" button
            case "Help" -> {
                //Creating a variable storing the game instructions
                String helpText = """
                        Hi! Welcome to Boggle!
                        Boggle is a game where the goal is to come up with as many words as you can
                        from a single letter.
                        To create a word:
                         1. First pick a letter on the board
                         2. Use other letters surrounding the letter you picked to come up with the word
                         Note: The surrounding letters MUST be touching the picked letter. Letters are considered\s
                              "touching" if they are horizontally, vertically, or diagonally adjacent to the
                               picked letter.
                         3. Come up with as many words as you can using that letter before the timer reaches 15s
                         4. Enjoy!""";

                //Creating a JMessageDialogue window to display the message to the user
                JOptionPane.showMessageDialog(null, helpText,
                        "General Information", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    //Creating the headsOrTailsFlip method
    public static void headsOrTailsFlip(){
        //Creating the JFrame
        JFrame firstMove = new JFrame("Heads or Tails?");
        //Setting the size of the window
        firstMove.setSize(500, 350);
        //Making the window non-resizable
        firstMove.setResizable(false);
        //Disabling the window's close button
        firstMove.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        //Adding a window listener to the JFrame
        firstMove.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                //If the close button was pressed, the "exitPrompt" method is called
                exitPrompt();
            }
        });

        //Creating the "heads" JButton
        JButton heads = new JButton("Heads");
        //Setting the "heads" JButton Font
        heads.setFont(new Font("DIN", Font.BOLD, 25));
        //Adding an ActionListener to the "heads" button
        heads.addActionListener(e -> {
            //Getting the ActionCommand
            String buttonPress = e.getActionCommand();

            //If the value chosen matches the return value of the "firstMove" method
            if (buttonPress.equals(firstMove())){
                //It is Player 1s turn
                player1Turn = true;

                //Setting Player 1s turn
                playerTurn.setText("Player 1");

                //Telling the user(s) that Player 1 is going first
                JOptionPane.showMessageDialog(null, "Player 1 won the coin flip!" +
                        "\nThey will be going first!", buttonPress + " Won!", JOptionPane.INFORMATION_MESSAGE);
            }
            //If the value does not match
            else {
                //It is not Player 1s turn
                player1Turn = false;

                //Setting Player 2s turn
                playerTurn.setText("Player 2");

                //Telling the user(s) that Player 1 is not going first
                JOptionPane.showMessageDialog(null, "Player 1 did not win the coin flip." +
                        "\nPlayer 2 will be going first!", buttonPress + " Lost!", JOptionPane.INFORMATION_MESSAGE);
            }

            //Closing the JFrame
            firstMove.dispose();

            //Letting the program know it can continue
            canContinue = true;
        });
        //Creating the "tails" JButton
        JButton tails = new JButton("Tails");
        //Setting the "tails" JButton Font
        tails.setFont(new Font("DIN", Font.BOLD, 25));
        //Adding an ActionListener to the "tails" button
        tails.addActionListener(e -> {
            //Getting the ActionCommand
            String buttonPress = e.getActionCommand();

            //If the value chosen matches the return value of the "firstMove" method
            if (buttonPress.equals(firstMove())){
                //It is Player 1s turn
                player1Turn = true;

                //Setting Player 1s turn
                playerTurn.setText("Player 1");

                //Telling the user(s) that Player 1 is going first
                JOptionPane.showMessageDialog(null, "Player 1 won the coin flip!" +
                        "\nThey will be going first!", buttonPress + " Won!", JOptionPane.INFORMATION_MESSAGE);
            }
            //If the value does not match
            else {
                //It is not Player 1s turn
                player1Turn = false;

                //Setting Player 2s turn
                playerTurn.setText("Player 2");


                //Telling the user(s) that Player 1 is not going first
                JOptionPane.showMessageDialog(null, "Player 1 did not win the coin flip." +
                        "\nPlayer 2 will be going first!", buttonPress + " Lost!", JOptionPane.INFORMATION_MESSAGE);
            }

            //Closing the JFrame
            firstMove.dispose();

            //Letting the program know it can continue
            canContinue = true;
        });

        //Creating a container JPanel for the buttons
        JPanel buttonContainer = new JPanel();
        //Adding the buttons to the buttonContainer
        buttonContainer.add(heads);
        buttonContainer.add(tails);
        //Applying BoxLayout to the buttonContainer
        buttonContainer.setLayout(new GridLayout(1, 2));

        //Creating JLabels for the user message
        JLabel userMessage1 = new JLabel("Pick \"Heads\" or \"Tails.\"");
        //Setting "userMessage1"s font
        userMessage1.setFont(new Font("DIN", Font.BOLD, 18));
        //Centering the text
        userMessage1.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        JLabel userMessage2 = new JLabel("If the coin lands on your choice, you go first!");
        //Setting "userMessage2"s font
        userMessage2.setFont(new Font("DIN", Font.PLAIN, 18));
        //Centering the text
        userMessage2.setHorizontalAlignment((int) CENTER_ALIGNMENT);

        //Creating a container JPanel for the user messages
        JPanel messageContainer = new JPanel();
        //Applying BorderLayoutLayout to the messageContainer
        messageContainer.setLayout(new GridLayout(2, 1));
        //Adding the messages to the container
        messageContainer.add(userMessage1);
        messageContainer.add(userMessage2);

        //Creating the finalContainerPanel
        JPanel finalContainerPanel = new JPanel();
        //Applying BorderLayout to the finalContainerPanel
        finalContainerPanel.setLayout(new BorderLayout());
        //Adding the messageContainer and the buttonContainer
        finalContainerPanel.add(BorderLayout.NORTH, messageContainer);
        finalContainerPanel.add(buttonContainer);

        //Adding the finalContainerPanel to the JFrame
        firstMove.add(finalContainerPanel);
        //Making the JFrame visible
        firstMove.setVisible(true);
        //Setting the focus of the panel
        firstMove.requestFocus();
    }

    //Creating the firstMove method
    public static String firstMove() { //method returns heads or tails
        Random rand = new Random();
        int randomNum = rand.nextInt(2); //generates random number between 1-2
        if (randomNum == 1) { //if the number is 1, it returns heads
            return "Heads";
        }
        else {
            return "Tails"; //if number is 2, it returns tails
        }
    }

    //Creating the diceRandomizer method
    public static void diceRandomizer(char[][] values){
        //Creating randomizer
        Random rand = new Random();

        //Creating a variable for the rows
        int rows = 0;
        //Creating a variable for the columns
        int columns = 0;

        //Creating a counted loop to pick the random faces
        for (char[] value : values) {
            //Creating a variable to store the random number
            int randNum = rand.nextInt(6);

            //Picking a random index
            char selectedFace = value[randNum];

            //Adding the current selected face to the current index of the grid
            gridButtons[rows][columns].setText(String.valueOf(selectedFace));

            //Adding 1 to the columns
            columns++;

            //If "columns" has reached the end of the columns
            if (columns == gridButtons[0].length) {
                //Resetting columns
                columns = 0;
                //Adding 1 to the rows
                rows++;
            }
            //If the rows has reached the end of the array
            if (rows == gridButtons.length) {
                //Resetting the rows
                rows = 0;
            }
        }
    }

    public static void dictionaryRead(ArrayList<String> words) throws FileNotFoundException { //method to read dictionary words into array list
        File input = new File("dictionary.txt"); //uses the .txt file as an input
        Scanner fileReader = new Scanner(input); //file reader

        while(fileReader.hasNextLine()) { //checks if the text file has next lines to read
            words.add(fileReader.nextLine()); //adds whatever is on the next line to the array list
        }
        //Closing the Scanner
        fileReader.close();
    }

    // Timer Method
    public void timer() {
        //Creating the timer object
        timer = new Timer(1000, e -> {
            //If the timer hits the 15s limit
            if (seconds == 15) {
                //Resetting the timer
                seconds = 0;

                rounds++; // Move onto next round

                //Updating the text on the GUI
                roundNumber.setText(String.valueOf(rounds));

                //If it is currently player 1s turn
                if (player1Turn) {
                    //It is no longer player 1s turn
                    player1Turn = false;

                    //Setting the text so the user can see who's turn it is
                    playerTurn.setText("Player 2");

                }
                //If it's not player 1s turn
                else {
                    //It is now player 1s turn
                    player1Turn = true;

                    //Setting the text so the user can see who's turn it is
                    playerTurn.setText("Player 1");
                }
            }

            //Adding seconds
            seconds++;

            //If the timer is under 10 seconds
            if (seconds < 10){
                //Setting the value of the timerLabel as 0xs
                timerLabel.setText("0" + (seconds) + "s");
            }
            //If the timer is at 10 seconds or greater
            else {
                //Setting the value of the timerLabel as xxs
                timerLabel.setText((seconds) + "s");
            }

        });
    }

    //Creating the main method
    public static void main(String[] args)throws FileNotFoundException{
        //Importing files from the dictionary into ArrayList
        dictionaryRead(dictionaryWords);

        //Running the main GUI
        new BoggleGame();

        //Running the headsOrTailsFlip method
        headsOrTailsFlip();

        //Generating the random letters
        diceRandomizer(dice);
    }
}