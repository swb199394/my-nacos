package com.wb.handler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;
import org.springframework.util.StringUtils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * @author ylx
 * @version 1.0
 * @date 2022/3/16 15:30
 */
@MappedJdbcTypes(JdbcType.VARCHAR)  //数据库类型
@MappedTypes({List.class})          //java数据类型
public class ListTypeHandler implements TypeHandler<List<String>> {

    @Override
    public void setParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType) throws SQLException {
        String value = dealListToOneStr(parameter);
        ps.setString(i , value);
    }

    /**
     * 集合拼接字符串
     * @param parameter
     * @return
     */
    private String dealListToOneStr(List<String> parameter){
        if(parameter == null || parameter.size() <=0) {
            return null;
        }
        StringBuilder res = new StringBuilder();
        for (int i = 0 ;i < parameter.size(); i++) {
            if(i == parameter.size()-1){
                res.append(parameter.get(i));
                return res.toString();
            }
            res.append(parameter.get(i)).append(",");
        }
        return null;
    }

    @Override
    public List<String> getResult(ResultSet rs, String columnName) throws SQLException {
        if(StringUtils.isEmpty(rs.getString(columnName))){
            return null;
        }
        return Arrays.asList(rs.getString(columnName).split(","));
    }

    @Override
    public List<String> getResult(ResultSet rs, int columnIndex) throws SQLException {
        return Arrays.asList(rs.getString(columnIndex).split(","));
    }

    @Override
    public List<String> getResult(CallableStatement cs, int columnIndex) throws SQLException{
        String value = cs.getString(columnIndex);
        return Arrays.asList(value.split(","));
    }
}
