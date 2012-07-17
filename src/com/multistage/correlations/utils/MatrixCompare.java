package com.multistage.correlations.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;

public class MatrixCompare implements Comparable {
  private String name;

  private double salary;

  MatrixCompare(String name, double salary) {
    this.name = name;
    this.salary = salary;
  }

  public MatrixCompare(Object object, Object object2) {
	  this.name = String.valueOf(object);
	  this.salary = Double.parseDouble(object2.toString());
  }

  String getName() {
    return name;
  }

  double getSalary() {
    return salary;
  }

  public String toString() {
	  return ""+getSalary();
    //return "Name = " + getName() + ", Salary = " + getSalary();
  }

  public int compareTo(Object o) {
    if (!(o instanceof MatrixCompare))
      throw new ClassCastException();

    MatrixCompare e = (MatrixCompare) o;
    
    return name.compareTo(e.getName());
  }

  static class SalaryComparator implements Comparator {
    public int compare(Object o1, Object o2) {
      if (!(o1 instanceof MatrixCompare) || !(o2 instanceof MatrixCompare))
        throw new ClassCastException();

      MatrixCompare e1 = (MatrixCompare) o1;
      MatrixCompare e2 = (MatrixCompare) o2;

      return (int) (e1.getSalary() - e2.getSalary());
    }
  }
}



    
    