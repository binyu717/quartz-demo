package cn.yu.services;

import cn.yu.model.Product;
import cn.yu.model.ProductQuery;
import cn.yu.util.response.PageInfo;

/**
 * @author bin.yu
 * @create 2018-05-28 19:43
 **/
public interface ProductsService {

    Integer insertProduct(Product productsModel);

    PageInfo queryProducts(ProductQuery queryModel);
}
