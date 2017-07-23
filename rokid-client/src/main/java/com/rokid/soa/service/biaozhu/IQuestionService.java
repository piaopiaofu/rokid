package com.rokid.soa.service.biaozhu;

import com.rokid.soa.common.RequestMap;
import com.rokid.soa.common.ResponseMap;

/**
 * 聊天 SERVICE
 * Created by tong on 16/10/26.
 */

public interface IQuestionService {
    
        
    /**
     * 聊天标注一栏。
     * @param requestMap
     * @return
     */
    public ResponseMap list(RequestMap requestMap);
    
    /**
     * 更新聊天标注信息。
     * @param requestMap
     * @return
     */
    public ResponseMap update(RequestMap requestMap);
    
    /**
     * chatfilter
     * @param requestMap
     * @return
     */
    public ResponseMap chatfilter(RequestMap requestMap);

}
