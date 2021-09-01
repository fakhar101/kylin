package org.apache.kylin.source.hudi;

import org.apache.kylin.common.KylinConfig;
import org.apache.kylin.engine.mr.IMRInput;
import org.apache.kylin.engine.spark.ISparkInput;
import org.apache.kylin.source.ISourceMetadataExplorer;
import org.apache.kylin.source.hive.HiveSource;


public class HudiSource extends HiveSource {
    public HudiSource(KylinConfig config) {
        super(config);
    }

    @Override
    public ISourceMetadataExplorer getSourceMetadataExplorer(){
        return super.getSourceMetadataExplorer();
    }

    @Override
    public <I> I adaptToBuildEngine(Class<I> engineInterface){
        if(engineInterface == IMRInput.class){
            return (I) new HudiMRInput();
        }
        else if(engineInterface == ISparkInput.class){
            return (I) new HudiSparkInput();
        }
        else{
            throw new RuntimeException("Cannot adapt to "+engineInterface);
        }
    }
}
