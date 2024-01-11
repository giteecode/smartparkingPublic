package com.smart.common.model;

import java.util.HashMap;
import java.util.Map;

/**
 *  页面响应
 * 	
 * 	@author 小柒2012
 */
public class Result extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	public Result() {
		put("code", 0);
	}

	public static Result error() {
		return error(500, "未知异常，请联系管理员");
	}

	public static Result error(String msg) {
		return error(500, msg);
	}

	public static Result error(int code, String msg) {
		Result r = new Result();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static Result ok(Object msg) {
		Result r = new Result();
		r.put("msg", msg);
		return r;
	}

    public static Result ok(Object msg,Object data) {
        Result r = new Result();
        r.put("msg", msg);
        r.put("data", data);
        return r;
    }

	public static Result ok(Map<String, Object> map) {
		Result r = new Result();
		r.putAll(map);
		return r;
	}

	public static Result ok() {
		Result r = new Result();
		r.put("msg", "操作成功");
		return r;
	}

    public static Boolean isOk(Result r) {
        if(r.get("code").toString().equals("0")){
            return true;
        }
        return false;
    }

	@Override
	public Result put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}