package org.apache.kylin.source.hudi;

import org.apache.kylin.engine.mr.IMRInput;
import org.apache.kylin.job.execution.DefaultChainedExecutable;
import org.apache.kylin.metadata.model.IJoinedFlatTableDesc;
import org.apache.kylin.metadata.model.ISegment;
import org.apache.kylin.metadata.model.TableDesc;
import org.apache.kylin.source.hive.HiveMRInput;

public class HudiMRInput extends HudiInputBase implements IMRInput {
    @Override
    public IMRTableInputFormat getTableInputFormat(TableDesc table, String uuid) {
        return new HiveMRInput.HiveTableInputFormat(getTableNameForHCat(table,uuid));
    }

    @Override
    public IBatchCubingInputSide getBatchCubingInputSide(IJoinedFlatTableDesc flatDesc) {
        return new HudiInputBase.HudiBaseBatchCubingInputSide(flatDesc);
    }

    @Override
    public IBatchMergeInputSide getBatchMergeInputSide(ISegment seg) {
        return new IMRBatchMergeInputSide() {
            @Override
            public void addStepPhase1_MergeDictionary(DefaultChainedExecutable jobFlow) {
                // doing nothing
            }
        };
    }

    public static  class HudiMRBatchCubingInputSide extends HudiBaseBatchCubingInputSide implements IMRBatchCubingInputSide{
        public HudiMRBatchCubingInputSide(IJoinedFlatTableDesc flatTableDesc){super(flatTableDesc);}

        @Override
        public IMRTableInputFormat getFlatTableInputFormat(){
            return new HiveMRInput.HiveTableInputFormat(getIntermediateTableIdentity());
        }
    }

}
