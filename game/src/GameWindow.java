/**
 * @author Kim Buckner
 * Date: Jan 13, 2019
 *
 * This is the actual "game". Will have to make some major changes.
 * This is just a "hollow" shell.
 *
 * When you get done, I should see the buttons at the top in the "play" area
 * (NOT a pull-down menu). The only one that should do anything is Quit.
 *
 * Should also see something that shows where the 4x4 board and the "spare"
 * tiles will be when we get them stuffed in.
 *
 * This COULD be part of a package but I choose to make the starting point NOT a
 * package. However all other added elements should certainly sub-packages.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow extends JFrame implements ActionListener
{
  /**
   * because it is a serializable object, need this or javac
   * complains <b>a lot</b>, the ID can be any integer.
   */
  public static final long serialVersionUID=1;

  /*
   * This is so I can try changing the starting point easily. Can certainly be
   * left out altogether. 
   */
  private int startAt=1;
  
  /**
   * Constructor sets the window name using super(), changes the layout,
   * which you really need to read up on, and maybe you can see why I chose
   * this one.
   *
   * @param s
   */

  public GameWindow(String s)
  {
    super(s);
    GridBagLayout gbl=new GridBagLayout();
    setLayout(gbl);
  }

  /**
   * For the buttons
   * @param e is the ActionEvent
   * 
   * BTW can ask the event for the name of the object generating event.
   * The odd syntax for non-java people is that "exit" for instance is
   * converted to a String object, then that object's equals() method is
   * called.
   */

  public void actionPerformed(ActionEvent e) {
    if("exit".equals(e.getActionCommand()))
      System.exit(0);
    if("reset".equals(e.getActionCommand()))
      System.out.println("reset pressed\n");
    if("new".equals(e.getActionCommand()))
      System.out.println("new pressed\n");
    }

  /**
   *  Establishes the inital board
   */

  public void setUp()
  {
    // actually create the array for elements, make sure it is big enough
    
    // Need to play around with the dimensions and the gridx/y values
    // These constraints are going to be added to the pieces/parts I 
    // stuff into the "GridBag".
    // YOU CAN USE any type of constraints you like. Just make it work.
    
    GridBagConstraints basic = new GridBagConstraints();
    basic.gridx=startAt;
    basic.gridy=0;
    //basic.gridwidth=3;
    //basic.gridheight=2;
    basic.insets = new Insets(16, 16, 140, 16);

    // This is really a constant in the GrdiBagConstraints. This way we 
    // don't need to know what type/value it is
    
    basic.fill=GridBagConstraints.BOTH;

    //Here I should create 16 -Elements- to put into my gameBoard
    //THE ELEMENTS CANNOT BE BUTTONS!!!!!!!!
    //I can also just arrange things as I like then have methods, or an
    //argument to the constructor that adds elements. 

    // Now I add each one, modifying the default gridx/y and add
    // it along with the modified constraint

    // And of course I have to add the buttons.
    basic.anchor = GridBagConstraints.NORTH;
    basic.fill=GridBagConstraints.HORIZONTAL;
    basic.gridx=1;
    basic.gridy=0;
    this.addButtons(basic);
    
    basic.gridx=0;
    basic.gridy=1;
    basic.anchor = GridBagConstraints.WEST;
    basic.insets = new Insets(32, 32, 32, 150);
    this.addSide(basic);
    
    basic.gridx=2;
    basic.gridy=1;
    basic.anchor = GridBagConstraints.EAST;
    basic.insets = new Insets(32, 150, 32, 32);
    this.addSide(basic);
    
    basic.gridx=1;
    basic.gridy=1;
    basic.insets = new Insets(0,0,0,0);
    basic.anchor = GridBagConstraints.CENTER;
    this.addCenter(basic);
    
    return;
    
    
  }
  /**
   * Used by setUp() to configure the buttons on a button bar and
   * add it to the gameBoard
   */

  public void addButtons(GridBagConstraints basic){
	  JPanel btnMenu = new JPanel();
	  
	  JButton newGameBtn = new JButton("New Game");
	  JButton resetBtn = new JButton("Reset");
	  JButton quitBtn = new JButton("Quit");
	  
	  btnMenu.setBackground(new Color(254, 211, 48, 255));
	  
	  newGameBtn.setActionCommand("new");
	  newGameBtn.addActionListener(this);
	  
	  resetBtn.setActionCommand("reset");
	  resetBtn.addActionListener(this);
	  
	  quitBtn.setActionCommand("exit");
	  quitBtn.addActionListener(this);
	  
	  btnMenu.add(newGameBtn);
	  btnMenu.add(resetBtn);
	  btnMenu.add(quitBtn);
	  this.add(btnMenu, basic);

    return;
  }
  
  public void addSide(GridBagConstraints basic) {
	  JPanel side = new JPanel();
	  
	  side.setAlignmentX(CENTER_ALIGNMENT);
	  
	  side.setBackground(new Color(0, 0, 0, 0));
	  
	  side.setLayout(new BoxLayout(side, BoxLayout.PAGE_AXIS));
	  
	  for(int i = 0; i < 8; i++) {
		  Tile tile = new Tile("R");
		  tile.setBackground(new Color(69, 170, 242));
		  side.add(tile, side);
		  side.add(Box.createRigidArea(new Dimension(8,8)));
	  }
	  

	  
	  this.add(side, basic);
	  
	  return;
	  
  }
  
  public void addCenter(GridBagConstraints basic) {
	  JPanel board = new JPanel(new GridBagLayout());
	  GridBagConstraints center = new GridBagConstraints();
	  
	  board.setBackground(new Color(0, 0, 0, 0));
	  
	  for(int y = 0; y < 4; y++) {
		  center.gridy=y;
		  for (int x = 0; x < 4; x++) {
			Tile tile = new Tile("xy");
			center.gridx=x;
			
			board.add(tile, center);
		}
	  }
	  
	  this.add(board, basic);
	  
	  return;
  }

};
