package todolistgui;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

/**
 * The Item class defines the attributes and methods of an item in the ToDoList.
 * 
 * @author William Hess
 */
public class Item implements Comparable, Serializable{
    private String label;
    private Date deadline;
    private String status = "";
    private Date progressDate;
    

    public Item(String label, Date deadline) {
        this.label = label;
        this.deadline = deadline;
    }

    public String getLabel() {
        return label;
    }

    public Date getDeadline() {
        return deadline;
    }

    public String getStatus() {
        return status;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
    
    /**
     * The setProgress() method sets the progress of an item to started or
     * completed.
     * @param status //default is "", status can be "started" or "completed"
     * @param progressDate // default is null, progressDate is necessary when 
     *                     // "started" and reset to null when "completed"
     */
    
    public void setProgress(String status, Date progressDate) {
        this.status = status;
        this.progressDate = progressDate;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.label);
        return hash;
    }
    /**
     * The equals() method is overridden such that items with the same label are
     * considered to be equivalent.  This allows the contains() method for 
     * ArrayLists to be utilized when assessing whether or not an item already 
     * exists in the To-Do list with the same label.
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Item other = (Item) obj;
        if (!Objects.equals(this.label, other.label)) {
            return false;
        }
        return true;
    }

    private String parseDate(Date date) {
        // java.sql.Date is in the format of yyyy-mm-dd
        // split the string representation of the date up into [year, month, day]
        String[] dateItems = date.toString().split("-");
        // rearrange and return the desired string i.e. "mm/dd/yyyy"
        return String.format("%s/%s/%s", dateItems[1], dateItems[2], dateItems[0]);
    }

    @Override
    public String toString() {
        String strDeadline = parseDate(deadline);
        switch (status) {
            case "started":
                String strProgressDate = parseDate(progressDate);
                return String.format("%s\n -%s (started %s)", label, strDeadline, strProgressDate);
            case "completed":
                return String.format("%s\n -%s (completed)", label, strDeadline);
        }
        return String.format("%s\n -%s", label, strDeadline);
    }

    /**
     * The compareTo() method is overridden such that when calling Collections.sort()
     * on the To-Do list, items are ordered chronologically by their deadline.
     * Items having the same deadline are ordered alphabetically.
     * @param t
     * @return 
     */
    
    @Override
    public int compareTo(Object t) {
        if (deadline.after(((Item) t).getDeadline())) {
            return 1;
        } else if (deadline.before(((Item) t).getDeadline())) {
            return -1;
        } else {
            // Should the deadline dates be the same, display in alphabetical order
            // This is done by invoking the compareTo method for a String on the 
            // items' labels. Since uppercase letters will come before lowercase
            // letters, the compareToIgnoreCase() method is called instead.
            return label.compareToIgnoreCase(((Item) t).getLabel());
        }
    }

}
