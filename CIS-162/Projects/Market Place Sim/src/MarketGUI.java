import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*************************************************************
 * GUI for a MarketPlace Simulator
 * 
 * @author Scott Grissom, Silas Agnew
 * @version November 21, 2017
 ************************************************************/
public class MarketGUI extends JFrame implements ActionListener{

    /** MarketPlace */
    MarketPlace store;

    /** Buttons */
    JButton simulateBtn;

    /** Text Fields */
    JTextField cashierField;
    JTextField avgArrivalField;
    JTextField avgServiceField;

    /** Checkbox */
    JCheckBox displayCheck;

    /** Results text area */
    JTextArea resultsArea;

    /** Menu items */
    JMenuBar  menus;
    JMenu     fileMenu;
    JMenuItem quitItem;
    JMenuItem clearItem;

    // Parameters
    int cashiers;
    double avgServiceTime;
    double avgArrivalTime;
    boolean displayCheckout;

    /*****************************************************************
     * Main Method
     ****************************************************************/ 
    public static void main(String args[]){
        MarketGUI gui = new MarketGUI();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setTitle("MarketPlace Simulator");
        gui.pack();
        gui.setVisible(true);
    }

    /*****************************************************************
     * constructor installs all of the GUI components
     ****************************************************************/    
    public MarketGUI(){
        // Database
        store = new MarketPlace();
        
        // set the layout to GridBag
        setLayout(new GridBagLayout());
        GridBagConstraints loc = new GridBagConstraints();

        // create results area to span one column and 10 rows
        resultsArea = new JTextArea(20,20);
        JScrollPane scrollPane = new JScrollPane(resultsArea);
        loc.gridx = 0;
        loc.gridy = 1;
        loc.gridheight = 10;  
        loc.insets.left = 20;
        loc.insets.right = 20;
        loc.insets.bottom = 20;
        add(scrollPane, loc);  

        // create Results label
        loc = new GridBagConstraints();
        loc.gridx = 0;
        loc.gridy = 0;
        loc.insets.bottom = 20;
        loc.insets.top = 20;
        add(new JLabel("Results"), loc);

        // create Parameters label
        loc = new GridBagConstraints();
        loc.gridx = 1;
        loc.gridy = 0;
        loc.gridwidth = 2;
        add(new JLabel("Parameters"), loc);

        // create Cashier label
        loc = new GridBagConstraints();
        loc.gridx = 1;
        loc.gridy = 1;
        add(new JLabel("Cashier"), loc);

        // create Avg Arrival time label
        loc = new GridBagConstraints();
        loc.gridx = 1;
        loc.gridy = 2;
        loc.insets.top = 5;
        add(new JLabel("Avg Arrival Time"), loc);

        // create Avg Service time label
        loc = new GridBagConstraints();
        loc.gridx = 1;
        loc.gridy = 3;
        loc.insets.top = 5;
        add(new JLabel("Avg Service Time"), loc);

        // create display checkbox
        displayCheck = new JCheckBox("Display");
        loc = new GridBagConstraints();
        loc.gridx = 2;
        loc.gridy = 4;
        add(displayCheck, loc);

        // create simulate sort button
        simulateBtn = new JButton("Simulate");
        loc = new GridBagConstraints();
        loc.gridx = 2;
        loc.gridy = 5;
        loc.anchor = GridBagConstraints.WEST;
        add(simulateBtn, loc);

        // create Cashier text field
        cashierField = new JTextField(5);
        loc = new GridBagConstraints();
        loc.gridx = 2;
        loc.gridy = 1;
        loc.anchor = GridBagConstraints.WEST;
        loc.insets.left = 5;
        add(cashierField, loc);
	
		// create Average Arrival time text field
        avgArrivalField = new JTextField(10);
        loc = new GridBagConstraints();
        loc.gridx = 2;
        loc.gridy = 2;
        loc.anchor = GridBagConstraints.WEST;
        loc.insets.top = 5;
        loc.insets.left = 5;
        add(avgArrivalField, loc);

        // create Average Service time text field
        avgServiceField = new JTextField(10);
        loc = new GridBagConstraints();
        loc.gridx = 2;
        loc.gridy = 3;
        loc.anchor = GridBagConstraints.WEST;
        loc.insets.top = 5;
        loc.insets.left = 5;
        add(avgServiceField, loc);
	
		simulateBtn.addActionListener(this);

        // hide details of creating menus
        setupMenus();
    }

    /*****************************************************************
     * This method is called when any button is clicked.  The proper
     * internal method is called as needed.
     * 
     * @param e the event that was fired
     ****************************************************************/       
    public void actionPerformed(ActionEvent e){

        // extract the button that was clicked
        JComponent buttonPressed = (JComponent) e.getSource();

        // Allow user to load baby names from a file    
        if (buttonPressed == clearItem)
        {
            resultsArea.setText("");
		}
		else if (buttonPressed == quitItem)
		{
            System.exit(0);
		}
		else if (buttonPressed == simulateBtn)
        {
            if (parseParameters())
            {
                resultsArea.setText("");
                store.setParameters(cashiers, avgServiceTime,
                        avgArrivalTime, displayCheckout);
                store.startSimulation();
                resultsArea.append(store.getReport());
            } else return;
        }
    }

    /*******************************************************
    Creates the menu items
     *******************************************************/    
    private void setupMenus(){
        fileMenu = new JMenu("File");
        quitItem = new JMenuItem("Quit");
        clearItem = new JMenuItem("Clear");
        fileMenu.add(clearItem);
        fileMenu.add(quitItem);
        menus = new JMenuBar();
        setJMenuBar(menus);
        menus.add(fileMenu);

		clearItem.addActionListener(this);
		quitItem.addActionListener(this);
    }

    //-Private Helper Methods--------------------------------------//

    /**
     * Parses simulation parameters from the associated text fields.
     * @return If all parameters were successfully parsed
     */
    private boolean parseParameters()
    {
        cashiers = 0;
        avgServiceTime = 0;
        avgArrivalTime = 0;
        try {
            cashiers = Integer.parseInt(cashierField.getText());
            if (cashiers < 1) throw new IllegalArgumentException();
        } catch (Exception ex)
        {
            JOptionPane.showMessageDialog(this,
                    "Text in the cashier field is ill-formatted.");
            return false;
        }
        try {
            avgArrivalTime = Double.parseDouble(avgArrivalField.getText());
            if (avgArrivalTime < .001) throw new IllegalArgumentException();
        } catch (Exception ex)
        {
            JOptionPane.showMessageDialog(this,
                    "Text in the Avg Arrival Time field is ill-formatted.");
            return false;
        }
        try {
            avgServiceTime = Double.parseDouble(avgServiceField.getText());
            if (avgServiceTime < .001) throw new IllegalArgumentException();
        } catch (Exception ex)
        {
            JOptionPane.showMessageDialog(this,
                    "Text in the Avg Service Time field is ill-formatted.");
            return false;
        }

        displayCheckout = displayCheck.isSelected();
        return true;
    }
}