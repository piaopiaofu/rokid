package com.rokid.soa.service.manage;

import com.rokid.soa.common.RequestMap;
import com.rokid.soa.common.ResponseMap;

/**
 * 用户 SERVICE
 * Created by tongxf on 16/10/25.
 */

public interface IUserService {
    
    /**
     * 登录。
     * @param requestMap
     * @return
     */
    public ResponseMap login(RequestMap requestMap);
    public ResponseMap loginOut(RequestMap requestMap);
        
    /**
     * 用户一栏。
     * @param requestMap
     * @return
     */
    public ResponseMap list(RequestMap requestMap);
    
    /**
     * 更新用户信息。
     * @param requestMap
     * @return
     */
    public ResponseMap update(RequestMap requestMap);
    
    /**
     * 删除用户。
     * @param requestMap
     * @return
     */
    public ResponseMap delete(RequestMap requestMap);

}
