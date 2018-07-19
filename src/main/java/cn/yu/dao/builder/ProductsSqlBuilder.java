package cn.yu.dao.builder;

import cn.yu.model.Product;
import cn.yu.model.ProductQuery;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author bin.yu
 * @create 2018-05-28 19:07
 **/
public class ProductsSqlBuilder {

    public String insertProduct(Product Products) {
        return new SQL() {{
            INSERT_INTO("products");
            if (StringUtils.isNotEmpty(Products.getName())) {
                VALUES("name", "#{name}");
            }
            if (Products.getCatalog() != null) {
                VALUES("catalog", "#{catalog}");
            }
            if (StringUtils.isNotEmpty(Products.getCatalogName())) {
                VALUES("catalogName", "#{catalogName}");
            }
            if (Products.getPrice() != null) {
                VALUES("price", "#{price}");
            }
            if (Products.getNumber() != null) {
                VALUES("number", "#{number}");
            }
            if (StringUtils.isNotEmpty(Products.getDescription())) {
                VALUES("description", "#{description}");
            }
            if (StringUtils.isNotEmpty(Products.getPicture())) {
                VALUES("picture", "#{picture}");
            }
            VALUES("releaseTime", "#{releaseTime}");
        }}.toString();
    }

    public String queryProducts(ProductQuery queryDo) {
        String sql = new SQL() {{
            SELECT("*");
            FROM("products");
            if (queryDo.getId() != null) {
                WHERE("id = #{id}");
            }
            if (StringUtils.isNotEmpty(queryDo.getName())) {
                WHERE("name = #{name}");
            }
            if (queryDo.getCatalog() != null) {
                WHERE("catalog=#{catalog}");
            }
            if (StringUtils.isNotEmpty(queryDo.getCatalogName())) {
                WHERE("catalogName=#{catalogName}");
            }
            if (queryDo.getPrice() != null) {
                WHERE("price=#{price}");
            }
            if (queryDo.getNumber() != null) {
                WHERE("number=#{number}");
            }
            if (StringUtils.isNotEmpty(queryDo.getDescription())) {
                WHERE("description=#{description}");
            }
            if (StringUtils.isNotBlank(queryDo.getSortName())&&StringUtils.isNotBlank(queryDo.getSort())) {
                ORDER_BY(queryDo.getSortName()+" "+queryDo.getSort());

            }
        }}.toString();

        long start = (queryDo.getPageNo()-1)*queryDo.getPageSize();
        sql+=" limit "+start+","+queryDo.getPageSize();
        return sql;
    }

    public String countProducts(ProductQuery query) {
        return   new SQL() {{
            SELECT("COUNT(*)");
            FROM("products");
            if (query.getId() != null) {
                WHERE("id = #{id}");
            }
            if (StringUtils.isNotEmpty(query.getName())) {
                WHERE("name = #{name}");
            }
            if (query.getCatalog() != null) {
                WHERE("catalog=#{catalog}");
            }
            if (StringUtils.isNotEmpty(query.getCatalogName())) {
                WHERE("catalogName=#{catalogName}");
            }
            if (query.getPrice() != null) {
                WHERE("price=#{price}");
            }
            if (query.getNumber() != null) {
                WHERE("number=#{number}");
            }
            if (StringUtils.isNotEmpty(query.getDescription())) {
                WHERE("description=#{description}");
            }
        }}.toString();
    }
}
