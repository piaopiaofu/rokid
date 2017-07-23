package com.rokid.soa.service.biaozhu;

import com.rokid.soa.common.RequestMap;
import com.rokid.soa.common.ResponseMap;

/**
 * 语音标注 SERVICE
 * Created by tong on 16/10/24.
 */

public interface IVoiceTriggerService {
    
        
    /**
     * 语音标注一栏。
     * @param requestMap
     * @return
     */
    public ResponseMap list(RequestMap requestMap);
    
    /**
     * 更新语音标注信息。
     * @param requestMap
     * @return
     */
    public ResponseMap update(RequestMap requestMap);
    
    /**
     * 更新语音无法播放错误
     * @param reqMap
     * @return
     */
    public ResponseMap updWavErr(RequestMap reqMap);

}
