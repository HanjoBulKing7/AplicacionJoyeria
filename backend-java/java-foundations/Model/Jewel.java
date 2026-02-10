package Model;

public class Jewel {

    private int id;
    private String name;
    private String description;
    private float price;
    private byte stock;
    private String imageUrl;

    enum Status{//Definition of the  ENUM
        ACTIVE, INACTIVE
    }

    private Status status;
    public Jewel() {}
    //Generic constructor with mandatory data based on the DB definition
    public Jewel(String name, float price, byte stock ){
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.status = Status.ACTIVE;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        if(price < 0) {
            System.out.println("Price must be greater than 0");
            return;
        }
        this.price = price;
    }

    public byte getStock() {
        return stock;
    }

    public void setStock(byte stock) {
        if(stock < 0){
            System.out.println("Stock must be >= 0");
            return;
        }
        this.stock = stock;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String image_url) {
        this.imageUrl = image_url;
    }

    public void decreaseStock(byte amount){
        if( amount <= 0 || stock - amount < 0) return;
        this.stock -= amount;
    }
}
