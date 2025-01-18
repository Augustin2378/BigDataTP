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

        // Parcours des valeurs associées à la paire
        for (IntWritable value : values) {
            if (value.get() == 0) {
                directlyConnected = true; // Les utilisateurs sont directement connectés
            } else {
                commonRelationsCount += value.get(); // Comptage des relations communes
            }
        }

        // Si les utilisateurs ne sont pas directement connectés et qu'ils ont des relations en commun
        if (!directlyConnected && commonRelationsCount > 0) {
            result.set(commonRelationsCount);
            context.write(key, result);
        }
    }
}
