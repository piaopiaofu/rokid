package com.rokid.soa.service.manage;

import com.rokid.soa.common.RequestMap;
import com.rokid.soa.common.ResponseMap;

/**
 * 统计功能 SERVICE
 * Created by fanglh on 16/10/28.
 */

public interface ICountService {
    
    /**
     * 按用户及年月统计
     * @param requestMap
     * @return
     */
    public ResponseMap userYm(RequestMap requestMap);
}
