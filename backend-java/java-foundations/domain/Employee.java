package domain;

import source.service.CashRegister;
import source.impl.DefaultEmployeeImpl;
import source.service.SaleService;

public class Employee extends DefaultEmployeeImpl {

    public String name;
    public byte age;


    public Employee(String name, CashRegister cashRegister, SaleService saleService){
        super(cashRegister, saleService);
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
