package com.rokid.soa.common;

public class JsonResultApi extends JsonResult {
	
	/**
	 * Api返回共通方法
	 */
	private static final long serialVersionUID = 1L;

	public JsonResultApi(ResponseMap map) throws Exception {
		if(map != null && map.get(ResponseMap.RETURN).equals(Constants.SUCCESS)){
			map.remove(ResponseMap.MESSAGE);
			map.remove(ResponseMap.RETURN);
			this.setCode(1);
			this.setData(JsonUtil.beanToJson(map));
			this.setMessage("success");
		}else{
			this.setCode(0);
			this.setData(null);
			this.setMessage(map.get(ResponseMap.MESSAGE).toString());
		}
	}
}
