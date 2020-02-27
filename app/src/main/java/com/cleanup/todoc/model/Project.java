package com.cleanup.todoc.model;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

/**
 * <p>Models for project in which tasks are included.</p>
 *
 * @author Gaëtan HERFRAY
 */
@Entity(tableName = "project_table")
public class Project {

    /**
     * The unique identifier of the project
     */
    @PrimaryKey
    private long project_id;

    /**
     * The name of the project
     */
    @NonNull
    @ColumnInfo(name = "project_name")
    private final String name;

    /**
     * The hex (ARGB) code of the color associated to the project
     */
    @ColorInt
    @ColumnInfo(name = "color")
    private final int color;

    /**
     * Instantiates a new Project.
     *
     * @param project_id    the unique identifier of the project to set
     * @param name  the name of the project to set
     * @param color the hex (ARGB) code of the color associated to the project to set
     */
    public Project(long project_id, @NonNull String name, @ColorInt int color) {
        this.project_id = project_id;
        this.name = name;
        this.color = color;
    }

//    /**
//     * Returns all the projects of the application.
//     *
//     * @return all the projects of the application
//     */
//    @NonNull
//    public static Project[] getAllProjects() {
//        return new Project[]{
//                new Project(1L, "Projet Tartampion", 0xFFEADAD1),
//                new Project(2L, "Projet Lucidia", 0xFFB4CDBA),
//                new Project(3L, "Projet Circus", 0xFFA3CED2),
//        };
//    }

//    /**
//     * Returns the project with the given unique identifier, or null if no project with that
//     * identifier can be found.
//     *
//     * @param id the unique identifier of the project to return
//     * @return the project with the given unique identifier, or null if it has not been found
//     */
//    @Nullable
//    public static Project getTaskProjectId(long id) {
//        for (Project project : ) {
//            if (project.id == id)
//                return project;
//        }
//        return null;
//    }

    /**
     * Returns the unique identifier of the project.
     *
     * @return the unique identifier of the project
     */
    public long getProject_id() {
        return project_id;
    }


    /**
     * Returns the name of the project.
     *
     * @return the name of the project
     */
    @NonNull
    public String getName() {
        return name;
    }

    /**
     * Returns the hex (ARGB) code of the color associated to the project.
     *
     * @return the hex (ARGB) code of the color associated to the project
     */
    @ColorInt
    public int getColor() {
        return color;
    }

    @Override
    @NonNull
    public String toString() {
        return getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return project_id == project.project_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(project_id);
    }
}
