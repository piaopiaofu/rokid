/************************* 版权声明 **********************************
 * 版权所有：Copyright (c) 2000, 2007 浙江钻木电子科技有限公司
 *
 * 工程名称：	cn.flintware.pda
 * 创建者：	黄晓峰 创建日期： 2007-9-6
 * 创建记录：	创建类结构。
 *
 * ************************* 变更记录 ********************************
 * 修改者： 
 * 修改日期：
 * 修改记录：
 *
 * 
 * ......************************* To Do List*****************************
 * 
 *
 * Suberversion 信息
 * ID:			$Id$
 * 源代码URL：	$HeadURL$
 * 最后修改者：	$LastChangedBy$
 * 最后修改日期：	$LastChangedDate$
 * 最新版本：		$LastChangedRevision$
 **/


package com.rokid.soa.common;

/**
 * 业务系统相关的异常.
 * @author <a href="mailto:xiaofenguser@163.com">黄晓峰</a>
 * @version $Revision$
 */
public class BizException extends RuntimeException {

	/**
	 * <code>[serialVersionUID]</code>.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 默认异常构造器.
	 */
	public BizException() {
		super();
	}

	/**
	 * 根据异常信息和原生异常构造对象.
	 * 
	 * @param message
	 *            异常信息.
	 * @param cause
	 *            原生异常.
	 */
	public BizException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * 根据异常信息构造对象.
	 * 
	 * @param message
	 *            异常信息.
	 */
	public BizException(final String message) {
		super(message);
	}

	/**
	 * 根据原生异常构造对象.
	 * 
	 * @param cause
	 *            原生异常.
	 */
	public BizException(final Throwable cause) {
		super(cause);
	}
	
	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}
