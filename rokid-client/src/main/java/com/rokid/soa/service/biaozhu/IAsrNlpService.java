package com.rokid.soa.service.biaozhu;

import com.rokid.soa.common.RequestMap;
import com.rokid.soa.common.ResponseMap;

/**
 * ASRNLP SERVICE
 * Created by tong on 16/10/26.
 */

public interface IAsrNlpService {
    
	public ResponseMap init(RequestMap requestMap);
	
    /**
     * ASRNLP标注一栏。
     * @param requestMap
     * @return
     */
    public ResponseMap list(RequestMap requestMap);
    
    /**
     * 更新ASRNLP标注信息。
     * @param requestMap
     * @return
     */
    public ResponseMap update(RequestMap requestMap);
    public ResponseMap updates(RequestMap requestMap);
    
    /**
     * 更新语音无法播放错误
     * @param reqMap
     * @return
     */
    public ResponseMap updWavErr(RequestMap reqMap);
    
    /**
     * 统计
     * @param reqMap
     * @return
     */
    public ResponseMap count(RequestMap reqMap);
    
    /**
     * 更新domain组
     * @param reqMap
     * @return
     */
    public ResponseMap selDomainGroup(RequestMap reqMap);
    public ResponseMap updDomainGroup(RequestMap reqMap);

}
