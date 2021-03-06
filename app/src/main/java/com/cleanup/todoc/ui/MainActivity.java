package com.cleanup.todoc.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.cleanup.todoc.R;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.model.TaskAndProject;
import com.cleanup.todoc.util.MainActivityToAdapter;
import com.cleanup.todoc.viewModel.ProjectViewModel;
import com.cleanup.todoc.viewModel.TaskViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;

/**
 * <p>Home activity of the application which is displayed when the user opens the app.</p>
 * <p>Displays the list of mTasks.</p>
 *
 * @author Gaëtan HERFRAY
 */
public class MainActivity extends AppCompatActivity implements TasksAdapter.DeleteTaskListener, MainActivityToAdapter {

    private static final String TAG = "MainActivity";

    private TaskViewModel mTaskViewModel;
    private ProjectViewModel mProjectViewModel;

    /**
     * List of all projects available in the application
     */
    private final ArrayList<Project> allProjects = new ArrayList<>();

    /**
     * List of all current mTasks of the application
     */
    @NonNull
    private final ArrayList<TaskAndProject> mTasks = new ArrayList<>();

    /**
     * The adapter which handles the list of mTasks
     */
    private TasksAdapter adapter;

    /**
     * The sort method to be used to display mTasks
     */
    @NonNull
    private SortMethod sortMethod = SortMethod.NONE;

    /**
     * Dialog to create a new task
     */
    @Nullable
    public AlertDialog dialog = null;

    @Nullable
    public AlertDialog dialogManageTask = null;

    /**
     * EditText that allows user to set the name of a task
     */
    @Nullable
    private EditText dialogEditText = null;

    /**
     * Spinner that allows the user to associate a project to a task
     */
    @Nullable
    private Spinner dialogSpinner = null;

    /**
     * The RecyclerView which displays the list of mTasks
     */
    // Suppress warning is safe because variable is initialized in onCreate
    @SuppressWarnings("NullableProblems")
    @NonNull
    private RecyclerView recyclerViewTasks;

    /**
     * The TextView displaying the empty state
     */
    // Suppress warning is safe because variable is initialized in onCreate
    @SuppressWarnings("NullableProblems")
    @NonNull
    private TextView lblNoTasks;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTaskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        mProjectViewModel = new ViewModelProvider(this).get(ProjectViewModel.class);

        setContentView(R.layout.activity_main);

        recyclerViewTasks = findViewById(R.id.list_tasks);
        lblNoTasks = findViewById(R.id.lbl_no_task);

        recyclerViewTasks.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new TasksAdapter(mTasks, this, this);
        recyclerViewTasks.setAdapter(adapter);

        retrieveProjects();
        retrieveTasks();

        findViewById(R.id.fab_add_task).setOnClickListener(view -> showAddTaskDialog());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.filter_alphabetical) {
            sortMethod = SortMethod.ALPHABETICAL;
        } else if (id == R.id.filter_alphabetical_inverted) {
            sortMethod = SortMethod.ALPHABETICAL_INVERTED;
        } else if (id == R.id.filter_oldest_first) {
            sortMethod = SortMethod.OLD_FIRST;
        } else if (id == R.id.filter_recent_first) {
            sortMethod = SortMethod.RECENT_FIRST;
        }

        updateTasksAdapter();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDeleteTask(Task task) {
        mTaskViewModel.delete(task);
    }

    /**
     * Called when the user clicks on the positive button of the Create Task Dialog.
     *
     * @param dialogInterface the current displayed dialog
     */
    private void onPositiveButtonClick(DialogInterface dialogInterface) {
        // If dialog is open
        if (dialogEditText != null && dialogSpinner != null) {
            // Get the name of the task
            String taskName = dialogEditText.getText().toString();

            // Get the selected project to be associated to the task
            Project taskProject = null;
            if (dialogSpinner.getSelectedItem() instanceof Project) {
                taskProject = (Project) dialogSpinner.getSelectedItem();
            }

            // If a name has not been set
            if (taskName.trim().isEmpty()) {
                dialogEditText.setError(getString(R.string.empty_task_name));
            }
            // If both project and name of the task have been set
            else if (taskProject != null) {

                Task task = new Task(
                        taskProject.getProject_id(),
                        taskName,
                        new Date().getTime()
                );
                TaskAndProject taskAndProject = new TaskAndProject();

                taskAndProject.setTask(task);
                taskAndProject.setProject(taskProject);

                addTask(taskAndProject);

                dialogInterface.dismiss();
            }
            // If name has been set, but project has not been set (this should never occur)
            else{
                dialogInterface.dismiss();
            }
        }
        // If dialog is aloready closed
        else {
            dialogInterface.dismiss();
        }
    }

    /**
     * Shows the Dialog for adding a Task
     */
    private void showAddTaskDialog() {
        final AlertDialog dialog = getAddTaskDialog();

        dialog.show();

        dialogEditText = dialog.findViewById(R.id.txt_task_name);
        dialogSpinner = dialog.findViewById(R.id.project_spinner);

        populateDialogSpinner();
    }

    /**
     * Adds the given task to the list of created mTasks.
     *
     * @param taskAndProject the task to be added to the list
     */
    private void addTask(@NonNull TaskAndProject taskAndProject) {
        mTaskViewModel.insert(taskAndProject.getTask());
        lblNoTasks.setVisibility(View.GONE);
    }

    /**
     * Updates the list of mTasks in the UI
     */
    private void updateTasksAdapter() {
        if (mTasks.size() == 0) {
            lblNoTasks.setVisibility(View.VISIBLE);
            recyclerViewTasks.setVisibility(View.GONE);
        } else {
            lblNoTasks.setVisibility(View.GONE);
            recyclerViewTasks.setVisibility(View.VISIBLE);
            switch (sortMethod) {
                case ALPHABETICAL:
                    Collections.sort(mTasks, new Task.TaskAZComparator());
                    break;
                case ALPHABETICAL_INVERTED:
                    Collections.sort(mTasks, new Task.TaskZAComparator());
                    break;
                case RECENT_FIRST:
                    Collections.sort(mTasks, new Task.TaskRecentComparator());
                    break;
                case OLD_FIRST:
                    Collections.sort(mTasks, new Task.TaskOldComparator());
                    break;
            }
        }
        adapter.setTasks(mTasks);
    }

    /**
     * Returns the dialog allowing the user to create a new task.
     *
     * @return the dialog allowing the user to create a new task
     */
    @NonNull
    private AlertDialog getAddTaskDialog() {
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this, R.style.Dialog);

        alertBuilder.setTitle(R.string.add_task);
        alertBuilder.setView(R.layout.dialog_add_task);
        alertBuilder.setPositiveButton(R.string.add, null);
        alertBuilder.setOnDismissListener(dialogInterface -> {
            dialogEditText = null;
            dialogSpinner = null;
            dialog = null;
        });

        dialog = alertBuilder.create();

        // This instead of listener to positive button in order to avoid automatic dismiss
        dialog.setOnShowListener(dialogInterface -> {

            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(view -> onPositiveButtonClick(dialog));
        });

        return dialog;
    }

    /**
     * Sets the data of the Spinner with projects to associate to a new task
     */
    private void populateDialogSpinner() {
        final ArrayAdapter<Project> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, allProjects);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (dialogSpinner != null) {
            dialogSpinner.setAdapter(adapter);
        }
    }

    @Override
    public void launchInputTaskDialog(TaskAndProject taskAndProject) {
        final AlertDialog.Builder alertBuilderManageTask = new AlertDialog.Builder(this, R.style.Dialog);
        Task task = taskAndProject.getTask();
        alertBuilderManageTask.setTitle("Modifier la tâche : ");
        alertBuilderManageTask.setView(R.layout.dialog_add_task);
        alertBuilderManageTask.setPositiveButton("mettre à jours", (dialog, which) -> {
            task.setName(Objects.requireNonNull(dialogEditText).getText().toString());
            Project project = (Project) Objects.requireNonNull(dialogSpinner).getSelectedItem();
            task.setTaskProjectId(project.getProject_id());
            mTaskViewModel.update(task);
        });
        alertBuilderManageTask.setOnDismissListener(dialogInterface -> {
            dialogEditText = null;
            dialogSpinner = null;
            dialog = null;
        });

        dialogManageTask = alertBuilderManageTask.create();

        dialogManageTask.show();

        dialogEditText = dialogManageTask.findViewById(R.id.txt_task_name);
        dialogSpinner = dialogManageTask.findViewById(R.id.project_spinner);
        populateDialogSpinner();

        Objects.requireNonNull(dialogEditText).setText(task.getName());
        for (int index = 0; index < Objects.requireNonNull(dialogSpinner).getAdapter().getCount(); index++){
            if (dialogSpinner.getAdapter().getItem(index).equals(taskAndProject.getProject())){
                dialogSpinner.setSelection(index);
            }
        }
    }

    /**
     * List of all possible sort methods for task
     */
    private enum SortMethod {
        /**
         * Sort alphabetical by name
         */
        ALPHABETICAL,
        /**
         * Inverted sort alphabetical by name
         */
        ALPHABETICAL_INVERTED,
        /**
         * Lastly created first
         */
        RECENT_FIRST,
        /**
         * First created first
         */
        OLD_FIRST,
        /**
         * No sort
         */
        NONE
    }

    private void retrieveTasks(){
        mTaskViewModel.getAllTasks().observe(this, tasks -> {
            if (mTasks.size()>0){
                mTasks.clear();
            }
            mTasks.addAll(tasks);
            updateTasksAdapter();
        });
    }

    private void retrieveProjects(){
        mProjectViewModel.getAllProjects().observe(this, projects -> {
            if (allProjects.size()>0){
                allProjects.clear();
            }
            allProjects.addAll(projects);
            for (Project p :
                    allProjects) {
                Log.d(TAG, "retrieveProjects: projet "+p.getProject_id()+" = "+p.getName());
            }
        });
    }
}
