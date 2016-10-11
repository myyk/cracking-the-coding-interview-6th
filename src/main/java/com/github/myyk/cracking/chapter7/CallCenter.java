package com.github.myyk.cracking.chapter7;

import java.util.Collection;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Implement a Call Center as specified in the book.
 *
 * Differences with the book's answer:
 *   - I kind of like that they had the 'currentCall' on the Employee, that way you
 *   can see who is handling a call at a given time
 *
 * Improvements I'd do in real life:
 *   - This actually fits an Actor model very well. I think it would be appropriate to
 *   use Akka Actors do do this job.
 */
public class CallCenter {
  public static abstract class Employee implements Comparable<Employee> {
    Manager manager;

    private boolean available = true;
    public boolean isAvailable() { return available; }
    public void makeBusy() { available = false; }
    public void makeFree() { available = true; }

    public Priority priority;

    @Override
    public int compareTo(Employee o) {
      if (o == null) { return -1; }

      return this.priority.compareTo(o.priority);
    }

    public boolean canHandleCall(Call call) {
      return isAvailable() && isAbleToHandle(call);
    }

    abstract public boolean isAbleToHandle(Call call);

    abstract public void handleCall(Call call);
    
  }

  /**
   * This way if we add new employee types we don't have to touch each class,
   * just order them here. Also we can view priorities in one place.
   */
  public static enum Priority {
    Respondent, Manager, Director
  }

  public static class Respondent extends Employee {
    public Priority priority() { return Priority.Respondent; }

    @Override
    public boolean isAbleToHandle(Call call) {
      return call.hashCode() % 2 == 0;
    }

    @Override
    public void handleCall(Call call) {
      // do stuff
    }
  }
  public static class Manager extends Employee {
    Manager manager;
    Employee[] directReports;

    public Priority priority() { return Priority.Manager; }

    @Override
    public boolean isAbleToHandle(Call call) {
      return call.hashCode() % 100 != 99;
    }

    @Override
    public void handleCall(Call call) {
      // do stuff
    }
  }
  public static class Director extends Manager {
    public Priority priority() { return Priority.Director; }
  }

  public static class Call {}

  /* LIFO to do a crude distribution of work over employees */
  final PriorityQueue<Employee> awaitingWork = new PriorityQueue<Employee>();

  final Set<Employee> employees = new HashSet<Employee>();

  public CallCenter(Collection<Employee> employees) {
    this.employees.addAll(employees);
    this.awaitingWork.addAll(employees);
  }

  public Employee dispatchCall(Call call) {
    if (!awaitingWork.isEmpty()) {
      Employee nextEmployee = awaitingWork.poll();
      while (nextEmployee != null) {
        if (nextEmployee.canHandleCall(call)) {
          nextEmployee.handleCall(call);
          nextEmployee.makeBusy();
          awaitingWork.remove(nextEmployee);
          return nextEmployee;
        } else {
          if (nextEmployee.isAvailable()) {
            awaitingWork.add(nextEmployee);
          }
          nextEmployee = nextEmployee.manager;
        }
      }
    }

    throw new RuntimeException("We're experiencing high volumes of calls right now, leave a "
      + "number and we'll call you back once we implement that feature.");
  }

  public void finishedCall(Employee employee) {
    employee.makeFree();
    awaitingWork.add(employee);
  }
}
