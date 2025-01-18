package org.epf.hadoop.colfil2;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class CommonRelationshipReducer extends Reducer<UserPair, IntWritable, UserPair, IntWritable> {

    private IntWritable result = new IntWritable();

    @Override
    protected void reduce(UserPair key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int commonRelationsCount = 0;
        boolean directlyConnected = false;

        for (IntWritable value : values) {
            if (value.get() == 0) {
                directlyConnected = true;
            } else {
                commonRelationsCount += value.get();
            }
        }

        if (!directlyConnected && commonRelationsCount > 0) {
            result.set(commonRelationsCount);
            context.write(key, result);
        }
    }
}
