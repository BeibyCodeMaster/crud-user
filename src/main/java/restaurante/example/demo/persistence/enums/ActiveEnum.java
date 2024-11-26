package restaurante.example.demo.persistence.enums;


public enum ActiveEnum {

    ACTIVE(true),
    INACTIVE(false);
    
    private final Boolean status;
        
    ActiveEnum(Boolean status){
        this.status = status;
    }

    public Boolean getStatus() {
        return this.status;
    }
}
