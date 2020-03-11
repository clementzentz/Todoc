package com.cleanup.todoc.util;

import com.cleanup.todoc.model.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TestUtil {
    public static final Task TEST_TASK_1 = new Task(1L, "aaa", new Date().getTime());
    public static final Task TEST_TASK_2 = new Task(2L, "bbb", new Date().getTime());
    public static final Task TEST_TASK_3 = new Task(3L, "ccc", new Date().getTime());

    static final List<Task> TEST_TASKS_LIST = Collections.unmodifiableList(
            new ArrayList<Task>(){{
                add(TEST_TASK_1);
                add(TEST_TASK_2);
                add(TEST_TASK_3);
            }}
    );
}
