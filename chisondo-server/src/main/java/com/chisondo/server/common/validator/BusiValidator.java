package com.chisondo.server.common.validator;

import com.chisondo.server.common.http.CommonReq;

public interface BusiValidator {
    void validate(CommonReq req);
}
