package com.github.myyk.cracking.chapter7;

import java.util.List;
import java.util.Set;

/**
 * Design a parking lot.
 *
 * Designed a lot with a multi-floors, gates, pass holders, one-time users,
 *   special designated spots, payment before leaving, credit card and cash
 *
 * Differences with the book's answer:
 *   Hrm... I have gotten this question before in an interview and the payments
 *   are always kind of complicated to implement. Maybe I always go there because
 *   I'm practical and see a parking lot as a business, but I like how they just dealt
 *   with the issue of parking vehicles and if they would fit or not. That's way easier
 *   to design in an interview.
 */
public class ParkingLot {
  public class LitSign {
    boolean isOn;
  }
  public class LotFullSign extends LitSign{
  }
  public class FloorFullSign extends LitSign{
    int floor;
  }
  public class Pass {
    long id;
    long accountHolderId;
    PassType type;
    Set<SpotQualities> qualitites;
    Spot assignedSpot; // nullable
    long /*DateTime*/ startDate;
    long /*DateTime*/ expireDate;
  }
  public enum PassType {
    Weekly, Monthly, Yearly, WeekendsOnly, WeekdaysOnly
  }
  public class Car {
    String licensePlate;
    String state;
    Pass pass;
  }
  public class Spot {
    int id;
    int floor;
    Set<SpotQualities> qualitites;
    boolean isReserved;
  }
  public enum SpotQualities {
    Electric, Compact, Oversized, Motorcycle
  }
  // TODO: Use JodaMoney
  public class Money {
    long amount; //smallest denomination
    String currancy; //ISO 4217
  }
  public interface PaymentMethod {
    public void pay(Money money);
    public void refund(Money money);
  }
  public class Cash implements PaymentMethod {
    @Override public void pay(Money money) {}
    @Override public void refund(Money money) {}
  }
  public class CreditCard implements PaymentMethod {
    @Override public void pay(Money money) {}
    @Override public void refund(Money money) {}
  }
  public class ParkingRate {
    Money rate;
    int minimumMinutes;
    int maximumMinutes;
//    Restrictions restrictions;
    // between certain times
    // special event
    // SpotQualities 
  }
  public class PaymentCalculator {
    List<ParkingRate> parkingRates;

    private Money calculatePaymentAmount(Ticket ticket) {
      return null;
    }
  }
  public class Paybooth {
    PaymentCalculator paymentCalculator;
    Ticket currentTicket;
    public void insertTicket(Ticket ticket) { currentTicket = ticket; }
    public Ticket payToExit(PaymentMethod paymentMethod) {
      paymentMethod.pay(paymentCalculator.calculatePaymentAmount(currentTicket));
      return ejectTicket();
    }
    public Ticket ejectTicket() {
      Ticket temp = currentTicket;
      currentTicket = null;
      return temp;
    }

  }
  public class GateConsole {
    Gate gate;
    public Ticket getTicket() { return new Ticket(); }
    public void readPass(Pass pass) {}
    public void exitWithTicket(Ticket ticket) {}
  }
  public class Gate {
    public void openGate() { }
    public void closeGate() { }
  }
  public class Ticket {
    public long /*TODO: use Joda Time*/ entryTime;
  }
}
