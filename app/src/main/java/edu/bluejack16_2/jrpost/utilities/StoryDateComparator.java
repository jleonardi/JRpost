package edu.bluejack16_2.jrpost.utilities;

import java.util.Comparator;

import edu.bluejack16_2.jrpost.models.Story;

/**
 * Created by User on 7/11/2017.
 */

public class StoryDateComparator implements Comparator<Story> {

    @Override
    public int compare(Story o1, Story o2) {
        return o2.getCreatedAt().compareTo(o1.getCreatedAt());
        //return compare(o1.getCreatedAt(), o2.getCreatedAt());
    }
}
