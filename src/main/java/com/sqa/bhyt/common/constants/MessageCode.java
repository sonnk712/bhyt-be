package com.sqa.bhyt.common.constants;

public class MessageCode {
	public static final int SUCCESS = 1;
	public static final String SUCCESS_MESSAGE = "Thành công";

	public static final int FAILED = 0;
	public static final String FAILED_MESSAGE = "Thất bại";
	
	//User exception
	public static final int ACCOUNT_EXISTS = 100;
	public static final String ACCOUNT_EXISTS_MESSAGE = "Tài khoản đã tồn tại!";
	
	public static final int ACCOUNT_NOT_EXISTS = 101;
	public static final String ACCOUNT_NOT_EXISTS_MESSAGE = "Tài khoản không tồn tại!";
	
	public static final int EMAIL_EXISTS = 102;
	public static final String EMAIL_EXISTS_MESSAGE = "Email đã tồn tại!";
	
	public static final Integer AUTHENTICATION_FAILED = 103;
	public static final String AUTHENTICATION_FAILED_MESSAGE = "Xác thực thất bại";
	
	public static final Integer USERNAME_NOT_EMPTY = 104;
	public static final String USERNAME_NOT_EMPTY_MESSAGE = "Tài khoản không được để trống!";
	
	public static final Integer PASSWORD_NOT_EMPTY = 105;
	public static final String PASSWORD_NOT_EMPTY_MESSAGE = "Mật khẩu không được để trống!";
	
	public static final Integer EMAIL_NOT_EMPTY = 106;
	public static final String EMAIL_NOT_EMPTY_MESSAGE = "Email không được để trống";
	
	public static final Integer NAME_NOT_EMPTY = 107;
	public static final String NAME_NOT_EMPTY_MESSAGE = "Tên không được để trống";
	
	public static final Integer PHONENUMBER_NOT_EMPTY = 108;
	public static final String PHONENUMBER_NOT_EMPTY_MESSAGE = "Số điện thoại không được để trống";
	
	public static final Integer BIRTHDAY_NOT_EMPTY = 109;
	public static final String BIRTHDAY_NOT_EMPTY_MESSAGE = "Ngày sinh không được để trống";
	
	public static final Integer GENDER_NOT_EMPTY = 110;
	public static final String GENDER_NOT_EMPTY_MESSAGE = "Giới tính không được để trống";
	
	public static final Integer ACCOUNT_LIST_INVALID = 111;
	public static final String ACCOUNT_LIST_INVALID_MESSAGE = "Tồn tại tài khoản không hoạt động!";
	
	public static final Integer LIST_IS_EMPTY = 112;
	public static final String LIST_IS_EMPTY_MESSAGE = "Danh sách trống!";
	
	public static final Integer IDENTITY_NOT_EMPTY = 113;
	public static final String IDENTITY_NOT_EMPTY_MESSAGE = "Mã định danh không được để trống!";
	
	public static final Integer CODE_NOT_EMPTY = 114;
	public static final String CODE_NOT_EMPTY_MESSAGE = "Mã không được để trống!";
	
	public static final Integer PINCODE_NOT_EMPTY = 115;
	public static final String PINCODE_NOT_EMPTY_MESSAGE = "Pincode không được để trống!";
	
	public static final Integer MEMBER_NOT_EXISTS = 116;
	public static final String MEMBER_NOT_EXISTS_MESSGAE = "Không tồn tại thẻ thành viên!";
	
	public static final Integer CODE_EXISTS = 117;
	public static final String CODE_EXISTS_MESSAGE = "Mã đã tồn tại";
	
	public static final Integer MEMBER_LIST_INVALID = 118;
	public static final String MEMBER_LIST_INVALID_MESSAGE = "Tồn tại thẻ thành viên không hoạt động!";
	
	public static final Integer ACCOUNT_LOCKED = 119;
	public static final String ACCOUNT_LOCKED_MESSAGE = "Tài khoản không hoạt động!";
	
	public static final Integer INFORMATION_OF_USER_FAILED = 120;
	public static final String INFORMATION_OF_USER_FAILED_MESSAGE = "Thông tin tài khoản không chính xác!";
	
	public static final Integer PASSWORD_NOT_MATCH = 121;
	public static final String PASSWORD_NOT_MATCH_MESSAGE = "Xác nhận mật khẩu không khớp!";

	public static final Integer LOGIN_FAILED = 122;
	public static final String LOGIN_FAILED_MESSAGE = "Tài khoản hoặc mật khẩu không chính xác!";
	
	public static final Integer ACCOUNT_IS_ACTIVATED = 123;
	public static final String ACCOUNT_IS_ACTIVATED_MESSAGE = "Tài khoản đã được kích hoạt trước đó!";
	
	public static final Integer CCCD_IS_INVALID = 124;
	public static final String CCCD_IS_INVALID_MESSAGE = "Căn cước công dân không hợp lệ!";
	
	public static final Integer CCCD_NOT_EMPTY = 125;
	public static final String CCCD_NOT_EMPTY_MESSAGE = "Không được để trống căn cước công dân!";
	
	public static final Integer DATE_NOT_EMPTY = 126;
	public static final String DATE_NOT_EMPTY_MESSAGE = "Ngày sinh không được để trống!";
	
	public static final Integer NATIONALITY_NOT_EMPTY = 127;
	public static final String NATIONALITY_NOT_EMPTY_MESSGAE = "Không được để trống quốc gia!";
	
	public static final Integer ORIGIN_PLACE_NOT_EMPTY = 128;
	public static final String ORIGIN_PLACE_NOT_EMPTY_MESSAGE = "Không được để trống quê quán!";
	
	public static final Integer RESIDENCE_PLACE_NOT_EMPTY = 129;
	public static final String RESIDENCE_PLACE_NOT_EMPTY_MESSAGE = "Không được để trống địa chỉ thường trú!";
	
	public static final Integer PERSIONAL_IDENTITY_NOT_EMPTY = 130;
	public static final String PERSIONAL_IDENTITY_NOT_EMPTY_MESSAGE = "Không được để trống đặc điểm nhận dạng!";
	
	public static final Integer INCORRECT_CREDENTIALS = 131;
	public static final String INCORRECT_CREDENTIALS_MESSAGE = "Thông tin xác thực không chính xác!";

	public static final Integer EMAIL_INVALID = 132;
	public static final String EMAIL_INVALID_MESSAGE = "Email không hợp lệ!";
	
	public static final Integer PHONE_NUMBER_INVALID = 133;
	public static final String PHONE_NUMBER_INVALID_MESSAGE = "Số điện thoại không hợp lệ!";
	
	public static final Integer PERSON_EXISTED = 134;
	public static final String PERSON_EXISTED_MESSAGE = "Người này đã tồn tại!";
	
	public static final Integer WARDS_CODE_NOT_EMPTY = 135;
	public static final String WARDS_CODE_NOT_EMPTY_MESSAGE = "Mã phường không được để trống!";
	
	public static final Integer DISTRICT_CODE_NOT_EMPTY = 136;
	public static final String DISTRICT_CODE_NOT_EMPTY_MESSAGE = "Mã quận không được để trống!";
	
	public static final Integer PROVINE_CODE_NOT_EMPTY = 137;
	public static final String PROVINE_CODE_NOT_EMPTY_MESSAGE = "Mã tỉnh không được để trống!";
	
	public static final Integer WARDS_NOT_FOUND = 138;
	public static final String WARDS_NOT_FOUND_MESSAGE = "Không tìm thấy mã phường!";
	
	public static final Integer DISTRICT_NOT_FOUND = 139;
	public static final String DISTRICT_NOT_FOUND_MESSAGE = "Không tìm thấy mã quận!";
	
	public static final Integer PROVINE_NOT_FOUND = 140;
	public static final String PROVINE_NOT_FOUND_MESSAGE = "Không tìm thấy mã tỉnh!";
	
	public static final Integer PROVINE_NOT_EMPTY = 141;
	public static final String PROVINE_NOT_EMPTY_MESSAGE = "Không được để trống thành phố!";
	
	public static final Integer PASSWORD_AT_LEAST_8_CHARACTERS = 142;
	public static final String PASSWORD_AT_LEAST_8_CHARACTERS_MESSAGE = "Mật khẩu không được để trống!";
	
	public static final Integer PHONE_NUMBER_ALREADY_EXISTS = 143;
	public static final String PHONE_NUMBER_ALREADY_EXISTS_MESSAGE = "Số điện thoại đã tồn tại!";
	
	public static final Integer ISSUER_NOT_EMPTY = 144;
	public static final String ISSUER_NOT_EMPTY_MESSAGE = "Nơi đăng ký không hợp lệ!";
	
	public static final Integer ISSUER_DATE_NOT_EMPTY = 145;
	public static final String ISSUER_DATE_NOT_EMPTY_MESSAGE = "Ngày đăng ký không hợp lệ!";
	
	//Address exception
	public static final Integer DELIVERY_ADDRESS_NOT_EMPTY = 200;
	public static final String DELIVERY_ADDRESS_NOT_EMPTY_MESSAGE = "Địa chỉ giao hàng không được để trống!";
	
	public static final Integer COMMUNE_NOT_EMPTY = 201;
	public static final String COMMUNE_NOT_EMPTY_MESSAGE = "Phường/Xã không được để trống!";
	
	public static final Integer DISTRICT_NOT_EMPTY = 202;
	public static final String DISTRICT_NOT_EMPTY_MESSAGE = "Quận/Huyện không được để trống!";
	
	public static final Integer CITY_NOT_EMPTY = 203;
	public static final String CITY_NOT_EMPTY_MESSAGE = "Tỉnh/Thành phố không được để trống!";
	
	public static final Integer ADDRESS_ID_NOT_EMPTY = 204;
	public static final String ADDRESS_ID_NOT_EMPTY_MESSAGE = "Mã định danh địa chỉ không được để trống!";
	
	public static final Integer ADDRESS_NOT_EXISTS = 205;
	public static final String ADDRESS_NOT_EXISTS_MESSAGE = "Địa chỉ không tồn tại!";
	
	public static final Integer LIST_ADDRESS_EMPTY = 206;
	public static final String LIST_ADDRESS_EMPTY_MESSAGE = "Danh sách địa chỉ trống!";
	
	public static final Integer ROLE_NOT_FOUND = 207;
	public static final String ROLE_NOT_FOUND_MESSAGE = "Không tìm thấy role!";
	
	//OTP
	public static final Integer SECRET_INVALID = 300;
	public static final String SECRET_INVALID_MESSAGE = "Khóa không hợp lệ!";
	
	public static final Integer VERIFY_INFO_INVALID = 301;
	public static final String VERIFY_INFO_INVALID_MESSAGE = "Thông tin xác thực không chính xác!";
	
	public static final Integer ACTICVATE_ACCOUNT_FAILED = 302;
	public static final String ACTICVATE_ACCOUNT_FAILED_MESSAGE = "Kích hoạt tài khoản thất bại!";
	
	public static final Integer VALIDATE_TIME_HAS_EXPIRED = 303;
	public static final String VALIDATE_TIME_HAS_EXPIRED_MESSAGE = "Mã xác thực đã hết hiệu lực!";
	
	public static final Integer TOKEN_INVALID = 304;
	public static final String TOKEN_INVALID_MESSAGE = "Token không hợp lệ!";
	
	public static final Integer ACCOUT_NOT_ACTIVATE = 305;
	public static final String ACCOUT_NOT_ACTIVATE_MESSAGE = "Tài khoản chưa kích hoạt, hay kích hoạt trước khi đăng nhập!";
	
	//Mail
	public static final Integer OTP_NOT_EMPTY = 400;
	public static final String OTP_NOT_EMPTY_MESSAGE = "OTP không được để trống!";
	
	public static final Integer TRANSACTION_NOT_EMPTY = 401;
	public static final String TRANSACTION_NOT_EMPTY_MESSAGE = "Phiên giao dịch không chính xác!";
	
	public static final Integer EMAIL_SENDING_FAILED = 402;
	public static final String EMAIL_SENDING_FAILED_MESSAGE = "Gửi mail thất bại!";
	
	//Type
	public static final Integer TYPE_EXISTED = 500;
	public static final String TYPE_EXISTED_MESSAGE = "Loại đăng ký đã tồn tại!";
	
	public static final Integer CREATE_TYPE_FAILED = 501;
	public static final String CREATE_TYPE_FAILED_MESSAGE = "Tạo loại đăng ký thất bại!";
	
	public static final Integer TYPE_ID_NOT_EMPTY = 502;
	public static final String TYPE_ID_NOT_EMPTY_MESSAGE = "Mã định danh loại đăng ký không được để trống!";
	
	public static final Integer TYPE_CODE_NOT_EMPTY = 503;
	public static final String TYPE_CODE_NOT_EMPTY_MESSAGE = "Mã loại đăng ký không được để trống!";
	
	public static final Integer TYPE_NOT_FOUND = 504;
	public static final String TYPE_NOT_FOUND_MESSAGE = "Không tìm thấy loại đăng ký!";
	
	public static final Integer EXISTED_INVALID_TYPE = 505;
	public static final String EXISTED_INVALID_TYPE_MESSAGE = "Tồn tại loại đăng ký không hợp lệ!";
	
	public static final Integer THIS_TYPE_NOT_VALID = 507;
	public static final String THIS_TYPE_NOT_VALID_MESSAGE = "Loại đăng ký không hợp lệ!";
	
	//Family Record
	public static final Integer FAMILY_NOT_FOUND = 600;
	public static final String FAMILY_NOT_FOUND_MESSAGE = "Không tìm thấy sổ hộ khẩu!";
	
	public static final Integer HOUSEHOLD_NOT_EMPTY = 601;
	public static final String HOUSEHOLD_NOT_EMPTY_MESSAGE = "Danh sách phải chứa chủ hộ!";
	
	public static final Integer HOUSEHOLD_INVALID = 602;
	public static final String HOUSEHOLD_INVALID_MESSAGE = "Danh sách chứa nhiều hơn 1 chủ hộ!";
	
	//Health insurance
	public static final Integer HOUSEHOLD_NOT_FOUND = 700;
	public static final String HOUSEHOLD_NOT_FOUND_MESSAGE = "Không tìm thấy thông tin chủ hộ!";
	
	public static final Integer EXISTED_MEMBER_INVALID = 701;
	public static final String EXISTED_MEMBER_INVALID_MESSAGE = "Tồn tại thành viên không thuộc hộ gia đình hoặc không hợp lệ!";
	
	public static final Integer PERSONAL_INFORMATION_INVALID = 702;
	public static final String PERSONAL_INFORMATION_INVALID_MESSAGE = "Thông tin cá nhân không hợp lệ!";
	
	public static final Integer CANT_CHARGE_COST = 703;
	public static final String CANT_CHARGE_COST_MESSAGE = "Lỗi trong quá trình tính phí!";
	
	public static final Integer USER_NOT_ELIGIBLE_TO_JOIN = 704;
	public static final String USER_NOT_ELIGIBLE_TO_JOIN_MESSAGE = "Người dùng không đủ điều kiện để tham gia với mức lương này!";
	
	public static final Integer WORK_TYPE_EMPTY = 705;
	public static final String WORK_TYPE_EMPTY_MESSAGE = "Điều kiện công việc không được để trống!";
	
	public static final Integer SALARY_INVALID = 706;
	public static final String SALARY_INVALID_MESSAGE = "Lương không được để trống!";
	
	public static final Integer WRONG_TYPE = 707;
	public static final String WRONG_TYPE_MESSAGE = "Loại đăng ký không đúng!";
	
	public static final Integer INSURANCE_MEMBER_EXISTED = 708;
	public static final String INSURANCE_MEMBER_EXISTED_MESSAGE = "Tồn tại thành viên đã có bảo hiểm tế!";
	
	public static final Integer INSURANCE_HOUSEHOLD_EXISTED = 709;
	public static final String INSURANCE_HOUSEHOLD_EXISTED_MESSAGE = "Chủ hộ đã có bảo hiểm y tế, chọn thời hạn 0 tháng để đăng ký cho thành viên!";
	
	public static final Integer INSURANCE_IS_ACTIVATE = 710;
	public static final String INSURANCE_IS_ACTIVATE_MESSAGE = "Bảo hiểm y tế đã được kích hoạt trước đó!";
	
	public static final Integer PROFILE_NOT_FOUND = 711;
	public static final String PROFILE_NOT_FOUND_MESSAGE = "Không tìm thấy hồ sơ từ thông tin người dùng!";
	
	public static final Integer EXISTED_MEMBER_IS_DUPLICATED = 712;
	public static final String EXISTED_MEMBER_IS_DUPLICATED_MESSAGE = "Tồn tại thành viên trong danh sách bị trùng lặp!";
	
	public static final Integer AVG_PAID_FOR_UNEMPLOYEE_INSURANCE_INVALID = 713;
	public static final String AVG_PAID_FOR_UNEMPLOYEE_INSURANCE_INVALID_MESSAGE = "Mức bình quân tiền lương tháng đóng bảo hiểm thất nghiệp không hợp lệ!";
	
	public static final Integer AVG_PAID_FOR_UNEMPLOYEE_SOCIAL_INSURANCE_INVALID = 714;
	public static final String AVG_PAID_FOR_UNEMPLOYEE_SOCIAL_INSURANCE_INVALID_MESSAGE = "Mức bình quân tiền lương tháng đóng bảo hiểm xã hội không hợp lệ!";
	
	public static final Integer NUMBER_OF_YEAR_PAYING_SOCIAL_INSURANCE_INVALID = 715;
	public static final String NUMBER_OF_YEAR_PAYING_SOCIAL_INSURANCE_INVALID_MESSAGE = "Số năm đóng bảo hiểm xã hội không hợp lệ!";
	
	
	//Period
	public static final Integer PERIOD_EXISTED = 800;
	public static final String PERIOD_EXISTED_MESSAGE = "thời hạn đã tồn tại!";
	
	public static final Integer CREATE_PERIOD_FAILED = 801;
	public static final String CREATE_PERIOD_FAILED_MESSAGE = "Tạo thời hạn thất bại!";
	
	public static final Integer PERIOD_ID_NOT_EMPTY = 802;
	public static final String PERIOD_ID_NOT_EMPTY_MESSAGE = "Mã định danh thời hạn không được để trống!";
	
	public static final Integer PERIOD_NOT_FOUND = 803;
	public static final String PERIOD_NOT_FOUND_MESSAGE = "Không tìm thấy thời hạn!";
	
	public static final Integer EXISTED_INVALID_PERIOD = 804;
	public static final String EXISTED_INVALID_PERIOD_MESSAGE = "Tồn tại thời hạn không hợp lệ!";
	
	public static final Integer PERIOD_CODE_NOT_EMPTY = 805;
	public static final String PERIOD_CODE_NOT_EMPTY_MESSAGE = "Mã thời hạn không được để trống!";
	
	public static final Integer MONTH_INVALID = 806;
	public static final String MONTH_INVALID_MESSAGE = "Số tháng phải lớn hơn 0 hoặc nhỏ hơn 12!";
	
	//Charge unit
	public static final Integer CHARGE_UNIT_EXISTED = 900;
	public static final String CHARGE_UNIT_EXISTED_MESSAGE = "Đơn vị tiền đã tồn tại!";
	
	public static final Integer CREATE_CHARGE_UNIT_FAILED = 901;
	public static final String CREATE_CHARGE_UNIT_FAILED_MESSAGE = "Tạo đơn vị tiền thất bại!";
	
	public static final Integer CHARGE_UNIT_ID_NOT_EMPTY = 902;
	public static final String CHARGE_UNIT_ID_NOT_EMPTY_MESSAGE = "Mã định danh đơn vị tiền không được để trống!";
	
	public static final Integer CHARGE_UNIT_NOT_FOUND = 903;
	public static final String CHARGE_UNIT_NOT_FOUND_MESSAGE = "Không tìm thấy đơn vị tiền!";
	
	public static final Integer EXISTED_INVALID_CHARGE_UNIT = 904;
	public static final String EXISTED_INVALID_CHARGE_UNIT_MESSAGE = "Tồn tại đơn vị tiền không hợp lệ!";
	
	public static final Integer CHARGE_UNIT_CODE_NOT_EMPTY = 905;
	public static final String CHARGE_UNIT_CODE_NOT_EMPTY_MESSAGE = "Mã đơn vị tiền không được để trống!";
	
	public static final Integer COST_INVALID = 906;
	public static final String COST_INVALID_MESSAGE = "Chi phí phải lớn hơn 0đ!";
	
	
	//report
	public static final Integer INSURANCE_IDENTITY_NOT_EMPTY = 1000;
	public static final String INSURANCE_IDENTITY_NOT_EMPTY_MESSAGE = "Mã số bảo hiểm y tế không được để trống!";
	
	public static final Integer PAID_FROM_NOT_EMPTY = 1001;
	public static final String PAID_FROM_NOT_EMPTY_MESSAGE = "Mã số bảo hiểm y tế không được để trống!";
	
	public static final Integer PAID_TO_NOT_EMPTY = 1002;
	public static final String PAID_TO_NOT_EMPTY_MESSAGE = "Mã số bảo hiểm y tế không được để trống!";
	
	public static final Integer TRANSACION_IS_EMPTY_IN_PERIOD = 1003;
	public static final String TRANSACION_IS_EMPTY_IN_PERIOD_MESSAGE = "Không có giao dịch nào trong khoảng thời gian này!";
	
	public static final Integer NOT_AUTHORIZED_TO_PERFORM_THIS_ACTION = 1004;
	public static final String NOT_AUTHORIZED_TO_PERFORM_THIS_ACTION_MESAGE = "Không đủ quyền để thực hiện thao tác này!";
	
	public static final Integer RECEIVING_PLACE_NOT_EMPTY = 1005;
	public static final String RECEIVING_PLACE_NOT_EMPTY_MESSAGE = "Không được để trống nơi nhận!";
	
	public static final Integer PERSON_NOT_EMPTY = 1006;
	public static final String PERSON_NOT_EMPTY_MESSAGE = "Không được để trống tên quản lý đại lý!";
	
	public static final Integer ADDRESS_NOT_EMPTY = 1007;
	public static final String ADDRESS_NOT_EMPTY_MESSAGE = "Không được để trống địa chỉ thu!";
	
	public static final Integer AGENCY_NOT_FOUND = 1008;
	public static final String AGENCY_NOT_FOUND_MESSAGE = "Không tìm thấy đại lý!";
	
	public static final Integer EXISTED_INVALID_AGENCY = 1009;
	public static final String EXISTED_INVALID_AGENCY_MESSAGE = "Tồn tại đại lý không hợp lệ!";
	
	public static final Integer AGENCY_EXISTED = 1010;
	public static final String AGENCY_EXISTED_MESSAGE = "Mã đại lý đã tồn tại!";
	
	public static final Integer USERNAME_INVALID = 1011;
	public static final String USERNAME_INVALID_MESSAGE = "Tài khoản phải chứa 12 ký tự số!";
	
	public static final Integer PASSWORD_INVALID = 1012;
	public static final String PASSWORD_INVALID_MEESGAE = "Mật khẩu phải chứa ít nhất 8 ký tự bao gồm, 1 ký tự viết thường, 1 ký tự"
			+ " viết hoa, 1 chữ số và 1 ký tự đặc biết!";
	
	public static final Integer ACCOUNT_IS_NOT_VERIFY = 1013;
	public static final String ACCOUNT_IS_NOT_VERIFY_MESSAGE = "Tài khoản hiện đang chưa xác minh, hãy xác minh sau đó thực hiện chức năng!";
	
	
	public static final Integer AGE_IS_NOT_ENOUGH = 1014;
	public static final String AGE_IS_NOT_ENOUGH_MESSAGE = "Số tuổi để hưởng lương hưu chưa hợp lệ!";
	
	public static final Integer NUMBER_OF_YEAR_NOT_ENOUGH = 1015;
	public static final String NUMBER_OF_YEAR_NOT_ENOUGH_MESSAGE = "Số năm đóng bảo hiểm xã hội chưa đủ!";
	
}


