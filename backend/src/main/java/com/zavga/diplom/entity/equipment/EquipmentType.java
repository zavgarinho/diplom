package com.zavga.diplom.entity.equipment;

public enum EquipmentType {
    VIDEO_SURVEILLANCE("Камера відеоспостереження"),
    ALARM_SYSTEM("Сигналізація"),
    ACCESS_CONTROL("Контроль доступу"),
    FIRE_ALARM("Пожежна сигналізація"),
    PANIC_BUTTON("Тривожна кнопка"),
    CONTROL_PANEL("Пульт централізованого спостереження ");


    private final String label;
    private EquipmentType(String label){
        this.label = label;
    }
    public String getLabel(){
        return this.label;
    }
}
