package org.epf.hadoop.colfil1;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class RelationshipJob {

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: RelationshipJob <input path> <output path>");
            System.exit(-1);
        }

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Relationship Job");

        job.setJarByClass(RelationshipJob.class);
        job.setInputFormatClass(RelationshipInputFormat.class);

        job.setMapperClass(RelationshipMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setCombinerClass(RelationshipReducer.class);

        job.setReducerClass(RelationshipReducer.class);
        job.setNumReduceTasks(2);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
