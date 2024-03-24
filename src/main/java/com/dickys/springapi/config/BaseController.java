package com.dickys.springapi.config;

public abstract class BaseController {

    public AbstractResponseHandler generateResponse(Object data) {
        return new AbstractResponseHandler() {
            @Override
            public Object data() {
                return data;
            }
        };
    }
}

