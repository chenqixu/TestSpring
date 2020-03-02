package com.cqx.testspring.webservice.common.util.session;

import com.cqx.testspring.webservice.common.util.other.DateUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Function
 *
 * @author chenqixu
 */
public class Function {
    public Function() {
    }

    public static String checkSplit(String str) {
        if (str != null && str.contains(",")) {
            return str.split(",").length > 1 ? str.split(",")[1] : str;
        } else {
            return str != null ? str : null;
        }
    }

    public static String setSelectStrInner(Object beanObj, String tableName1, String tableName2) {
        return setSelectStrInner(beanObj, tableName1, tableName2, "");
    }

    public static String setSelectStrInner(Object beanObj, String tableName1, String tableName2, String tableFlag) {
        boolean home_mzone_area = false;
        boolean home_area_code = false;
        boolean mzone_user_brand_desc = false;
        boolean user_status = false;
        boolean confirm_cur_flag = false;
        String sqlstr = "select ";
        Field[] allFields = beanObj.getClass().getDeclaredFields();

        for (int i = 1; i < allFields.length; ++i) {
            String fieldName = allFields[i].getName();
            Object value = null;

            try {
                value = PropertyUtils.getProperty(beanObj, fieldName);
            } catch (IllegalAccessException var15) {
                var15.printStackTrace();
            } catch (InvocationTargetException var16) {
                var16.printStackTrace();
            } catch (NoSuchMethodException var17) {
                var17.printStackTrace();
            }

            if (value != null) {
                if (fieldName.equals("home_mzone_area")) {
                    sqlstr = sqlstr + "b.home_area_desc as mzone_home_area_desc,";
                    home_mzone_area = true;
                } else if (fieldName.equals("home_area_code")) {
                    sqlstr = sqlstr + "c.home_area_desc as home_area_desc,";
                    home_area_code = true;
                } else if (fieldName.equals("user_brand_desc") && tableFlag.equals("0")) {
                    sqlstr = sqlstr + "d.user_brand_desc as mzone_user_brand_desc,";
                    mzone_user_brand_desc = true;
                } else if (fieldName.equals("user_status")) {
                    sqlstr = sqlstr + "e.status_desc,";
                    user_status = true;
                } else if (fieldName.equals("confirm_cur_flag")) {
                    sqlstr = sqlstr + "f.is_desc,";
                    confirm_cur_flag = true;
                } else if (fieldName.equals("join_date")) {
                    sqlstr = sqlstr + "to_char(x.join_date,'yyyy-mm-dd'),";
                } else {
                    sqlstr = sqlstr + "x." + fieldName + ",";
                }
            }
        }

        if (sqlstr.length() > 0) {
            sqlstr = sqlstr.substring(0, sqlstr.length() - 1);
        }

        sqlstr = sqlstr + " from " + tableName1 + " a ";
        if (tableFlag.equals("targetGotone")) {
            sqlstr = sqlstr + " inner join " + tableName2 + " x on a.user_id=x.user_id and a.sum_date = x.sum_date";
        } else {
            sqlstr = sqlstr + " inner join " + tableName2 + " x on a.user_id=x.user_id ";
        }

        if (home_area_code) {
            sqlstr = sqlstr + " left join " + "bishow." + "dim_home_area c on c.home_area_code=x.home_county";
        }

        if (mzone_user_brand_desc) {
            sqlstr = sqlstr + " left join " + "bishow." + "dim_brand d on d.user_brand=a.brand_id";
        }

        if (user_status) {
            sqlstr = sqlstr + " left join " + "bishow." + "dim_user_status e on e.status_id=a.user_status";
        }

        return sqlstr;
    }

    public static String setSelectStr(Object beanObj, String tableName) {
        return setSelectStr(beanObj, tableName, "");
    }

    public static String setSelectStr(Object beanObj, String tableName, String tableFlag) {
        boolean home_mzone_area = false;
        boolean home_area_code = false;
        boolean mzone_user_brand_desc = false;
        boolean user_status = false;
        boolean confirm_cur_flag = false;
        boolean is_mplan = false;
        boolean is_prepay_limit = false;
        String sqlstr = "select ";
        Field[] allFields = beanObj.getClass().getDeclaredFields();

        for (int i = 1; i < allFields.length; ++i) {
            String fieldName = allFields[i].getName();
            Object value = null;

            try {
                value = PropertyUtils.getProperty(beanObj, fieldName);
            } catch (IllegalAccessException var16) {
                var16.printStackTrace();
            } catch (InvocationTargetException var17) {
                var17.printStackTrace();
            } catch (NoSuchMethodException var18) {
                var18.printStackTrace();
            }

            if (value != null) {
                if (fieldName.equals("home_mzone_area")) {
                    sqlstr = sqlstr + "b.home_area_desc as mzone_home_area_desc,";
                    home_mzone_area = true;
                } else if (fieldName.equals("home_area_code")) {
                    sqlstr = sqlstr + "c.home_area_desc as home_area_desc,";
                    home_area_code = true;
                } else if (fieldName.equals("user_brand_desc") && tableFlag.equals("0")) {
                    sqlstr = sqlstr + "d.user_brand_desc as mzone_user_brand_desc,";
                    mzone_user_brand_desc = true;
                } else if (fieldName.equals("user_status")) {
                    sqlstr = sqlstr + "e.status_desc,";
                    user_status = true;
                } else if (fieldName.equals("confirm_cur_flag")) {
                    sqlstr = sqlstr + "f.is_desc as f_is_desc,";
                    confirm_cur_flag = true;
                } else if (fieldName.equals("is_mplan")) {
                    sqlstr = sqlstr + "g.is_desc as g_is_desc,";
                    is_mplan = true;
                } else if (fieldName.equals("is_prepay_limit")) {
                    sqlstr = sqlstr + "h.is_desc as h_is_desc,";
                    is_prepay_limit = true;
                } else if (fieldName.equals("join_date")) {
                    if (tableFlag.equals("targetGotone")) {
                        sqlstr = sqlstr + "to_char(x.join_date,'yyyy-mm-dd'),";
                    } else {
                        sqlstr = sqlstr + "to_char(a.join_date,'yyyy-mm-dd'),";
                    }
                } else if (tableFlag.equals("targetGotone")) {
                    sqlstr = sqlstr + "x." + fieldName + ",";
                } else {
                    sqlstr = sqlstr + "a." + fieldName + ",";
                }
            }
        }

        if (sqlstr.length() > 0) {
            sqlstr = sqlstr.substring(0, sqlstr.length() - 1);
        }

        sqlstr = sqlstr + " from " + tableName + " a ";
        if (tableFlag.equals("targetGotone")) {
            sqlstr = sqlstr + " inner join " + "bishow." + "qry_gotone_inte_chg_user_info x on a.user_id=x.user_id and a.sum_date = x.sum_date";
        }

        if (home_mzone_area) {
            sqlstr = sqlstr + " left join " + "bishow." + "dim_home_area b on b.home_area_code=a.home_county";
        }

        if (home_area_code) {
            if (tableFlag.equals("targetGotone")) {
                sqlstr = sqlstr + " left join " + "bishow." + "dim_home_area c on c.home_area_code=a.operator_county";
            } else if (tableFlag.equals("1")) {
                sqlstr = sqlstr + " left join " + "bishow." + "dim_home_area c on c.home_area_code=a.home_county";
            } else {
                sqlstr = sqlstr + " left join " + "bishow." + "dim_home_area c on c.home_area_code=a.home_area_code";
            }
        }

        if (mzone_user_brand_desc) {
            sqlstr = sqlstr + " left join " + "bishow." + "dim_brand d on d.user_brand=a.brand_id";
        }

        if (user_status) {
            sqlstr = sqlstr + " left join " + "bishow." + "dim_user_status e on e.status_id=a.user_status";
        }

        if (confirm_cur_flag) {
            sqlstr = sqlstr + " left join " + "bishow." + "dim_is_func f on f.is_id=a.confirm_cur_flag";
        }

        if (is_mplan) {
            sqlstr = sqlstr + " left join " + "bishow." + "dim_is_func g on g.is_id=a.is_mplan";
        }

        if (is_prepay_limit) {
            sqlstr = sqlstr + " left join " + "bishow." + "dim_is_func h on h.is_id=a.is_prepay_limit";
        }

        return sqlstr;
    }

    public static String setSelectStr(Object beanObj, String[] strTableName) {
        boolean basicTable = false;
        boolean addTable = false;
        boolean consumeTable = false;
        boolean needTable = false;
        boolean behaviorTable = false;
        boolean serviceTable = false;
        boolean userStatus = false;
        String sqlstr = "select ";
        Field[] allFields = beanObj.getClass().getDeclaredFields();

        for (int i = 1; i < allFields.length; ++i) {
            String fieldName = allFields[i].getName();
            Object value = null;

            try {
                value = PropertyUtils.getProperty(beanObj, fieldName);
            } catch (IllegalAccessException var15) {
                var15.printStackTrace();
            } catch (InvocationTargetException var16) {
                var16.printStackTrace();
            } catch (NoSuchMethodException var17) {
                var17.printStackTrace();
            }

            if (value != null) {
                if (fieldName.equals("msisdn")) {
                    sqlstr = sqlstr + "basicTable.msisdn,";
                    basicTable = true;
                } else if (fieldName.equals("brand_desc")) {
                    sqlstr = sqlstr + "basicTable.brand_desc,";
                    basicTable = true;
                } else if (fieldName.equals("user_brand")) {
                    sqlstr = sqlstr + "basicTable.user_brand,";
                    basicTable = true;
                } else if (fieldName.equals("user_brand_desc")) {
                    sqlstr = sqlstr + "basicTable.user_brand_desc,";
                    basicTable = true;
                } else if (fieldName.equals("user_status")) {
                    sqlstr = sqlstr + "nvl(e.status_desc,'未知'),";
                    userStatus = true;
                    basicTable = true;
                } else if (fieldName.equals("join_date")) {
                    sqlstr = sqlstr + "basicTable.join_date,";
                    basicTable = true;
                } else if (fieldName.equals("user_name")) {
                    sqlstr = sqlstr + "addTable.user_name,";
                    addTable = true;
                } else if (fieldName.equals("user_tel")) {
                    sqlstr = sqlstr + "addTable.user_tel,";
                    addTable = true;
                } else if (fieldName.equals("ic_addr")) {
                    sqlstr = sqlstr + "addTable.ic_addr,";
                    addTable = true;
                } else if (fieldName.equals("user_addr")) {
                    sqlstr = sqlstr + "addTable.user_addr,";
                    addTable = true;
                } else if (fieldName.equals("terminal_manufact")) {
                    sqlstr = sqlstr + "addTable.terminal_manufact,";
                    addTable = true;
                } else if (fieldName.equals("model_desc")) {
                    sqlstr = sqlstr + "addTable.model_desc,";
                    addTable = true;
                } else if (fieldName.equals("pre2_total_fee")) {
                    sqlstr = sqlstr + "consumeTable.pre2_total_fee,";
                    consumeTable = true;
                } else if (fieldName.equals("pre_total_fee")) {
                    sqlstr = sqlstr + "consumeTable.pre_total_fee,";
                    consumeTable = true;
                } else if (fieldName.equals("sum_fee")) {
                    sqlstr = sqlstr + "consumeTable.sum_fee,";
                    consumeTable = true;
                } else if (fieldName.equals("pre_pkg_fee")) {
                    sqlstr = sqlstr + "consumeTable.pre_pkg_fee,";
                    consumeTable = true;
                } else if (fieldName.equals("pre_local_cfee")) {
                    sqlstr = sqlstr + "consumeTable.pre_local_cfee,";
                    consumeTable = true;
                } else if (fieldName.equals("pre_sum_fee")) {
                    sqlstr = sqlstr + "consumeTable.pre_sum_fee,";
                    consumeTable = true;
                } else if (fieldName.equals("pre_roam_fee")) {
                    sqlstr = sqlstr + "consumeTable.pre_roam_fee,";
                    consumeTable = true;
                } else if (fieldName.equals("pre_local_lfee")) {
                    sqlstr = sqlstr + "consumeTable.pre_local_lfee,";
                    consumeTable = true;
                } else if (fieldName.equals("pre_sp_fee")) {
                    sqlstr = sqlstr + "consumeTable.pre_sp_fee,";
                    consumeTable = true;
                } else if (fieldName.equals("less_50amt_months")) {
                    sqlstr = sqlstr + "consumeTable.less_50amt_months,";
                    consumeTable = true;
                } else if (fieldName.equals("pre_data_fee")) {
                    sqlstr = sqlstr + "consumeTable.pre_data_fee,";
                    consumeTable = true;
                } else if (fieldName.equals("pre2_total_call_dur60")) {
                    sqlstr = sqlstr + "needTable.pre2_total_call_dur60,";
                    needTable = true;
                } else if (fieldName.equals("pre_total_call_dur60")) {
                    sqlstr = sqlstr + "needTable.pre_total_call_dur60,";
                    needTable = true;
                } else if (fieldName.equals("voc_call_dur60")) {
                    sqlstr = sqlstr + "needTable.voc_call_dur60,";
                    needTable = true;
                } else if (fieldName.equals("pre_local_call_dur60")) {
                    sqlstr = sqlstr + "needTable.pre_local_call_dur60,";
                    needTable = true;
                } else if (fieldName.equals("pre_roam_call_dur60")) {
                    sqlstr = sqlstr + "needTable.pre_roam_call_dur60,";
                    needTable = true;
                } else if (fieldName.equals("pre_ldc_call_dur60")) {
                    sqlstr = sqlstr + "needTable.pre_ldc_call_dur60,";
                    needTable = true;
                } else if (fieldName.equals("pre_sms_up_cnt")) {
                    sqlstr = sqlstr + "needTable.pre_sms_up_cnt,";
                    needTable = true;
                } else if (fieldName.equals("pre_sum_data")) {
                    sqlstr = sqlstr + "needTable.pre_sum_data,";
                    needTable = true;
                } else if (fieldName.equals("pre_mms_call_cnt")) {
                    sqlstr = sqlstr + "needTable.pre_mms_call_cnt,";
                    needTable = true;
                } else if (fieldName.equals("pre_voc_call_cnt")) {
                    sqlstr = sqlstr + "needTable.pre_voc_call_cnt,";
                    needTable = true;
                } else if (fieldName.equals("voc_call_cnt")) {
                    sqlstr = sqlstr + "needTable.voc_call_cnt,";
                    needTable = true;
                } else if (fieldName.equals("roam_call_dur60")) {
                    sqlstr = sqlstr + "needTable.roam_call_dur60,";
                    needTable = true;
                } else if (fieldName.equals("ldc_call_dur60")) {
                    sqlstr = sqlstr + "needTable.ldc_call_dur60,";
                    needTable = true;
                } else if (fieldName.equals("sms_up_cnt")) {
                    sqlstr = sqlstr + "needTable.sms_up_cnt,";
                    needTable = true;
                } else if (fieldName.equals("sum_data")) {
                    sqlstr = sqlstr + "needTable.sum_data,";
                    needTable = true;
                } else if (fieldName.equals("call_cnt")) {
                    sqlstr = sqlstr + "needTable.call_cnt,";
                    needTable = true;
                } else if (fieldName.equals("divert_other_cnt")) {
                    sqlstr = sqlstr + "behaviorTable.divert_other_cnt,";
                    behaviorTable = true;
                } else if (fieldName.equals("last_term_type")) {
                    sqlstr = sqlstr + "behaviorTable.last_term_type,";
                    behaviorTable = true;
                } else if (fieldName.equals("last_voc_call_dur60")) {
                    sqlstr = sqlstr + "behaviorTable.last_voc_call_dur60,";
                    behaviorTable = true;
                } else if (fieldName.equals("voc_acall_dur60")) {
                    sqlstr = sqlstr + "behaviorTable.voc_call_dur60,";
                    behaviorTable = true;
                } else if (fieldName.equals("other_nbr_cnt")) {
                    sqlstr = sqlstr + "behaviorTable.other_nbr_cnt,";
                    behaviorTable = true;
                } else if (fieldName.equals("pay_channel")) {
                    sqlstr = sqlstr + "behaviorTable.pay_channel,";
                    behaviorTable = true;
                } else if (fieldName.equals("morn_call_dur60")) {
                    sqlstr = sqlstr + "behaviorTable.morn_call_dur60,";
                    behaviorTable = true;
                } else if (fieldName.equals("noon_call_dur60")) {
                    sqlstr = sqlstr + "behaviorTable.noon_call_dur60,";
                    behaviorTable = true;
                } else if (fieldName.equals("night_call_dur60")) {
                    sqlstr = sqlstr + "behaviorTable.night_call_dur60,";
                    behaviorTable = true;
                } else if (fieldName.equals("twelve_consume_change_value")) {
                    sqlstr = sqlstr + "cc.twelve_consume_change_value,";
                    behaviorTable = true;
                } else if (fieldName.equals("six_consume_change_value")) {
                    sqlstr = sqlstr + "cc.six_consume_change_value,";
                    behaviorTable = true;
                } else if (fieldName.equals("three_consume_change_value")) {
                    sqlstr = sqlstr + "cc.three_consume_change_value,";
                    behaviorTable = true;
                } else if (fieldName.equals("all_other_nbr_cnt")) {
                    sqlstr = sqlstr + "behaviorTable.all_other_nbr_cnt,";
                    behaviorTable = true;
                } else if (fieldName.equals("member_flag")) {
                    sqlstr = sqlstr + "serviceTable.member_flag,";
                    serviceTable = true;
                } else if (fieldName.equals("mgr_id")) {
                    sqlstr = sqlstr + "serviceTable.mgr_id,";
                    serviceTable = true;
                } else if (fieldName.equals("manager_name")) {
                    sqlstr = sqlstr + "serviceTable.manager_name,";
                    serviceTable = true;
                } else if (fieldName.equals("card_level")) {
                    sqlstr = sqlstr + "serviceTable.card_level,";
                    serviceTable = true;
                } else if (fieldName.equals("gen_type")) {
                    sqlstr = sqlstr + "serviceTable.gen_type,";
                    serviceTable = true;
                } else if (fieldName.equals("limit_flag")) {
                    sqlstr = sqlstr + "serviceTable.limit_flag,";
                    serviceTable = true;
                } else if (fieldName.equals("balance_amt")) {
                    sqlstr = sqlstr + "serviceTable.balance_amt,";
                    serviceTable = true;
                } else if (fieldName.equals("expire_date")) {
                    sqlstr = sqlstr + "serviceTable.expire_date,";
                    serviceTable = true;
                }
            }
        }

        if (sqlstr.length() > 0) {
            sqlstr = sqlstr.substring(0, sqlstr.length() - 1);
        }

        sqlstr = sqlstr + " from " + "bishow." + strTableName[6] + " cc ";
        if (basicTable) {
            sqlstr = sqlstr + " left join " + "bishow." + strTableName[0] + " basicTable ";
            sqlstr = sqlstr + " on " + " basicTable.user_id = cc.user_id ";
        }

        if (addTable) {
            sqlstr = sqlstr + " left join " + "bishow." + strTableName[1] + " addTable ";
            sqlstr = sqlstr + " on " + " cc.user_id = addTable.user_id ";
        }

        if (consumeTable) {
            sqlstr = sqlstr + " left join " + "bishow." + strTableName[2] + " consumeTable ";
            sqlstr = sqlstr + " on " + " cc.user_id = consumeTable.user_id ";
        }

        if (needTable) {
            sqlstr = sqlstr + " left join " + "bishow." + strTableName[3] + " needTable ";
            sqlstr = sqlstr + " on " + " cc.user_id = needTable.user_id ";
        }

        if (behaviorTable) {
            sqlstr = sqlstr + " left join " + "bishow." + strTableName[4] + " behaviorTable ";
            sqlstr = sqlstr + " on " + " cc.user_id = behaviorTable.user_id ";
        }

        if (serviceTable) {
            sqlstr = sqlstr + " left join " + "bishow." + strTableName[5] + " serviceTable ";
            sqlstr = sqlstr + " on " + " cc.user_id = serviceTable.user_id ";
        }

        if (userStatus) {
            sqlstr = sqlstr + " left join " + "bishow." + "dim_user_status" + " e ";
            sqlstr = sqlstr + " on " + " basicTable.user_status = e.status_id ";
        }

        return sqlstr;
    }

    public static void setBeanParams(Object beanObj, String beanParams) {
        Map paramsMap = new HashMap();
        String[] dataStr;
        int i;
        String fieldName;
//        String[] dataStr;
        if (beanParams.contains("0x7f")) {
            dataStr = beanParams.split("0x7f");

            for (i = 0; i < dataStr.length; ++i) {
                fieldName = dataStr[i];
                if (fieldName.contains("0x09")) {
                    dataStr = fieldName.split("0x09");

                    for (int j = 0; j < dataStr.length; ++j) {
                        String data = dataStr[j];
                        if (data.contains("=")) {
                            dataStr = data.split("=");
                            if (dataStr.length > 1) {
                                paramsMap.put(dataStr[0], dataStr[1]);
                            }
                        }
                    }
                } else if (fieldName.contains("=")) {
                    dataStr = fieldName.split("=");
                    if (dataStr.length > 1) {
                        paramsMap.put(dataStr[0], dataStr[1]);
                    }
                }
            }
        } else if (beanParams.contains("0x09")) {
            dataStr = beanParams.split("0x09");

            for (i = 0; i < dataStr.length; ++i) {
                fieldName = dataStr[i];
                if (fieldName.contains("=")) {
                    dataStr = fieldName.split("=");
                    if (dataStr.length > 1) {
                        paramsMap.put(dataStr[0], dataStr[1]);
                    }
                } else {
                    paramsMap.put(fieldName, fieldName + "no value");
                }
            }
        } else if (beanParams.contains("=")) {
            dataStr = beanParams.split("=");
            if (dataStr.length > 1) {
                paramsMap.put(dataStr[0], dataStr[1]);
            }
        } else {
            paramsMap.put(beanParams, beanParams + " no value");
        }

        Field[] allFields = beanObj.getClass().getDeclaredFields();

        for (i = 1; i < allFields.length; ++i) {
            fieldName = allFields[i].getName();
            Object propertyValue = paramsMap.get(fieldName);
            if (propertyValue != null) {
                try {
                    BeanUtils.setProperty(beanObj, fieldName, propertyValue);
                } catch (IllegalAccessException var10) {
                    var10.printStackTrace();
                } catch (InvocationTargetException var11) {
                    var11.printStackTrace();
                }
            }
        }

    }

    public static void setWhereCondition(StringBuffer sqlstr, Object obj, String whereColumn, String strflag) {
        if (obj != null && obj != "-999999") {
            if (whereColumn.indexOf("home_area_code") < 0 && whereColumn.indexOf("home_code") < 0 && whereColumn.indexOf("home_mzone_area") < 0) {
                if (whereColumn.indexOf("home_county") < 0 && whereColumn.indexOf("home_country") < 0 && whereColumn.indexOf("member_home_county") < 0) {
                    if (whereColumn.indexOf("home_town") >= 0) {
                        // TODO: 2020/2/28  SQLBuilder
//                        sqlstr.append(SQLBuilder.getDataPrivSql(obj, whereColumn, "home_town"));
                    } else if (strflag.trim().equals("true")) {
                        sqlstr.append(" and " + whereColumn + "='" + obj + "'");
                    } else if (strflag.trim().equals("false")) {
                        sqlstr.append(" and " + whereColumn + "=" + obj);
                    } else if (strflag.trim().equals("like")) {
                        sqlstr.append(" and " + whereColumn + " like '%" + obj + "%'");
                    } else if (strflag.trim().equals(">")) {
                        sqlstr.append(" and " + whereColumn + ">" + obj);
                    } else if (strflag.trim().equals("<")) {
                        sqlstr.append(" and " + whereColumn + "<" + obj);
                    } else if (strflag.trim().equals("<=")) {
                        sqlstr.append(" and " + whereColumn + "<=" + obj);
                    } else if (strflag.trim().equals(">=")) {
                        sqlstr.append(" and " + whereColumn + ">=" + obj);
                    }
                } else {
                    // TODO: 2020/2/28 SQLBuilder 
//                    sqlstr.append(SQLBuilder.getDataPrivSql(obj, whereColumn));
                }
            } else {
                // TODO: 2020/2/28 SQLBuilder 
//                sqlstr.append(SQLBuilder.getDataPrivSql(obj, whereColumn, "home_code"));
            }
        }

    }

    public static void setWhereCondition(Object beanObj, StringBuffer sqlstr) {
        setWhereCondition(beanObj, sqlstr, "0");
    }

    public static void setWhereCondition(Object beanObj, StringBuffer sqlstr, String imeiType) {
        Field[] allFields = beanObj.getClass().getDeclaredFields();

        for (int i = 1; i < allFields.length; ++i) {
            Object value = null;
            String fieldName = allFields[i].getName();

            try {
                value = PropertyUtils.getProperty(beanObj, fieldName);
            } catch (IllegalAccessException var12) {
                var12.printStackTrace();
            } catch (InvocationTargetException var13) {
                var13.printStackTrace();
            } catch (NoSuchMethodException var14) {
                var14.printStackTrace();
            }

            if (!fieldName.equals("isRollUp") && !fieldName.equals("select_column") && !fieldName.equals("select_show_column") && !fieldName.equals("group_column")) {
                String[] temp;
                String tempStr;
                if (fieldName.equals("sum_month")) {
                    if (value != null && !value.toString().equals("") && !value.toString().equals("-999999")) {
                        if (value.toString().indexOf(",") >= 0) {
                            temp = value.toString().split(",");
                            sqlstr.append(" and a." + fieldName + ">=" + temp[0]);
                            sqlstr.append(" and a." + fieldName + "<=" + temp[1]);
                        } else if (value.toString().indexOf("0x09") >= 0) {
                            tempStr = value.toString().replaceAll("0x09", ",");
                            sqlstr.append(" and a." + fieldName + " in (" + tempStr + ")");
                        } else {
                            sqlstr.append(" and a." + fieldName + "=" + value);
                        }
                    } else {
                        sqlstr.append(" and a." + fieldName + "=" + DateUtil.getLastMonth(DateUtil.getCurrDate("yyyyMM")));
                    }
                } else if (fieldName.equals("sum_date")) {
                    if (value != null && !value.toString().equals("") && !value.toString().equals("-999999")) {
                        if (value.toString().indexOf(",") >= 0) {
                            temp = value.toString().split(",");
                            sqlstr.append(" and a." + fieldName + ">=" + temp[0]);
                            sqlstr.append(" and a." + fieldName + "<=" + temp[1]);
                        } else if (value.toString().indexOf("0x09") >= 0) {
                            tempStr = value.toString().replaceAll("0x09", ",");
                            sqlstr.append(" and a." + fieldName + " in (" + tempStr + ")");
                        } else {
                            sqlstr.append(" and a." + fieldName + "=" + value);
                        }
                    } else {
                        sqlstr.append(" and a." + fieldName + "=" + DateUtil.getLastDay(DateUtil.getCurrDate("yyyyMMdd")));
                    }
                } else if (!fieldName.equals("home_area_code") || !imeiType.equals("223") && !imeiType.equals("222")) {
                    if (fieldName.equals("home_area_code") && imeiType.equals("2222")) {
                        // TODO: 2020/2/28  SQLBuilder
//                        sqlstr.append(SQLBuilder.buildDimTreeWhere(value, "a.home_area_code", "bishow.dim_home_area", "home_area_group_code,home_area_code"));
                    } else if (fieldName.equals("sell_case_id")) {
                        // TODO: 2020/2/28 SQLBuilder 
//                        sqlstr.append(SQLBuilder.buildDimTreeWhere(value, "a.sell_case_id", "bishow.Dim_sale", "home_area_group_code,active_id,sub_active_id,sale_id"));
                    } else if (fieldName.equals("home_county") && (imeiType.equals("223") || imeiType.equals("222"))) {
                        // TODO: 2020/2/28 SQLBuilder 
//                        sqlstr.append(SQLBuilder.getDataPrivSql(value, "trim(a.home_county)", "home_county_desc"));
                    } else if (!fieldName.equals("brand_id") || !imeiType.equals("223") && !imeiType.equals("222") && !imeiType.equals("2222")) {
                        if (fieldName.equals("visitant_mgr_id") && imeiType.equals("223")) {
                            if (value != null && !value.equals("-9999") && !value.equals("-999999") && !value.equals("null") && !value.equals("")) {
                                sqlstr.append(" and a." + fieldName + " in (" + value.toString().replaceAll("0x09", ",") + ")");
                            }
                        } else if (value != null && !value.equals("-9999") && !value.equals("-999999") && !value.equals("null") && !value.equals("")) {
                            if (!value.toString().contains(",") && !value.toString().contains("0x12") && !value.toString().contains("0x11")) {
                                if (value != null && !fieldName.equals("home_city")) {
                                    if (fieldName.equals("start_date")) {
                                        sqlstr.append(" and a.day_id>=" + value);
                                    } else if (fieldName.equals("end_date")) {
                                        sqlstr.append(" and a.day_id<=" + value);
                                    } else if (!fieldName.equals("netage_integra") && !fieldName.equals("consume_integra") && !fieldName.equals("datasvc_integral") && !fieldName.equals("credit_integral")) {
                                        if (!fieldName.equals("cus_name") && !fieldName.equals("ic_no") && !fieldName.equals("cus_addr")) {
                                            if (fieldName.equals("join_date")) {
                                                sqlstr.append(" and to_char(a.join_date,'yyyymm')=" + value + "");
                                            } else if (!fieldName.equals("month_rent_ratio") && !fieldName.equals("voice_consume_ratio")) {
                                                if (fieldName.equals("accept_item_type")) {
                                                    sqlstr.append(" and a." + fieldName + " in(" + value + ")");
                                                } else if (fieldName.equals("home_area_code")) {
                                                    // TODO: 2020/2/28 SQLBuilder 
//                                                    sqlstr.append(SQLBuilder.getDataPrivSql(value, "a.home_area_code"));
                                                } else if (fieldName.equals("msisdn")) {
                                                    sqlstr.append(" and a." + fieldName + " like '%" + value + "%'");
                                                } else if (fieldName.equals("visitant_mgr_id")) {
                                                    if (imeiType.equals("222")) {
                                                        if (value.toString().indexOf("0x09") >= 0) {
                                                            tempStr = value.toString().replaceAll("0x09", ",");
                                                            sqlstr.append(" and a." + fieldName + " in (" + tempStr + ")");
                                                        } else {
                                                            sqlstr.append(" and a." + fieldName + " = " + value + "");
                                                        }
                                                    } else {
                                                        sqlstr.append(" and a." + fieldName + " in (select visitant_mgr_id from " + "bishow." + "Dim_Visitant_Mgr where visitant_mgr_desc like '%" + value + "%')");
                                                    }
                                                } else if (fieldName.equals("mgr_id")) {
                                                    if (value.toString().contains("0x09")) {
                                                        sqlstr.append(" and a." + fieldName + " in (" + value.toString().replaceAll("0x09", ",") + ")");
                                                    } else {
                                                        sqlstr.append(" and a." + fieldName + " in (" + value + ")");
                                                    }
                                                } else if (fieldName.equals("alter_lm_amt")) {
                                                    sqlstr.append(" and a." + fieldName + " >=" + value + "");
                                                } else if (fieldName.equals("alter_lm_amt2")) {
                                                    sqlstr.append(" and a.alter_lm_amt <=" + value + "");
                                                } else if (fieldName.equals("base_avg_amt")) {
                                                    sqlstr.append(" and a." + fieldName + " >=" + value + "");
                                                } else if (fieldName.equals("base_avg_amt2")) {
                                                    sqlstr.append(" and a.base_avg_amt <=" + value + "");
                                                } else if (fieldName.equals("balance_inte")) {
                                                    sqlstr.append(" and a." + fieldName + " >=" + value + "");
                                                } else if (fieldName.equals("balance_inte2")) {
                                                    sqlstr.append(" and a.balance_inte <=" + value + "");
                                                } else if (fieldName.equals("complain_channel")) {
                                                    sqlstr.append(" and a." + fieldName + " like '%" + value + "%'");
                                                } else if (!fieldName.equals("complain_cnt1") && !fieldName.equals("ring_fee1") && !fieldName.equals("sum_fee1") && !fieldName.equals("sum_date1") && !fieldName.equals("ring_sp_cnt1") && !fieldName.equals("ring_center_cnt1") && !fieldName.equals("join_dur1") && !fieldName.equals("ring_dur1") && !fieldName.equals("club_dur1") && !fieldName.equals("ring_cnt1") && !fieldName.equals("low_dur1")) {
                                                    if (!fieldName.equals("complain_cnt2") && !fieldName.equals("ring_fee2") && !fieldName.equals("sum_fee2") && !fieldName.equals("sum_date2") && !fieldName.equals("ring_sp_cnt2") && !fieldName.equals("ring_center_cnt2") && !fieldName.equals("join_dur2") && !fieldName.equals("ring_dur2") && !fieldName.equals("club_dur2") && !fieldName.equals("ring_cnt2") && !fieldName.equals("low_dur2")) {
                                                        if (!fieldName.equals("change_grade") && !fieldName.equals("cancel_type") && !fieldName.equals("is_ring") && !fieldName.equals("ring_type") && !fieldName.equals("is_box") && !fieldName.equals("is_package") && !fieldName.equals("club_level")) {
                                                            if (fieldName.equals("opr_source")) {
                                                                sqlstr.append(" and trim(a." + fieldName + ") = '" + value + "'");
                                                            } else if (fieldName.equals("music_brand_id")) {
                                                                sqlstr.append(" and trim(a.brand_id) like '%" + value + "%'");
                                                            } else if (fieldName.equals("sum_time")) {
                                                                sqlstr.append(" and a.sum_month = " + value + "");
                                                            } else {
                                                                sqlstr.append(" and a." + fieldName + "=" + value);
                                                            }
                                                        } else {
                                                            sqlstr.append(" and trim(a." + fieldName + ") = '" + value + "'");
                                                        }
                                                    } else {
                                                        tempStr = fieldName.substring(0, fieldName.length() - 1);
                                                        sqlstr.append(" and a." + tempStr + " <=" + value + "");
                                                    }
                                                } else {
                                                    tempStr = fieldName.substring(0, fieldName.length() - 1);
                                                    sqlstr.append(" and a." + tempStr + " >=" + value + "");
                                                }
                                            } else {
                                                sqlstr.append(" and (a." + fieldName + ">=" + value);
                                                double value2 = Double.valueOf(value.toString()) + 0.1D;
                                                sqlstr.append(" and a." + fieldName + "<" + value2 + ")");
                                            }
                                        } else {
                                            sqlstr.append(" and a." + fieldName + " is null");
                                        }
                                    } else {
                                        sqlstr.append(" and a." + fieldName + ">0");
                                    }
                                }
                            } else {
                                temp = null;
                                if (!value.toString().contains("0x12") && !value.toString().contains("0x11")) {
                                    temp = value.toString().split(",");
                                } else {
                                    // TODO: 2020/2/28  PowerUtil
//                                    temp = PowerUtil.decodeDataPriv(value.toString()).split(",");
                                }

                                if (temp.length <= 1 && !value.toString().contains("0x12") && !value.toString().contains("0x11")) {
                                    if (temp.length == 1) {
                                        if (fieldName.equals("sum_fee") || fieldName.equals("new_bus_fee")) {
                                            sqlstr.append(" and a." + fieldName + " >= " + temp[0] + "");
                                        }
                                    } else {
                                        value = null;
                                    }
                                } else {
                                    String allValue = value.toString();
                                    if (temp.length > 1) {
                                        value = temp[1];
                                    }

                                    if (fieldName.equals("home_area_code")) {
                                        if (imeiType.equals("2")) {
                                            // TODO: 2020/2/28  SQLBuilder
//                                            sqlstr.append(SQLBuilder.getDataPrivSql(allValue, "x.home_county"));
                                        } else if (imeiType.equals("22")) {
                                            // TODO: 2020/2/28 SQLBuilder
//                                            sqlstr.append(SQLBuilder.getDataPrivSql(allValue, "a.operator_county"));
//                                            sqlstr.append(SQLBuilder.getDataPrivSql(allValue, "x.home_county"));
                                        } else if (imeiType.equals("222")) {
                                            // TODO: 2020/2/28 SQLBuilder
//                                            sqlstr.append(SQLBuilder.getDataPrivSql(allValue, "a.home_county"));
                                        } else {
                                            // TODO: 2020/2/28 SQLBuilder
//                                            sqlstr.append(SQLBuilder.getDataPrivSql(allValue, "a." + fieldName + ""));
                                        }
                                    } else if (!fieldName.equals("town_id") && !fieldName.equals("his_town_id")) {
                                        if (fieldName.equals("mob_term_type")) {
                                            if (!temp[0].equals("1") && !temp[0].equals("0")) {
                                                if (temp[0].equals("2")) {
                                                    sqlstr.append(" and a." + fieldName + " = " + value + "");
                                                }
                                            } else {
                                                sqlstr.append(" and a." + fieldName + " in (select mob_term_type from " + "bishow." + "dim_mob_term_type where mob_term_brand_name='" + value + "')");
                                            }
                                        } else if (fieldName.equals("callpay_change_id")) {
                                            if (!temp[0].equals("1") && !temp[0].equals("0")) {
                                                if (temp[0].equals("2")) {
                                                    sqlstr.append(" and a." + fieldName + " = " + value + "");
                                                }
                                            } else {
                                                sqlstr.append(" and a." + fieldName + " in (select callpay_change_id from " + "bishow." + "dim_callpay_change_lev where callpay_change_group='" + value + "')");
                                            }
                                        } else if (fieldName.equals("tran_num_lev_id")) {
                                            if (!temp[0].equals("1") && !temp[0].equals("0")) {
                                                if (temp[0].equals("2")) {
                                                    sqlstr.append(" and a." + fieldName + " = " + value + "");
                                                }
                                            } else {
                                                sqlstr.append(" and a." + fieldName + " in (select cdr_num_lev_id from " + "bishow." + "dim_cdr_num_lev where cdr_num_lev_group1='" + value + "')");
                                            }
                                        } else if (fieldName.equals("model_id")) {
                                            if (!temp[0].equals("1") && !temp[0].equals("0")) {
                                                if (temp[0].equals("2")) {
                                                    sqlstr.append(" and a." + fieldName + " = " + value + "");
                                                }
                                            } else {
                                                sqlstr.append(" and a." + fieldName + " in (select model_id from " + "bishow." + "dim_term_model where property_id=" + value + ")");
                                            }
                                        } else if (!fieldName.equals("his_online_dur_lev_id") && !fieldName.equals("online_dur_lev_id") && !fieldName.equals("online_dur_lev_id")) {
                                            if (!fieldName.equals("user_brand") && !fieldName.equals("his_user_brand") && !fieldName.equals("brand_id")) {
                                                if (!fieldName.equals("channel_type") && !fieldName.equals("his_channel_type")) {
                                                    if (!fieldName.equals("channel_id") && !fieldName.equals("his_channel_id") && !fieldName.equals("org_id")) {
                                                        if (!fieldName.equals("sum_fee") && !fieldName.equals("new_bus_fee") && !fieldName.equals("last_tow_mon_sum_fee") && !fieldName.equals("last_mon_sum_fee")) {
                                                            if (fieldName.equals("home_mzone_area")) {
                                                                // TODO: 2020/2/28 SQLBuilder
//                                                                sqlstr.append(SQLBuilder.getDataPrivSql(allValue, "a.home_county"));
                                                            } else if (fieldName.equals("bal_integral")) {
                                                                if (temp[0].equals("-999999")) {
                                                                    sqlstr.append(" and a." + fieldName + " < " + temp[1] + "");
                                                                } else if (temp[1].equals("-999999")) {
                                                                    sqlstr.append(" and a." + fieldName + " >= " + temp[0] + "");
                                                                } else {
                                                                    sqlstr.append(" and a." + fieldName + " >= " + temp[0] + "");
                                                                    sqlstr.append(" and a." + fieldName + " < " + temp[1] + "");
                                                                }
                                                            } else if (fieldName.equals("accept_date")) {
                                                                if (temp[0].equals("-999999")) {
                                                                    sqlstr.append(" and a.sum_date <= " + temp[1] + "");
                                                                    sqlstr.append(" and x.sum_date <= " + temp[1] + "");
                                                                } else if (temp[1].equals("-999999")) {
                                                                    sqlstr.append(" and a.sum_date >= " + temp[0] + "");
                                                                    sqlstr.append(" and x.sum_date >= " + temp[0] + "");
                                                                } else {
                                                                    sqlstr.append(" and a.sum_date >= " + temp[0] + "");
                                                                    sqlstr.append(" and x.sum_date >= " + temp[0] + "");
                                                                    sqlstr.append(" and a.sum_date <= " + temp[1] + "");
                                                                    sqlstr.append(" and x.sum_date <= " + temp[1] + "");
                                                                }
                                                            } else if (!fieldName.equals("home_town") && !fieldName.equals("max_town") && !fieldName.equals("submax_town") && !fieldName.equals("last_mon_max_town")) {
                                                                if (!fieldName.equals("netage_integra") && !fieldName.equals("consume_integra") && !fieldName.equals("datasvc_integra") && !fieldName.equals("credit_integral")) {
                                                                    if (fieldName.equals("accept_item_type")) {
                                                                        if (allValue.contains(",")) {
                                                                            if (allValue.split(",").length == 4) {
                                                                                sqlstr.append(" and a." + fieldName + " not in(" + allValue + ")");
                                                                            } else {
                                                                                sqlstr.append(" and a." + fieldName + " in(" + allValue + ")");
                                                                            }
                                                                        } else {
                                                                            sqlstr.append(" and a." + fieldName + " = " + allValue + "");
                                                                        }
                                                                    } else if (fieldName.equals("vip_type")) {
                                                                        if (value.equals("9999")) {
                                                                            sqlstr.append(" and a." + fieldName + " = " + 0 + "");
                                                                        } else {
                                                                            sqlstr.append(" and a." + fieldName + " = " + value + "");
                                                                        }
                                                                    } else {
                                                                        String brandIdStr;
                                                                        if (fieldName.equals("opr_source")) {
                                                                            brandIdStr = "";

                                                                            for (int y = 0; y < temp.length; ++y) {
                                                                                if (y != temp.length - 1) {
                                                                                    brandIdStr = brandIdStr + "'" + temp[y] + "',";
                                                                                } else {
                                                                                    brandIdStr = brandIdStr + "'" + temp[y] + "'";
                                                                                }
                                                                            }

                                                                            sqlstr.append(" and trim(a." + fieldName + ") in (" + brandIdStr + ")");
                                                                        } else if (fieldName.equals("music_brand_id")) {
                                                                            brandIdStr = "";
                                                                            if (allValue.indexOf("神州行") >= 0) {
                                                                                allValue = allValue.replace("神州行", "新神州行,神州行");
                                                                            }

                                                                            if (allValue.indexOf("全球通") >= 0) {
                                                                                allValue = allValue.replace("全球通", "全球通,全球通（升级版）");
                                                                            }

                                                                            String[] _tempStr = allValue.split(",");

                                                                            for (int y = 0; y < _tempStr.length; ++y) {
                                                                                if (y != _tempStr.length - 1) {
                                                                                    brandIdStr = brandIdStr + "'" + _tempStr[y] + "',";
                                                                                } else {
                                                                                    brandIdStr = brandIdStr + "'" + _tempStr[y] + "'";
                                                                                }
                                                                            }

                                                                            sqlstr.append(" and trim(a.brand_id) in (" + brandIdStr + ")");
                                                                        } else {
                                                                            sqlstr.append(" and a." + fieldName + "=" + value);
                                                                        }
                                                                    }
                                                                } else {
                                                                    sqlstr.append(" and a." + fieldName + ">" + 0);
                                                                }
                                                            } else if (fieldName.equals("home_town")) {
                                                                // TODO: 2020/2/28  SQLBuilder
//                                                                sqlstr.append(SQLBuilder.getDataPrivSql(allValue, "a.home_town", "home_town"));
                                                            } else if (!value.equals("0") && !value.equals("590")) {
                                                                if (!temp[0].equals("1") && !temp[0].equals("0")) {
                                                                    if (temp[0].equals("2")) {
                                                                        sqlstr.append(" and a." + fieldName + " in (select call_town from " + "bishow." + "dim_home_town where call_town_group_code = " + value + ")");
                                                                    } else if (temp[0].equals("3")) {
                                                                        sqlstr.append(" and a." + fieldName + " in (select home_town from " + "bishow." + "cfg_region_town_distribute where sum_month=" + DateUtil.getLastMonth(DateUtil.getCurrDate("yyyyMM")) + " where region_id = " + value + ")");
                                                                    } else if (temp[0].equals("4")) {
                                                                        sqlstr.append(" and a." + fieldName + " = " + value + "");
                                                                    }
                                                                } else {
                                                                    sqlstr.append(" and a." + fieldName + " in (select call_town from " + "bishow." + "dim_home_town where call_town_group1_code = " + value + ")");
                                                                }
                                                            }
                                                        } else if (temp[0].equals("-999999") && !temp[1].equals("-999999")) {
                                                            sqlstr.append(" and a." + fieldName + " <= " + temp[1] + "");
                                                        } else if (temp[1].equals("-999999") && !temp[0].equals("-999999")) {
                                                            sqlstr.append(" and a." + fieldName + " >= " + temp[0] + "");
                                                        } else if (!temp[0].equals("-999999") && !temp[1].equals("-999999")) {
                                                            sqlstr.append(" and a." + fieldName + " >= " + temp[0] + "");
                                                            sqlstr.append(" and a." + fieldName + " <= " + temp[1] + "");
                                                        }
                                                    } else if (!temp[0].equals("1") && !temp[0].equals("0")) {
                                                        if (temp[0].equals("2")) {
                                                            sqlstr.append(" and a." + fieldName + " = " + value + "");
                                                        }
                                                    } else {
                                                        sqlstr.append(" and a." + fieldName + " in (select channel_type_id from " + "bishow." + "Dim_Channel_Type where channel_type_group_id = " + value + ")");
                                                    }
                                                } else {
                                                    String[] channeltemp;
                                                    if (!imeiType.equals("0") && !imeiType.equals("11")) {
                                                        if (imeiType.equals("1")) {
                                                            if (temp[0].equals("3")) {
                                                                sqlstr.append(" and a." + fieldName + " = " + value + "");
                                                            }
                                                        } else if (imeiType.equals("3")) {
                                                            if (!temp[0].equals("1") && !temp[0].equals("0")) {
                                                                if (temp[0].equals("2")) {
                                                                    sqlstr.append(" and a.org_id =" + value + "");
                                                                }
                                                            } else {
                                                                sqlstr.append(" and a." + fieldName + " = " + value + "");
                                                            }
                                                        } else if (imeiType.equals("4")) {
                                                            if (!temp[0].equals("1") && !temp[0].equals("0")) {
                                                                if (temp[0].equals("2")) {
                                                                    sqlstr.append(" and a.channel_name_id in (select channel_type_id from bishow.Dim_Channel_Type where home_area_code = " + value + ")");
                                                                } else if (temp[0].equals("3")) {
                                                                    if (value.toString().contains("0x12")) {
                                                                        channeltemp = value.toString().split("0x12");
                                                                        sqlstr.append(" and a.channel_name_id in (select channel_type_id from bishow.Dim_Channel_Type where home_area_code = " + channeltemp[1] + " and channel_type_group_id = " + channeltemp[0] + ")");
                                                                    }
                                                                } else if (temp[0].equals("4")) {
                                                                    sqlstr.append(" and a.channel_name_id =" + value + "");
                                                                }
                                                            } else {
                                                                sqlstr.append(" and a.channel_name_id in (select channel_type_id from bishow.Dim_Channel_Type where home_area_group_code = " + value + ")");
                                                            }
                                                        } else if (imeiType.equals("5")) {
                                                            if (!temp[0].equals("1") && !temp[0].equals("0")) {
                                                                if (temp[0].equals("2")) {
                                                                    sqlstr.append(" and a.channel_id =" + value + "");
                                                                }
                                                            } else {
                                                                sqlstr.append(" and a." + fieldName + " = " + value + "");
                                                            }
                                                        }
                                                    } else if (!temp[0].equals("1") && !temp[0].equals("0")) {
                                                        if (temp[0].equals("2")) {
                                                            sqlstr.append(" and a." + fieldName + " in (select channel_type_id from " + "bishow." + "Dim_Channel_Type where home_area_code = " + value + ")");
                                                        } else if (temp[0].equals("3")) {
                                                            channeltemp = value.toString().split("0x12");
                                                            sqlstr.append(" and a." + fieldName + " in (select channel_type_id from " + "bishow." + "Dim_Channel_Type where home_area_code = " + channeltemp[1] + " and channel_type_group_id = " + channeltemp[0] + ")");
                                                        } else if (temp[0].equals("4")) {
                                                            sqlstr.append(" and a." + fieldName + " = " + value + "");
                                                        }
                                                    } else {
                                                        sqlstr.append(" and a." + fieldName + " in (select channel_type_id from " + "bishow." + "Dim_Channel_Type where home_area_group_code = " + value + ")");
                                                    }
                                                }
                                            } else if (!temp[0].equals("1") && !temp[0].equals("0")) {
                                                if (temp[0].equals("2")) {
                                                    sqlstr.append(" and a." + fieldName + " in (select user_brand from " + "bishow." + "Dim_Brand where pkg_type_group_id = " + value + ")");
                                                } else if (temp[0].equals("3")) {
                                                    sqlstr.append(" and a." + fieldName + " in (select user_brand from " + "bishow." + "Dim_Brand where pkg_type = " + value + ")");
                                                } else if (temp[0].equals("4")) {
                                                    sqlstr.append(" and a." + fieldName + " = " + value + "");
                                                }
                                            } else {
                                                sqlstr.append(" and a." + fieldName + " in (select user_brand from " + "bishow." + "Dim_Brand where brand_id = " + value + ")");
                                            }
                                        } else if (!temp[0].equals("1") && !temp[0].equals("0")) {
                                            if (temp[0].equals("2")) {
                                                sqlstr.append(" and a." + fieldName + " = " + value + "");
                                            }
                                        } else {
                                            sqlstr.append(" and a." + fieldName + " in (select create_lev_id from " + "bishow." + "dim_active_dur_lev where create_lev_group = '" + value + "')");
                                        }
                                    } else if (!value.equals("0") && !value.equals("590")) {
                                        if (!temp[0].equals("1") && !temp[0].equals("0")) {
                                            if (temp[0].equals("2")) {
                                                sqlstr.append(" and a." + fieldName + " in (select call_town from " + "bishow." + "dim_home_town where call_town_group_code = " + value + ")");
                                            } else if (temp[0].equals("3")) {
                                                sqlstr.append(" and a." + fieldName + " = " + value + "");
                                            }
                                        } else {
                                            sqlstr.append(" and a." + fieldName + " in (select call_town from " + "bishow." + "dim_home_town where call_town_group1_code = " + value + ")");
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        // TODO: 2020/2/28 SQLBuilder
//                        sqlstr.append(SQLBuilder.buildDimTreeWhere(value, "a.brand_id", "bishow.dim_brand", "brand_id,pkg_type_group_id,pkg_type,user_brand"));
                    }
                } else {
                    // TODO: 2020/2/28 SQLBuilder
//                    sqlstr.append(SQLBuilder.buildDimTreeWhere(value, "a.home_county", "bishow.dim_home_area", "home_area_group_code,home_area_code"));
                }
            }
        }

    }

    public static void setWhereCondition(Object beanObj, StringBuffer sqlstr, String[] strTableName, String flag) {
        Field[] allFields = beanObj.getClass().getDeclaredFields();

        for (int i = 1; i < allFields.length; ++i) {
            Object value = null;
            String fieldName = allFields[i].getName();

            try {
                value = PropertyUtils.getProperty(beanObj, fieldName);
            } catch (IllegalAccessException var9) {
                var9.printStackTrace();
            } catch (InvocationTargetException var10) {
                var10.printStackTrace();
            } catch (NoSuchMethodException var11) {
                var11.printStackTrace();
            }

            if (fieldName.equals("home_area_code") && (flag.equals("keep") || flag.equals("roamLdc") || flag.equals("conChg"))) {
//                sqlstr.append(SQLBuilder.buildDimTreeWhere(value, "cc.home_area_code", "bishow.dim_home_area", "home_area_group_code,home_area_code"));
            } else if (!fieldName.equals("brand_desc") || !flag.equals("keep") && !flag.equals("roamLdc") && !flag.equals("conChg")) {
                if (fieldName.equals("create_lev_id") && (flag.equals("keep") || flag.equals("roamLdc"))) {
//                    sqlstr.append(SQLBuilder.buildDimTreeWhere(value, "cc.online_dur_lev_id", "bishow.dim_active_dur_lev", "create_lev_group,create_lev_id"));
                } else if (fieldName.equals("status_id") && flag.equals("keep")) {
//                    sqlstr.append(SQLBuilder.buildDimTreeWhere(value, "cc.status_id", "bishow.dim_user_status", "status_id"));
                } else if (fieldName.equals("status_id") && flag.equals("roamLdc")) {
//                    sqlstr.append(SQLBuilder.buildDimTreeWhere(value, "cc.user_status", "bishow.dim_user_status", "status_id"));
                } else if (fieldName.equals("consume_id") && (flag.equals("keep") || flag.equals("roamLdc"))) {
//                    sqlstr.append(SQLBuilder.buildDimTreeWhere(value, "cc.consume_id", "bishow.dim_consume_lev", "consume_id"));
                } else if (fieldName.equals("bill_type_id") && flag.equals("keep")) {
//                    sqlstr.append(SQLBuilder.buildDimTreeWhere(value, "cc.bill_type_id", "bishow.dim_bill_type", "bill_type_id"));
                } else if (fieldName.equals("roam_bill_dur_id")) {
//                    sqlstr.append(SQLBuilder.buildDimTreeWhere(value, "cc.roam_bill_dur_lev_id", "bishow.dim_roam_bill_dur_lev", "roam_bill_dur_group1,roam_bill_dur_id"));
                } else if (fieldName.equals("ldc_bill_dur_id")) {
//                    sqlstr.append(SQLBuilder.buildDimTreeWhere(value, "cc.ldc_bill_dur_lev_id", "bishow.dim_ldc_bill_dur_lev", "ldc_bill_dur_group1,ldc_bill_dur_id"));
                } else if (fieldName.equals("voc_bill_dur_id")) {
//                    sqlstr.append(SQLBuilder.buildDimTreeWhere(value, "cc.bill_dur_lev_id", "bishow.dim_bill_dur_lev", "voc_bill_dur_group1,voc_bill_dur_id"));
                } else if (value != null && !value.equals("-9999") && !value.equals("-999999") && !value.equals("null") && !value.equals("") && !value.equals("9999")) {
                    if (value.toString().contains(",")) {
                        String[] temp = value.toString().split(",");
                        if (temp.length > 1) {
                            value = temp[1];
                            if (fieldName.equals("home_area_code")) {
                                if (!value.equals("0") && !value.equals("590")) {
//                                    sqlstr.append(SQLBuilder.getDataPrivSql(value, "cc." + fieldName + ""));
                                }
                            } else if (fieldName.equals("brand_desc") || fieldName.equals("brand_id")) {
                                if (!temp[0].equals("1") && !temp[0].equals("0")) {
                                    if (temp[0].equals("2")) {
                                        sqlstr.append(" and cc.user_brand in (select user_brand  from bishow.Dim_Brand where pkg_type_group_id  = " + value + ")");
                                    } else if (temp[0].equals("3")) {
                                        sqlstr.append(" and cc.user_brand in (select user_brand  from bishow.Dim_Brand where pkg_type  = " + value + ")");
                                    } else if (temp[0].equals("4")) {
                                        sqlstr.append(" and cc.user_brand = " + value + "");
                                    }
                                } else {
                                    sqlstr.append(" and cc.user_brand in (select user_brand from bishow.Dim_Brand where brand_id = " + value + ")");
                                }
                            }

                            if (fieldName.equals("create_lev_id")) {
                                if (!temp[0].equals("1") && !temp[0].equals("0")) {
                                    if (temp[0].equals("2")) {
                                        sqlstr.append(" and cc.online_dur_lev_id = " + value + "");
                                    }
                                } else {
                                    sqlstr.append(" and cc.online_dur_lev_id in (select create_lev_id from bishow.dim_active_dur_lev where create_lev_group = '" + value + "')");
                                }
                            }

                            if (fieldName.equals("roam_bill_dur_id")) {
                                if (!temp[0].equals("1") && !temp[0].equals("0")) {
                                    if (temp[0].equals("2")) {
                                        sqlstr.append(" and cc.roam_bill_dur_lev_id = " + value);
                                    }
                                } else {
                                    sqlstr.append(" and cc.roam_bill_dur_lev_id in (select roam_bill_dur_id  from bishow.dim_roam_bill_dur_lev where roam_bill_dur_group1 = '" + value + "')");
                                }
                            }

                            if (fieldName.equals("ldc_bill_dur_id")) {
                                if (!temp[0].equals("1") && !temp[0].equals("0")) {
                                    if (temp[0].equals("2")) {
                                        sqlstr.append(" and cc.ldc_bill_dur_lev_id = " + value);
                                    }
                                } else {
                                    sqlstr.append(" and cc.ldc_bill_dur_lev_id in (select ldc_bill_dur_id  from bishow.dim_ldc_bill_dur_lev where ldc_bill_dur_group1 = '" + value + "')");
                                }
                            }

                            if (fieldName.equals("voc_bill_dur_id")) {
                                if (!temp[0].equals("1") && !temp[0].equals("0")) {
                                    if (temp[0].equals("2")) {
                                        sqlstr.append(" and cc.bill_dur_lev_id = " + value);
                                    }
                                } else {
                                    sqlstr.append(" and cc.bill_dur_lev_id in (select voc_bill_dur_id  from bishow.dim_bill_dur_lev where voc_bill_dur_group1 = '" + value + "')");
                                }
                            }
                        }
                    } else {
                        String strValue = value.toString();
                        if (fieldName.equals("is_group") && !value.equals("9999")) {
                            sqlstr.append(" and cc.is_group_id = " + value + "");
                        } else if (fieldName.equals("consume_lev")) {
                            sqlstr.append(" and cc.three_consume_id = " + strValue);
                        } else if (fieldName.equals("twelve_consume_change_value")) {
                            sqlstr.append(" and cc.twelve_consume_change_id = " + strValue);
                        } else if (fieldName.equals("six_consume_change_value")) {
                            sqlstr.append(" and cc.six_consume_change_id = " + strValue);
                        } else if (fieldName.equals("three_consume_change_value")) {
                            sqlstr.append(" and cc.three_consume_change_id = " + strValue);
                        } else if (fieldName.equals("consume_range_flag")) {
                            sqlstr.append(" and cc.last_month_consume_change_id = " + strValue);
                        } else if (fieldName.equals("consume_change_range")) {
                            sqlstr.append(" and cc.last_month_con_chg_lev_id = " + strValue);
                        } else if (fieldName.equals("sum_month")) {
                            if (flag.equals("keep")) {
                                sqlstr.append(" and cc.sum_date = " + strValue);
                            } else {
                                sqlstr.append(" and cc.month_id = " + strValue);
                            }
                        }

                        if (fieldName.equals("status_id")) {
                            if (flag.equals("keep")) {
                                sqlstr.append(" and cc." + fieldName + " = " + strValue);
                            } else if (flag.equals("roamLdc")) {
                                sqlstr.append(" and cc.user_status = " + strValue);
                            }
                        }

                        if (fieldName.equals("is_top_lose_id")) {
                            if (flag.equals("keep")) {
                                sqlstr.append(" and cc." + fieldName + " = " + strValue);
                            } else if (flag.equals("roamLdc")) {
                                sqlstr.append(" and cc.is_top_loss = " + strValue);
                            }
                        }

                        if (fieldName.equals("consume_id")) {
                            sqlstr.append(" and cc." + fieldName + " = " + strValue);
                        }

                        if (fieldName.equals("bill_type_id")) {
                            sqlstr.append(" and cc." + fieldName + " = " + strValue);
                        }

                        if (fieldName.equals("fee_waring_id")) {
                            sqlstr.append(" and cc.less_50amt_months = " + strValue);
                        }
                    }
                }
            } else {
//                sqlstr.append(SQLBuilder.buildDimTreeWhere(value, "cc.user_brand", "bishow.dim_brand", "brand_id,pkg_type_group_id,pkg_type,user_brand"));
            }
        }

    }

    public static List setSvcList(List dataList, int row_count) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        List svcList = new Vector();
        List rowList = new Vector();
//        SvcContBean svc = new SvcContBean();
//        RowBean row = new RowBean();
//        return setSvcList(svcList, svc, rowList, row, dataList, row_count);
        return null;
    }

    public static List setSvcList(List svcList, Object svcObj, List rowList, Object rowObj, List dataList, int row_count) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        setBeanList(rowObj, dataList, row_count);
        rowList.add(rowObj);
        setBeanList(svcObj, rowList, row_count);
        svcList.add(svcObj);
        return svcList;
    }

    private static void setBeanList(Object beanObj, List valueList, int row_count) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Field[] allFields = beanObj.getClass().getDeclaredFields();

        for (int i = 1; i < allFields.length; ++i) {
            Object value = null;
            String fieldName = allFields[i].getName();
            value = PropertyUtils.getProperty(beanObj, fieldName);
            if (value instanceof Collection) {
                PropertyUtils.setProperty(beanObj, fieldName, valueList);
            } else {
                PropertyUtils.setProperty(beanObj, fieldName, row_count);
            }
        }

    }

    public static boolean equesObj(Object obj1, Object obj2) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        boolean flag = true;
        Field[] allFields = obj1.getClass().getDeclaredFields();
        Field[] allFields2 = obj2.getClass().getDeclaredFields();
        if (allFields.length != allFields2.length) {
            return false;
        } else {
            for (int i = 1; i < allFields.length; ++i) {
                Object value = null;
                Object value2 = null;
                String fieldName = allFields[i].getName();
                String fieldName2 = allFields2[i].getName();
                if (!fieldName.equals(fieldName2)) {
                    return false;
                }

                value = PropertyUtils.getProperty(obj1, fieldName);
                value2 = PropertyUtils.getProperty(obj2, fieldName);
                if (value != null && value2 != null) {
                    if (!(value instanceof Collection)) {
                        if (!value.toString().equals(value2.toString())) {
                            return false;
                        }
                    } else {
                        if (!(value2 instanceof Collection)) {
                            return false;
                        }

                        List tempList = (List) value;
                        List tempList2 = (List) value2;
                        if (tempList.size() != tempList2.size()) {
                            return false;
                        }

                        for (int j = 0; j < tempList.size(); ++j) {
//                            if (tempList.get(j) instanceof RowBean) {
//                                if (tempList2.get(j) instanceof RowBean) {
//                                    RowBean rowBean1 = (RowBean) tempList.get(j);
//                                    List rowBeanList1 = rowBean1.getRow();
//                                    RowBean rowBean2 = (RowBean) tempList2.get(j);
//                                    List rowBeanList2 = rowBean2.getRow();
//                                    if (rowBeanList1.size() != rowBeanList2.size()) {
//                                        return false;
//                                    }
//
//                                    for (int k = 0; k < rowBeanList1.size(); ++k) {
//                                        if (!equesObj(rowBeanList1.get(k), rowBeanList2.get(k))) {
//                                            return false;
//                                        }
//                                    }
//                                }
//                            } else {
//                                String str1 = tempList.get(j).toString();
//                                String str2 = tempList2.get(j).toString();
//                                if (!str1.equals(str2)) {
//                                    return false;
//                                }
//                            }
                        }
                    }
                }
            }

            return flag;
        }
    }

    public static List getGBTitleList(String titleStr) {
        Map tempMap = getTitleMap();
        List titleList = new Vector();
        String[] colData;
        int j;
        String colStr;
        if (titleStr.contains("0x7f")) {
            colData = titleStr.split("0x7f");

            for (j = 0; j < colData.length; ++j) {
                colStr = colData[j];
                if (colStr.contains("0x09")) {
                    colData = colStr.split("0x09");

                    for (int jj = 0; jj < colData.length; ++j) {
                        String data = colData[jj];
                        if (tempMap.get(data).toString() != null) {
                            titleList.add(tempMap.get(data).toString());
                        }
                    }
                } else if (tempMap.get(colStr).toString() != null) {
                    titleList.add(tempMap.get(colStr).toString());
                }
            }
        } else if (titleStr.contains("0x09")) {
            colData = titleStr.split("0x09");

            for (j = 0; j < colData.length; ++j) {
                colStr = colData[j];
                if (tempMap.get(colStr) != null) {
                    titleList.add(tempMap.get(colStr).toString());
                }
            }
        } else if (tempMap.get(titleStr).toString() != null) {
            titleList.add(tempMap.get(titleStr).toString());
        }

        return titleList;
    }

    public static List getGBTitleList(Object beanObj) {
        return getGBTitleList(beanObj, "");
    }

    public static List getGBTitleList(Object beanObj, String tabFlag) {
        Map tempMap = new HashMap();
        if (tabFlag.equals("")) {
            tempMap = getTitleMap();
        } else if (tabFlag.equals("1")) {
            tempMap = getMzoneTitleMap();
        } else if (tabFlag.equals("2")) {
            tempMap = getKusTitleMap();
        }

        List titleList = new Vector();
        Field[] allFields = beanObj.getClass().getDeclaredFields();

        for (int i = 1; i < allFields.length; ++i) {
            Object value = null;
            String fieldName = allFields[i].getName();

            try {
                value = PropertyUtils.getProperty(beanObj, fieldName);
            } catch (IllegalAccessException var9) {
                var9.printStackTrace();
            } catch (InvocationTargetException var10) {
                var10.printStackTrace();
            } catch (NoSuchMethodException var11) {
                var11.printStackTrace();
            }

            if (value != null && !value.equals("") && !value.equals("-999999")) {
                titleList.add(((Map) tempMap).get(fieldName).toString());
            }
        }

        return titleList;
    }

    public static Map getMzoneTitleMap() {
        Map tempMap = new HashMap();
        tempMap.put("user_id", "用户编码");
        tempMap.put("home_city", "归属地市");
        tempMap.put("home_county", "归属县市");
        tempMap.put("msisdn", "手机号码");
        tempMap.put("brand_id", "套餐编号");
        tempMap.put("user_brand_desc", "套餐名称");
        tempMap.put("user_status", "用户状态");
        tempMap.put("join_date", "入网时间");
        tempMap.put("cus_name", "客户姓名");
        tempMap.put("cus_tel", "联系电话");
        tempMap.put("ic_addr", "证件地址");
        tempMap.put("cus_addr", "通信地址");
        tempMap.put("model_desc", "终端厂商");
        tempMap.put("enum_value_desc", "终端型号");
        tempMap.put("confirm_times", "自开通M计划确认次数");
        tempMap.put("confirm_month_late", "最近一次确认M值的月份");
        tempMap.put("confirm_cur_flag", "本月是否已确认M值");
        tempMap.put("netage_integra", "网龄");
        tempMap.put("consume_integra", "通话费");
        tempMap.put("datasvc_integral", "数据业务体验");
        tempMap.put("credit_integral", "个性化奖励");
        tempMap.put("lm_bal_integral", "上月累积M值");
        tempMap.put("lm_chg_integral", "上月兑换M值");
        tempMap.put("bal_integral", "当月累积M值");
        tempMap.put("sum_integral", "当月新增M值");
        tempMap.put("chg_integral", "当月兑换M值");
        tempMap.put("sum_month", "统计月份");
        tempMap.put("user_id", "用户编码");
        tempMap.put("msisdn", "手机号码");
        tempMap.put("home_city", "归属地市");
        tempMap.put("home_county", "归属县市");
        tempMap.put("brand_id", "套餐编号");
        tempMap.put("user_brand_desc", "套餐名称");
        tempMap.put("join_date", "入网时间");
        tempMap.put("is_mplan", "是否开通M计划");
        tempMap.put("lm_mbalance_amt", "前月M值余额");
        tempMap.put("lm_sum_fee", "前月总消费金额");
        tempMap.put("lmlm_sum_fee", "两月前总消费金额");
        tempMap.put("lm_total_dur60", "前月总计费时长");
        tempMap.put("lmlm_total_dur60", "两月前总计费时长");
        tempMap.put("lmlm_base_dur60", "两月前基本通话计费时长");
        tempMap.put("lmlm_roma_dur60", "两月前漫游计时长");
        tempMap.put("lmlm_long_dur60", "两月前长途计费时长");
        tempMap.put("lmlm_sms_cnt", "两月前短信发送条数");
        tempMap.put("lmlm_gprs_flux", "两月前GPRS流量");
        tempMap.put("lmlm_mms_cnt", "两月前彩信条数");
        tempMap.put("grp_user_flag", "集团成员标志");
        tempMap.put("grp_manager_id", "客户经理工号");
        tempMap.put("grp_manager_name", "客户经理姓名");
        tempMap.put("vip_level_id", "卡类用户标志");
        tempMap.put("gen_type", "出帐类型");
        tempMap.put("is_prepay_limit", "是否参加预存限期消费");
        tempMap.put("prepay_balance_amt", "预存限期消费专款帐本余额");
        tempMap.put("prepay_expire_date", "预存限期消费失效时间");
        tempMap.put("sum_month", "统计时间");
        return tempMap;
    }

    public static Map getTitleMap() {
        Map tempMap = new HashMap();
        tempMap.put("msisdn", "手机号");
        tempMap.put("home_area_code", "局向");
        tempMap.put("brand_desc", "品牌");
        tempMap.put("user_brand", "套餐编号");
        tempMap.put("user_brand_desc", "套餐名称");
        tempMap.put("user_status", "用户状态");
        tempMap.put("join_date", "入网时间");
        tempMap.put("user_name", "姓名");
        tempMap.put("user_tel", "联系电话");
        tempMap.put("ic_addr", "证件地址");
        tempMap.put("user_addr", "通讯地址");
        tempMap.put("terminal_manufact", "终端厂商");
        tempMap.put("model_desc", "终端型号");
        tempMap.put("pre2_total_fee", "前月总消费金额");
        tempMap.put("pre_total_fee", "上月总消费金额");
        tempMap.put("sum_fee", "本月消费金额");
        tempMap.put("pre_sum_fee", "上月同期消费金额");
        tempMap.put("pre_pkg_fee", "上月月租及套餐金额");
        tempMap.put("pre_local_cfee", "上月基本通话费金额");
        tempMap.put("pre_roam_fee", "上月漫游金额");
        tempMap.put("pre_local_lfee", "上月长途金额");
        tempMap.put("pre_data_fee", "上月增值数据业务金额");
        tempMap.put("pre_sp_fee", "上月代收梦网信息费金额");
        tempMap.put("amt_less50_month", "连续消费金额大于120元月数");
        tempMap.put("pre2_total_call_dur60", "前月总计费时长");
        tempMap.put("pre_total_call_dur60", "上月总计费时长");
        tempMap.put("voc_call_dur60", "本月计费时长(截止清单抽取当日)");
        tempMap.put("pre_local_call_dur60", "上月基本通话计费时长");
        tempMap.put("pre_roam_call_dur60", "上月漫游计时长");
        tempMap.put("pre_ldc_call_dur60", "上月长途计费时长");
        tempMap.put("pre_sms_up_cnt", "上月短信发送条数");
        tempMap.put("pre_sum_data", "上月GPRS流量");
        tempMap.put("pre_mms_call_cnt", "上月彩信条数");
        tempMap.put("local_call_dur60", "本月基本通话计费时长");
        tempMap.put("roam_call_dur60", "本月漫游计费时长");
        tempMap.put("ldc_call_dur60", "本月长途计费时长");
        tempMap.put("sms_up_cnt", "本月短信发送条数");
        tempMap.put("data_flux", "本月GPRS流量");
        tempMap.put("mms_call_cnt", "本月彩信条数");
        tempMap.put("pre_voc_call_cnt", "上月语音通话次数");
        tempMap.put("voc_call_cnt", "本月语音通话次数");
        tempMap.put("divert_other_cnt", "本月呼转竞争对手次数");
        tempMap.put("last_term_type", "最近一次通话对象");
        tempMap.put("last_voc_call_dur60", "最近一次通话时长");
        tempMap.put("all_other_nbr_cnt", "通话方个数");
        tempMap.put("other_nbr_cnt", "网内通话方个数");
        tempMap.put("morn_call_dur60", "早间计费时长");
        tempMap.put("noon_call_dur60", "午后计费时长");
        tempMap.put("night_call_dur60", "晚间计费时长");
        tempMap.put("pay_channel", "缴费渠道");
        tempMap.put("voc_acall_dur60", "主叫计费时长");
        tempMap.put("member_flag", "集团成员标志");
        tempMap.put("mgr_id", "客户经理工号");
        tempMap.put("manager_name", "客户经理姓名");
        tempMap.put("card_level", "卡类用户标志");
        tempMap.put("gen_type", "出帐类型");
        tempMap.put("limit_flag", "是否参加预存限期消费");
        tempMap.put("balance_amt", "预存限期消费专款帐本余额");
        tempMap.put("expire_date", "预存限期消费失效时间");
        tempMap.put("sum_date", "时间");
        return tempMap;
    }

    public static Map getKusTitleMap() {
        Map tempMap = new HashMap();
        tempMap.put("msisdn", "手机号");
        tempMap.put("brand_desc", "品牌");
        tempMap.put("user_brand", "套餐编号");
        tempMap.put("user_brand_desc", "套餐名称");
        tempMap.put("user_status", "用户状态");
        tempMap.put("join_date", "入网时间");
        tempMap.put("user_name", "姓名");
        tempMap.put("user_tel", "联系电话");
        tempMap.put("ic_addr", "证件地址");
        tempMap.put("user_addr", "通讯地址");
        tempMap.put("terminal_manufact", "终端厂商");
        tempMap.put("model_desc", "终端型号");
        tempMap.put("pre2_total_fee", "前月总消费金额");
        tempMap.put("pre_total_fee", "上月总消费金额");
        tempMap.put("sum_fee", "本月消费金额");
        tempMap.put("pre_sum_fee", "上月同期消费金额");
        tempMap.put("pre_pkg_fee", "上月月租及套餐金额");
        tempMap.put("pre_local_cfee", "上月基本通话费金额");
        tempMap.put("pre_roam_fee", "上月漫游金额");
        tempMap.put("pre_local_lfee", "上月长途金额");
        tempMap.put("pre_data_fee", "上月增值数据业务金额");
        tempMap.put("pre_sp_fee", "上月代收梦网信息费金额");
        tempMap.put("less_50amt_months", "消费金额小于50元月数");
        tempMap.put("pre2_total_call_dur60", "前月总计费时长");
        tempMap.put("pre_total_call_dur60", "上月总计费时长");
        tempMap.put("pre_local_call_dur60", "上月基本通话计费时长");
        tempMap.put("pre_roam_call_dur60", "上月漫游计时长");
        tempMap.put("pre_ldc_call_dur60", "上月长途计费时长");
        tempMap.put("pre_sms_up_cnt", "上月短信发送条数");
        tempMap.put("pre_sum_data", "上月GPRS流量");
        tempMap.put("pre_mms_call_cnt", "上月彩信条数");
        tempMap.put("voc_call_dur60", "本月计费时长(截止清单抽取当日)");
        tempMap.put("roam_call_dur60", "本月漫游计费时长");
        tempMap.put("ldc_call_dur60", "本月长途计费时长");
        tempMap.put("sms_up_cnt", "本月短信发送条数");
        tempMap.put("voc_call_cnt", "本月语音通话次数");
        tempMap.put("sum_data", "本月GPRS流量");
        tempMap.put("call_cnt", "本月彩信条数");
        tempMap.put("pre_voc_call_cnt", "上月语音通话次数");
        tempMap.put("divert_other_cnt", "本月呼转竞争对手次数");
        tempMap.put("last_term_type", "最近一次通话对象");
        tempMap.put("last_voc_call_dur60", "最近一次通话时长");
        tempMap.put("voc_acall_dur60", "主叫计费时长");
        tempMap.put("other_nbr_cnt", "网内通话方个数");
        tempMap.put("morn_call_dur60", "早间计费时长");
        tempMap.put("noon_call_dur60", "午后计费时长");
        tempMap.put("night_call_dur60", "晚间计费时长");
        tempMap.put("twelve_consume_change_value", "消费金额异动系数（12个月）");
        tempMap.put("six_consume_change_value", "消费金额异动系数（6个月）");
        tempMap.put("three_consume_change_value", "消费金额异动系数（3个月）");
        tempMap.put("pay_channel", "缴费渠道");
        tempMap.put("all_other_nbr_cnt", "通话方个数");
        tempMap.put("member_flag", "集团成员标志");
        tempMap.put("mgr_id", "客户经理工号");
        tempMap.put("manager_name", "客户经理姓名");
        tempMap.put("card_level", "卡类用户标志");
        tempMap.put("gen_type", "出帐类型");
        tempMap.put("limit_flag", "是否参加预存限期消费");
        tempMap.put("balance_amt", "预存限期消费专款帐本余额");
        tempMap.put("expire_date", "预存限期消费失效时间");
        return tempMap;
    }

    public static Map getErrorMap() {
        Map errorMap = new HashMap();
        errorMap.put("1501001", "基站编码在不同的地市下存在，请更正后再导入！");
        errorMap.put("1501002", "乡镇编码在不同的县市下存在，请更正后再导入！");
        errorMap.put("1501003", "基站编码在不同的乡镇存在，请更正后再导入！");
        errorMap.put("1501004", "归属镇代码前三位需和归属县代码不一致，请更正后再导入！");
        errorMap.put("1501005", "归属镇名称对应不用的归属镇代码，请更正后再导入！");
        errorMap.put("1501006", "片区编码在不同的县市下存在，请更正后再导入！");
        errorMap.put("1501007", "一个乡镇在不同的片区下存在，请更正后再导入！");
        errorMap.put("1501008", "基站配置表中存在的乡镇编码必须在片区中也存在！，请更正后再导入！");
        return errorMap;
    }

    public static Map getAreaMap() {
        Map areaMap = new HashMap();
        areaMap.put("590", "省中心");
        areaMap.put("591", "福州");
        areaMap.put("592", "厦门");
        areaMap.put("593", "宁德");
        areaMap.put("594", "莆田");
        areaMap.put("595", "泉州");
        areaMap.put("596", "漳州");
        areaMap.put("597", "龙岩");
        areaMap.put("598", "三明");
        areaMap.put("599", "南平");
        return areaMap;
    }

    public static List getTitleList(String titleStr) {
        List titleList = new Vector();
        String[] titleStrArray = titleStr.split(",");

        for (int i = 0; i < titleStrArray.length; ++i) {
            titleList.add(titleStrArray[i]);
        }

        return titleList;
    }

    public static String checkIntValue(String str) {
        try {
            int intV = Integer.valueOf(str);
            str = String.valueOf(intV);
        } catch (Exception var2) {
            str = "-999999";
        }

        return str;
    }

    public static boolean equalsValue(String value, String equalValue) {
        return value != null && value.equals(equalValue);
    }

    public static boolean equalsNull(Object value) {
        return value == null || value.equals("") || value.equals("-999999") || value.equals("-9999");
    }

    public static boolean equalsNum(String numStr) {
        boolean flag = false;

        try {
            Long.parseLong(numStr);
            flag = true;
        } catch (Exception var3) {
            flag = false;
        }

        return flag;
    }

    public static boolean isMaxDataRights(String rightStr) {
        return rightStr == null || rightStr.equals("") || rightStr.equals("0") || rightStr.equals("590") || rightStr.indexOf("590") > 0;
    }
}
