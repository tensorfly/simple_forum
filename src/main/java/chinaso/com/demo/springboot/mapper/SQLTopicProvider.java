package chinaso.com.demo.springboot.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * @author fangqian
 * @date 2018/11/26 14:24
 */
public class SQLTopicProvider {

    public String deleteTopics(Map map) {
        List<String> topicIds =  (List<String>)map.get("topicIds");
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM topic WHERE topicId IN (");
        for (int i = 0; i < topicIds.size(); i++) {
            sb.append("'").append(topicIds.get(i)).append("'");
            if (i < topicIds.size() - 1)
                sb.append(",");
        }
        sb.append(")");
        System.out.println(sb.toString());
        return sb.toString();
    }

    public String batchUpdateState(Map map) {
        List<String> topicIds =  (List<String>)map.get("topicIds");
        String state = map.get("state").toString();
        StringBuilder sb = new StringBuilder();
        sb.append("update topic set state =" + state + " WHERE topicId IN (");
        for (int i = 0; i < topicIds.size(); i++) {
            sb.append("'").append(topicIds.get(i)).append("'");
            if (i < topicIds.size() - 1)
                sb.append(",");
        }
        sb.append(")");
        System.out.println(sb.toString());
        return sb.toString();
    }
}
