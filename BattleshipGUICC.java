//Music sound track for game. Play it in the background. https://youtu.be/brIkzQaaNz4?t=4m4s
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.TitledBorder;
import java.io.*;
import java.util.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

public class BattleshipGUICC {

	private static int letterCord;
	private static int numCord;
	private static JButton pBtn[][];
	private static JButton cBtn[][];
	private static JLabel playerLbl;
	private static JLabel cpuLbl;
	private static JFrame frame;
	private static char[][] playerMap = new char[10][10];
	private static char[][] cpuMap = new char[10][10];
	private static String pathToPlayer;
	private static String pathToCpu;
	private static int[] fleetHp = { 5, 5 };
	private static int[] botData = { 1, 0, 0, 0, 1, 0, 0 }; // botData[7] boats, botData[8] oldboats
	private static int[] shipsHp = { 4, 5, 3, 2, 3, 4, 5, 3, 2, 3 };
			
	public static void sunken() { //efficient way to check if ships have been sunken. Uses a hp system, reducing a ships hp when its letter on the map is hit. Also checks for a win.
		String possibleShips = "BCSPD";
		String player = "You have sunk ";
		String[] shipNames = { "Battleship", "Aircraft Carrier", "Submarine", "Patrol Boat", "Destroyer", "Battleship",
				"Aircraft Carrier", "Submarine", "Patrol Boat", "Destroyer" };
		for (int i = 0; i < 10; i++) {
			if (shipsHp[i] == 0) {
				shipsHp[i]--;
				if (i > 4) {
					cpuLbl.setText("<html>" + cpuLbl.getText() + "<br>The ChrisBot has sunk the " + shipNames[i] + "</html>");
					fleetHp[1]--;
					botData[3] = 1;
				} else {
					playerLbl.setText("<html>" + playerLbl.getText() + "<br>You have sunk the " + shipNames[i] + "</html>");
					fleetHp[0]--;
				}
			}
		}
		if (fleetHp[0] == 0 || fleetHp[1] == 0 ) {
			for (int x = 0; x < 10; x++) {
				for (int y = 0; y < 10; y++) {
					pBtn[x][y].setEnabled(false);
					cBtn[x][y].setEnabled(false);
				}
			}
			if (fleetHp[0] == 0)
				JOptionPane.showMessageDialog(frame, "The user has won the game!");
			else if (fleetHp[1] == 0)
				JOptionPane.showMessageDialog(frame, "The cpu has won the game!");
		}			
	}

	private static void initGui() { //iniates the GUI 
		String letters = "ABCDEFGHIJ";
		frame = new JFrame("BattleshipGUI");
		JPanel panel = new JPanel();
		JMenuItem importItem, restartItem, exitItem;
		JMenuBar menuBar = menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		importItem = new JMenuItem("Open");
		restartItem = new JMenuItem("Restart Game");
		exitItem = new JMenuItem("Exit");
		menu.add(importItem);
		menu.add(restartItem);
		menu.add(exitItem);
		menuBar.add(menu);
		restartItem.addActionListener(new MenuListener());
		exitItem.addActionListener(new MenuListener());
		importItem.addActionListener(new MenuListener());
		JPanel playerGrid = new JPanel(new GridLayout(10, 10));
		JPanel cpuGrid = new JPanel(new GridLayout(10, 10));
		JPanel playerContainer = new JPanel();
		JPanel cpuContainer = new JPanel();
		JPanel colsContainer = new JPanel();
		JPanel cpuMsgContainer = new JPanel();
		JPanel playerMsgContainer = new JPanel();
		JLabel rows = new JLabel(new ImageIcon("rows.png"));
		JLabel rows1 = new JLabel(new ImageIcon("rows.png"));
		JLabel cols = new JLabel(new ImageIcon("cols.png"));
		playerLbl = new JLabel("Please open the PLAYER.txt file!");
		cpuLbl = new JLabel("Please open the CPU.txt file!");
		pBtn = new JButton[10][10];
		cBtn = new JButton[10][10];
		TitledBorder cpuTitle = BorderFactory.createTitledBorder("CPU Messages");
		cpuTitle.setTitleColor(Color.RED);
		TitledBorder playerTitle = BorderFactory.createTitledBorder("Player Messages");
		playerTitle.setTitleColor(Color.BLUE);
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				pBtn[x][y] = new JButton("*");
				playerGrid.add(pBtn[x][y]);
				cBtn[x][y] = new JButton("*");
				cBtn[x][y].addActionListener(new MakingMoves());
				cBtn[x][y].setActionCommand(String.valueOf(letters.charAt(x)) + y);
				cpuGrid.add(cBtn[x][y]);
				pBtn[x][y].setForeground(Color.blue);
				cBtn[x][y].setForeground(Color.red);
				pBtn[x][y].setBackground(new Color(0, 0, 0, 0));
				cBtn[x][y].setBackground(new Color(0, 0, 0, 0));
				pBtn[x][y].setContentAreaFilled(false);
				cBtn[x][y].setContentAreaFilled(false);
				cBtn[x][y].setEnabled(false);
			}
		}
		playerMsgContainer.add(playerLbl);
		cpuMsgContainer.add(cpuLbl);
		playerContainer.add(rows1);
		playerContainer.add(playerGrid);
		cpuContainer.add(rows);
		cpuContainer.add(cpuGrid);
		playerLbl.setPreferredSize(new Dimension(1155, 70));
		cpuLbl.setPreferredSize(new Dimension(1155, 70));
		playerGrid.setPreferredSize(new Dimension(500, 500));
		cpuGrid.setPreferredSize(new Dimension(500, 500));
		cpuContainer.setPreferredSize(new Dimension(575, 515));
		playerContainer.setPreferredSize(new Dimension(575, 515));
		colsContainer.add(cols);
		panel.add(colsContainer);
		panel.add(playerContainer);
		panel.add(cpuContainer);
		panel.add(playerMsgContainer);
		panel.add(cpuMsgContainer);
		cpuLbl.setFont(new Font("Arial", Font.BOLD, 16));
		cpuLbl.setForeground(Color.RED);
		playerLbl.setFont(new Font("Arial", Font.BOLD, 16));
		playerLbl.setForeground(Color.BLUE);
		playerContainer.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		cpuContainer.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		playerLbl.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		playerLbl.setBorder(playerTitle);

		cpuLbl.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		cpuLbl.setBorder(cpuTitle);
		((javax.swing.border.TitledBorder) cpuLbl.getBorder()).setTitleFont(new Font("Arial", Font.BOLD, 16));
		((javax.swing.border.TitledBorder) playerLbl.getBorder()).setTitleFont(new Font("Arial", Font.BOLD, 16));
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setSize(1185, 810);
		frame.setJMenuBar(menuBar);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(panel);
		frame.getContentPane().setBackground(Color.WHITE);
	}

	public static void chrisBot() { //Intelligent cpu. Once it gets a hit it locks on the rest of the ship by attacking cordinates around the hit. Uses same information the the player has.
		String letters = "ABCDEFGHIJ";
		String possibleShips = "BCSPD";
		int xCord = 0, yCord = 0;
		if (botData[3] == 1) { // BotData hold data in this order: [0]mode, [1]lastX, [2]lastY, [3]boatsSunken,[4]attempt
			botData[0] = 1;
			botData[4] = 0;
			botData[3] = 0;
		}
		if (botData[0] == 2) { // Mode 2, once the bot has got a hit.
			while (true) {
				if (botData[4] <= 5) {
					xCord = botData[1] + 1; // attacks to the right if it got a hit to it continues until is misses or a ship is sunken.
					yCord = botData[2];
				} else if (botData[4] <= 10) { // attacks left ditto as said above.
					xCord = botData[1] - 1;
					yCord = botData[2];
				} else if (botData[4] <= 15) { // attacks below ditto as said above.
					xCord = botData[1];
					yCord = botData[2] + 1;
				} else if (botData[4] <= 25) { // attacks above ditto as said above.
					xCord = botData[1];
					yCord = botData[2] - 1;
				}
				if (playerMap[yCord][xCord] == 'H') { // if the bot hits in the middle of a ship and destroys the parts in that direction, this code allows the bot to attack the other side of the boat.
					botData[1] = botData[5];
					botData[2] = botData[6];
				} else if (playerMap[yCord][xCord] == 'M') { // if the bot gets a miss botData[4] gets 6 added to it moving son to the next attack sequence.
					botData[4] += 6;
				} else
					break;
			}
		}

		if (botData[0] == 1) {
			while (true) {
				xCord = (int) (Math.random() * 10); // attacks a random cord when in mode 1.
				yCord = (int) (Math.random() * 10);
				if (playerMap[yCord][xCord] != 'M' && playerMap[yCord][xCord] != 'H')
					break;
			}
		}
		if (playerMap[yCord][xCord] == '*' || playerMap[yCord][xCord] == 'M') {
			playerMap[yCord][xCord] = 'M';
			pBtn[yCord][xCord].setIcon(new ImageIcon("M.png"));
			cpuLbl.setText("The ChrisBot has attacked " + letters.charAt(yCord) + xCord + " and missed!");
			if (botData[0] == 2)
				botData[4] += 6;
		} else {
			shipsHp[5 + possibleShips.indexOf(String.valueOf(playerMap[yCord][xCord]))]--;
			playerMap[yCord][xCord] = 'H';
			pBtn[yCord][xCord].setIcon(new ImageIcon("H.png"));
			cpuLbl.setText("The ChrisBot has attacked " + letters.charAt(yCord) + xCord + " and hit!");
			if (botData[0] == 1) {
				botData[5] = xCord;
				botData[6] = yCord;
			}
			botData[0] = 2;
			botData[1] = xCord; // updates bot on last coordinates when there is a hit.
			botData[2] = yCord;
		}
		if (botData[0] == 2)
			if (xCord == 9 && botData[4] <= 5 || xCord == 0 && botData[4] <= 10 && botData[4] > 5
					|| yCord == 9 && botData[4] <= 15 && botData[4] > 10
					|| yCord == 0 && botData[4] <= 20 && botData[4] > 15) // makes sure that the bot doesnt cause an out of bound error.
				botData[4] += 6;
	}

	public static void loadFile(char[][] gameBoard, String fileName) throws IOException {
		BufferedReader inputStream = null;
		try {
			inputStream = new BufferedReader(new FileReader(fileName));
			for (int y = 0; y < 10; y++) {
				String lineRead = inputStream.readLine();
				for (int x = 0; x < 10; x++) {
					gameBoard[y][x] = lineRead.split(" ", 0)[x].charAt(0);
				}
			}
		} catch (FileNotFoundException exception) {
			System.out.println("Error: '" + fileName + "' could not be loaded.");
		} finally {
			if (inputStream != null)
				inputStream.close();
		}
	}

	private static class MenuListener implements ActionListener {

		private static int whoOpen = 0;

		public static void loadBoards() throws IOException {
			loadFile(playerMap, pathToPlayer);
			loadFile(cpuMap, pathToCpu);
			for (int x = 0; x < 10; x++) {
				for (int y = 0; y < 10; y++) {
					pBtn[x][y].setText(Character.toString(playerMap[x][y]));
					cBtn[x][y].setEnabled(true);
				}
			}
			
		}

		public void actionPerformed(ActionEvent e) {
			String event = e.getActionCommand();
			if (event.equals("Open")) {
				JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				int returnValue = chooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = chooser.getSelectedFile();
					if (whoOpen == 0) {
						pathToPlayer = selectedFile.getAbsolutePath();
						playerLbl.setText("Player Board selected (" + pathToPlayer + ")");
						whoOpen++;
					} else if (whoOpen == 1) {
						pathToCpu = selectedFile.getAbsolutePath();
						cpuLbl.setText("CPU Board selected (" + pathToCpu + ")");
						whoOpen++;
						try {
							loadBoards();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(frame, "You already selected both the CPU and player boards!");
					}
				}
			} else if (event.equals("Restart Game")) {
				whoOpen = 0;
				fleetHp[0] = 5;
				fleetHp[1] = 5;
				botData[0] = 1;
				botData[1] = 0;
				botData[2] = 0;
				botData[3] = 0;
				botData[4] = 1;
				botData[5] = 0;
				botData[6] = 0;
				shipsHp[0] = 4;
				shipsHp[1] = 5;
				shipsHp[2] = 3;
				shipsHp[3] = 2;
				shipsHp[4] = 3;
				shipsHp[5] = 4;
				shipsHp[6] = 5;
				shipsHp[7] = 3;
				shipsHp[8] = 2;
				shipsHp[9] = 3; 
				frame.setVisible(false);
				frame.dispose();
				initGui();
				JOptionPane.showMessageDialog(frame, "Game restarted!");
				
			} else if (event.equals("Exit")) {
				System.exit(0);
			}
		}
	}

	public static void main(String[] args) {
		initGui();
	}

	private static class MakingMoves implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			String letters = "ABCDEFGHIJ";
			String command = event.getActionCommand();
			letterCord = letters.indexOf(String.valueOf(command.charAt(0))); // converts the letter into a number 0-9
			numCord = Integer.parseInt(command.replaceAll("\\D+",""));
			String possibleShips = "BCSPD";
			int winner = 0;
			cBtn[letterCord][numCord].removeActionListener(this); // disable re-click
			if (cpuMap[letterCord][numCord] == '*') {
				cBtn[letterCord][numCord].setIcon(new ImageIcon("M.png"));	
				cpuMap[letterCord][numCord] = 'M';
			} else {
				shipsHp[possibleShips.indexOf(String.valueOf(cpuMap[letterCord][numCord]))]--;
				cBtn[letterCord][numCord].setIcon(new ImageIcon("H.png"));
				cpuMap[letterCord][numCord] = 'H';
			}
			chrisBot();
			if (cpuMap[letterCord][numCord] == 'M')
				playerLbl.setText("You have missed sir!");
			else
				playerLbl.setText("Direct hit, nice shot sir!");
			sunken();
		}
	}
}