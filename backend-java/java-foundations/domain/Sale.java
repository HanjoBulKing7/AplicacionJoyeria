package domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Sale {

    float totalAmount;
    PaymentMethod paymentMethod;
    Customer customer;
    byte quantity;
    Employee employee;
    Jewel jewel;
    LocalDateTime date;
    DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        if(totalAmount < 0){
            System.out.println("Amount of sold items must be greater than zero");
            return;
        }
        this.totalAmount = totalAmount;
    }

    public PaymentMethod getPaymentMethod() {

        return this.paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        if(paymentMethod == null){
            System.out.println("Payment method cannot be empty");
            return;
        }
        this.paymentMethod = paymentMethod;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        if(customer == null){
            System.out.println("There must be a customer to generate the ticket");
        }
        this.customer = customer;
    }

    public byte getQuantity() {
        return quantity;
    }

    public void setQuantity(byte quantity) {
        this.quantity = quantity;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {

        this.employee = employee;
    }

    public Jewel getJewel() {
        return jewel;
    }

    public void setJewel(Jewel jewel) {
        this.jewel = jewel;
    }

    public LocalDateTime getDate() {

        return this.date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;

    }
}
