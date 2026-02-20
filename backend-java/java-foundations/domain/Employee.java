package domain;

import source.service.CashRegister;
import source.servicer.impl.DefaultEmployeeImpl;

import java.time.LocalDate;

public class Employee extends DefaultEmployeeImpl {

    public String name;
    public byte age;


    public Employee(String name, CashRegister cashRegister){
        super(cashRegister);
        this.name = name;
    }

    public byte getAge() {
        return age;
    }

    public void setAge(byte age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
