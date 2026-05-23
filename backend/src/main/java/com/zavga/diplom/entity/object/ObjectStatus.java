package com.zavga.diplom.entity.object;

public enum ObjectStatus {
    NEW("Новий"),
    EQUIPMENT_PLANNED("Планується обладнання"),
    INSTALLATION_IN_PROGRESS("Роботи в процесі"),
    READY_FOR_PROTECTION("Готовий до введення під охорону"),
    UNDER_PROTECTION("Під охороною");

    private final String label;
    private ObjectStatus(String label){
        this.label = label;
    }

    public String getLabel(){
        return this.label;
    }


}

