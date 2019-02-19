package edu.ooad.calender.calender;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static javax.swing.BorderFactory.createLineBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import edu.ooad.calender.model.Holiday;
import edu.ooad.calender.model.Month;


/**
 * @author Thomas Francis
 * 
 *         Class Calender - an swing based calender for year 2019
 *
 */
public class Calender implements ActionListener {

  private static final String IMAGE_FILE = "SCU.jpg";

  private static final List<String> DAYS_OF_WEEK = asList("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday");

  Map<String, Month> monthMap = new LinkedHashMap<String, Month>();
  Map<String, List<Holiday>> holidaysMap = new HashMap<String, List<Holiday>>();
  private JFrame frame;
  private Dimension screenSize;

  // intialize to 2, January has two blocks empty
  int empty_blocks = 2;

  public Calender() {
    init();
  }

  /**
   * Initialization of the static data
   * 
   */
  public void init() {

    frame = new JFrame("C A L E N D E R");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

    monthMap.put("January", getMonthData("January", 31, asList(new Holiday(1, "New Year"), new Holiday(21, "Birthday of Martin Luther King, Jr."))));
    monthMap.put("February", getMonthData("February", 29, asList(new Holiday(18, "Washington’s Birthday"))));
    monthMap.put("March", getMonthData("March", 31, emptyList()));
    monthMap.put("April", getMonthData("April", 30, emptyList()));
    monthMap.put("May", getMonthData("May", 31, asList(new Holiday(27, "Memorial Day"))));
    monthMap.put("June", getMonthData("June", 30, emptyList()));
    monthMap.put("July", getMonthData("July", 31, asList(new Holiday(4, "Independence Day"))));
    monthMap.put("August", getMonthData("August", 31, emptyList()));
    monthMap.put("September", getMonthData("September", 30, asList(new Holiday(2, "Labor Day"))));
    monthMap.put("October", getMonthData("October", 31, asList(new Holiday(14, "Columbus Day"))));
    monthMap.put("November", getMonthData("November", 30, asList(new Holiday(11, "Veteran's Day"), new Holiday(28, "Thanksgiving Day"))));
    monthMap.put("December", getMonthData("December", 31, asList(new Holiday(25, "Christmas"))));
    screenSize = Toolkit.getDefaultToolkit().getScreenSize();
  }


  /**
   * @param name
   * @param days
   * @param holidays
   * @return Month Object
   */
  private Month getMonthData(String name, int days, List<Holiday> holidays) {
    return new Month(holidays, days, name);
  }

  /**
   * entry method to open calendar
   */
  public void runCalender() {
    JPanel containerPanel = new JPanel();
    BoxLayout boxLayout = new BoxLayout(containerPanel, BoxLayout.Y_AXIS);
    containerPanel.setLayout(boxLayout);
//    containerPanel.setPreferredSize(screenSize);
    containerPanel.add(addLogoPanel());

    JPanel editPanel = addEditPanel();
//    containerPanel.add(editPanel, BorderLayout.EAST);

    JPanel calenderView = getCalenderPanel();
    calenderView.add(getMonthPanel(monthMap.get("March"), calenderView.getPreferredSize()));
    monthMap.values().forEach(month -> calenderView.add(getMonthPanel(month, calenderView.getPreferredSize())));
    calenderView.setAutoscrolls(true);
    containerPanel.add(calenderView);
    
    JScrollPane scrollPane = new JScrollPane(calenderView, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    scrollPane.setBounds(0, 0, 10, 900);
    
    frame.setLocationRelativeTo(null);
    frame.getContentPane().add(scrollPane);
    frame.pack();
    frame.setVisible(true);
  }

  private JPanel getCalenderPanel() {
    JPanel calenderView = new JPanel();
    calenderView.setLayout( new BoxLayout(calenderView, BoxLayout.Y_AXIS));
    calenderView.setSize(getIntValOfDouble(screenSize.getWidth() * 3.5/5), getIntValOfDouble(screenSize.getHeight() * 9/10));
    calenderView.setPreferredSize(new Dimension(getIntValOfDouble(screenSize.getWidth() * 3.4/5), getIntValOfDouble(screenSize.getHeight() * 9/10)));
    calenderView.setAutoscrolls(true);
    return calenderView;
  }

  private JPanel addEditPanel() {
    JPanel editPanel = new JPanel();
//    editPanel.setSize(getIntValOfDouble(screenSize.getWidth() * 2.5/5), getIntValOfDouble(screenSize.getHeight() * 8.5/10));
    editPanel.setPreferredSize(new Dimension(getIntValOfDouble(screenSize.getWidth() * 1.5/5), getIntValOfDouble(screenSize.getHeight() * 9/10)));    
    editPanel.setBackground(Color.MAGENTA);
    editPanel.setLayout(new BoxLayout(editPanel, BoxLayout.Y_AXIS));
    JLabel panelName = new JLabel("Customize Calender");
    JPanel firstPanel = new JPanel();
    JLabel eventNameLabel = new JLabel("Event Name:");
    JTextField eventName = new JTextField("                         ");
    JLabel eventNameDateLabel = new JLabel("Event Date:");
    JTextField eventDate = new JTextField("                         ");
    JButton addEventButton = new JButton("Add Event");
    
    firstPanel.add(eventNameLabel);
    firstPanel.add(eventName);
    firstPanel.add(eventNameDateLabel);
    firstPanel.add(eventDate);
    firstPanel.add(addEventButton);
    
    JLabel textColor = new JLabel("Change Text Color :");
    JComboBox<String> dropDown = new JComboBox<String>();
    dropDown.setName("Change Text Color: ");
    dropDown.addItem("GREEN");
    dropDown.addItem("RED");
    dropDown.addItem("BLUE");
    JLabel historyLabel = new JLabel("History:");
    JTextArea textArea = new JTextArea();
    textArea.setPreferredSize(new Dimension(20,20));
    JButton deleteButton = new JButton("Delete Event");
    editPanel.add(panelName);
    editPanel.add(firstPanel);
    editPanel.add(textColor);
    editPanel.add(dropDown);
    editPanel.add(historyLabel);
    editPanel.add(textArea);
    editPanel.add(deleteButton);
    return editPanel;
  }

  private int getIntValOfDouble(double d) {
    return Double.valueOf(d).intValue();
  }

  /**
   * This add static SCU logo panel
   * 
   * @return Jpanel
   */
  private JPanel addLogoPanel() {
    JPanel logoPanel = new JPanel();
    logoPanel.add(new JLabel(new ImageIcon(getClass().getClassLoader().getResource(IMAGE_FILE))),
        SwingConstants.CENTER);

    JRadioButton day = new JRadioButton("D A Y");
    JRadioButton month = new JRadioButton("M O N T H");
    JRadioButton year = new JRadioButton("Y E A R");

    ButtonGroup radioButtonGroup = new ButtonGroup();
    radioButtonGroup.add(day);
    radioButtonGroup.add(month);
    radioButtonGroup.add(year);

    logoPanel.add(day);
    logoPanel.add(month);
    logoPanel.add(year);
    
    logoPanel.setBackground(Color.GRAY);
    System.out.println(screenSize.getHeight() + " " + screenSize.getWidth());
//    logoPanel.setSize(new Dimension(getIntValOfDouble(screenSize.getWidth()), getIntValOfDouble(screenSize.getHeight()* 1.5/10) ));
    logoPanel.setPreferredSize(new Dimension(getIntValOfDouble(screenSize.getWidth()), getIntValOfDouble(screenSize.getHeight()* 1.2/10)));// TODO update it with dynamic value
    logoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
    return logoPanel;
  }

  /**
   * @param panel
   * @param color
   */
  private void setPanelColor(JPanel panel, Color color) {
    panel.setOpaque(true);
    panel.setBackground(color);
  }

  /**
   * This will retrieve the customize Month Panel for specific details about the month
   * 
   * @param month
   * @param superContainerSize 
   * @return Component
   */
  private Component getMonthPanel(Month month, Dimension superContainerSize) {
    JPanel monthContainer = new JPanel();
    monthContainer.setPreferredSize(new Dimension(getIntValOfDouble(superContainerSize.getWidth() * 1), getIntValOfDouble(superContainerSize.getHeight() * 0.9)));
    monthContainer.setLayout(new BoxLayout(monthContainer, BoxLayout.Y_AXIS));
    monthContainer.add(getMonthNamePanel(month.getName(), monthContainer.getPreferredSize()));
    monthContainer.add(retrieveDaysNamePanel(monthContainer.getPreferredSize()));
    monthContainer.add(processAndGetDayGridPanel(month, monthContainer.getPreferredSize()));
    return monthContainer;
  }

  /**
   * Generate the label for each days and add to grid Panel
   * 
   * @param month
   * @param superContainerGridSize 
   * @return Jpanel
   */
  private JPanel processAndGetDayGridPanel(Month month, Dimension superContainerGridSize) {
    JPanel gridDaysPanel = new JPanel();
    gridDaysPanel.setLayout(new GridLayout(0, 7));
    gridDaysPanel.setPreferredSize(new Dimension(1350, 725));
    gridDaysPanel.setBorder(createLineBorder(Color.black));

    addEmptyLabels(gridDaysPanel, empty_blocks);

    empty_blocks = (empty_blocks + month.getNumberOfDays().size()) % 7;

    boolean firstDayOfTheMonth = true;
    for (int date : month.getNumberOfDays()) {

      JLabel dayLabel = new JLabel(String.valueOf(date), SwingConstants.CENTER);
      dayLabel.setOpaque(true);
      Optional<Holiday> holiday = month.getHolidays().stream().filter(day -> day.getDate() == date).findFirst();
      if (firstDayOfTheMonth) {
        dayLabel.setText(holiday.isPresent() ? date + " (" + holiday.get().getEvent() + ") " : dayLabel.getText());
        dayLabel.setBackground(new Color(111, 155, 68));
        firstDayOfTheMonth = false;
      } else {
        dayLabel.setText(holiday.isPresent() ? date + " (" + holiday.get().getEvent() + ") " : dayLabel.getText());
        dayLabel.setBackground(new Color(179, 206, 152));
      }
      dayLabel.setBorder(createLineBorder(Color.black));
      gridDaysPanel.add(dayLabel);
    }

    addEmptyLabels(gridDaysPanel, 7 - empty_blocks);
    return gridDaysPanel;
  }

  /**
   * 
   * Adding Empty Labels
   * 
   * @param gridDaysPanel
   * @param noOfBlocks
   */
  private void addEmptyLabels(JPanel gridDaysPanel, int noOfBlocks) {
    for (int i = 0; i < noOfBlocks; i++) {
      JLabel label = new JLabel(" ", SwingConstants.CENTER);
      label.setOpaque(true);
      label.setBackground(new Color(237, 249, 224));
      label.setBorder(createLineBorder(Color.black));
      gridDaysPanel.add(label);
    }
  }

  /**
   * Get Month Name Panel
   * 
   * @param name
   * @return Jpanel
   */
  private JPanel getMonthNamePanel(String name, Dimension superContainerSize) {
    JPanel monthNamePanel = new JPanel();
    monthNamePanel.setPreferredSize(new Dimension(1350, 35));
    setPanelColor(monthNamePanel, new Color(108, 122, 95));
    JLabel jLabel = new JLabel(name);
    jLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 19));
    monthNamePanel.add(jLabel, SwingConstants.CENTER);
    return monthNamePanel;
  }

  /**
   * Retrieve static day name panel
   * @param superContainerSize 
   * 
   * @return
   */
  private Component retrieveDaysNamePanel(Dimension superContainerSize) {
    JPanel jPanel = new JPanel();
    jPanel.setLayout(new GridLayout(0, 7));
    jPanel.setPreferredSize(new Dimension(1350, 20));
    for (String dayName : DAYS_OF_WEEK) {
      JLabel label = new JLabel(dayName, SwingConstants.CENTER);
      label.setBorder(createLineBorder(Color.black));
      jPanel.add(label);
    }
    return jPanel;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String actionCommand = e.getActionCommand();
    if (actionCommand.equals("day")) {
      // add functionality for day show
    } else if (actionCommand.equals("month")) {
      // add functionality for the month show
    } else {
      // add data for year
    }
  }
}
