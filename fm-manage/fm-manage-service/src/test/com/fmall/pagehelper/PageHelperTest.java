package com.fmall.pagehelper;

import com.fmall.mapper.TbItemMapper;
import com.fmall.pojo.TbItem;
import com.fmall.pojo.TbItemExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by jack.zhang
 * email:57525101@qq.com
 * 2017/4/28 20:10
 */

public class PageHelperTest {
    @Test
    public void testPageHelper() throws Exception {
        //初始化spring容器
     /*   ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
        TbItemMapper itemMapper = applicationContext.getBean(TbItemMapper.class);

        PageHelper.startPage(1,10);
        TbItemExample example = new TbItemExample();
        List<TbItem> tbItemList = itemMapper.selectByExample(example);

        PageInfo<TbItem> pageInfo = new PageInfo<>(tbItemList);
        System.out.println(pageInfo.getTotal());
        System.out.println(pageInfo.getPages());
        System.out.println(tbItemList.size());*/
    }
}
