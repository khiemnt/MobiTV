package com.viettel.vpmt.mobiletv.network.dto;

import java.util.List;

/**
 * Settings DTO
 * Created by neo on 5/26/2016.
 */
public class PlayerSetting {
    private List<ErrorType> errorType;

    public List<ErrorType> getErrorType() {
        return errorType;
    }

    public void setErrorType(List<ErrorType> errorType) {
        this.errorType = errorType;
    }

    public class ErrorType {
        private int id;
        private String content;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
