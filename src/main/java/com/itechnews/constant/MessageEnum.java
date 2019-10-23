package com.itechnews.constant;

public enum MessageEnum {

	MSG_ADDED_SUCCESSFULLY("Thực hiện thêm thành công"),
	MSG_ERROR("Có lỗi trong quá trình xử lý"),
	MSG_UPDATED_SUCCESSFULLY("Thực hiện sửa thành công"),
	MSG_DELETED_SUCCESSFULLY("Thực hiện xóa thành công");

	private final String message;

	MessageEnum(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
