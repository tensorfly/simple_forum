package chinaso.com.demo.springboot.service.impl;

import chinaso.com.demo.springboot.Util.Common;
import chinaso.com.demo.springboot.entity.Section;
import chinaso.com.demo.springboot.mapper.SectionMapper;
import chinaso.com.demo.springboot.service.SectionService;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fangqian
 * @date 2018/11/15 10:32
 */
@Service
public class SectionServiceImpl implements SectionService {

    private static Logger logger = LoggerFactory.getLogger(SectionServiceImpl.class);

    @Autowired
    SectionMapper sectionMapper;

    @Autowired
    @Qualifier("commonUtil")
    private Common commonUtil;

    @Override
    public List<Section> getSections() {


        return sectionMapper.getSections();
    }

    @Override
    public String getSectionsJson() {

        try {
            List<Section> sections = sectionMapper.getSections();
            return JSON.toJSONString(commonUtil.result("0", "查询版块信息数据成功", sections));
        } catch (Exception e) {
            logger.error("查询apk信息数据失败", e);
            return JSON.toJSONString(commonUtil.result("200", "查询版块信息数据失败", null));
        }
    }

    @Override
    public String deleteBySectionId(int sectionId) {
        try {
            sectionMapper.deleteBySectionId(sectionId);
            return JSON.toJSONString(commonUtil.result("0", "删除成功", null));
        }catch (Exception e) {
            logger.error("删除失败", e);
            return JSON.toJSONString(commonUtil.result("200", "删除失败", null));
        }
    }
}
