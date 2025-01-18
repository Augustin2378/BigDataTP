package org.epf.hadoop.colfil3;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class RecommendationMapper extends Mapper<Object, Text, Text, Text> {

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString().trim();

        String[] parts = line.split("\\s+");
        if (parts.length < 2) return;

        String[] users = parts[0].split(",");
        if (users.length < 2) return;

        String userA = users[0];
        String userB = users[1];
        String commonFriendsCount = parts[1];

        context.write(new Text(userA), new Text(userB + ":" + commonFriendsCount));
        context.write(new Text(userB), new Text(userA + ":" + commonFriendsCount));
    }
}