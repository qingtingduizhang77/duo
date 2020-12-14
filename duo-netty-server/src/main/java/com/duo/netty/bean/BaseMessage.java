package com.duo.netty.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseMessage implements Serializable {

    private String control_id;
    private String device_id;
    private String message_type;
    private String connect_message;

}
