package source;

import Model.Customer;
import Model.Jewel;

public class Main {

    public static void main(String[] args) {
        Jewel ring = new Jewel("Gold ring", 150.99f, (byte)10);
        Customer johancito = new Customer("Johan", "449-585-9472", "Ags,Ags,Mx");
        System.out.println("A new item has arrived to the store: " + ring.getName());
        System.out.println("The customer "+johancito.getName()+" from: "+johancito.getAddress()+" is carrying $100");
        System.out.println("Since he's friend of the CEO we are offering a %50 discount so now he can get it");
        ring.setPrice(75.00f);
        System.out.println(johancito.getName()+" has bought: "+ring.getPrice()+" for: "+ring.getPrice());
        ring.decreaseStock((byte)1);
        System.out.println("Now the store has "+ ring.getStock() );
    }
}
