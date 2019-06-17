package de.beatyourtask.beatyourtask.controller;

import de.beatyourtask.beatyourtask.model.Project;
import de.beatyourtask.beatyourtask.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.ArrayList;

/**
 * Controller for handeling Ajax request when an sortable item was moved
 *
 */
@Controller
public class AjaxController {




    @Autowired
    private ProjectService projectService;
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
