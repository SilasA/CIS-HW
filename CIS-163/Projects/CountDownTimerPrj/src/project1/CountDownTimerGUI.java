/**
 * A GUI for {@link project1.CountDownTimer}.
 *
 * Has functionality for multiple timer manipulation and saving.
 *
 * @author Silas Agnew
 * @version January 16, 2018
 */

package project1;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class CountDownTimerGUI extends JFrame implements ActionListener,
                                                ListSelectionListener
{
    // Labels
    private JLabel timerLabel;
    private JLabel infoLabel;
    private JLabel manipLabel;
    private JLabel timerValueLabel;
    private JLabel startValueLabel;

    // Buttons
    private JButton addTimerBtn;
    private JButton removeTimerBtn;
    private JButton startBtn;
    private JButton stopBtn;
    private JButton incrBtn;
    private JButton decrBtn;
    private JButton suspendAllBtn;

    // Text
    private JList<CountDownTimer> timerArea;
    private JTextField    timerValueField;
    private JTextField    startValueField;

    // Menu
    private JMenuBar  menus;
    private JMenu     fileMenu;
    private JMenu     aboutMenu;
    private JMenuItem aboutItem;
    private JMenuItem saveItem;
    private JMenuItem loadItem;
    private JMenuItem closeItem;

    /**
     * Timer list that contains the {@link CountDownTimer} objects
     * for the {@link JList} {@code timerArea}.
     */
    private DefaultListModel<CountDownTimer> timerList;
    
    /**
     * Timer that is used to actually countdown the {@link CountDownTimer}
     * s in {@code timerArea}.
     */
    private Timer timer;
    
    /**
     * Whether or not the timers are suspended
     * Used for toggling the suspend button
     */
    private boolean suspended = false;
    
    /*******************************************************************
     * Instantiates a GUI for {@link CountDownTimer}.
     ******************************************************************/
    public CountDownTimerGUI()
    {
        timerList = new DefaultListModel<>();
        timer = new Timer(1000, e -> updateTimerInfo());
        timer.start();

        // Labels
        timerLabel = new JLabel("Timers");
        infoLabel = new JLabel("Info");
        manipLabel = new JLabel("Manipulation");
        timerValueLabel = new JLabel("Timer Value");
        startValueLabel = new JLabel("Start Value");

        // Button
        addTimerBtn = new JButton("Add Timer");
        addTimerBtn.setToolTipText(
                "Adds a timer to the container with the start time value");
        removeTimerBtn = new JButton("Remove Timer");
        removeTimerBtn.setToolTipText(
                "Removes the selected timer");
        startBtn = new JButton("Start");
        startBtn.setToolTipText("Starts the timer counting down");
        stopBtn = new JButton("Stop");
        stopBtn.setToolTipText("Stops the timer from counting");
        incrBtn = new JButton("Increment");
        incrBtn.setToolTipText("Increases the timer value by 1 second");
        decrBtn = new JButton("Decrement");
        decrBtn.setToolTipText("Decreases the timer value by 1 second");
        suspendAllBtn = new JButton("Suspend All");
        suspendAllBtn.setToolTipText(
                "Toggle suspension of all addition actions to all the timers");

        // Text
        timerArea = new JList<>(timerList);
        timerArea.setToolTipText("Select or Add a timer");
        timerArea.addListSelectionListener(this);
        timerValueField = new JTextField("h:mm:ss", 6);
        startValueField = new JTextField("h:mm:ss", 6);

        // Menu
        menus = new JMenuBar();
        fileMenu = new JMenu("File");
        aboutMenu = new JMenu("About");
        saveItem = new JMenuItem("Save");
        loadItem = new JMenuItem("Load");
        closeItem = new JMenuItem("Close");
        aboutItem = new JMenuItem("About");

        setupComponents();
    }

    /*******************************************************************
     * Sets up layout of GUI components.
     ******************************************************************/
    public void setupComponents()
    {
        setupMenus();
        
        setLayout(new GridBagLayout());
        GridBagConstraints loc = new GridBagConstraints();
        
        // Timer area
        loc.gridx = 0;
        loc.gridy = 0;
        loc.insets.top = 5;
        add(timerLabel, loc);
    
        JScrollPane scrollPane = new JScrollPane(timerArea);
        add(scrollPane, loc);
        
        loc.gridy = 1;
        loc.insets.left = 5;
        loc.insets.right = 5;
        loc.insets.top = 5;
        loc.insets.bottom = 5;
        loc.gridheight = 7;
        timerArea.setCellRenderer(new DefaultListCellRenderer());
        timerArea.setVisible(true);
        timerArea.setPreferredSize(new Dimension(220, 250));
        add(timerArea, loc);
        
        // Info area
        loc.gridx = 1;
        loc.gridy = 0;
        loc.gridwidth = 2;
        loc.gridheight = 1;
        add(infoLabel, loc);
        
        loc.gridy = 1;
        loc.gridx = 1;
        loc.gridwidth = 1;
        add(timerValueLabel, loc);
        
        loc.gridx = 2;
        add(timerValueField, loc);
        
        // Manipulation area
        loc.gridx = 1;
        loc.gridy = 2;
        loc.gridwidth = 2;
        add(manipLabel, loc);
        
        loc.gridy = 3;
        loc.gridwidth = 1;
        loc.anchor = GridBagConstraints.LINE_START;
        loc.fill = GridBagConstraints.HORIZONTAL;
        add(startBtn, loc);
        startBtn.addActionListener(this);
    
        loc.gridy = 3;
        loc.gridx = 2;
        add(stopBtn, loc);
        stopBtn.addActionListener(this);
    
        loc.gridy = 4;
        loc.gridx = 1;
        add(incrBtn, loc);
        incrBtn.addActionListener(this);
    
        loc.gridy = 4;
        loc.gridx = 2;
        add(decrBtn, loc);
        decrBtn.addActionListener(this);
    
        loc.gridy = 5;
        loc.gridx = 1;
        add(startValueLabel, loc);
    
        loc.gridy = 5;
        loc.gridx = 2;
        add(startValueField, loc);
        
        loc.gridy = 6;
        loc.gridx = 1;
        loc.anchor = GridBagConstraints.NORTH;
        add(addTimerBtn, loc);
        addTimerBtn.addActionListener(this);
    
        loc.gridy = 6;
        loc.gridx = 2;
        add(removeTimerBtn, loc);
        removeTimerBtn.addActionListener(this);
        
        loc.gridy = 7;
        loc.gridx = 1;
        loc.gridwidth = 2;
        add(suspendAllBtn, loc);
        suspendAllBtn.addActionListener(this);
    }
    
    /*******************************************************************
     * Sets up the toolbar menus
     ******************************************************************/
    public void setupMenus()
    {
        saveItem.addActionListener(this);
        loadItem.addActionListener(this);
        closeItem.addActionListener(this);
        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        fileMenu.add(closeItem);
        
        aboutItem.addActionListener(this);
        aboutMenu.add(aboutItem);
        
        menus.add(fileMenu);
        menus.add(aboutMenu);
    
        setJMenuBar(menus);
    }
    
    /*******************************************************************
     * Updates the display of the selected timers info
     ******************************************************************/
    public void updateTimerInfo()
    {
        if (timerArea.getSelectedIndex() < 0)
            timerValueField.setText("h:mm:ss");
        else
            timerValueField.setText(timerArea.getSelectedValue().toString());
    }
    
    /*******************************************************************
     * Handles a button press event from the GUI.
     *
     * @param e Event information
     ******************************************************************/
    @Override
    public void actionPerformed(ActionEvent e)
    {
        JComponent component = (JComponent)e.getSource();
        
        if (component == addTimerBtn)
        {
            CountDownTimer c;
            try
            {
                c = new CountDownTimer(startValueField.getText());
            }
            catch (NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(
                        this, "Please put a start value in to the text box");
                return;
            }
            timerList.addElement(c);
        }
        else if (component == removeTimerBtn)
        {
            if (timerArea.getSelectedIndex() >= 0)
            {
                timer.removeActionListener(timerArea.getSelectedValue());
                timerList.removeElement(timerArea.getSelectedValue());
            }
        }
        else if (component == startBtn)
        {
            timer.addActionListener(timerArea.getSelectedValue());
        }
        else if (component == stopBtn)
        {
            timer.removeActionListener(timerArea.getSelectedValue());
        }
        else if (component == incrBtn)
        {
            if (timerArea.getSelectedValue() != null)
                timerArea.getSelectedValue().inc();
        }
        else if (component == decrBtn)
        {
            if (timerArea.getSelectedValue() != null)
                timerArea.getSelectedValue().dec();
        }
        else if (component == saveItem)
        {
            if (timerArea.getSelectedValue() != null)
            {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter(
                        "Text Files", ".txt"));
                if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
                {
                    timerArea.getSelectedValue().save(fileChooser.getSelectedFile().getAbsolutePath());
                }
            }
        }
        else if (component == loadItem)
        {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter(
                    "Text Files", "txt"));
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
            {
                CountDownTimer c = new CountDownTimer();
                c.load(fileChooser.getSelectedFile().getAbsolutePath());
                timerList.addElement(c);
            }
        }
        else if (component == closeItem)
        {
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
        else if (component == aboutItem)
        {
            JOptionPane.showMessageDialog(this,
                    "Designed by Silas Agnew 2018");
        }
        else if (component == suspendAllBtn)
        {
            suspended = !suspended;
            CountDownTimer.suspend(suspended);
        }
        updateTimerInfo();
    }
    
    /*******************************************************************
     * Updates {@code TimerArea} when a selection is made by the user
     *
     * @param e Event information
     ******************************************************************/
    @Override
    public void valueChanged(ListSelectionEvent e)
    {
        updateTimerInfo();
    }
}
