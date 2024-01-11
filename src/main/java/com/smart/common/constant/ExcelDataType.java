package com.smart.common.constant;

/**
 * Excel导入导出数据类型枚举
 */
public enum ExcelDataType {
	// 字符串类型
	STRING("String"),
	// 数值类型
	INTEGER("Integer"),
	// Long类型
	LONG("Long"),
	// Double类型
	DOUBLE("Double"),
	// BigDecimal类型
	BIG_DECIMAL("BigDecimal"),
	// Float类型
	FLOAT("Float"),
	// Date类型
	DATE("Date"),
	// Timestamp
	TIMESTAMP("Timestamp"),
	// Macro类型
	MACRO("Macro"),
	// Money类型
	MONEY("Money");

	public String dataType;

	ExcelDataType(String dataType) {
		this.dataType = dataType;
	}
}
