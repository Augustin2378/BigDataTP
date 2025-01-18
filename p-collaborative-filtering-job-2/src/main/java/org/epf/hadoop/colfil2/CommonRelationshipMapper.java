package org.epf.hadoop.colfil2;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class CommonRelationshipMapper extends Mapper<LongWritable, Text, UserPair, IntWritable> {
    private final static IntWritable ZERO = new IntWritable(0);
    private final static IntWritable ONE = new IntWritable(1);

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] parts = value.toString().split("\t");
        String user = parts[0];
        String[] relations = parts[1].split(",");

        for (int i = 0; i < relations.length; i++) {
            for (int j = i + 1; j < relations.length; j++) {
                UserPair pair = new UserPair(relations[i], relations[j]);
                context.write(pair, ONE);
            }
            UserPair directPair = new UserPair(user, relations[i]);
            context.write(directPair, ZERO);
        }
    }
}
