package com.cqx.testspringboot.activiti.exception;

/**
 * ExceptionCode
 *
 * @author chenqixu
 */
public final class ExceptionCode {
    private ExceptionCode() {
        throw new IllegalStateException("Constant Class");
    }

    public static final String UNKNOWN = "sys-99999";

    // web服务系统一些通用的错误, 参照http错误码, 以"sys-00"开头
    // 请求参数非法, 这里主要指http header缺少
    public static final String INVALID_PARAMETER = "sys-00400";
    // 身份认证失败
    public static final String UNAUTHORIZED = "sys-00401";
    // 鉴权失败
    public static final String FORBIDDEN = "sys-00403";
    // 访问数径不存在
    public static final String NOT_FOUND = "sys-00404";
    // http方法不允许
    public static final String METHOD_NOT_ALLOWED = "sys-00405";
    // 请求超时
    public static final String REQUEST_TIMEOUT = "sys-00408";
    // Content Length必需
    public static final String LENGTH_REQUIRED = "sys-00411";
    // 请求体过大
    public static final String REQUEST_ENTITY_TOO_LARGE = "sys-00413";
    // 异常的MEDIA TYPE
    public static final String UNSUPPORTED_MEDIA_TYPE = "sys-00415";
    // 服务内部错误, 这是一个比较宽泛的异常
    public static final String INTERNAL_SERVER_ERROR = "sys-00500";
    // 服务忙
    public static final String SERVICE_UNAVAILABLE = "sys-00503";
    // 系统服务在初始化时的错误
    public static final String SYSTEM_INIT_ERROR = "sys-01000";

    // =======================================================================================================
    // web服务系统一些通用的错误, 不以http做为参照
    // 内部认证失败
    public static final String INNER_UNAUTHORIZED = "sys-00700";
    // 内部认证失败, Authentication非法
    public static final String INNER_UNAUTHORIZED_INVALID = "sys-00701";
    // 内部认证失败, X-Inner-KeyId为空
    public static final String INNER_UNAUTHORIZED_KEYID_EMPTY = "sys-00702";
    // 内部认证失败, X-Inner-Key为空
    public static final String INNER_UNAUTHORIZED_KEY_EMPTY = "sys-00703";
    // 内部认证失败, X-Inner-KeyId非法
    public static final String INNER_UNAUTHORIZED_KEYID_INVALID = "sys-00704";

    // 认证服务ip地址为空
    public static final String AUTH_HOST_EMPTY = "sys-00710";
    // 认证类型非法
    public static final String AUTH_TYPE_INVALID = "sys-00711";

    // 授权码已禁用
    public static final String AUTH_CODE_FORBIDDEN = "sys-00730";
    // 授权码已非法
    public static final String AUTH_CODE_INVILID = "sys-00731";

    // 服务熔断
    public static final String SERVICE_FUSE = "sys-00800";
    // 服务降级
    public static final String SERVICE_DEGRADATION = "sys-00801";
    // 服务限流
    public static final String SERVICE_CURRENT_LIMITING = "sys-00802";
    // 服务暂停
    public static final String SERVICE_PAUSE = "sys-00803";
    // 服务维护中
    public static final String SERVICE_MAINTAIN = "sys-00804";

    // =======================================================================================================
    // 数据库部分
    // 配置为空
    public static final String DB_EMPTY_CONFIG = "db-00001";
    // 配置为无效
    public static final String DB_INVALID_CONFIG = "db-00002";
    // 数据库类型配置非法
    public static final String DB_INVALID_TYPE = "db-00002";

    // 建表异常
    public static final String DB_CREATE_ERROR = "db-00100";
    // 查询异常
    public static final String DB_QUERY_ERROR = "db-00101";
    // 插入异常
    public static final String DB_INSERT_ERROR = "db-00102";
    // 更新异常
    public static final String DB_UPDATE_ERROR = "db-00103";
    // 删除异常
    public static final String DB_DELETE_ERROR = "db-00104";

    // =======================================================================================================
    // 网络部分
    // 网络连接失败
    public static final String NET_FAILURE = "net-00001";
}
