package com.duo.modules.health.model;

import lombok.Data;

@Data
public class DiagnoseVO {
    private String service_id; //问诊ID
    private String symptom_describe;//病情主诉
    private String current_history;//现病史
    private String past_history;//既往病史
    private String drug_allergy;//过敏史
    private String genetic_disease;//遗传病史
    private String diagnosis_result;//诊断结果
    private String diagnosis_suggest;//处理建议
}
