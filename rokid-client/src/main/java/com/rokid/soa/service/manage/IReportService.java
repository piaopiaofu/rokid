package com.rokid.soa.service.manage;

import com.rokid.soa.common.RequestMap;
import com.rokid.soa.common.ResponseMap;

/**
 * 统计功能 SERVICE
 * Created by fanglh on 16/10/28.
 */

public interface IReportService {
    
    /**
     * ASR/NLP 年月统计
     * @param requestMap
     * @return
     */
    public ResponseMap asrNlp(RequestMap requestMap);
    
    /**
     * ASR/NLP 性别 年月统计
     * @param requestMap
     * @return
     */
    public ResponseMap asrNlpSex(RequestMap requestMap);
    
    /**
     * Active 活跃度统计
     * @param requestMap
     * @return
     */
    public ResponseMap active(RequestMap reqMap);
    
    /**
     * VOICE 年月统计
     * @param requestMap
     * @return
     */
    public ResponseMap voice(RequestMap reqMap);
    
    /**
     * CHAT 年月统计
     * @param requestMap
     * @return
     */
    public ResponseMap chat(RequestMap reqMap);
    public ResponseMap chatNew(RequestMap reqMap);
    
    /**
     * CHAT删除选择
     * @param requestMap
     * @return
     */
    public ResponseMap chatNewDel(RequestMap reqMap);
}
