package com.ezyfox.cvconnect.constant;

import com.tvd12.ezyfox.util.EzyEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
public enum ProcessCode {

    INIT("Init"),
    SEND_CV_TO_AGENCY("Send cv to agency"),
    SEND_CV_TO_COMPANY("send cv to company"),
    SUCCESS("success"),
    FAIL("fail")
    ;

    @Getter
    private final String name;

    private static final Map<Object, ProcessCode> MAP =
        EzyEnums.enumMap(ProcessCode.class, it -> it.name);
    
    public static ProcessCode of(String name) {
        return name == null ? null : MAP.get(name);
    }

}
