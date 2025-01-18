package org.epf.hadoop.colfil2;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class ColFilJob2 {
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: CommonRelationshipJob <input path> <output path>");
            System.exit(-1);
        }

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Common Relationships");

        job.setJarByClass(ColFilJob2.class);

        // Mapper configuration
        job.setMapperClass(CommonRelationshipMapper.class);
        job.setMapOutputKeyClass(UserPair.class);
        job.setMapOutputValueClass(IntWritable.class);

        // Partitioner configuration
        job.setPartitionerClass(UserPairPartitioner.class);

        // Reducer configuration
        job.setReducerClass(CommonRelationshipReducer.class);
        job.setNumReduceTasks(2); // Utiliser deux Reducers

        // Input and output configuration
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        // Output key/value classes
        job.setOutputKeyClass(UserPair.class);
        job.setOutputValueClass(IntWritable.class);

        // Run the job
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}