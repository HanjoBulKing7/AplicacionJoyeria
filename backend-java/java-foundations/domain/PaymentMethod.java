package domain;

public enum PaymentMethod {
    //Associate values
    C("CASH"), CC("CREDIT CARD"), DC("DEBIT CARD");
    //Field of the Enum
    String paymentMethodName;
    //Constructor
    PaymentMethod(String paymentMethodName){
        this.paymentMethodName = paymentMethodName;
    }
   //Getter to retrieve the value
   public String getPaymentMethodName(){
        return this.paymentMethodName;
   }
}
