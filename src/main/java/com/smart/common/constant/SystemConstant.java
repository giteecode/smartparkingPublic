package com.smart.common.constant;

/**
 * 系统级静态变量

 */
public class SystemConstant {

	/**
	 * 文件分隔符
	 */
	public static final String SF_FILE_SEPARATOR = System.getProperty("file.separator");

	/**
	 * 数据标识
	 */
	public static final String DATA_ROWS = "rows";

	/**
	 * 成功
	 */
	public static final String SUCCESS = "success";
	/**
	 * 失败
	 */
	public static final String ERROR = "error";

	/**
	 * 真
	 */
	public static final String TRUE = "true";
	/**
	 * 假
	 */
	public static final String FALSE = "false";


    public static final String FILE = "file";

	/**
	 * 删除
	 */
	public static final Short DELETE_STATUS_YES = 0;

	public static final Short DELETE_STATUS_NO = 1;


	/**
	 * 头像：0  默认 1 上传
	 */
	public static final Short AVATAR_STATUS_YES = 1;

	public static final Short AVATAR_STATUS_NO = 0;

    /**
     * 支付状态 1 ：支付 0：未支付
     */
    public static final Short PAY_STATUS_NO = 0;

    public static final Short PAY_STATUS_YES = 1;


    /**
     * 支付类型 0：微信  1：支付宝
     */
    public static final Short PAY_TYPE_WX = 0;

    public static final Short PAY_TYPE_ALI = 1;


    /**
     * 管理员-角色
     */
    public static final String  ROLE_ADMIN = "admin";

    /**
     * 机构管理员-角色
     */
    public static final String  ROLE_ORG_ADMIN = "orgAdmin";

    /**
     * 是
     */
    public static final String Y = "Y";

    /**
     * 文件存放路径 HTTP
     */
    public static final String FILE_PATH = "/file/";

    /**
     * 类型  0：包月车  1：VIP免费车 2：临时
     */
    public static final Short CAR_TYPE_MONTH = 0;

    public static final Short CAR_TYPE_VIP = 1;

    public static final Short CAR_TYPE_TEMP = 2;

    /**
	 * 菜单类型
	 */
	public enum MenuType {
		/**
		 * 目录
		 */
		CATALOG(0),
		/**
		 * 菜单
		 */
		MENU(1),
		/**
		 * 按钮
		 */
		BUTTON(2);

		private final int value;

		MenuType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	/**
	 * 通用字典
	 */
	public enum MacroType {

		/**
		 * 类型
		 */
		TYPE(0),

		/**
		 * 参数
		 */
		PARAM(1);

		private final int value;

		MacroType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

	}

	/**
	 * 通用变量，表示可用、禁用、显示、隐藏、是、否
	 */
	public enum StatusType {

		/**
		 * 禁用，隐藏
		 */
		DISABLE((short)0),

		/**
		 * 可用，显示
		 */
		ENABLE((short)1),

		/**
		 * 显示
		 */
		SHOW((short)1),

		/**
		 * 隐藏
		 */
		HIDDEN((short)0),

		/**
		 * 是
		 */
		YES((short)1),

		/**
		 * 否
		 */
		NO((short)0);

		private final short value;

		StatusType(short value) {
			this.value = value;
		}

		public short getValue() {
			return value;
		}
	}
}
