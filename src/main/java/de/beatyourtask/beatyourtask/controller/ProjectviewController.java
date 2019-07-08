package de.beatyourtask.beatyourtask.controller;

import de.beatyourtask.beatyourtask.model.Project;
import de.beatyourtask.beatyourtask.model.Tasklist;
import de.beatyourtask.beatyourtask.services.MonsterService;
import de.beatyourtask.beatyourtask.services.ProjectService;
import de.beatyourtask.beatyourtask.services.TaskService;
import de.beatyourtask.beatyourtask.services.TasklistService;
import de.beatyourtask.beatyourtask.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import de.beatyourtask.beatyourtask.model.Tasklist;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import de.beatyourtask.beatyourtask.model.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the ProjectView
 * contains methods for controlling the Projectview
 */
@Controller

public class ProjectviewController {

    @Autowired
    private TasklistService tasklistService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    UserService userService;
    @Autowired
    private TaskService taskService;

    @Autowired
    MonsterService monsterService;
    @ModelAttribute(value = "newTaskListAttribute")
    public Tasklist newTasklist() {
        return new Tasklist();
    }

    @ModelAttribute(value = "newTaskAttribute")
    public Task newTAsk() {
        return new Task();
    }


    /**
     * Loads the Projectview with the responding tasklists in the database
     *
     * @param model model containing all the tasklist in the database
     * @return projectview with all tasklist in database
     */
    @GetMapping("/Project{ProjectId}")
    public ModelAndView loadListsID(Model model, @RequestParam("ProjectId") Integer id) {
        //for loading the lists in the rigth order
        Project currentProject = projectService.findById(id);
        List<Tasklist> unSortedTasklists = currentProject.getLists();
        List<Integer> order = currentProject.getOrders();
        List<Tasklist> sortedTasklists = new ArrayList<Tasklist>();
        List<Tasklist> listsToRemoveFromUnsorted = new ArrayList<Tasklist>();

        for (Integer currentId : order) {
            for (Tasklist currenTasklist : unSortedTasklists) {
                if (currenTasklist.getListId() == currentId) {
                    sortedTasklists.add(currenTasklist);
                    listsToRemoveFromUnsorted.add(currenTasklist);
                    break;
                }
            }
        }
        unSortedTasklists.removeAll(listsToRemoveFromUnsorted);

        System.out.println("unsorted Tasklist" + unSortedTasklists);
        System.out.println("Sorted Tsklist: " + sortedTasklists);

        //if some of list ids were not allready saved in the order these tasklist will be loaded at the end
        //e.g. when a list is added
        for (Tasklist currenTasklist : unSortedTasklists) {
            sortedTasklists.add(currenTasklist);
        }
        System.out.println("Sorted Tsklist: " + sortedTasklists);


        model.addAttribute("tasklists", sortedTasklists);
        // Stuff for the gamification like level, exp and the monster information
        model.addAttribute("Lvl", userService.getCurrentUser().getLvl());
        model.addAttribute("Exp", userService.getCurrentUser().getExp());
        model.addAttribute("Damage", userService.getCurrentUser().getDamage());
        model.addAttribute("Surname", userService.getCurrentUser().getSurname());
        model.addAttribute("Lastname", userService.getCurrentUser().getLastname());
        model.addAttribute("MonsterId", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getMonsterId());
        model.addAttribute("currentHp", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getCurrentLifePoints());
        model.addAttribute("maxHp", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getLifepoints());
        model.addAttribute("MonsterPic", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getMonsterPic());
        model.addAttribute("percentage", monsterService.findMonsterById(userService.getCurrentUser().getActiveMonsterId()).getPercentageLeft());
//-------------------------------------------------------------------------------------------------
        System.out.println(id);
        System.out.println("in /ProjectID");



        // a list containing the tasks for each tasklist, index in the list is the same as in sortedTaskList
        List<List<Task>> tasks = new ArrayList<List<Task>>();

        for (Tasklist list : sortedTasklists) {
            int listID = list.getListId();

            // Tasklist containing the sorted tasks for a list
            List<Task> sortedTasksForList = new ArrayList<>();
            List<Task> unsortedTasksForList = tasklistService.loadTasklistById(listID).getTasks();
            List<Integer> orderTasks = list.getOrderTasks();
            List<Task> listRemoveFromUnsortedTask = new ArrayList<Task>();

            for(Integer currentTaskID : orderTasks) {
                for (Task currentTask : unsortedTasksForList) {
                    System.out.println("Curren task id: "+currentTaskID);
                    System.out.println("Curren task id 2: "+currentTask.getTaskId());

                    if(currentTask.getTaskId() == currentTaskID) {
                        sortedTasksForList.add(currentTask);
                        listRemoveFromUnsortedTask.add(currentTask);
                    }
                }
            }


            //if some of list ids were not allready saved in the order these tasklist will be loaded at the end
            //e.g. when a list is added
            unsortedTasksForList.removeAll(listRemoveFromUnsortedTask);
            for (Task currentTask : unsortedTasksForList) {
                sortedTasksForList.add(currentTask);
            }

            tasks.add(sortedTasksForList);

            //tasks.add(tasklistService.loadTasklistById(listID).getTasks());
        }
        model.addAttribute("allTasks", tasks);

        String view = "Projectview";
        return new ModelAndView(view, "command", model);
    }


    /**
     * Saves a new list in the database
     *
     * @param newTaskListAttribute new Tasklist Object from View
     * @return reirekt to Projekt
     */
    @RequestMapping(value = "/addList", method = RequestMethod.POST)
    public String addList(@ModelAttribute("newTaskListAttribute") Tasklist newTaskListAttribute, @RequestHeader(value = "referer", required = false) final String referer) {

        System.out.println("in add List");
        String projectId = referer.substring(referer.indexOf("=") + 1, referer.length());
        System.out.println("ProjectId: " + projectId);
        tasklistService.saveList(newTaskListAttribute);
        System.out.println("saved tasklist");
        System.out.println("saved tasklist with id: " + newTaskListAttribute.getListId());


        try {
            System.out.println("In try");
            Integer projectIdInt = Integer.parseInt(projectId);

            projectService.findById(projectIdInt).addTasklist(tasklistService.loadTasklistById(newTaskListAttribute.getListId()));

            projectService.save(projectService.findById(projectIdInt));

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "redirect:/Project?ProjectId="+projectId;

    }

    /**
     * Edit a list in the database
     *
     * @param newTaskListAttribute new Tasklist Object from View
     * @return reirekt to Projekt
     */
    @RequestMapping(value = "/editList", method = RequestMethod.POST)
    public String editList(@ModelAttribute("newTaskListAttribute") Tasklist newTaskListAttribute, @RequestHeader(value = "referer", required = false) final String referer) {
        String projectId = referer.substring(referer.indexOf("=") + 1, referer.length());
        System.out.println("ProjectId: " + projectId);

        int id = newTaskListAttribute.getListId();
        System.out.println(id);
        System.out.println("in Edit");
        System.out.println(newTaskListAttribute.getColor());
        Tasklist list = tasklistService.loadTasklistById(id);
        String color = newTaskListAttribute.getColor().replaceFirst("background-color:", "");
        System.out.println(color);
        list.setColor(color);
        list.setListName(newTaskListAttribute.getListName());
        tasklistService.saveList(list);

        return "redirect:/Project?ProjectId=" + projectId;
    }

    /**
     * Deletes a list in the database
     *
     * @param newTaskListAttribute new Tasklist Object from View
     * @return reirekt to Projekt
     */
    @RequestMapping(value = "/deleteList", method = RequestMethod.POST)
    public String deleteList(@ModelAttribute("newTaskListAttribute") Tasklist newTaskListAttribute, @RequestHeader(value = "referer", required = false) final String referer) {
        String projectId = referer.substring(referer.indexOf("=") + 1, referer.length());
        System.out.println("ProjectId: " + projectId);

        int id = newTaskListAttribute.getListId();
        tasklistService.deleteTasklistById(id);

        return "redirect:/Project?ProjectId=" + projectId;
    }


    /**
     * Saves a new task in the database
     *
     * @param newTaskAttribute new task Object from View
     * @return redirekt to Projekt
     */
    @RequestMapping(value = "/addTask", method = RequestMethod.POST)
    public String addTask(@ModelAttribute("newTaskAttribute") Task newTaskAttribute, @ModelAttribute("newTaskListAttribute") Tasklist newTaskListAttribute, @RequestHeader(value = "referer", required = false) final String referer) {
        String projectId = referer.substring(referer.indexOf("=") + 1, referer.length());
        int listID = newTaskListAttribute.getListId();

        System.out.println("in add Task");
        System.out.println("ProjectId: " + projectId);
        System.out.println("Task created in ListID: " + listID);
        System.out.println("Taskname: " + newTaskAttribute.getTaskName());
        System.out.println("duedate: " + newTaskAttribute.getDate());

        // damit dateelemnt nur angezeiigt wird wenn datum ausgewählt
        String dateValue = newTaskAttribute.getDate();
        if (dateValue == "") {
            newTaskAttribute.setDate(null);
        }

        taskService.saveTask(newTaskAttribute);

        try {

            tasklistService.loadTasklistById(listID).addTasks(taskService.getTaskById(newTaskAttribute.getTaskId()));
            tasklistService.saveList(tasklistService.loadTasklistById(listID));

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return "redirect:/Project?ProjectId=" + projectId;
    }

    @RequestMapping(value = "/editTask", method = RequestMethod.POST)
    public String editTask(@ModelAttribute("newTaskAttribute") Task newTaskAttribute, @ModelAttribute("newTaskListAttribute") Tasklist newTaskListAttribute, @RequestHeader(value = "referer", required = false) final String referer) {
        String projectId = referer.substring(referer.indexOf("=") + 1, referer.length());

        int taskID = newTaskListAttribute.getListId();

        Task task = taskService.getTaskById(taskID);
        task.setTaskName(newTaskAttribute.getTaskName());
        task.setDate(newTaskAttribute.getDate());
        System.out.println("getDate: "+newTaskAttribute.getDate());

        // damit dateelemnt nur angezeiigt wird wenn datum ausgewählt
        String dateValue = newTaskAttribute.getDate();
        if (dateValue == "") {
            task.setDate(null);
        }

        taskService.saveTask(task);

        return "redirect:/Project?ProjectId=" + projectId;
    }
}
