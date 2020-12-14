package com.duo.modules.health.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name = "treatment_record")
public class TreatmentRecord extends BaseEntity {

    @Id
    private String service_id;//主键id

    private String man_id;//就诊人id

    private String man_name;//就诊人名称

    private String endoscope;//窥镜检查

    private String project_id;//项目id

    private String service_no;//诊疗单号

    private String auditing;//状态

    private String member_id;//会员id

    private String member_name;//会员姓名

    private String reserve_id;//预约订单

    private String age;//年龄

    private String is_return;//是否复诊

    private String height;//身高

    private String weight;//体重

    private String temperature;//体温

    private String heart_rate;//心率

    private String sbp;//血压(收缩压)

    private String dbp;//血压(舒张压)

    private String symptom_describe;//病症表现

    private String current_history;//现病史

    private String diagnosis_result;//诊断结果

    private String diagnosis_suggest;//处理建议

    private String doctor_id;//doctor_id

    private String doctor_name;//专家名称

    private String user_id;//系统帐号id

    private Date start_time;//诊疗开始时间

    private Date end_time;//诊疗结束时间

    private String check_doctor_id;//审核药师id

    private String check_doctor_name;//审核药师

    private String check_user_id;//审核药师帐号

    private Date sub_check_time;//提交审核时间

    private Integer sub_check_status;//是否提交审核

    private Date check_time;//审核时间

    private Integer check_result;//审核结果 0未审核  1通过 2退回

    private String check_remark;//审核结果备注

    private Date pay_time;//付款时间

    private String back_status;//退款状态

    private Date back_time;//退款时间

    private String pay_type;//付款方式

    private String pay_status;//付款状态

    private String pay_id;//收单方id

    private Double goods_money;//药费

    private Double service_money;//诊疗费

    private Double pay_money;//付款金额

    private Double order_money;//订单金额

    private Double discount_money;//优惠金额

    private String memo;//备注

    private String device_name;//设备名称

    private String device_id;//设备id

    private String device_code;//设备编号

    private String device_address;//设备地址

    private String add_userid;//创建人id

    private Date add_date;//创建时间

}