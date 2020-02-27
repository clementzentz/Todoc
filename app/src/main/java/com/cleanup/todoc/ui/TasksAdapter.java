package com.cleanup.todoc.ui;

import android.content.res.ColorStateList;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cleanup.todoc.R;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.model.TaskAndProject;
import com.cleanup.todoc.util.MainActivityToAdapter;

import java.util.List;

import butterknife.ButterKnife;

/**
 * <p>Adapter which handles the list of mTasks to display in the dedicated RecyclerView.</p>
 *
 * @author Gaëtan HERFRAY
 */
public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskViewHolder> {

    private static final String TAG = "TasksAdapter";

    private MainActivityToAdapter mMainActivityToAdapter;

    /**
     * The list of mTasks the adapter deals with
     */
    @NonNull
    private List<TaskAndProject> mTasks;

    /**
     * The listener for when a task needs to be deleted
     */
    @NonNull
    private final DeleteTaskListener mDeleteTaskListener;
    /**
     * Instantiates a new TasksAdapter.
     *
     * @param tasks the list of mTasks the adapter deals with to set
     */
    TasksAdapter(@NonNull final List<TaskAndProject> tasks,
                 @NonNull final DeleteTaskListener deleteTaskListener,
                 @NonNull MainActivityToAdapter mainActivityToAdapter) {
        this.mTasks = tasks;
        this.mDeleteTaskListener = deleteTaskListener;
        this.mMainActivityToAdapter = mainActivityToAdapter;
    }

    /**
     * Set the list of mTasks the adapter deals with.
     *
     * @param tasks the list of mTasks the adapter deals with to set
     */
    void setTasks(List<TaskAndProject> tasks) {
        mTasks = tasks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_task, viewGroup, false);
        return new TaskViewHolder(view, mDeleteTaskListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int position) {
        taskViewHolder.bind(mTasks.get(position));
        taskViewHolder.mView.setOnClickListener(v -> {
            mMainActivityToAdapter.launchInputTaskDialog(mTasks.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    /**
     * Listener for deleting mTasks
     */
    public interface DeleteTaskListener {
        /**
         * Called when a task needs to be deleted.
         *
         * @param task the task that needs to be deleted
         */
        void onDeleteTask(Task task);
    }

    /**
     * <p>ViewHolder for task items in the mTasks list</p>
     *
     * @author Gaëtan HERFRAY
     */
    class TaskViewHolder extends RecyclerView.ViewHolder {
        /**
         * The circle icon showing the color of the project
         */
        private final AppCompatImageView imgProject;

        /**
         * The TextView displaying the name of the task
         */
        private final TextView lblTaskName;

        /**
         * The TextView displaying the name of the project
         */
        private final TextView lblProjectName;

        /**
         * The delete icon
         */
        private final AppCompatImageView imgDelete;

        /**
         * The listener for when a task needs to be deleted
         */
        private final DeleteTaskListener deleteTaskListener;

        View mView;

        /**
         * Instantiates a new TaskViewHolder.
         *
         * @param itemView the view of the task item
         * @param deleteTaskListener the listener for when a task needs to be deleted to set
         */
        TaskViewHolder(@NonNull View itemView, @NonNull DeleteTaskListener deleteTaskListener) {
            super(itemView);

            this.deleteTaskListener = deleteTaskListener;

            imgProject = itemView.findViewById(R.id.img_project);
            lblTaskName = itemView.findViewById(R.id.lbl_task_name);
            lblProjectName = itemView.findViewById(R.id.lbl_project_name);
            imgDelete = itemView.findViewById(R.id.img_delete);

            ButterKnife.bind(this, itemView);
            mView = itemView;

            imgDelete.setOnClickListener(view -> {
                final Object tag = view.getTag();
                if (tag instanceof TaskAndProject) {
                    Log.d(TAG, "TaskViewHolder: task to delete "+((TaskAndProject) tag).getTask().getName());
                    TaskViewHolder.this.deleteTaskListener.onDeleteTask(((TaskAndProject) tag).getTask());
                }
            });
        }

        /**
         * Binds a task to the item view.
         *
         * @param taskAndProject the task to bind in the item view
         */
        void bind(TaskAndProject taskAndProject) {
            Project taskProject = taskAndProject.getProject();
            lblTaskName.setText(taskAndProject.getTask().getName());
            imgDelete.setTag(taskAndProject);

            if (taskProject != null) {
                imgProject.setSupportImageTintList(ColorStateList.valueOf(taskProject.getColor()));
                lblProjectName.setText(taskProject.getName());
            } else {
                imgProject.setVisibility(View.INVISIBLE);
                lblProjectName.setText("");
            }

        }
    }
}
