package de.beatyourtask.beatyourtask.controller;

import de.beatyourtask.beatyourtask.model.Project;
import de.beatyourtask.beatyourtask.model.Tasklist;
import de.beatyourtask.beatyourtask.services.ProjectService;
import de.beatyourtask.beatyourtask.services.TaskService;
import de.beatyourtask.beatyourtask.services.TasklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;



/**
 * Controller for handeling Ajax request when an sortable item was moved
 *
 */
@Controller
public class AjaxController {




    @Autowired
    private ProjectService projectService;

    @Autowired
    private TasklistService tasklistService;

    @Autowired
    private TaskService taskService;

    /**
     *Class for updating the order of lists in a project when a list was moved
     * @param jsonOrder JsonString of the Ajax Post with the order of the listsids(jquerry ui sortable serialize)
     * @param referer url of the site that send the ajax post request
     * @return "Home" has no effect
     */
    @PostMapping("/ajaxMovedList")
    public String changeOrderOfList(@RequestParam("json") String jsonOrder, @RequestHeader(value = "referer", required = false) final String referer) {
        //Id des Projektes in dem eine Liste bewegt wurde
        String projectId;
        System.out.println("in changeOrderOfList");
        System.out.println("JsonString: "+jsonOrder);
        System.out.println("Referer: "+referer);

        projectId = referer.substring(referer.indexOf("=") + 1, referer.length());
        System.out.println("ProjectId: "+projectId);


        try {
            System.out.println("In try");
            Integer projectIdInt = Integer.parseInt(projectId);
            Project project = projectService.findById(projectIdInt);
            project.setOrders(getListOrderFromJsonString(jsonOrder));
            projectService.save(project);
            System.out.println("________" + projectService.findById(projectIdInt).getOrders());
        } catch (NumberFormatException e){
            e.printStackTrace();


        }

        return "Home";
    }


    /**
     * save the new order of the task
     * @param jsonOrder
     * @return
     */
    @PostMapping("/ajaxSaveTaskOrder")
    public  String  changeOrderOfTask(@RequestParam("json") String jsonOrder) {
        System.out.println("in ajaxSaveTaskOrder");
        String listID = jsonOrder.substring(10, jsonOrder.indexOf("&"));
        System.out.println("listID: "+listID);

        try {
            int listIDInt = Integer.parseInt(listID);
            Tasklist list = tasklistService.loadTasklistById(listIDInt);
            list.setOrderTasks(getTaskOrderFromJsonString(jsonOrder));
            tasklistService.saveList(list);
            System.out.println("Saved order of tasks: " + tasklistService.loadTasklistById(listIDInt).getOrderTasks());

        } catch(NumberFormatException e) {
            e.printStackTrace();
        }

        return "Home";
    }


    /**
     * when task is moved to a new list list
     * @param jsonOrder JsonString of the Ajax Post
     * @return
     */
    @PostMapping("/ajaxMovedTaskToNewList")
    public  String  moveTaskToList(@RequestParam("json") String jsonOrder) {
        System.out.println("in ajaxMovedTaskToNewList");
        System.out.println(jsonOrder);

        String listID = jsonOrder.substring(10, jsonOrder.indexOf("&"));
        System.out.println("listID: "+listID);

        jsonOrder = jsonOrder.replaceAll("\"", "");
        jsonOrder = jsonOrder.substring(jsonOrder.indexOf("&")+1, jsonOrder.length());

        String taskID = jsonOrder.replace("idtask_", "");
        System.out.println("taskID: "+taskID);

        try {
            int taskIDint = Integer.parseInt(taskID);
            int listIDint = Integer.parseInt(listID);

            tasklistService.loadTasklistById(listIDint).addTasks(taskService.getTaskById(taskIDint));
            tasklistService.saveList(tasklistService.loadTasklistById(listIDint));

            System.out.println("Saved tasks: " + tasklistService.loadTasklistById(listIDint).getOrderTasks());


        } catch(NumberFormatException e) {
            System.out.println(e);
        }


        return "Home";
    }

    /**
     * when task is removed from a list to another
     * @param jsonOrder JsonString of the Ajax Post
     * @return
     */
    @PostMapping ("/ajaxRemovedTaskFromList")
    public String removedTaskFromList (@RequestParam("json") String jsonOrder) {
        System.out.println("in ajaxRemovedTaskFromList");
        System.out.println(jsonOrder);

        String listID = jsonOrder.substring(10, jsonOrder.indexOf("&"));
        System.out.println("listID: "+listID);

        jsonOrder = jsonOrder.replaceAll("\"", "");
        jsonOrder = jsonOrder.substring(jsonOrder.indexOf("&")+1, jsonOrder.length());

        String taskID = jsonOrder.replace("idtask_", "");
        System.out.println("taskID: "+taskID);


        try {
            int taskIDint = Integer.parseInt(taskID);
            int listIDint = Integer.parseInt(listID);

            tasklistService.loadTasklistById(listIDint).removeTasks(taskService.getTaskById(taskIDint));
            tasklistService.saveList(tasklistService.loadTasklistById(listIDint));

            System.out.println("Saved tasks: " + tasklistService.loadTasklistById(listIDint).getOrderTasks());


        } catch(NumberFormatException e) {
            System.out.println(e);
        }

        return "Home";
    }




    /**
     * Makes an Arraylist containing the ordered taskIDs from the JsonString of the Ajax Post from the moved tasks
     * @param jsonOrder JsonString of the Ajax Post
     * @return Arraylist containing the ordered tasksIDs from a List
     */
    private ArrayList<Integer> getTaskOrderFromJsonString(String jsonOrder) {
        ArrayList<Integer> order = new ArrayList<>();

        jsonOrder = jsonOrder.substring(jsonOrder.indexOf("&")+1, jsonOrder.length());
        jsonOrder = jsonOrder.replaceAll("\"", "");
        System.out.println("Removed "+ jsonOrder);

        while(jsonOrder.indexOf("=") >= 0) {
            int taskIDInt;
            String taskIDString;

            jsonOrder = jsonOrder.substring(jsonOrder.indexOf("=") + 1, jsonOrder.length());

            if(jsonOrder.indexOf("&") >= 0){
                taskIDString =  jsonOrder.substring(0, jsonOrder.indexOf("&"));
            } else {
                taskIDString = jsonOrder;
            }
            System.out.println("taskID: "+taskIDString);

            try {
                taskIDInt = Integer.parseInt(taskIDString);
                order.add(taskIDInt);
            }
            catch (NumberFormatException e) {
                System.out.println(e);
                //TODO Fehlerbehandlung: dann die alte Reihenfolge beibeghalten?!
            }
        }

        System.out.println("Complete Array: "+order);
        return order;
    }

    /**
     * Makes an Arraylist containing the ordered ListIds from the JsonString of the Ajax Post
     * @param jsonOrder JsonString of the Ajax Post
     * @return Arraylist containing the ordered ListIds from a proect
     */
    private ArrayList<Integer> getListOrderFromJsonString(String jsonOrder) {
        ArrayList<Integer> order = new ArrayList<>();

        jsonOrder = jsonOrder.replaceAll("\"", "");

        System.out.println("Remove \": "+jsonOrder);

        while(jsonOrder.indexOf("=") >= 0) {
            String listIDString;
            int listIDInt;

            jsonOrder = jsonOrder.substring(jsonOrder.indexOf("=") + 1, jsonOrder.length());

            if(jsonOrder.indexOf("&") >= 0){
                listIDString =  jsonOrder.substring(0, jsonOrder.indexOf("&"));
            } else {
                listIDString = jsonOrder;
            }
            System.out.println("ListID: "+listIDString);

            try {
                listIDInt = Integer.parseInt(listIDString);
                order.add(listIDInt);
            }
            catch (NumberFormatException e) {
                System.out.println(e);
                //TODO Fehlerbehandlung: dann die alte Reihenfolge beibeghalten?!
            }
        }

        System.out.println("Complete Array: "+order);
        return order;
    }


}
