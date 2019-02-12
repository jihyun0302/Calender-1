package edu.ooad.calender.calender;

import static java.util.stream.Collectors.toList;
import static javax.swing.BorderFactory.createLineBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class Calender {

  Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();

  public void init() {
    map.put("Jan", generateDays(1, 30));
    map.put("Feb", generateDays(1, 28));
    map.put("Mar", generateDays(1, 30));
    map.put("Apr", generateDays(1, 28));
    map.put("May", generateDays(1, 30));
    map.put("Jun", generateDays(1, 28));
    map.put("Jul", generateDays(1, 30));
    map.put("Aug", generateDays(1, 28));
    map.put("Sep", generateDays(1, 30));
    map.put("Oct", generateDays(1, 28));

  }

  private List<Integer> generateDays(int start, int end) {
    return IntStream.range(start, end).boxed().collect(toList());
  }

  private void displayData() {
    JFrame frame = new JFrame("Calender");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

    JPanel logoPanel = new JPanel();
    logoPanel.add(new JLabel("SCU LOGO"));
    // logoPanel.setSize(new Dimension(JFrame.MAXIMIZED_HORIZ, 2));
    // frame.add(firstPanel);

    JPanel container = new JPanel();
    container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

    JPanel janPanel = new JPanel(new BorderLayout());
    GridLayout gridLayout = new GridLayout(0, 7);
    janPanel.setLayout(gridLayout);

    JPanel febPanel = new JPanel(new BorderLayout());
    febPanel.setLayout(gridLayout);

    container.add(janPanel);
    container.add(febPanel);

    frame.add(logoPanel);
    frame.add(container);
    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

    JScrollPane pane = new JScrollPane(frame, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    // Then, add the jScrojllPane to your frame
    frame.getContentPane().add(pane);
    // frame.add(pane);
    System.out.println("displayed");

    for (int i = 1; i <= 31; i++) {
      janPanel.add(addTextView(i));
      febPanel.add(addTextView(i));
    }

    frame.setVisible(true);
  }

  public JLabel addTextView(int i) {
    JLabel label = new JLabel("" + i, SwingConstants.CENTER);
    label.setSize(1, 1);
    label.setBackground(Color.WHITE);
    label.setBorder(createLineBorder(Color.BLACK, 1, true));
    return label;
  }

  public static void main(String[] args) {
    new Calender().displayData();
  }

}
