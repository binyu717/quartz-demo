package cn.yu.dao.impl;

import cn.yu.dao.ProductsDao;
import cn.yu.dao.mapper.ProductsMapper;
import cn.yu.model.Product;
import cn.yu.model.ProductQuery;
import cn.yu.util.response.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author bin.yu
 * @create 2018-05-28 19:41
 **/
@Component
public class ProductsDaoImpl implements ProductsDao {

    @Autowired
    private ProductsMapper productsMapper;

    @Override
    public Integer insertProduct(Product productsDo) {
        return productsMapper.insertProduct(productsDo);
    }

    @Override
    public PageInfo queryProducts(ProductQuery queryDo) {
        PageInfo pageInfo = new PageInfo();
        List<Product> products = productsMapper.queryProducts(queryDo);
        Long total = productsMapper.countProducts(queryDo);
        pageInfo.setList(products);
        pageInfo.setPageNo(queryDo.getPageNo());
        pageInfo.setPageSize(queryDo.getPageSize());
        pageInfo.setTotalNum(total);
        return pageInfo;
    }
}
