package Model;

public class Customer {

    private int  id;
    private String name;
    private String phoneNumber;
    private String address;
    public Customer(){}
    //Generic constructor with the mandatory data during pure java project
    public Customer(String name, String phoneNumber, String address) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name.length() == 0) {
            System.out.println("Name is mandatory");
            return;
        }
        this.name = name;
    }

    public String getPhoneNumber() {

        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if(phoneNumber.length() == 0) {
            System.out.println("Phone number is mandatory");
            return;
        }
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
