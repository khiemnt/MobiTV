package com.viettel.vpmt.mobiletv.base;

import java.io.Serializable;
import java.util.UUID;

/**
 * Base DTO
 * Created by neo on 2/5/2016.
 */
public class BaseDTO implements Serializable {
    protected UUID uuid;

    public BaseDTO() {
    }

    public BaseDTO(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
