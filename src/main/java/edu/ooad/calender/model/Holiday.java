package edu.ooad.calender.model;

/**
 * @author Thomas Francis
 *
 * Domain object to map Holiday
 */
public class Holiday {

  private int date;
  private String event;

  public Holiday(int date, String event) {
    super();
    this.date = date;
    this.event = event;
  }

  /**
   * Accesor for date
   * @return
   */
  public int getDate() {
    return date;
  }

  /**
   * Acessor for Event 
   * @return String
   */
  public String getEvent() {
    return event;
  }

}
