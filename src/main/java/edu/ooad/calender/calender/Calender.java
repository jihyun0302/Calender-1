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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import edu.ooad.calender.model.Holiday;
import edu.ooad.calender.model.Month;


/**
 * @author Thomas Francis
 * 
 * Class Calender - an swing based calender for year 2019
 *
 */
public class Calender {

  private static final String IMAGE_FILE = "SCU.jpg";

  private static final List<String> DAYS_OF_WEEK =
      asList("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday");

  Map<String, Month> monthMap = new LinkedHashMap<String, Month>();
  Map<String, List<Holiday>> holidaysMap = new HashMap<String, List<Holiday>>();

  // intialize to 2, January has two blocks empty
  int empty_blocks = 2;

  public Calender(){
    init();
  }

  /**
   * Initialization of the static data
   * 
   */
  public void init() {
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
   * entry method to open calender
   */
  public void runCalender() {
    JFrame frame = new JFrame("Calender");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

    JPanel containerPanel = new JPanel();
    containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
    containerPanel.add(addLogoPanel());

    // Adding month panels
    monthMap.values().forEach(month -> containerPanel.add(getMonthPanel(month)));

    JScrollPane scrollPane = new JScrollPane(containerPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    
    frame.getContentPane().add(scrollPane, BorderLayout.EAST);
    frame.pack();
    frame.setVisible(true);
  }

  /**
   * This add static SCU logo panel
   * @return Jpanel
   */
  private JPanel addLogoPanel() {
    JPanel logoPanel = new JPanel();
    logoPanel.add(new JLabel(new ImageIcon(getClass().getClassLoader().getResource(IMAGE_FILE))), SwingConstants.CENTER);
    logoPanel.setPreferredSize(new Dimension(1350, 90));
    logoPanel.setBorder(createLineBorder(Color.black));
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
   * This will retrieve the customize Month Panel for specific details about the 
   * month
   * @param month
   * @return Component
   */
  private Component getMonthPanel(Month month) {
    JPanel monthContainer = new JPanel();
    monthContainer.setLayout(new BoxLayout(monthContainer, BoxLayout.Y_AXIS));
    JPanel gridDaysPanel = processAndGetDayGridPanel(month);
    monthContainer.add(getMonthNamePanel(month.getName()));
    monthContainer.add(retrieveDaysNamePanel());
    monthContainer.add(gridDaysPanel);
    return monthContainer;
  }

  /**
   * Generate the label for each days and add to grid Panel
   * @param month
   * @return Jpanel
   */
  private JPanel processAndGetDayGridPanel(Month month) {
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
      Optional<Holiday> holiday =
          month.getHolidays().stream().filter(day -> day.getDate() == date).findFirst();
      if (firstDayOfTheMonth) {
        dayLabel.setText(holiday.isPresent() ? date + " (" + holiday.get().getEvent() + ") "
            : dayLabel.getText());
        dayLabel.setBackground(new Color(111, 155, 68));
        firstDayOfTheMonth = false;
      } else {
        dayLabel.setText(holiday.isPresent() ? date + " (" + holiday.get().getEvent() + ") "
            : dayLabel.getText());
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
   * @param name
   * @return Jpanel
   */
  private JPanel getMonthNamePanel(String name) {
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
   * @return
   */
  private Component retrieveDaysNamePanel() {
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
}
