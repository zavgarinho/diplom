package com.zavga.diplom.entity.object;

public enum ObjectType {
    APARTMENT("Квартира"),
    HOUSE("Приватний будинок"),
    OFFICE("Офіс"),
    SHOP("Магазин"),
    WAREHOUSE("Складське приміщення"),
    INDUSTRIAL("Завод");

    private final String label;

    private ObjectType(String label){
        this.label = label;
    }

    public String getLabel(){
        return this.label;
    }
}
