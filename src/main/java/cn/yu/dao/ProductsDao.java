package cn.yu.dao;

import cn.yu.model.Product;
import cn.yu.model.ProductQuery;
import cn.yu.util.response.PageInfo;

/**
 * @author bin.yu
 * @create 2018-05-28 19:40
 **/
public interface ProductsDao {

    Integer insertProduct(Product productsDo);

    PageInfo queryProducts(ProductQuery queryDo);
}
