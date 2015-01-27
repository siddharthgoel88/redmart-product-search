package controllers;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import model.Inventory;

import com.fasterxml.jackson.databind.node.ObjectNode;

import play.data.DynamicForm;
import play.data.Form;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("RedMart Product Search"));
    }
    
    public static Result productSearch() {
    	DynamicForm searchForm = Form.form().bindFromRequest();
    	String productId = searchForm.get("productid");
    	System.out.println(searchForm.toString());
    	System.out.println("id =" + productId);
    	List<Inventory> matchingProducts = Inventory.findProducts(productId);
    	return ok(play.libs.Json.toJson(matchingProducts));
    }
    
    public static Result update() {
    	ObjectNode response = play.libs.Json.newObject();
       	DynamicForm updateForm = Form.form().bindFromRequest();
       	System.out.println(updateForm.toString());
    	String id = updateForm.get("id");
    	System.out.println("Id is " + id);
    	String title = updateForm.get("title");
		String price = updateForm.get("price");
		if(!verifyId(id)) {
			return badRequest("Invalid id");
		} else if (!verifyTitle(title)) {
			return badRequest("Invalid title");
		} else if (!verifyPrice(price)) {
			return badRequest("Invalid price");
		}
    		
		Inventory.updateDB(Integer.parseInt(id), title, Double.parseDouble(price));
		response.put("success", true);
		response.put("message", "DB updated successfully");
		return ok(response);
    }

	private static boolean verifyId(String id) {
		if(Inventory.onlyOneIdExists(id)) {
			return true;
		}
		return false;
	}

	private static boolean verifyTitle(String title) {
		if (title != null && StringUtils.isAlphanumericSpace(title)) {
			return true;
		}
		return false;
	}
	
	private static boolean verifyPrice(String price) {
		if (price != null) {
			   try {
		            Double.parseDouble(price);
		            return true;
		        } catch (NumberFormatException e) {
		            return false;
		        }
		}
		return false;
	}

}
