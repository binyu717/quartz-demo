package cn.yu.dao.mapper;

import cn.yu.dao.builder.ProductsSqlBuilder;
import cn.yu.model.Product;
import cn.yu.model.ProductQuery;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * 产品mapper
 *
 * @author bin.yu
 * @create 2018-05-28 18:46
 **/
public interface ProductsMapper {

    @InsertProvider(type = ProductsSqlBuilder.class,method = "insertProduct")
    Integer insertProduct(Product productsDo);

    @SelectProvider(type = ProductsSqlBuilder.class,method = "queryProducts")
    List<Product> queryProducts(ProductQuery queryDo);

    @SelectProvider(type = ProductsSqlBuilder.class,method = "countProducts")
    Long countProducts(ProductQuery query);
}
