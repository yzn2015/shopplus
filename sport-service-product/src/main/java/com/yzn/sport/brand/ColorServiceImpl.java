package com.yzn.sport.brand;

import com.yzn.sport.color.ColorService;
import com.yzn.sport.mapper.ColorMapper;
import com.yzn.sport.pojo.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: YangZaining
 * @Date: Created in 18:41$ 2018/8/5$
 */
@Service("colorService")
public class ColorServiceImpl implements ColorService {
    @Autowired
    private ColorMapper colorMapper;

    public List<Color> selectAllColor() {

        return colorMapper.selectAllColor();
    }
}
