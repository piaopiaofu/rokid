package com.rokid.soa.service.biaozhu;

import com.rokid.soa.common.RequestMap;
import com.rokid.soa.common.ResponseMap;

/**
 * 聊天Filter SERVICE
 * Created by tong on 16/10/26.
 */

public interface IChatService {
    
        
    /**
     * 聊天Filter一栏。
     * @param requestMap
     * @return
     */
    public ResponseMap list(RequestMap requestMap);
    
    /**
     * 更新聊天Filter信息。
     * @param requestMap
     * @return
     */
    public ResponseMap update(RequestMap requestMap);
    
    /**
     * 删除话题及更新关联话题的聊天数据
     * @param requestMap
     * @return
     */
    public ResponseMap del(RequestMap requestMap);

}
