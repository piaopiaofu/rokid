package com.rokid.soa.service.manage;

import com.rokid.soa.common.RequestMap;
import com.rokid.soa.common.ResponseMap;

/**
 * 分工指派 SERVICE
 * Created by tongxf on 16/11/13.
 */

public interface IWorkService {
           
    /**
     * 一栏。
     * @param requestMap
     * @return
     */
    public ResponseMap list(RequestMap requestMap);
    
    /**
     * 更新信息。
     * @param requestMap
     * @return
     */
    public ResponseMap update(RequestMap requestMap);
    
    /**
     * 删除。
     * @param requestMap
     * @return
     */
    public ResponseMap delete(RequestMap requestMap);

}
