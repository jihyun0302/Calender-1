package edu.ooad.calender.model;

import static java.util.stream.Collectors.toList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author Thomas Francis
 *
 *Domain object for Month
 *
 */
public class Month {
  private List<Holiday> holidays;
  private List<Integer> numberOfDays;
  private String name;

  public Month(List<Holiday> holidays, int numberOfDays, String name) {
    super();
    this.holidays = holidays;
    this.numberOfDays = IntStream.rangeClosed(1, numberOfDays).boxed().collect(toList());
    this.name = name;
  }

  /**
   * get holidays
   * @return List<Holiday>
   */
  public List<Holiday> getHolidays() {
    return holidays;
  }

  
  /**
   * get number of days
   * @return List<Integer> 
   */
  public List<Integer> getNumberOfDays() {
    return numberOfDays;
  }

  /**
   * get Name of month
   * @return Name
   */
  public String getName() {
    return name;
  }

}
