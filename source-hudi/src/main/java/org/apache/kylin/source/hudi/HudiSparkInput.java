package org.apache.kylin.source.hudi;

import org.apache.kylin.engine.spark.ISparkInput;
import org.apache.kylin.job.execution.DefaultChainedExecutable;
import org.apache.kylin.metadata.model.IJoinedFlatTableDesc;
import org.apache.kylin.metadata.model.ISegment;

public class HudiSparkInput extends HudiInputBase implements ISparkInput {
    @Override
    public IBatchCubingInputSide getBatchCubingInputSide(IJoinedFlatTableDesc flatDesc) {
        return new HudiSparkBatchCubingInputSide(flatDesc);
    }

    @Override
    public IBatchMergeInputSide getBatchMergeInputSide(ISegment seg) {
        return new ISparkBatchMergeInputSide() {
            @Override
            public void addStepPhase1_MergeDictionary(DefaultChainedExecutable jobFlow) {
                // hudi sparkInputSide not used for dictionary
            }
        };
    }

    public static class HudiSparkBatchCubingInputSide extends HudiBaseBatchCubingInputSide implements ISparkBatchCubingInputSide{
        public HudiSparkBatchCubingInputSide(IJoinedFlatTableDesc flatTableDesc){
            super(flatTableDesc);
        }
    }
}
