package com.duo.modules.system.entity;

import com.duo.core.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Table(name="system_attachment")
public class SystemAttachment extends BaseEntity{ 

    private String record_flag;//数据标识

    private java.util.Date modify_date;//修改时间

    private String modify_userid;//修改人id

    private java.util.Date add_date;//创建时间

    private String add_userid;//创建人id

    private String memo;//备注

    private String file_md5;//MD5

    private String upload_user;//上传人

    private String file_size;//文件大小

    private String upload_ip;//上传IP

    private String file_path;//文件路径

    private String file_type;//文件类型

    private java.util.Date file_date;//创建时间

    private String file_name;//文件名

    private String dept_id;//dept_id

    private String auditing;//素材状态

    private String dept_name;//部门

    private String use_descript;//适用描述

    private String type_id;//附件分类id

    private java.util.Date audit_date;//审核时间

    private String table_name;//表名或标识

    private String audit_user;//审核人

    private String data_id;//数据id

    private java.util.Date upload_date;//提交时间

    @Id
     private String file_id;//主键id

 }