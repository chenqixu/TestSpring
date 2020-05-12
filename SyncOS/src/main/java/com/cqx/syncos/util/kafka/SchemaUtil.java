package com.cqx.syncos.util.kafka;

import com.cqx.syncos.task.bean.TaskInfo;
import org.apache.avro.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SchemaUtil
 *
 * @author chenqixu
 */
public class SchemaUtil {

    private static final Logger logger = LoggerFactory.getLogger(SchemaUtil.class);
    private String schemaStr;

    public SchemaUtil(String schemaStr) {
        this.schemaStr = schemaStr;
    }

    public SchemaUtil(TaskInfo taskInfo) {
        schemaStr = generateAvsc(taskInfo);
    }

    private String generateAvsc(TaskInfo taskInfo) {
        StringBuilder fiedls = new StringBuilder();
        String[] array = taskInfo.getSrc_fields().split(",", -1);
        for (int i = 0; i < array.length; i++) {
            String tmp = "{\"name\": \"%s\",  \"type\": [\"string\", \"null\"]}";
            fiedls.append(String.format(tmp, array[i]));
            if (i == array.length - 1) {
            } else {
                fiedls.append(",");
            }
        }
        String avsc = "{\"namespace\": \" com.newland\",\"type\": \"record\",\"name\": \"%s\",\"fields\":[%s]}";
        return String.format(avsc, taskInfo.getDst_name(), fiedls.toString());
    }

    public Schema getSchema() {
        return new Schema.Parser().parse(schemaStr);
    }

    public String getSchemaStr() {
        return schemaStr;
    }
}
